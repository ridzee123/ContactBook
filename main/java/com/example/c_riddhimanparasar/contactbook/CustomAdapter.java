package com.example.c_riddhimanparasar.contactbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;
import java.lang.Character;

public class CustomAdapter extends RecyclerView.Adapter {

    private List<ContactDetails> list;
    TelephonyManager telephonyManager;
    private Context context;
    private int position = -11;

    public CustomAdapter(List<ContactDetails> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ((Holder) holder).textViewOne.setText(list.get(position).getName());
        ((Holder) holder).textViewTwo.setText(list.get(position).getContact());
        ((Holder) holder).textViewName.setText(list.get(position).getName().toUpperCase().charAt(0) + "");
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        ((Holder) holder).textViewName.setBackgroundColor(color);
        GradientDrawable bgShape = (GradientDrawable) ((Holder) holder).textViewName.getBackground();
        bgShape.setColor(color);
        ((Holder) holder).rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse(list.get(position).getContact()));
                intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", list.get(position).getContact(), null));
                context.startActivity(intent);

            }
        });
        ((Holder) holder).rl_parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("sk002", "Long Pressed");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to block?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = list.get(position).getName();
                        final String contact = list.get(position).getContact();

                        if (UserDbHelper.getInstance(context.getApplicationContext()).blockContact(name, contact))
                            Toast.makeText(context, "Number Blocked", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(context, "Number Already Blocked", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancel", null).create().show();
                return true;
            }
        });


    }

    public void updateList(List<ContactDetails> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView textViewOne, textViewTwo, textViewName;
        RelativeLayout rl_parent;

        public Holder(View itemView) {
            super(itemView);
            textViewOne = (TextView) itemView.findViewById(R.id.textViewOne);
            textViewTwo = (TextView) itemView.findViewById(R.id.textViewTwo);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
        }
    }
}

