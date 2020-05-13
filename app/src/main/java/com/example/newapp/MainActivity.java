package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pgsBar;
    private Handler hdlr = new Handler();
    private FirebaseAuth mAuth;
    private DatabaseReference rootDb;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        pgsBar = (ProgressBar) findViewById(R.id.waterLevel);
        //initialize buttons
        final Button signOut = (Button) findViewById(R.id.signOut);
        final Button addSensor = (Button) findViewById(R.id.addButton);
//        Button test = (Button) findViewById(R.id.pButton);


        rootDb = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userData = rootDb.child("users").child(user.getUid()).child("dataList");
//        System.out.println(user.getUid());
//        DatabaseReference userData = users.child(user.getUid()).child("dataList");

       new Thread(new Runnable() {
           @Override
           public void run() {
               signOut.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       FirebaseAuth.getInstance().signOut();
                       Intent signOut = new Intent(getApplicationContext(), Firebase_Init.class);
                       startActivity(signOut);
                   }
               });
           }
       }).start();
//        test.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                String testy = "ThisisaTest";
//                writeNewData("7:34",user.getUid(),75);
//            }
//        });

        addSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent addScreen = new Intent(getApplicationContext(), AddSensor.class);
                startActivity(addScreen);
            }
        });

        new Thread(new Runnable() {
            public void run () {
            userData.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded (@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                        {
                            String sensor, percent;
                            Long timeStamp;
                            TextView type = (TextView) findViewById(R.id.sensorType);
                            TextView lvl = (TextView) findViewById(R.id.sensorData);
                            TextView time = (TextView) findViewById(R.id.timeView);
                            sensor = dataSnapshot.child("sensor").getValue(String.class);
                            percent = dataSnapshot.child("data_point").getValue(String.class);
                            timeStamp = dataSnapshot.child("time").getValue(Long.class);
    //                System.out.println(timeStamp);

                            type.setText(sensor);
                            lvl.setText(percent + "%");
                            pgsBar.setProgress(Integer.parseInt(percent));
                            time.setText(convertTime(timeStamp));
    //                System.out.println(sensor);
                        }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    throw databaseError.toException();
                }
            });
            }
        }).start();
//        userData.addValueEventListener(new ValueEventListener() {
////            Create user object to only query the correct user's data
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String time, sensor, percent;
//                sensor = dataSnapshot.child("sensor").getValue(String.class);
//                percent = dataSnapshot.child("data_point").getValue(String.class);
//                time = dataSnapshot.child("time").getValue(String.class);
////                TextView data = (TextView) findViewById(R.id.sensorData);
////                data.setText(sensor);
//                System.out.println("new data " + sensor);
//                System.out.println(time);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });


//    private void writeNewData(String time, String userId, int percent) {
//        DataEntry de = new DataEntry(time,userId,percent);
//        mDatabase.child("waterData").child("sensor_data").child("sensor_type").child("water").setValue(de);
//    }
    }
    //convert timestamp to string
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}