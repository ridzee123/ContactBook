package com.example.c_riddhimanparasar.contactbook;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MyFragment extends Fragment {

    final List<ContactDetails> list = new ArrayList<>();
    private SearchView searchView;
    private SearchTask searchTask = null;
    private CustomAdapter customAdapter;

    public MyFragment() {
    }

    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_layout, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = (SearchView) view.findViewById(R.id.sv_all_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        displayContacts();
        //Toast.makeText(getActivity(), "Fragment Loaded", Toast.LENGTH_LONG).show();
        return view;

    }

    public void displayContacts() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (phones.moveToNext()) {
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setContact(number);
            contactDetails.setName(name);
            list.add(contactDetails);
        }
        phones.close();
        customAdapter = new CustomAdapter(list, getActivity());
        recyclerView.setAdapter(customAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                /*for (int i = 0; i < list.size(); i++) {

                    if (list.get(i).getName().startsWith(query)) {
                        list_search.add(list.get(i));
                    }
                }*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getActivity(), newText, Toast.LENGTH_LONG).show();
                if (searchTask != null) {
                    searchTask.cancel(true);
                    searchTask = null;
                }
                searchTask = new SearchTask(newText, list);
                searchTask.execute();
                return false;
            }
        });
    }

    class SearchTask extends AsyncTask<Void, Void, List<ContactDetails>> {
        String newText;
        List<ContactDetails> list;

        public SearchTask(String newText, List<ContactDetails> list) {
            this.newText = newText;
            this.list = list;
        }

        @Override
        protected List<ContactDetails> doInBackground(Void... voids) {
            List<ContactDetails> list_search = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (isCancelled()) {
                    break;
                }
                if (list.get(i).getName().toLowerCase().contains(newText)) {
                    list_search.add(list.get(i));
                }
            }
            return list_search;
        }

        @Override
        protected void onPostExecute(List<ContactDetails> list) {
            customAdapter.updateList(list);
            customAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(customAdapter);
        }
    }
}
