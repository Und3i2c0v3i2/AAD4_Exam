package com.example.aad4.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "attraction_table")
public class TouristAttraction extends BaseObservable implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String desc;
    @DatabaseField
    private String imgUri;
    @DatabaseField
    private String address;
    @DatabaseField
    private int phone;
    @DatabaseField
    private String webAddress;
    @DatabaseField
    private String businessHours;
    @DatabaseField
    private double price;

    private List<Comment> comments;

    public TouristAttraction(){}


    protected TouristAttraction(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        imgUri = in.readString();
        address = in.readString();
        phone = in.readInt();
        webAddress = in.readString();
        businessHours = in.readString();
        price = in.readDouble();
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Creator<TouristAttraction> CREATOR = new Creator<TouristAttraction>() {
        @Override
        public TouristAttraction createFromParcel(Parcel in) {
            return new TouristAttraction(in);
        }

        @Override
        public TouristAttraction[] newArray(int size) {
            return new TouristAttraction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(imgUri);
        dest.writeString(address);
        dest.writeInt(phone);
        dest.writeString(webAddress);
        dest.writeString(businessHours);
        dest.writeDouble(price);
        dest.writeTypedList(comments);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.aad4.BR.name);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(com.example.aad4.BR.desc);
    }

    @Bindable
    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
        notifyPropertyChanged(com.example.aad4.BR.imgUri);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(com.example.aad4.BR.address);
    }

    private String phoneString;

    @Bindable
    public String getPhoneString() {
        if(phone == 0) {
            this.phoneString = null;
        } else {
            this.phoneString = String.valueOf(phone);
        }
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        try {
            this.phone = Integer.parseInt(phoneString);
        } catch(NumberFormatException ex){
            this.phone = 0;//default value
        }

        notifyPropertyChanged(com.example.aad4.BR.phoneString);
    }



    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Bindable
    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
        notifyPropertyChanged(com.example.aad4.BR.webAddress);
    }

    @Bindable
    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
        notifyPropertyChanged(com.example.aad4.BR.businessHours);
    }

    private String priceString;

    @Bindable
    public String getPriceString() {
        if(price == 0) {
            this.priceString = null;
        } else {
            this.priceString = String.valueOf(price);
        }
        return priceString;
    }

    public void setPriceString(String priceString) {
        try {
            this.price = Double.parseDouble(priceString);
        } catch(NumberFormatException ex){
            this.price = 0;//default value
        }

        notifyPropertyChanged(com.example.aad4.BR.priceString);
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
