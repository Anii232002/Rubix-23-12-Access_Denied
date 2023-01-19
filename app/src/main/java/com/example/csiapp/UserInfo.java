package com.example.csiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.HashMap;

public class UserInfo extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private ImageView pickDateBtn, heightpick, weightpick;
    private Button muscle_g, weight_g, weight_l, set;
    public TextView selectedDateTV, height, weight, bmi_tv, ideal_wt, target_wt;
    private LocalDate lb;
    private String bmi = "", height_str = "", weight_str = "", target = "", a = "";
    private int age;
    ImageView back;

    ProgressDialog pd;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);
        heightpick = findViewById(R.id.idBtnPickHeight);
        height = findViewById(R.id.height);
        weightpick = findViewById(R.id.idBtnPickWeight);
        weight = findViewById(R.id.weight);
        bmi_tv = findViewById(R.id.bmi);
        ideal_wt = findViewById(R.id.idealweight);

        muscle_g = findViewById(R.id.muscle_gain);
        weight_g = findViewById(R.id.weight_gain);
        weight_l = findViewById(R.id.weight_loss);
        set = findViewById(R.id.submit);
        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        back = findViewById(R.id.back_user_info);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        muscle_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target = "muscle gain";
                muscle_g.setBackgroundColor(getResources().getColor(R.color.purple));
                weight_g.setBackgroundColor(getResources().getColor(R.color.grey));
                weight_l.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });
        weight_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target = "weight gain";
                weight_g.setBackgroundColor(getResources().getColor(R.color.purple));
                weight_l.setBackgroundColor(getResources().getColor(R.color.grey));
                muscle_g.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });
        weight_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target = "weight loss";
                weight_l.setBackgroundColor(getResources().getColor(R.color.purple));
                muscle_g.setBackgroundColor(getResources().getColor(R.color.grey));
                weight_g.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });

        weightpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(35, 120, 2);

                if (weight_str != "" && height_str != "") {
                    bmi_tv.setText(calculateBMI(Double.parseDouble(height_str), Double.parseDouble(weight_str)));
                    bmi = bmi_tv.getText().toString();
                    ideal_wt.setText(idealWeight(Double.parseDouble(height_str), Double.parseDouble(weight_str), age));
                }


            }
        });
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        UserInfo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
//                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    lb = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                                    age = calculateAge(lb, LocalDate.of(2023, 1, 20));
                                    selectedDateTV.setText("Age :" + age);
                                }


                            }
                        },

                        year, month, day);

                datePickerDialog.show();
            }
        });

        heightpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(120, 200, 1);


            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (target != "" && weight_str != "" && height_str != "" && bmi != "0.0") {
                    uploadData(target, weight_str, height_str, bmi, age);
                } else {
                    Toast.makeText(UserInfo.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void show(int min, int max, int i) {

        final Dialog d = new Dialog(UserInfo.this);
        WindowManager.LayoutParams params = d.getWindow().getAttributes();
        params.width = 600; // set width
        params.height = 800; // set height
        d.getWindow().setAttributes(params);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);

        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(max);
        np.setMinValue(min);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 1) {
                    height.setText("Height :" + String.valueOf(np.getValue()) + " cms");
                    height_str = String.valueOf(np.getValue());
                } else if (i == 2) {
                    weight.setText("Weight :" + String.valueOf(np.getValue()) + " kgs");
                    weight_str = String.valueOf(np.getValue());
                    bmi_tv.setText(calculateBMI(Double.parseDouble(height_str), Double.parseDouble(weight_str)));
                    bmi = bmi_tv.getText().toString();
                    ideal_wt.setText(idealWeight(Double.parseDouble(height_str), Double.parseDouble(weight_str), age));
                }

                String.valueOf(np.getValue());
                d.dismiss();
            }
        });

        d.show();


    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Period.between(birthDate, currentDate).getYears();
            }
        } else {
            return 0;
        }
        return 0;
    }

    public static String calculateBMI(double height, double weight) {
        double heightInMeters = height / 100.0;

// calculate BMI
        double bmi = weight / (heightInMeters * heightInMeters);

        return String.valueOf(bmi);
    }

    public static String idealWeight(double height, double weight, int age) {
        double idealWeightLow, idealWeightHigh;
        if (age <= 25) {
            idealWeightLow = (height - 100) - (height - 150) / 4;
            idealWeightHigh = (height - 100) - (height - 150) / 4 + (height - 150) / 10;
        } else {
            idealWeightLow = (height - 100) - (height - 150) / 4 - (height - 150) / 20;
            idealWeightHigh = (height - 100) - (height - 150) / 4 + (height - 150) / 20;
        }
        return idealWeightLow + "-" + idealWeightHigh;
    }

    private void uploadData(String t, String weight_str, String height_str, String bmi, int age) {
        FirebaseUser user = auth.getCurrentUser();
        pd.setMessage("Uploading");
        pd.show();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("uid", user.getUid());
        hashMap.put("uEmail", user.getEmail());
//        hashMap.put("pId",timestamp);
        hashMap.put("Target", t);
        hashMap.put("weight", weight_str);
        hashMap.put("height", height_str);
        hashMap.put("bmi", bmi);
        hashMap.put("age", age);
        // Putting data in firebase

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserData");
        ref.child(user.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Toast.makeText(UserInfo.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        Log.d("CreatePost", "onSuccess: uploaded" + hashMap);

                        // Starting Main Activity
                        startActivity(new Intent(UserInfo.this, MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UserInfo.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}