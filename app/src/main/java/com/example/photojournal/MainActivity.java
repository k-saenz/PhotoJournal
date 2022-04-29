package com.example.photojournal;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.photojournal.data.PhotoDB;
import com.example.photojournal.models.Photo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;


public class MainActivity extends FragmentActivity {
    public static FragmentManager fragmentManager;
    public static PhotoDB photoDB;
    EasyImage easyImage = new EasyImage.Builder(this)
            .setCopyImagesToPublicGalleryFolder(false)
            .setFolderName("photojournal Saved Pictures")
            .allowMultiple(false)
            .build();

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

        Button btnAddImage = findViewById(R.id.btnAddPicture);
//        btnAddImage.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, 3);
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void addPicture(View view) {
        easyImage.openCameraForImage(this);
    }
}