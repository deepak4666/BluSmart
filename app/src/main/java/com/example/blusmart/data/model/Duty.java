package com.example.blusmart.data.model;
import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Duty implements Serializable, Parcelable
{

    @SerializedName("assigned")
    @Expose
    private String assigned;
    @SerializedName("id")
    private int id;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("type")
    @Expose
    private String type;

    public final static Parcelable.Creator<Duty> CREATOR = new Creator<Duty>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Duty createFromParcel(Parcel in) {
            return new Duty(in);
        }

        public Duty[] newArray(int size) {
            return (new Duty[size]);
        }

    }
            ;
    private final static long serialVersionUID = 8027183622046263635L;

    protected Duty(Parcel in) {
        this.assigned = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Duty() {
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(assigned);
        dest.writeValue(id);
        dest.writeValue(state);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }

}