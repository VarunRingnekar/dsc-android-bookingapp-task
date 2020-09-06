package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateBookingActivity extends AppCompatActivity {

    Bundle extras;

    private TextView tvDisplayDate, tvDisplayTime, tvBookingClientName, tvBookingClientAddress;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private String clientName, clientAddress, uploadDate, uploadTime, uploadDateTime;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        extras = getIntent().getExtras();

        clientName = extras.getString("clientName");
        clientAddress = extras.getString("clientAddress");

        tvDisplayDate = findViewById(R.id.tvBookingCreateDate);
        tvDisplayTime = findViewById(R.id.tvBookingCreateTime);
        tvBookingClientName = findViewById(R.id.tvBookingClientName);
        tvBookingClientAddress = findViewById(R.id.tvBookingClientAddress);

        tvBookingClientName.setText("Client Name: " + clientName);
        tvBookingClientAddress.setText("Client Address: " + clientAddress);

        cal = Calendar.getInstance();

        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting current date to set as default when user opens the datePicker

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateBookingActivity.this,
                        android.R.style.Widget_Holo_Light,
                        mDateSetListener,
                        year, month, day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                uploadDate = "";
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                tvDisplayDate.setText(date);
                uploadDate = year + "/" + String.format("%02d", month) + "/" + String.format("%02d", dayOfMonth);

            }
        };

        tvDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting current time to set as default when user opens timePicker

                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateBookingActivity.this,
                        android.R.style.Widget_Holo_Light,
                        mTimeSetListener,
                        hour, min, false);
                timePickerDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                uploadTime = "";
                String time = String.format("%02d",hourOfDay)  + ":" + String.format("%02d", minute);
                tvDisplayTime.setText(time);
                uploadTime = time;
            }
        };



    }

    public void btnUploadBooking(View view) {

        //Combining date and time before uploading, so that we'll be able to sort it while retrieving

        uploadDateTime = uploadDate + "T" + uploadTime;

        BookingClass bookingData = new BookingClass(uploadDateTime, clientName, clientAddress);

        String currentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Bookings")
                .child(currentDateTime).setValue(bookingData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CreateBookingActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(CreateBookingActivity.this, MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateBookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}