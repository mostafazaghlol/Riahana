package com.mostafa.android.riahana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {
    ImageView imageViewAdds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageViewAdds = (ImageView)findViewById(R.id.imageAdds);
        Picasso.with(this).load(R.drawable.adds).into(imageViewAdds);
    }
}
