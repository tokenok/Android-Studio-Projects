package dq3395yi.jtu_day8app;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.zip.Inflater;

import static dq3395yi.jtu_day8app.R.array.toppings;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubmitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubmitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private static ListView inview;

    private EditText edc_miles;
    private TextView tv_total_cost;

    public SubmitFragment() {
        // Required empty public constructor
    }

    public static SubmitFragment newInstance(ListView lv) {
        SubmitFragment fragment = new SubmitFragment();

        inview = lv;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.submit_fragment, container, false);

        edc_miles = (EditText)view.findViewById(R.id.editText);
        tv_total_cost = (TextView)view.findViewById(R.id.TV_TOTAL_PRICE);

        if (inview != null){
            calcPrice(inview);
        }
        inview = null;

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public double calcPrice(ListView lv){
        double cost = 11.0;

        boolean b_portrait = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);

        SparseBooleanArray checkedPositions = lv.getCheckedItemPositions();

        String[] rtoppings = getResources().getStringArray(toppings);
        String[] toppingtypes = new String[rtoppings.length - (b_portrait ? 1 : 0)];
        for (int j = 0; j < rtoppings.length - (b_portrait ? 1 : 0); j++){
            String[] t = rtoppings[j].split(",");
            toppingtypes[j] = t[1];
        }

        for (int i = 0; i < checkedPositions.size(); i++){
            if (checkedPositions.valueAt(i)){
                String item = lv.getAdapter().getItem(checkedPositions.keyAt(i)).toString();

                int ind = checkedPositions.keyAt(i);
                if (ind < toppingtypes.length) {
                    String type = toppingtypes[ind];

                    Log.d("checked", item + " " + type);

                    switch (type){
                        case "m":
                            cost += 3.0;
                            break;
                        case "v":
                            cost += 2.5;
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        double deliveryCost = 0.0;
        String smiles = edc_miles.getText().toString();
        if (smiles.length() > 0)
            deliveryCost = Double.parseDouble(smiles) * 0.5;

        tv_total_cost.setText("Price: $" + String.format("%.2f", cost) + " + $" + String.format("%.2f", deliveryCost) + " (Delivery Fee)");

        return cost;
    }
}
