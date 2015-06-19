package com.dk.mensajero.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matias on 19/06/15.
 */
public class ParcelableUser implements Parcelable {

    String name;
    String profilePicture;
    String phone;

    public ParcelableUser() { ; };

    public ParcelableUser(Parcel in) {
        readFromParcel(in);
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(profilePicture);
        dest.writeString(phone);
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    private void readFromParcel(Parcel in) {
        name = in.readString();
        profilePicture = in.readString();
        phone = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelableUser createFromParcel(Parcel in) {
            return new ParcelableUser(in); }
        public ParcelableUser[] newArray(int size) {
            return new ParcelableUser[size]; } };

}
