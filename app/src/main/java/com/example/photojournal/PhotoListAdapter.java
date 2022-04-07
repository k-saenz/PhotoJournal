package com.example.photojournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photojournal.models.Photo;

import java.util.LinkedList;

public class PhotoListAdapter extends
        RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private static LinkedList<Photo> mPhotoList;
    private final LayoutInflater mInflater;

    public PhotoListAdapter(Context context, LinkedList<Photo> photoList){
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
        holder.photoItemView.setText(current.getDescription());
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class PhotoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public final TextView photoItemView;
        final PhotoListAdapter mAdapter;

        public PhotoViewHolder(@NonNull View itemView, PhotoListAdapter adapter) {
            super(itemView);
            photoItemView = itemView.findViewById(R.id.photoDescription);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Photo item = mPhotoList.get(mPosition);

            //TODO: Navigate to NewPhotoFragment and pass in Photo object to edit

            mAdapter.notifyDataSetChanged();
        }
    }
}
