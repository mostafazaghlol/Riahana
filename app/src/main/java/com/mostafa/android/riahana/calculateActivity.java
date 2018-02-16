package com.mostafa.android.riahana;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class calculateActivity extends AppCompatActivity {

    TextView SendtextView,Selecttextview,EditPicture;
    ImageView imageViewprofile;
    EditText myEditText;
    ScrollView mScrollView;
    int x=0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if(requestCode == RESULT_OK) {
                    Log.i("MainActivity", "case 0");
                }
                break;
            case 1:
                if(resultCode == RESULT_OK) {
                    images.imageUri = data.getData();
                    imageViewprofile.setImageURI(images.imageUri);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColored(this);
        setContentView(R.layout.activity_calculate);
        mScrollView = (ScrollView)findViewById(R.id.mScrollView);
        myEditText = (EditText)findViewById(R.id.myedittext);
        SendtextView = (TextView)findViewById(R.id.send);
        Selecttextview = (TextView)findViewById(R.id.selectservices);
        EditPicture = (TextView)findViewById(R.id.editPicture);
        imageViewprofile = (ImageView)findViewById(R.id.ima3);
        final Intent intent = new Intent(this,servicesTypeActivity.class);
        intent.putExtra("cat",1);


        Selecttextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                images.x = 1;
                startActivity(intent);
                finish();
            }
        });

        Intent i = getIntent();
        if(i.getStringExtra("Title")!= ""){
            Selecttextview.setText(i.getStringExtra("Title"));

        }else {
            Selecttextview.setText(getResources().getString(R.string.EyelidTightening));
        }
        SendtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(calculateActivity.this, "sent !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setStatusBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
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

    public void getImageFromGalary(View view) {
        if(images.x!=0) {
            Intent pickerPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickerPhotoIntent, 1);
//            images.x=0;
        }else{
            Toast.makeText(this, "Enter the Services Type First !", Toast.LENGTH_SHORT).show();
        }
        }

    public void backIcon(View view) {
        onBackPressed();
    }
}
