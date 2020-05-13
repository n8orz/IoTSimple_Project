package com.example.newapp;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DataEntry {
    public String mEmail;
    public String mSensorName;
    public String mDataVal;
    public String mTimeStamp;
    public DataEntry(){

    }

    public DataEntry(String email, String sensorName, String dataVal, String timeStamp){
        mEmail = email;
        mSensorName = sensorName;
        mDataVal = dataVal;
        mTimeStamp = timeStamp;
    }
    public String getEmail(){
        return mEmail;
    }
    public void setEmail(String email){ this.mEmail = email; }
    public String getSensorName(){return mSensorName;}
    public void setSensorName(String sensorName){ this.mSensorName=sensorName; }
    public String getDataVal(){return mDataVal;}
    public void setDataVal(String dataVal){ this.mDataVal=dataVal; }
    public String getTimeStamp(){return mTimeStamp;}
    public void setTimeStamp(String timeStamp){ this.mTimeStamp=timeStamp; }


}
