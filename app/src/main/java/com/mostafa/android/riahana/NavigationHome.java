package com.mostafa.android.riahana;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NavigationHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView,navi;
    Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8 ;
    ScrollView scrollView;
    TextView textViewTitle;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        imageView = (ImageView)findViewById(R.id.navi);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        bt6 = (Button)findViewById(R.id.bt6);
        bt7 = (Button)findViewById(R.id.bt7);
        bt8 = (Button)findViewById(R.id.bt8);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearLayout= (LinearLayout) findViewById(R.id.content);
        textViewTitle =(TextView)findViewById(R.id.title);
        navi = (ImageView)findViewById(R.id.navi);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragment != null) {
                scrollView.setVisibility(View.VISIBLE);
                discardChanges();
                fragment =null;

            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    android.support.v4.app.Fragment fragment = null;

    public void displaySelectedScreen(int id) {

        switch (id) {
            case R.id.servecies:
                changes(R.string.chooseyourserv,R.color.black,R.drawable.black,R.color.yellow);
                fragment = new ServiesFragment();
                break;
            case R.id.profile:
                break;
            case R.id.book:
                break;
            case R.id.calculate:
                break;
            case R.id.reservertion:
                break;
            case R.id.coupon:
                break;
            case R.id.offers:
                break;
            case R.id.logout:
                break;
        }
        if (fragment != null) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    private void changes(int string,int color,int drawable,int colorParent){
        scrollView.setVisibility(View.GONE);
        textViewTitle.setText(getResources().getString(string));
        textViewTitle.setTextColor(getResources().getColor(color));
        navi.setImageDrawable(getResources().getDrawable(drawable));
        linearLayout.setBackgroundColor(getResources().getColor(colorParent));
    }
    private void discardChanges(){
        textViewTitle.setText(getResources().getString(R.string.Home));
        textViewTitle.setTextColor(getResources().getColor(R.color.white));
        navi.setImageDrawable(getResources().getDrawable(R.drawable.navi));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
    }
}
