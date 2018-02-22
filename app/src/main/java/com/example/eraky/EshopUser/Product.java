package com.example.eraky.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{

    private int Category_id;
    private int id;
    private String name;
    private String description;
    private String price;
    private double rate;
    private String image;
    private int prange;
    private int trend;

    public int getTrend() {
        return trend;
    }

    public void setTrend(int trend) {
        this.trend = trend;
    }

    protected Product(Parcel in) {
        Category_id = in.readInt();
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        rate = in.readDouble();
        image = in.readString();
        prange=in.readInt();
        trend=in.readInt();
    }

    public Product() {
    }

    public int getRange() {
        return prange;
    }

    public void setRange(int range) {
        this.prange = range;
    }

    public void setCategory_id(int category_id) {
        Category_id = category_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory_id() {

        return Category_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public double getRate() {
        return rate;
    }

    public String getImage() {
        return image;
    }

    public static Creator<Product> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Category_id);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
        parcel.writeDouble(rate);
        parcel.writeString(image);
        parcel.writeInt(prange);
        parcel.writeInt(trend);
    }
}
