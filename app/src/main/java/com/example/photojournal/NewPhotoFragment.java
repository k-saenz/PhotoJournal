package com.example.photojournal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.photojournal.models.DigitalPhoto;
import com.example.photojournal.models.Exposure;
import com.example.photojournal.models.FilmPhoto;
import com.example.photojournal.models.Lens;
import com.example.photojournal.models.Photo;
import com.example.photojournal.models.PhotoFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NewPhotoFragment extends Fragment {

    public NewPhotoFragment() {
        super(R.layout.fragment_new_photo);
    }

    public static NewPhotoFragment newInstance() {
        NewPhotoFragment fragment = new NewPhotoFragment();

        return fragment;
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

        //Inflate Dialogs for date and time pickers
        createDateTimeDialogs(v);

        RadioGroup rdoGrpPhotoType = (RadioGroup) v.findViewById(R.id.rdoGrpPhotoType);
        TextView filmOrRes = (TextView) v.findViewById(R.id.txtFilmOrRes);

        rdoGrpPhotoType.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId){
                case R.id.rdoFilm:
                    filmOrRes.setHint("Film Used");
                    submit("film", v);
                    break;
                case R.id.rdoDigital:
                    filmOrRes.setHint("Resolution");
                    submit("digital", v);
                    break;
            }
        });
        // Inflate the layout for this fragment
        return v;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void submit(String photoMedium, View v){
        Button submit = v.findViewById(R.id.btnSubmit);
        submit.setOnClickListener(view -> {
            Photo photo = PhotoFactory.createPhoto(photoMedium);

            //General
            EditText date = (EditText) view.findViewById(R.id.txtDate);
            TextView time = view.findViewById(R.id.txtTime);
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

            photo.setShutterSpeed(Integer.parseInt(shutterSpeed.getText().toString()));
            photo.setAperture(Float.parseFloat(aperture.getText().toString()));
            photo.setIso(Integer.parseInt(iso.getText().toString()));
            photo.setLens(lens.getText().toString());
            photo.setCamera(camera.getText().toString());

            //About
            TextView description = view.findViewById(R.id.txtDescription);
            photo.setDescription(description.getText().toString());

            RadioGroup rdoGrpExposure = view.findViewById(R.id.rdoGrpExposure);
            rdoGrpExposure.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                switch (checkedId){
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

            EditText filmOrRes = view.findViewById(R.id.txtFilmOrRes);
            String filmResString = filmOrRes.getText().toString();
            //Subclass specific properties
            if (photoMedium.equalsIgnoreCase("film")){
                FilmPhoto film = (FilmPhoto) photo;
                film.setFilmUsed(filmResString);
            } else {
                DigitalPhoto digital = (DigitalPhoto) photo;
                digital.setResolution(filmResString);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createDateTimeDialogs(View v){

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

    private FilmPhoto forFilmSubmit(Photo p){
        FilmPhoto f = (FilmPhoto) p;
        return f;
    }

    private DigitalPhoto forDigitalSubmit(Photo p){
        DigitalPhoto d = (DigitalPhoto) p;
        return d;
    }



}