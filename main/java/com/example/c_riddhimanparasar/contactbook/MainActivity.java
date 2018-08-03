package com.example.c_riddhimanparasar.contactbook;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

public class MainActivity extends Activity implements CustomAdapterTwo.ChangeList {

    FrameLayout frameLayout;
    NavigationView navigationView;
    FragmentTransaction ft;
    FragmentTransaction fragmentTransaction;
    private MyFragmentTwo myFragmentTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.container);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, new MyFragment()).commit();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.All_Contacts) {
                    ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new MyFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.Block_Contacts) {
                    myFragmentTwo = new MyFragmentTwo();
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, myFragmentTwo).addToBackStack("blockFrag").commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void changeList(List<ContactDetails> list) {
        myFragmentTwo.changeList(list);
    }
}
