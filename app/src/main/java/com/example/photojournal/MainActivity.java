package com.example.photojournal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.photojournal.models.Exposure;
import com.example.photojournal.models.FilmPhoto;
import com.example.photojournal.models.Photo;
import com.example.photojournal.models.PhotoFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.time.LocalDateTime;
import java.util.LinkedList;


public class MainActivity extends FragmentActivity {
    //SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }



}