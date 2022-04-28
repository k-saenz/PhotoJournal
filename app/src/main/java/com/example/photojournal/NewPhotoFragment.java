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
        Bundle args = getArguments();
        Photo photoDataForEdit = null;

        if (args != null){
            photoDataForEdit = (Photo) args.getSerializable("photo");
        }

        Gson gson = new Gson();

        //Inflate Dialogs for date and time pickers
        createDateTimeDialogs(v);

        RadioGroup rdoGrpPhotoType = (RadioGroup) v.findViewById(R.id.rdoGrpPhotoType);
        TextView filmOrRes = (TextView) v.findViewById(R.id.txtFilmOrRes);
        String filmResString = filmOrRes.getText().toString();

        Button submit = v.findViewById(R.id.btnSubmit);

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

        //Fill data fields with data from obj if user wants to edit their entry
        if (photoDataForEdit != null){
            editPhoto(photoDataForEdit, v);
        }

        submit.setOnClickListener(view -> {

            Photo newPhoto = setPhotoProperties(photo, v);

            if (newPhoto != null){
                String toastText = "Submitted " + newPhoto.getName();
                Toast toast = Toast.makeText(view.getContext(), toastText, Toast.LENGTH_SHORT);
                toast.show();

                MainActivity.photoDB.photoDAO().addPhoto(newPhoto);
                getActivity().onBackPressed();
            }
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
            int currentDay = LocalDateTime.now().getDayOfMonth();
            int currentMonth = LocalDateTime.now().getMonthValue();
            int currentYear = LocalDateTime.now().getYear();

            DatePickerDialog picker = new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");

                dateView.setText(LocalDate.of(year, month, day).format(pattern));
            }, currentYear, currentMonth, currentDay);

            picker.show();
        });

        timeView.setOnClickListener(view -> {
            int currentHour = LocalDateTime.now().getHour();
            int currentMinute = LocalDateTime.now().getMinute();

            TimePickerDialog picker = new TimePickerDialog(getContext(), (timePicker, hour, minute) -> {
                DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
                timeView.setText(LocalTime.of(hour, minute).format(pattern));
            }, currentHour, currentMinute, false);

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

            String strName = name.getText().toString();

            if (strName.equalsIgnoreCase("")){
                Toast toast = Toast.makeText(view.getContext(), "This entry needs a name", Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }

            photo.setName(strName);

            try {
                LocalDateTime datetime = LocalDateTime.of(
                        LocalDate.parse(date.getText().toString(), DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                        LocalTime.parse(time.getText().toString(), DateTimeFormatter.ofPattern("HH:mm")));
                photo.setDateTime(datetime);
            } catch (Exception e){
                photo.setDateTime(LocalDateTime.now());
            }

            //Camera Settings
            TextView shutterSpeed = view.findViewById(R.id.txtShutterSpeed);
            TextView aperture = view.findViewById(R.id.txtAperture);
            TextView iso = view.findViewById(R.id.txtIso);
            TextView lens = view.findViewById(R.id.txtLens);
            TextView camera = view.findViewById(R.id.txtCamera);
            TextView filmOrRes = view.findViewById(R.id.txtFilmOrRes);

            try {
                photo.setShutterSpeed(Integer.parseInt(shutterSpeed.getText().toString()));
                photo.setAperture(Float.parseFloat(aperture.getText().toString()));
                photo.setIso(Integer.parseInt(iso.getText().toString()));
            } catch (NumberFormatException nfe){
                photo.setShutterSpeed(0);
                photo.setAperture(0);
                photo.setIso(0);
                Log.e("NumberFormatException", nfe.toString());
            }

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
            Log.e("Something went wrong with createPhotoProperties", e.toString());
        }
        return photo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void editPhoto(Photo p, View view){
        //Get Views
        //General
        EditText name = (EditText) view.findViewById(R.id.txtName);
        EditText date = (EditText) view.findViewById(R.id.txtDate);
        TextView time = view.findViewById(R.id.txtTime);
        //Camera Settings
        TextView shutterSpeed = view.findViewById(R.id.txtShutterSpeed);
        TextView aperture = view.findViewById(R.id.txtAperture);
        TextView iso = view.findViewById(R.id.txtIso);
        TextView lens = view.findViewById(R.id.txtLens);
        TextView camera = view.findViewById(R.id.txtCamera);
        TextView filmOrRes = view.findViewById(R.id.txtFilmOrRes);
        //About
        TextView description = view.findViewById(R.id.txtDescription);
        RadioGroup rdoGrpExposure = view.findViewById(R.id.rdoGrpExposure);
        RadioGroup rdoGrpPhotoType = view.findViewById(R.id.rdoGrpPhotoType);

        //Set Text
        //General
        name.setText(p.getName());
        date.setText(p.getDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        time.setText(p.getDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        //Camera Settings
        try{
            shutterSpeed.setText(p.getShutterSpeed());
            aperture.setText(Float.toString(p.getAperture()));
            iso.setText(p.getIso());
        } catch (Exception e){
            shutterSpeed.setText("");
            aperture.setText("");
            iso.setText("");
            Log.e("editPhoto, setting Number fields", e.toString());
        }

        lens.setText(p.getLens());
        camera.setText(p.getCamera());
        filmOrRes.setText(p.getFilmOrRes());
        //About
        description.setText(p.getDescription());
        try {
            switch (p.getExposure()){
                case OVER_EXPOSED:
                    rdoGrpExposure.check(R.id.rdoOverExposed);
                    break;
                case UNDER_EXPOSED:
                    rdoGrpExposure.check(R.id.rdoUnderExposed);
                    break;
                case WELL_EXPOSED:
                    rdoGrpExposure.check(R.id.rdoPerfectExposure);
                    break;
            }
        } catch (NullPointerException nuEx){
            Log.e("Exposure was null", nuEx.toString());
        }


        if (p.isFilm()){
            rdoGrpPhotoType.check(R.id.rdoFilm);
        } else {
            rdoGrpPhotoType.check(R.id.rdoDigital);
        }

    }
}