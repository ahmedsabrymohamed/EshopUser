package com.example.eraky.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mine on 22/02/18.
 */

public class RateResponse implements Parcelable{

    private int success;
    private float rate;
    private String message;

    protected RateResponse(Parcel in) {
        success = in.readInt();
        rate = in.readFloat();
        message = in.readString();
    }

    public static final Creator<RateResponse> CREATOR = new Creator<RateResponse>() {
        @Override
        public RateResponse createFromParcel(Parcel in) {
            return new RateResponse(in);
        }

        @Override
        public RateResponse[] newArray(int size) {
            return new RateResponse[size];
        }
    };

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(success);
        parcel.writeFloat(rate);
        parcel.writeString(message);
    }
}

