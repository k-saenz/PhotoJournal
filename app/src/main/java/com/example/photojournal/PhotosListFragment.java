package com.example.photojournal;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photojournal.models.DigitalPhoto;
import com.example.photojournal.models.FilmPhoto;
import com.example.photojournal.models.Photo;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class PhotosListFragment extends Fragment {

    private List<Photo> mPhotoList;
    private RecyclerView mRecyclerView;
    private PhotoListAdapter mAdapter;

    public PhotosListFragment() {
        super(R.layout.fragment_photos_list);
    }

    public static PhotosListFragment newInstance() {
        return new PhotosListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photos_list, container, false);

        FloatingActionButton fab = v.findViewById(R.id.fabAddEntry);

        fab.setOnClickListener(view -> {
            NewPhotoFragment newFrag = NewPhotoFragment.newInstance();
            FragmentTransaction transaction = MainActivity.fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, newFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mPhotoList = MainActivity.photoDB.photoDAO().getPhotos();

        mRecyclerView = v.findViewById(R.id.rv_journal);
        mAdapter = new PhotoListAdapter(getActivity(), mPhotoList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}