package com6510.oak.shef.ac.uk.com6510;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com6510.oak.shef.ac.uk.com6510.database.Picture;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    private static List<Picture> pictures = new ArrayList<>();
    private static Context context;

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
        pictures.addAll(getImageElements(returnedPhotos));
        setPictures(pictures);
        //notifyDataSetChanged();
        //recyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    private List<Picture> getImageElements(List<File> returnedPhotos) {
        List<Picture> imageElementList= new ArrayList<>();
        for (File file: returnedPhotos){
            Picture element= new Picture(file.getAbsolutePath(), "");
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
}
