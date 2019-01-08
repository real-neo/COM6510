package uk.ac.shef.oak.com6510.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.repository.PictureRepository;
import uk.ac.shef.oak.com6510.database.Picture;

public class PictureViewModel extends AndroidViewModel {

    private PictureRepository repository;
    private LiveData<List<Picture>> allPictures;

    public PictureViewModel(@NonNull Application application) {
        super(application);
        repository = new PictureRepository(application);
        allPictures = repository.getAllPictures();
    }

    public void insert(Picture picture) {
        repository.insert(picture);
    }

    public void update(Picture picture) {
        repository.update(picture);
    }

    public void delete(Picture picture) {
        repository.delete(picture);
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    public LiveData<List<Picture>> search(String key) {
        return repository.search(key);
    }

    public PictureRepository getRepository() {
        return repository;
    }

    public void populateDataBase() {
        List<Picture> pictures;
        pictures = new ArrayList<>();
        pictures.add(new Picture("/storage/emulated/0/DCIM/Camera/IMG_20181118_103857.jpg", "Title 1"));
        pictures.add(new Picture("/storage/emulated/0/DCIM/Camera/IMG_20181118_103846.jpg", "Title 2"));
        pictures.add(new Picture("/storage/emulated/0/DCIM/Camera/IMG_20181118_103828.jpg", "Title 3"));
        for (Picture pict: pictures)
            new PictureRepository.InsertPictureAsyncTask(repository.getPictureDAO()).execute(pict);
    }
}
