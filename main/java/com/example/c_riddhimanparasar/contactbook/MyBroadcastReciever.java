package com.example.c_riddhimanparasar.contactbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.example.c_riddhimanparasar.contactbook.UserContract.TABLE_NAME;

public class MyBroadcastReciever extends BroadcastReceiver {
    private TelephonyManager telephonyManager;
    String contact;
    UserDbHelper userDbHelper;
    Cursor cursor;
    boolean flag= false;

    @Override
    public void onReceive(final Context context, Intent intent) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    userDbHelper =  new UserDbHelper(context.getApplicationContext());
                    SQLiteDatabase db = userDbHelper.getReadableDatabase();
                    String[] projections = {UserContract.USER_NAME ,UserContract.USER_CONTACT};
                    cursor=  db.query(TABLE_NAME, projections, null, null, null, null, null);
                    cursor.moveToFirst();
                    do{
                        contact = cursor.getString(1);
                        if(incomingNumber.contains(contact))
                        {
                            flag = true;
                            break;
                        }
                    }while(cursor.moveToNext());
                    if (flag) {
                        try {
                            // Class MyClass = Class.forName(telephonyManager.getClass().getName());
                            Method m1 = telephonyManager.getClass().getDeclaredMethod("getITelephony");
                            m1.setAccessible(true);
                            Object iTelephony = m1.invoke(telephonyManager);
                            Method m2 = iTelephony.getClass().getDeclaredMethod("endCall");
                            m2.invoke(iTelephony);

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    }
}
