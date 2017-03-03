package com.example.mjwolf.notsosimpledb;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends ListFragment //implements FragmentManager.OnBackStackChangedListener
{

    private static final String TAG ="ListFragment";

    private long activeID;
    boolean showTwoFragments;
    private OnFragmentInteractionListener mListener;
    private DBAdapter db;
    private List<Contact> contacts;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemFragment.
     */

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBAdapter(getActivity());
        db.open();

        contacts = Contact.getAll(db);

        db.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, contacts);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //position is the spot on the View. It does not correspond to the position in the db.
                //Rather, it corresponds to the position in the List<Contacts>
                //We need to map the position in the list to the position in the db

                long dbPosition = contacts.get(position).getId();
                Log.d(TAG,"dbPosition is " + Long.toString(dbPosition));

                db.open();
                Cursor c = db.getContact(dbPosition);

                if (c != null) {
                    Toast.makeText(getActivity(), "Email address: " + c.getString(c.getColumnIndex(db.KEY_EMAIL)), Toast.LENGTH_LONG).show();
                }
                db.close();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putLong("activeID", activeID);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void notifyAdapter(boolean atEnd, long databasePosition){
        // Whenever a change is made in the DB, this method must be called in order to reflect
        // the DB changes in the ListView. Since the ListView is built from the List<Contact>
        // we make the changes there and then have the Adapter let the View know of the changes.
        Log.d(TAG, "notifyAdapter");
        db.open();

        if (atEnd){
            //the extra parameter makes things more efficient if there a Contact was added
            contacts.add(Contact.cursorToContact(db.getContact(databasePosition),db));

        }else { //otherwise we need to search for the right one.
            for (int i = 0; i < contacts.size(); ++i) {
                if (contacts.get(i).getId() == databasePosition) {

                    Cursor c = db.getContact(databasePosition);

                    if (c.isAfterLast()) {//item has been removed from the DB. Is there a better way?
                        contacts.remove(i);
                    }
                    else {    //item is still in the DB, but changed.
                       // Log.d("ItemFragment", "Updating list<Contact> element");
                        contacts.set(i, Contact.cursorToContact(c, db));
                    }
                    break;
                }

            }
        }
        db.close();

        // After making changes to the List<Contact> notify the adapter that there have been changes
        ((ArrayAdapter<Contact>) getListAdapter()).notifyDataSetChanged();
    }


    void  viewUserInfo(){

        if (showTwoFragments){
             mListener.onFragmentInteraction(activeID);
        }
        else{
            Intent intent = new Intent();
            intent.setClass(getActivity(), MainActivity.class);
            intent.putExtra("activeID", activeID);
            startActivity(intent);
        }
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            long dbPosition = contacts.get(position).getId();
            activeID = dbPosition;
            mListener.onFragmentInteraction(dbPosition);
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(long id);
    }
}
