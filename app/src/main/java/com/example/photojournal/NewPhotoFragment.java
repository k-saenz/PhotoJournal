package com.example.photojournal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photojournal.models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class NewPhotoFragment extends Fragment {

    private Context mContext;
    private SharedPreferences mPrefs;
    private LinkedList<Photo> mPhotos;

    public NewPhotoFragment() {
        super(R.layout.fragment_new_photo);
    }

    public static NewPhotoFragment newInstance() {
        NewPhotoFragment fragment = new NewPhotoFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_photo, container, false);
        mPrefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();

        //Inflate Dialogs for date and time pickers
        createDateTimeDialogs(v);

        RadioGroup rdoGrpPhotoType = (RadioGroup) v.findViewById(R.id.rdoGrpPhotoType);
        TextView filmOrRes = (TextView) v.findViewById(R.id.txtFilmOrRes);
        String filmResString = filmOrRes.getText().toString();

        Button submit = v.findViewById(R.id.btnSubmit);

        SharedPreferences.Editor editor = mPrefs.edit();

        Photo photo = new Photo();

        rdoGrpPhotoType.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.rdoFilm:
                    filmOrRes.setHint("Film Stock Used");
                    photo.setFilm(true);
                    break;

                case R.id.rdoDigital:
                    filmOrRes.setHint("Resolution");
                    photo.setFilm(false);
                    break;
            }
        });

        submit.setOnClickListener(view -> {

            Photo newPhoto = setPhotoProperties(photo, v);
            String json = gson.toJson(newPhoto);
            editor.putString("Photo", json);
            editor.apply();

            String toastText = "Submitted " + newPhoto.getName();
            Toast toast = Toast.makeText(view.getContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();

            getActivity().onBackPressed();
        });

        // Inflate the layout for this fragment
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createDateTimeDialogs(View v) {

        EditText dateView = v.findViewById(R.id.txtDate);
        EditText timeView = v.findViewById(R.id.txtTime);
        dateView.setInputType(InputType.TYPE_NULL);
        timeView.setInputType(InputType.TYPE_NULL);

        dateView.setOnClickListener(view -> {
            int day = LocalDateTime.now().getDayOfMonth();
            int month = LocalDateTime.now().getMonthValue();
            int year = LocalDateTime.now().getYear();

            DatePickerDialog picker = new DatePickerDialog(getContext(), (datePicker, i, i1, i2) -> {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                dateView.setText(LocalDateTime.now().format(pattern));
            }, year, month, day);

            picker.show();
        });

        timeView.setOnClickListener(view -> {
            int hour = LocalDateTime.now().getHour();
            int minute = LocalDateTime.now().getMinute();

            TimePickerDialog picker = new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
                timeView.setText(LocalDateTime.now().format(pattern));
            }, hour, minute, false);

            picker.show();
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Photo setPhotoProperties(Photo photo, View view) {
        try {
            //General
            EditText name = (EditText) view.findViewById(R.id.txtName);
            EditText date = (EditText) view.findViewById(R.id.txtDate);
            TextView time = view.findViewById(R.id.txtTime);
            photo.setName(name.getText().toString());
            LocalDateTime datetime = LocalDateTime.of(
                    LocalDate.parse(date.getText().toString()),
                    LocalTime.parse(time.getText().toString()));
            photo.setDateTime(datetime);

            //Camera Settings
            TextView shutterSpeed = view.findViewById(R.id.txtShutterSpeed);
            TextView aperture = view.findViewById(R.id.txtAperture);
            TextView iso = view.findViewById(R.id.txtIso);
            TextView lens = view.findViewById(R.id.txtLens);
            TextView camera = view.findViewById(R.id.txtCamera);
            TextView filmOrRes = view.findViewById(R.id.txtFilmOrRes);

            photo.setShutterSpeed(Integer.parseInt(shutterSpeed.getText().toString()));
            photo.setAperture(Float.parseFloat(aperture.getText().toString()));
            photo.setIso(Integer.parseInt(iso.getText().toString()));
            photo.setLens(lens.getText().toString());
            photo.setCamera(camera.getText().toString());
            photo.setFilmOrRes(filmOrRes.getText().toString());

            //About
            TextView description = view.findViewById(R.id.txtDescription);
            photo.setDescription(description.getText().toString());

            RadioGroup rdoGrpExposure = view.findViewById(R.id.rdoGrpExposure);
            rdoGrpExposure.setOnCheckedChangeListener((rdoGrp, chkId) -> {
                switch (chkId) {
                    case R.id.rdoOverExposed:
                        photo.setExposure(Exposure.OVER_EXPOSED);
                        break;
                    case R.id.rdoUnderExposed:
                        photo.setExposure(Exposure.UNDER_EXPOSED);
                        break;
                    case R.id.rdoPerfectExposure:
                        photo.setExposure(Exposure.WELL_EXPOSED);
                        break;
                }
            });

        } catch (Exception e) {
            Log.e("createPhotoProperties", e.toString());
        }
        return photo;
    }

    private void saveFilmData(SharedPreferences.Editor editor, LinkedList<FilmPhoto> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Photo List", json);
        editor.apply();
    }

    private void saveDigitalData(SharedPreferences.Editor editor, LinkedList<DigitalPhoto> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("Photo List", json);
        editor.apply();
    }

    private void loadData(SharedPreferences prefs){
        Gson gson = new Gson();
        String json = prefs.getString("Photo List", "");
        Type type = new TypeToken<LinkedList<Photo>>() {}.getType();
        mPhotos = gson.fromJson(json, type);

        if (mPhotos == null){
            mPhotos = new LinkedList<>();
        }
    }

}