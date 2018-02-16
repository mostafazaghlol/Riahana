package com.mostafa.android.riahana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class servicesTypeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_services_type);
            final String[] values = new String[] { getResources().getString(R.string.eyeleftprocess),
                    getResources().getString(R.string.rhinoplastv),
                    getResources().getString(R.string.EyelidTightening),
                    getResources().getString(R.string.Embellrsktheear),
                    getResources().getString(R.string.LipGlossProcess),
                    getResources().getString(R.string.processofTon),
                    getResources().getString(R.string.processoffat),
                    getResources().getString(R.string.breastimplants)
            };

            ListView listView = (ListView) findViewById(R.id.list_item);
            customArrayAdapter customArrayAdapter= new customArrayAdapter(this, 0, values);
          //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, values);
            listView.setAdapter(customArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final Intent i2 = getIntent();
                    Intent intent=null;
                    if(i2.getIntExtra("cat",10) == 0) {
                        intent= new Intent(servicesTypeActivity.this, BookingActivity.class);

                    }else if(i2.getIntExtra("cat",10)==1){
                        intent = new Intent(servicesTypeActivity.this, calculateActivity.class);
                    }
                    intent.putExtra("uri",i2.getStringExtra("uri"));
                    intent.putExtra("Title",values[i]);
                    finish();
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Log.e("servicesTypeActivity "," "+e.getMessage());
        }
    }
}
