package com6510.uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com6510.uk.ac.shef.oak.com6510.database.Picture;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private static List<Picture> pictures = new ArrayList<>();
    private Context context;

    public PictureAdapter(Context cont) {
        context = cont;
    }

    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture, parent, false);
        context = parent.getContext();
        return new PictureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder pictureHolder, final int position) {
        Picture currentPicture = pictures.get(position);
        File file = new File(currentPicture.getPicturePath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        pictureHolder.imageViewPicture.setImageBitmap(bitmap);
        pictureHolder.textViewTitle.setText(currentPicture.getTitle());

        pictureHolder.imageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowImageActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    public void onPhotosReturned(List<File> returnedPhotos, RecyclerView recyclerView) {
        try {
            pictures.addAll(getImageElements(returnedPhotos));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPictures(pictures);
        //notifyDataSetChanged();
        //recyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    private List<Picture> getImageElements(List<File> returnedPhotos) throws IOException {
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
        }
        return imageElementList;
    }

    class PictureHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPicture;
        private TextView textViewTitle;

        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPicture = itemView.findViewById(R.id.picture);
            textViewTitle = itemView.findViewById(R.id.picture_title);
        }
    }

    public static List<Picture> getPictures() {
        return pictures;
    }

    private Context getContext() {
        return this.context;
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
}
