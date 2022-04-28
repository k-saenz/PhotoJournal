package com.example.photojournal;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.photojournal.data.PhotoDB;
import com.example.photojournal.models.Photo;

import java.util.LinkedList;


public class MainActivity extends FragmentActivity {
    private LinkedList<Photo> mPhotoList;
    public static FragmentManager fragmentManager;
    public static PhotoDB photoDB;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null){ return; }

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new PhotosListFragment())
                    .commit();
        }

        photoDB = Room.databaseBuilder(getApplicationContext(), PhotoDB.class, "photodb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }



}