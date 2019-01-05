package com6510.uk.ac.shef.oak.com6510;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com6510.uk.ac.shef.oak.com6510.database.Picture;

public class ShowImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Bundle b = getIntent().getExtras();
        int position=-1;
        if(b != null) {
            position = b.getInt("position");
            if (position!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.show_picture);
                Picture element= PictureAdapter.getPictures().get(position);
                if (element != null) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(element.getPicturePath());
                    imageView.setImageBitmap(myBitmap);
                }
            }

        }
    }
}
