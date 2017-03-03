package com.example.mjwolf.notsosimpledb;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjwolf on 2/27/17.
 */

class Contact {
    private String name;
    /*
        private String emailAddress;
    */
    private long id;

    public String getName(){
        return name;}

/*
    public String getEmailAddress( ){
        return emailAddress;}
*/

    public long getId() {
        return id;
    }

    public void setName(String n){
        name = n;
    }

    /*   public void setEmailAddress(String email){
           emailAddress = email;
       }
   */
    public void setId(long id1){
        if (id1 > 0){
            id = id1;
        }
        else{
            id = 0;
        }
    }

    public String toString(){
        return name;
    }

    static public List<Contact> getAll(DBAdapter db){ //this is a class method
        List<Contact> contacts = new ArrayList<Contact>();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst())
        {
            do {
                Contact contact = cursorToContact(c, db);
                contacts.add(contact);

            } while (c.moveToNext());
        }
        c.close();

        return contacts;
    }
    static public Contact cursorToContact( Cursor c, DBAdapter db){
        Contact contact = new Contact();
        contact.setId(c.getInt(c.getColumnIndex(db.KEY_ROWID)));
        contact.setName(c.getString(c.getColumnIndex(db.KEY_NAME)));
        // contact.setEmailAddress(c.getString(c.getColumnIndex(db.KEY_EMAIL)));

        return contact;

    }
}
