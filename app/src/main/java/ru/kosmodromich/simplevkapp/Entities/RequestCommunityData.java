package ru.kosmodromich.simplevkapp.Entities;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestCommunityData implements Parcelable {
    private String name;
//    private String leftAgeValue;
//    private String rightAgeValue;
//    private String userCity;
//    private boolean onlyOnline;

//    public RequeCommunityData(String userName, String leftAgeValue, String rightAgeValue,
//                       String userCity, boolean onlyOnline) {
//        this.userName = userName;
//        this.leftAgeValue = leftAgeValue;
//        this.rightAgeValue = rightAgeValue;
//        this.userCity = userCity;
//        this.onlyOnline = onlyOnline;
//    }

    public RequestCommunityData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private RequestCommunityData(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }

    public static final Creator<RequestCommunityData> CREATOR = new Creator<RequestCommunityData>() {
        @Override
        public RequestCommunityData createFromParcel(Parcel in) {
            return new RequestCommunityData(in);
        }

        @Override
        public RequestCommunityData[] newArray(int size) {
            return new RequestCommunityData[size];
        }
    };
}