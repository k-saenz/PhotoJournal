package com.example.photojournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;

import com.example.photojournal.models.Exposure;
import com.example.photojournal.models.FilmPhoto;
import com.example.photojournal.models.Photo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotoListAdapter mAdapter;
    private LinkedList<Photo> mPhotoList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        TEST DATA
         */
        mPhotoList = new LinkedList<Photo>();
        for (int i = 0; i < 3; i++){
            Photo photo = new FilmPhoto();
            photo.setDescription("Just some photo");
//            photo.setId(Integer.toString(i));
//            photo.setAperture(i + 4);
//            photo.setExposure(Exposure.OVER_EXPOSED);
//            photo.setDateTime(LocalDateTime.now());
//            photo.setShutterSpeed("500");
//            photo.setLocation("Here");

            mPhotoList.addLast(photo);
        }

        FloatingActionButton fab = this.findViewById(R.id.fabAddEntry);

        fab.setOnClickListener(view -> {
            NewPhotoFragment newFrag = NewPhotoFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.entries_fragment_container_view, newFrag);
            transaction.disallowAddToBackStack();
            transaction.commit();
        });

        mRecyclerView = findViewById(R.id.rv_journal);
        mAdapter = new PhotoListAdapter(this, mPhotoList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}