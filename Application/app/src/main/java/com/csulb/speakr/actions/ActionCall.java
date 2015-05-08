package com.csulb.speakr.actions;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.csulb.speakr.Actions;

/**
 * Created by Alex on 08/05/2015.
 */
public class ActionCall implements Actions.UserActionListener{

    public final static String TAG = "ActionCall";

    @Override
    public boolean doAction(Context context, String[] args) {
        if (args.length == 0)
            return false;
        return readContacts(context, args[0]);
    }

    public boolean readContacts(Context context, String searchName){
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (name.equals(searchName)){
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            System.out.println("name : " + name);
                            System.out.println("phone" + phone);
                            //Call
                            try {
                                String uri = "tel:" + phone.trim();
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setData(Uri.parse(uri));
                                context.startActivity(intent);
                                return true;
                            } catch (Exception e) {
                                Log.e(TAG, "Can't load phone call interface with number : " + phone);
                                e.printStackTrace();
                            }
                        }
                        pCur.close();
                        return false;
                    }
                }
            }
        }
        return false;
    }

}
