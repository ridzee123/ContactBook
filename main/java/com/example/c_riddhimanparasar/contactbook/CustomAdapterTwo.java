package com.example.c_riddhimanparasar.contactbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class CustomAdapterTwo extends RecyclerView.Adapter {

    private final Context context;
    List<ContactDetails> list;
    UserDbHelper userDbHelper;
    ChangeList changeListListner;

    public CustomAdapterTwo(List<ContactDetails> list, Context context) {
        this.list = list;
        this.context = context;
        changeListListner = (ChangeList) context;
    }

    public void updateList(List<ContactDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blockcontacts_holder, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((MyHolder) holder).contactName.setText(list.get(position).getName());
        ((MyHolder) holder).contactNumber.setText(list.get(position).getContact());
        ((MyHolder) holder).r1_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to unblock?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //userDbHelper = new UserDbHelper(context);
                        SQLiteDatabase db = UserDbHelper.getInstance(context).getWritableDatabase();
                        String where = UserContract.USER_NAME + " = " + "'" + list.get(position).getName().toString() + "'";
                        int val = db.delete(UserContract.TABLE_NAME, where, null);
                        if (val != 0) {
                            list.remove(position);
                            changeListListner.changeList(list);
                            }
                        //Log.d("skk002", "the value is "+);
                        //((MyHolder) holder).r1_layout.removeViewAt(position);

                        Toast.makeText(context.getApplicationContext(), "Unblocked", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", null).create().show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber;
        RelativeLayout r1_layout;

        public MyHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);
            r1_layout = itemView.findViewById(R.id.unblock_contact_holder);
        }
    }

    public interface ChangeList {
        public void changeList(List<ContactDetails> list);
    }
}
