package com.mostafa.android.riahana;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
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
    ScrollView scrollView;
    TextView textViewTitle;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            imageView = (ImageView) findViewById(R.id.language);

            scrollView = (ScrollView) findViewById(R.id.scrollView);
            linearLayout = (LinearLayout) findViewById(R.id.content);
            textViewTitle = (TextView) findViewById(R.id.title);
            navi = (ImageView) findViewById(R.id.navi);


            final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            });
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }catch(InflateException E){
            Log.e("The error is "," "+E.getMessage());
        }

        }

    @Override
    public void onBackPressed() {
       final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragment != null) {
                scrollView.setVisibility(View.VISIBLE);
                discardChanges();
                fragment =null;

            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationHome.this);
            builder.setTitle(getResources().getString(R.string.quit));
            builder.setMessage(getResources().getString(R.string.quitQuestion));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
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
                try {
                    changes(R.string.chooseyourserv, R.color.black, R.drawable.black, R.color.yellow);
                    fragment = new ServiesFragment();
                }catch (Exception e){
                    Log.e("NavigationHome","The error is  "+e.getMessage());
                }
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

    private void changes(int string,int color,int drawable,int colorParent) throws  Exception {
        scrollView.setVisibility(View.GONE);
        imageView.setVisibility(View.INVISIBLE);
        textViewTitle.setText(getResources().getString(string));
        textViewTitle.setTextColor(getResources().getColor(color));
        navi.setImageDrawable(getResources().getDrawable(drawable));
        linearLayout.setBackgroundColor(getResources().getColor(colorParent));
    }
    private void discardChanges(){
        textViewTitle.setText(getResources().getString(R.string.Home));
        imageView.setVisibility(View.VISIBLE);
        textViewTitle.setTextColor(getResources().getColor(R.color.white));
        navi.setImageDrawable(getResources().getDrawable(R.drawable.navi));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.green));

    }
}
