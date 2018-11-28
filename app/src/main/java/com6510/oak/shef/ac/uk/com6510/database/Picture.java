package com6510.oak.shef.ac.uk.com6510.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "picture_table")
public class Picture {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "path")
    private String picturePath;
    @ColumnInfo(name = "title")
    private String title;

    public int getId() {
        return id;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Picture(String picturePath, String title) {
        this.picturePath = picturePath;
        this.title = title;
    }

}
