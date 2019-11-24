package com.example.aad4.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.aad4.db.DBHelper.COLUMN_FOREIGN_ID;

@DatabaseTable(tableName = "comments_table")
public class Comment extends BaseObservable implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String text;

    @DatabaseField(foreign = true, columnName = COLUMN_FOREIGN_ID)
    private TouristAttraction touristAttraction;

    public Comment() {
    }


    protected Comment(Parcel in) {
        id = in.readInt();
        text = in.readString();
        touristAttraction = in.readParcelable(TouristAttraction.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeParcelable(touristAttraction, flags);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(com.example.aad4.BR.text);
    }

    public TouristAttraction getTouristAttraction() {
        return touristAttraction;
    }

    public void setTouristAttraction(TouristAttraction touristAttraction) {
        this.touristAttraction = touristAttraction;
    }
}
