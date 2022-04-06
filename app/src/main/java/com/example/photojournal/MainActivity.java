package com.example.photojournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.example.photojournal.R;
import com.example.photojournal.models.Photo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mJournalEntries;
    private ArrayList<Photo> mPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addNewEntry(View view) {

    }
}