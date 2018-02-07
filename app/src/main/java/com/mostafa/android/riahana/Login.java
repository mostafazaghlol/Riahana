package com.mostafa.android.riahana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {
    ImageView imageViewAdds;
    TextView textViewCreateOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageViewAdds = (ImageView)findViewById(R.id.imageAdds);
        textViewCreateOne = (TextView)findViewById(R.id.createOne);
        final Intent i = new Intent(this,Signup.class);

        textViewCreateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(i);
            }
        });
        Picasso.with(this).load(R.drawable.adds).into(imageViewAdds);
    }
}
