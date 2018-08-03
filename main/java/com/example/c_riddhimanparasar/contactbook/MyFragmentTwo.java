package com.example.c_riddhimanparasar.contactbook;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentTwo extends Fragment {

    RecyclerView recyclerView;
    TextView textView;
    Cursor cursor;
    ContactDetails contactDetails;
    List<ContactDetails> list = new ArrayList<>();
    private CustomAdapterTwo customAdapterTwo;

    public MyFragmentTwo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blockcontacts_layout, container, false);
        recyclerView = view.findViewById(R.id.block_contacts_holder);
        textView = view.findViewById(R.id.empty_text);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        displayBlockContacts();
        return view;
    }

    public void displayBlockContacts() {
        String name, contact;
       /* if (cursor == null)
            Toast.makeText(getActivity().getApplicationContext(), "The list is empty", Toast.LENGTH_LONG).show();
        else {*/
        cursor = UserDbHelper.getInstance(getActivity().getApplicationContext()).retrieveInfo();
        if (cursor.moveToFirst()) {
            android.util.Log.d("skk002 ", cursor.getCount() + "");
            do {
                name = cursor.getString(0);
                contact = cursor.getString(1);
                contactDetails = new ContactDetails();
                contactDetails.setName(name);
                contactDetails.setContact(contact);
                list.add(contactDetails);
            } while (cursor.moveToNext());
            customAdapterTwo = new CustomAdapterTwo(list, (MainActivity) getActivity());
            recyclerView.setAdapter(customAdapterTwo);
        } else {

            Toast.makeText(getActivity().getApplicationContext(), "The list is empty", Toast.LENGTH_LONG).show();
            getActivity().getFragmentManager().popBackStack();

        }
    }


    public void changeList(List<ContactDetails> list) {
        customAdapterTwo.updateList(list);
        customAdapterTwo.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapterTwo);
        if (list.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("The list is empty");
        }


    }
}
