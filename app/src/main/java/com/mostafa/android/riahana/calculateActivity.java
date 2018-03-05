package com.mostafa.android.riahana;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class calculateActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_REQUEST = 1888;
    String urluser = "http://raihana-eg.com/site_api/api/cost_calculation";
    Uri imageUri;
    ProgressDialog progDailog;
    ImageView imageView;
    EditText description;
    String comment, encodimg = "0", id_client , result, message;
    TextView send;
    AdView mAdView;
    InterstitialAd mInterstitialAd;

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
//            view.setBackground(context.getResources().getDrawable(R.));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_calculate);
        send = findViewById(R.id.send);
        mAdView = findViewById(R.id.adViewcalculate);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interCalculate));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        imageView = findViewById(R.id.ima3);
        description = findViewById(R.id.description);
        final SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        id_client = sharedPreferences.getString("client_id", " ");
        final CharSequence[] items = {getString(R.string.choosePic), getString(R.string.picfromstudio),getString(R.string.cancel)};
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(calculateActivity.this);
                builder.setTitle(getString(R.string.chossePicture));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals(getString(R.string.choosePic))) {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAMERA_REQUEST);
                        } else if (items[item].equals(getString(R.string.picfromstudio))) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto , PICK_IMAGE);
                        } else if (items[item].equals(getString(R.string.cancel))) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }

    public void sendData(){
        intialize();

        if(!validate()) {

        } else {
            onRegisterSuccess();
        }

    }

    public void onRegisterSuccess(){
        processing();
        new GetDateUser().execute(id_client, images.lang, encodimg, comment);

    }

    public boolean validate(){
        boolean valid = true;

        if(comment.isEmpty()){
            description.setError(getString(R.string.writedescription));
            valid = false;
        }

        if(encodimg == "0"){
            AlertDialog.Builder builder = new AlertDialog.Builder(calculateActivity.this);
            builder.setMessage(getString(R.string.choosePicture))
                    .setNegativeButton(getString(R.string.yes), null)
                    .create()
                    .show();
            valid = false;
        }

        return  valid;
    }

    public void intialize(){
        comment = description.getText().toString().trim();
    }

    public void backIcon(View view) {
        onBackPressed();
    }

    public void processing(){
        progDailog = new ProgressDialog(calculateActivity.this);
        progDailog.setTitle(getString(R.string.calculatecost));
        progDailog.setMessage(getString(R.string.pleasewait));
        progDailog.setProgress(0);
        progDailog.setMax(70);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress <= 70) {
                    try {
                        progDailog.setProgress(progress);
                        progress++;
                        Thread.sleep(700);
                    } catch (Exception e) {

                    }
                }
                progDailog.dismiss();
            }
        });
        thread.start();
        progDailog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            Bitmap Bimg = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bimg.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        }

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Bitmap Bimg = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bimg.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        }
    }

    public void end(){
        finish();
    }

    class GetDateUser extends AsyncTask<String, Boolean, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            String id_client = strings[0];
            String lang = strings[1];
            String img = strings[2];
            String comment = strings[3];

            // if  you have  to send  data  to the databse
            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("id_client", id_client));
            pairs.add(new BasicNameValuePair("lang", lang));
            pairs.add(new BasicNameValuePair("img", img));
            pairs.add(new BasicNameValuePair("comment", comment));

            JsonReader j = new JsonReader(urluser, pairs);
            result = j.sendRequest();

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    message = jsonobject.getString("message");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            progDailog.cancel();
            AlertDialog.Builder builder = new AlertDialog.Builder(calculateActivity.this);
            builder.setMessage(message)
                    .setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            end();
                        }
                    })
                    .create()
                    .show();

        }
    }

}
