package com6510.oak.shef.ac.uk.com6510.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import com6510.oak.shef.ac.uk.com6510.database.Picture;
import com6510.oak.shef.ac.uk.com6510.database.PictureDAO;
import com6510.oak.shef.ac.uk.com6510.database.PictureDatabase;

public class PictureRepository {

    public PictureDAO pictureDAO;
    private LiveData<List<Picture>> allPictures;

    public PictureRepository(Application application) {
        PictureDatabase database = PictureDatabase.getInstance(application);
        pictureDAO = database.pictureDAO();
        allPictures = pictureDAO.getAllPictures();
    }

    public void insert(Picture picture) {
        new InsertPictureAsyncTask(pictureDAO).execute(picture);
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    public PictureDAO getPictureDAO() {
        return pictureDAO;
    }

    public void setPictureDAO(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    /**
     * Insert Async Task
     */
    public static class InsertPictureAsyncTask extends AsyncTask<Picture, Void, Void> {
        private PictureDAO pictureDAO;
        public InsertPictureAsyncTask(PictureDAO pictureDAO) {
            this.pictureDAO = pictureDAO;
        }

        @Override
        protected Void doInBackground(Picture... pictures) {
            pictureDAO.insert(pictures[0]);
            return null;
        }
    }
}
