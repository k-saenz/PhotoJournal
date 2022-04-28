package com.example.photojournal;

import static android.os.Environment.getExternalStoragePublicDirectory;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photojournal.models.Photo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PhotoListAdapter extends
        RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private static List<Photo> mPhotoList;
    private final LayoutInflater mInflater;

    public PhotoListAdapter(Context context, List<Photo> photoList){
        mInflater = LayoutInflater.from(context);
        this.mPhotoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.photo_item, parent, false);

        return new PhotoViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo current = mPhotoList.get(position);
        holder.photoNameItemView.setText(current.getName());
        holder.photoDescriptionItemView.setText(current.getDescription());
        //holder.photoPicturePath
    }

    @Override
    public int getItemCount() {
        int size = 0;
        try{
            size = mPhotoList.size();
        } catch (Exception e){
            Log.e("something weird", e.toString());
        }

        return size;
    }


    public static class PhotoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public final TextView photoPictureItemView;
        public final TextView photoDescriptionItemView;
        public final TextView photoNameItemView;
        final PhotoListAdapter mAdapter;

        public PhotoViewHolder(@NonNull View itemView, PhotoListAdapter adapter) {
            super(itemView);
            photoNameItemView = itemView.findViewById(R.id.photo_name);
            photoDescriptionItemView = itemView.findViewById(R.id.photo_description);
            photoPictureItemView = itemView.findViewById(R.id.imgPicTaken);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Photo item = mPhotoList.get(mPosition);
            Toast toast = Toast.makeText(view.getContext(), "Clicked " + item.getDescription(), Toast.LENGTH_SHORT);
            toast.show();
            Bundle data = new Bundle();
            data.putSerializable("photo", item);

            NewPhotoFragment newPhotoFragment = NewPhotoFragment.newInstance();
            newPhotoFragment.setArguments(data);

            MainActivity.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newPhotoFragment)
                    .addToBackStack(null)
                    .commit();


            mAdapter.notifyDataSetChanged();
        }

        String currentPhotoPath;


    }
}
