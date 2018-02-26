package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class NavigationHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageViewarabic, imageViewenglish, navi;
    ScrollView scrollView;
    TextView textViewTitle;
    LinearLayout linearLayout;

    private Locale myLocale;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setStatusBarColored(this);
            setContentView(R.layout.activity_main);
            sharedPreferences = getSharedPreferences("pref", 0);
            imageViewarabic = (ImageView) findViewById(R.id.language);
            imageViewenglish = (ImageView) findViewById(R.id.language2);
            if (images.lang == "2") {
                imageViewarabic.setVisibility(View.INVISIBLE);
                imageViewenglish.setVisibility(View.VISIBLE);
            } else if (images.lang == "1") {
                imageViewenglish.setVisibility(View.INVISIBLE);
                imageViewarabic.setVisibility(View.VISIBLE);
            }
            scrollView = (ScrollView) findViewById(R.id.scrollView);
            linearLayout = (LinearLayout) findViewById(R.id.content);
            textViewTitle = (TextView) findViewById(R.id.title);
            navi = (ImageView) findViewById(R.id.navi);
            fragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();

            final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            });
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        } catch (InflateException E) {
            Log.e("The error is ", " " + E.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

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
            case R.id.Home:
                if (fragment != null) {
//                    scrollView.setVisibility(View.VISIBLE);
//                    discardChanges();
                    fragment = new HomeFragment();

//                    fragment = null;
                }
                break;
            case R.id.servecies:
                Intent ServicesActivity = new Intent(this, com.mostafa.android.riahana.ServicesActivity.class);
                NavigationHome.this.startActivity(ServicesActivity);
                break;
            case R.id.profile:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent profileIntent = new Intent(this, profileActivity.class);
                    profileIntent.putExtra("name", sharedPreferences.getString("fname", " "));
                    profileIntent.putExtra("phone", sharedPreferences.getString("user_phone", " "));
                    profileIntent.putExtra("email", sharedPreferences.getString("user", " "));
                    profileIntent.putExtra("client_id", sharedPreferences.getString("client_id", " "));
                    startActivity(profileIntent);
//                    Toast.makeText(this, "fname is "+sharedPreferences.getString("fname"," "), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + getString(R.string.mustlog), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 1;
                    images.login = 1;
                    startActivity(loginIntent);
                }
                break;
//            case R.id.book:
//                Intent ServicesActivity2 = new Intent(this, com.mostafa.android.riahana.ServicesActivity.class);
//                NavigationHome.this.startActivity(ServicesActivity2);
//                break;
            case R.id.calculate:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent CalIntent = new Intent(this, calculateActivity.class);
                    startActivity(CalIntent);
                } else {
                    Toast.makeText(this, "" + getString(R.string.mustlog), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 2;
                    images.login = 1;
                    startActivity(loginIntent);
                }
                break;
            case R.id.oldcalculate:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent CalIntentold = new Intent(this, oldcalculationActivity.class);
                    startActivity(CalIntentold);
                } else {
                    Toast.makeText(this, "" + getString(R.string.mustlog), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 3;
                    images.login = 1;
                    startActivity(loginIntent);
                }
                break;
            case R.id.newcalculate:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent CalIntentnew = new Intent(this, newcalculationActivity.class);
                    startActivity(CalIntentnew);
                } else {
                    Toast.makeText(this, "" + getString(R.string.mustlog), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 4;
                    images.login = 1;
                    startActivity(loginIntent);
                }
                break;
            case R.id.newReservertion:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent ResIntent2 = new Intent(this, ComfirmedReservationsActivity.class);
                    startActivity(ResIntent2);
                } else {
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 5;
                    images.login = 1;
                    startActivity(loginIntent);
                }
                break;
            case R.id.oldReservertion:
                if (sharedPreferences.contains("user") && sharedPreferences.getInt("login", 0) == 1) {
                    Intent ResIntent = new Intent(this, WaitedReservationsActivity.class);
                    startActivity(ResIntent);
                } else {
                    Toast.makeText(this, "" + getString(R.string.mustlog), Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(this, Login.class);
                    images.activity = 6;
                    startActivity(loginIntent);
                }
                break;
            case R.id.coupon:
                Intent couponIntent = new Intent(this, couponActivity.class);
//                ResIntent.putExtra("Title",getResources().getString(R.string.eyeleftprocess));
                startActivity(couponIntent);
                break;
            case R.id.offers:
                Intent offerIntent = new Intent(this, offersActivity.class);
//                ResIntent.putExtra("Title",getResources().getString(R.string.eyeleftprocess));
                startActivity(offerIntent);
                break;
            case R.id.contact:
                Intent contact = new Intent(this, Contact_us.class);
                startActivity(contact);
                break;
            case R.id.Aboutapi:
                Intent aboutIntent = new Intent(this, About_us.class);
                startActivity(aboutIntent);
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationHome.this);
                builder.setTitle(getResources().getString(R.string.goout));
                builder.setMessage(getResources().getString(R.string.dogoout));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

    private void changes(int string, int color, int drawable, int colorParent) throws Exception {
        scrollView.setVisibility(View.GONE);
        imageViewarabic.setVisibility(View.INVISIBLE);
        textViewTitle.setText(getResources().getString(string));
        textViewTitle.setTextColor(getResources().getColor(color));
        navi.setImageDrawable(getResources().getDrawable(drawable));
        linearLayout.setBackgroundColor(getResources().getColor(colorParent));
    }

    private void discardChanges() {
        textViewTitle.setText(getResources().getString(R.string.Home));
        imageViewarabic.setVisibility(View.VISIBLE);
        textViewTitle.setTextColor(getResources().getColor(R.color.white));
        navi.setImageDrawable(getResources().getDrawable(R.drawable.navi));
        linearLayout.setBackground(getResources().getDrawable(R.drawable.homebackgroung));

    }

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }

    public static int getStatusBarHeight(Activity context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLanguage(View view) {
        if (view.getId() == R.id.language) {
            images.lang = "2";
            changeLang("ar");
//        view.setVisibility(View.GONE);
            imageViewarabic.setVisibility(View.INVISIBLE);
        } else if (view.getId() == R.id.language2) {
            changeLang("en");
            images.lang = "1";
            view.setVisibility(View.GONE);
            imageViewarabic.setVisibility(View.VISIBLE);
        }
    }
}
