package com.example.photojournal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.photojournal.models.Photo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import pl.aprilapps.easyphotopicker.EasyImage;

public class NewPhotoFragment extends Fragment {

    private Context mContext;
    private LinkedList<Photo> mPhotos;
    //Exposure constants
    private final String UNDER_EXPOSED = "under";
    private final String OVER_EXPOSED = "over";
    private final String PERFECT_EXPOSURE = "perfect";

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
            int photoId = args.getInt("photoId");
            photoDataForEdit = MainActivity.photoDB.photoDAO().getPhoto(photoId);

        }
        //Inflate Dialogs for date and time pickers
        createDateTimeDialogs(v);

        RadioGroup rdoGrpPhotoType = v.findViewById(R.id.rdoGrpPhotoType);
        TextView filmOrRes = v.findViewById(R.id.txtFilmOrRes);

        Button submit = v.findViewById(R.id.btnSubmit);
        Button addPicture = v.findViewById(R.id.btnAddPicture);

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
            Photo fromEdit = editPhoto(photoDataForEdit, v);
            Button btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(view -> {
                MainActivity.photoDB.photoDAO().deletePhoto(fromEdit);
                Toast toast = Toast.makeText(view.getContext(), "Deleted " + fromEdit.getName(), Toast.LENGTH_SHORT);
                toast.show();
                getActivity().onBackPressed();
            });
        }

        EasyImage easyImage = new EasyImage.Builder(getActivity())
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("photojournal Saved Pictures")
                .allowMultiple(false)
                .build();

        addPicture.setOnClickListener(view -> {
            easyImage.openChooser(this);
        });

        Photo finalPhotoDataForEdit = photoDataForEdit;
        submit.setOnClickListener(view -> {
            Photo newPhoto = setPhotoProperties(photo, v);

            if (newPhoto != null){
                String toastText = "Submitted " + newPhoto.getName();
                Toast toast = Toast.makeText(view.getContext(), toastText, Toast.LENGTH_SHORT);
                toast.show();

                if (finalPhotoDataForEdit != null){
                    MainActivity.photoDB.photoDAO().deletePhoto(finalPhotoDataForEdit);
                }

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

            RadioButton rdoOver = view.findViewById(R.id.rdoOverExposed);
            RadioButton rdoUnder = view.findViewById(R.id.rdoUnderExposed);

            if (rdoOver.isChecked()){
                photo.setExposure(OVER_EXPOSED);
            } else if (rdoUnder.isChecked()){
                photo.setExposure(UNDER_EXPOSED);
            } else {
                photo.setExposure(PERFECT_EXPOSURE);
            }

            RadioButton rdoFilm = view.findViewById(R.id.rdoFilm);
            RadioButton rdoDigital = view.findViewById(R.id.rdoDigital);

            if (rdoFilm.isChecked()){
                photo.setFilm(true);
            } else {
                photo.setFilm(false);
            }

        } catch (Exception e) {
            Log.e("Something went wrong with createPhotoProperties", e.toString());
        }
        return photo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Photo editPhoto(Photo p, View view){
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
                case PERFECT_EXPOSURE:
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

        return p;
    }
}