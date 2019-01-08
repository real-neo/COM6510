package com6510.uk.ac.shef.oak.com6510;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com6510.uk.ac.shef.oak.com6510.database.Picture;
import com6510.uk.ac.shef.oak.com6510.viewmodel.PictureViewModel;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private PictureViewModel viewModel;

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private static final String TAG = "MainActivity";
    private Activity activity;
    private RecyclerView recyclerView;
    private PictureAdapter adapter;
    private Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_map:
                intent = new Intent(this, MapActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(this, SearchActivity.class);
                this.startActivity(intent);
                return true;
        }
      return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        setTitle("Picture Manager - Gallery");

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
//        setSupportActionBar(myToolbar);


        final int numberOfColumns = 3;

        recyclerView = findViewById(R.id.grid_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        activity = this;

        adapter = new PictureAdapter(context);
        recyclerView.setAdapter(adapter);
//        adapter.setHasStableIds(true); // when it's enabled, app CRASHES!!!

        checkPermissions(getApplicationContext());

        initEasyImage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
            }
        });

        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });


        viewModel = ViewModelProviders.of(this).get(PictureViewModel.class);
        //viewModel.populateDataBase();
        viewModel.getAllPictures().observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(@Nullable List<Picture> pictures) {
                //update recyclerview
                adapter.setPictures(pictures);
            }
        });


    }

    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
                adapter.onPhotosReturned(imageFiles, recyclerView);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        try {
            addImageElements(returnedPhotos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    private void addImageElements(List<File> returnedPhotos) throws IOException {
        List<Picture> imageElementList= new ArrayList<>();
        ExifInterface exif;
        for (File file: returnedPhotos){
            Picture element= new Picture(file.getAbsolutePath(), file.getName());
            exif = new ExifInterface(file.getAbsolutePath());
            String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
            element.setDate(date);
            // also set date and stuff
            Double dlat = null, dlon = null;
            if (lat != null || lon != null) {
                dlat = convertToDegree(lat);
                dlon = convertToDegree(lon);
                element.setLat(dlat);
                element.setLon(dlon);
            }
            imageElementList.add(element);
            viewModel.getRepository().insert(element);
        }
    }

    private Double convertToDegree(String stringDMS){
        Double result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Double(FloatD + (FloatM/60) + (FloatS/3600));

        return result;
    }

    public Activity getActivity() {
        return activity;
    }

}
