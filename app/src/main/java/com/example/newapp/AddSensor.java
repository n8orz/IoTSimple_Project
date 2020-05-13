package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddSensor extends AppCompatActivity {
    private DatabaseReference rootDb,sensorChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);


        rootDb = FirebaseDatabase.getInstance().getReference();
        sensorChild = rootDb.child("sensors");
        final Spinner options = (Spinner) findViewById(R.id.sensorSpinner);
        final List<String> sensors = new ArrayList<>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sensors);
//        categories.add(rootDb.child("sensors").child("sensor3").getValue(String.class));
//        categories.add(sensor2);
//        categories.add(sensor3);
        sensorChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sensors.add(dataSnapshot.child("sensor1").getValue(String.class));
                sensors.add(dataSnapshot.child("sensor2").getValue(String.class));
                sensors.add(dataSnapshot.child("sensor3").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        options.setAdapter(dataAdapter);
    }
}
