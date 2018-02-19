package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextView textViewCreateOne,forgetpass;
    EditText editTextuser, editTextpass;
    String user, pass, lan = "2";
    Button loginbt, clearbt;
    CheckBox checkBoxRemember;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarColored(this);
        checkBoxRemember = (CheckBox) findViewById(R.id.ckRemeberme);
        editTextuser = (EditText) findViewById(R.id.user);
        editTextpass = (EditText) findViewById(R.id.pass);
        loginbt = (Button) findViewById(R.id.login);
        forgetpass = (TextView)findViewById(R.id.forgetpass);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass2 cdd = new CustomDialogClass2(Login.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        if (sharedPreferences.contains("user")) {
            editTextuser.setText(sharedPreferences.getString("user", " "));
        }
        if (sharedPreferences.contains("password")) {
            editTextpass.setText(sharedPreferences.getString("password", " "));
        }
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginbt.setBackgroundResource(R.drawable.buttongreen);
                user = editTextuser.getText().toString().trim();
                pass = editTextpass.getText().toString().trim();
                if (checkBoxRemember.isChecked()) {
                    editor = sharedPreferences.edit();
                    editor.putString("user", user);
                    editor.putString("password", pass);
                    editor.commit();
                }
                login(user, pass, lan);
            }
        });

        textViewCreateOne = (TextView) findViewById(R.id.createOne);
        final Intent i = new Intent(this, Signup.class);

        textViewCreateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }


    public void login(String xuser, String xpass, String xlan) {
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject respons = jsonArray.getJSONObject(i);
                        String message = respons.getString("message");
                        int messsageid = respons.getInt("messageID");
                        if (messsageid == 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage(message)
                                    .setNegativeButton("موافق", null)
                                    .create()
                                    .show();
                        } else if (messsageid == 1) {
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            Intent Bookingintent = new Intent(Login.this, BookingActivity.class);
                            Bookingintent.putExtra("Title", getResources().getString(R.string.eyeleftprocess));
                            editor.putInt("login",1);
                            editor.commit();
                            Login.this.startActivity(Bookingintent);
                            finish();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginPatiRequest = new LoginRequest(user, pass, lan, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(loginPatiRequest);

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
//            view.setBackground(context.getResources().getDrawable());
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
}
