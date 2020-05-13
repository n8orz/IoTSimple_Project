package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class History extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference rootDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rootDb = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userData = rootDb.child("users").child(user.getUid()).child("dataList");
        Query dataEntries = userData.orderByChild("time").limitToFirst(10);

        dataEntries.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded (@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                String sensor, percent;
                Long timeStamp;
                TextView type = (TextView) findViewById(R.id.sensorType);
                TextView lvl = (TextView) findViewById(R.id.sensorData);TextView time = (TextView) findViewById(R.id.timeView);
                sensor = dataSnapshot.child("sensor").getValue(String.class);
                percent = dataSnapshot.child("data_point").getValue(String.class);
                timeStamp = dataSnapshot.child("time").getValue(Long.class);
                //                System.out.println(timeStamp);
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
}
