package com.example.lenovo.myviews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import views.viewgroup1;


public class SlidingMenuActivity extends Activity implements View.OnClickListener {
    viewgroup1 menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);
        menu = (viewgroup1) findViewById(R.id.menulayout);
        TextView tx1 = (TextView) findViewById(R.id.tx1);
        TextView tx2 = (TextView) findViewById(R.id.tx2);
        tx1.setOnClickListener(this);
        tx2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sliding_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx1:
                menu.setType(1);
                break;
            case R.id.tx2:
                menu.setType(0);
                break;

        }
    }
}
