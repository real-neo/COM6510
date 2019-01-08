package com6510.uk.ac.shef.oak.com6510;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import com6510.uk.ac.shef.oak.com6510.database.Picture;

public class ImageExtraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_extra);
        setTitle("Picture Manager - Location Details");

        Bundle b = getIntent().getExtras();

        if(b != null) {
            String path = b.getString("path");
            String title = b.getString("title");
            String description = b.getString("description");
            String date = b.getString("date");
            if (path != null){
                ImageView imageView = (ImageView) findViewById(R.id.extra_picture);
                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                imageView.setImageBitmap(myBitmap);
                TextView titleTextViev = findViewById(R.id.extra_title);
                TextView descriptionTextViev = findViewById(R.id.extra_description);
                TextView dateTextViev = findViewById(R.id.extra_date);

                titleTextViev.setText("Title: " + title);
                if (description == null) {
                    descriptionTextViev.setText("Description: No description");
                }
                else
                    descriptionTextViev.setText("Description: " + description);
                dateTextViev.setText("Date: " + date);
            }

        }
    }
}
