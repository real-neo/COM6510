package com6510.oak.shef.ac.uk.com6510.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = Picture.class, version = 2)
public abstract class PictureDatabase extends RoomDatabase {

    private static PictureDatabase instance;

    public abstract PictureDAO pictureDAO();

    public static synchronized PictureDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PictureDatabase.class, "picture_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //new PopulateDbAsyncTask(instance).execute();
        }
    };

    /*private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PictureDAO pictureDAO;

        private PopulateDbAsyncTask(PictureDatabase db) {
            pictureDAO = db.pictureDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pictureDAO.insert(new Picture("drawable://" + R.drawable.joe1, "Title 4"));
            Log.i("AsyncTask", "Picture inserted");
            pictureDAO.insert(new Picture("drawable://" + R.drawable.joe2, "Title 5"));
            Log.i("AsyncTask", "Picture inserted");
            pictureDAO.insert(new Picture("drawable://" + R.drawable.joe3, "Title 6"));
            Log.i("AsyncTask", "Picture inserted");
            return null;
        }
    }*/
}
