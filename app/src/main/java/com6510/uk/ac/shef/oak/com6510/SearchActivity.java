package com6510.uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class SearchActivity extends AppCompatActivity {

    private Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_gallery:
                intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                //return true;
            case R.id.menu_map:
                intent = new Intent(this, MapActivity.class);
                this.startActivity(intent);
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = getApplicationContext();
        setTitle("Picture Manager - Search");
    }
}
