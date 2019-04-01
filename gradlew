package com.tvd.tvd_employee.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tvd.tvd_employee.R;
import com.tvd.tvd_employee.posting.SendingData;
import com.tvd.tvd_employee.values.FunctionCall;

import java.util.ArrayList;
import java.util.Objects;

import static com.tvd.tvd_employee.values.Constant.EMPLOYEE_DETAILS_SUBMIT_FAILURE;
import static com.tvd.tvd_employee.values.Constant.EMPLOYEE_DETAILS_SUBMIT_SUCCESS;

public class Attendance_Posting extends Fragment implements {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int RequestPermissionCode = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ImageView click_image;
    View view;
    static FunctionCall functionCall;
    private Toolbar toolbar;
    String cons_imageextension = "", cons_ImgAdd = "", employee_id = "", file_encode = "", ImageDecode = "", device_ID = "", location = "", remark = "";
    static String pathname = "", pathextension = "", filename = "", emp_id = "";
    double lati = 0, longi = 0;
    private static Uri fileUri;
    EditText txt_location;
    LocationManager locationManager;
    Button submit;
    SendingData sendingData;
    EditText empid, remarks;
    ProgressDialog progressdialog;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case EMPLOYEE_DETAILS_SUBMIT_SUCCESS:
                    progressdialog.dismiss();
                    Toast.makeText(getActivity(), "Employee Details Submission successful..", Toast.LENGTH_SHORT).show();
                    break;

                case EMPLOYEE_DETAILS_SUBMIT_FAILURE:
                    progressdialog.dismiss();
                    Toast.makeText(getActivity(), "Employee Details Submission Failure!!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    public Attendance_Posting() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_attendance__posting, container, false);
        initialize_view();
        getLocation();
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // ActivityCompat#requestPermissions here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        if (tm != null) {
            device_ID = tm.getDeviceId();
        }

        txt_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                submit.setEnabled(!TextUtils.isEmpty(charSequence.toString().trim()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                submit.setEnabled(!TextUtils.isEmpty(charSequence.toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                submit.setEnabled(!TextUtils.isEmpty(editable.toString().trim()));
            }
        });

        emp_id = "12345";
        empid.setText(emp_id);
        return view;
    }

    private void initialize_view() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = view.findViewById(R.id.toolbar);
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        click_image = view.findViewById(R.id.im_current_read_image);

        txt_location = view.findViewById(R.id.location);
 