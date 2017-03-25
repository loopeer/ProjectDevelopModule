package com.loopeer.bottomimagepicker;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    public String url;
    public String name;
    public long time;

    public Image(String path, String name, long time){
        this.url = path;
        this.name = name;
        this.time = time;
    }

    public Image(String photoTakeurl) {
        url = photoTakeurl;
    }

    protected Image(Parcel in) {
        url = in.readString();
        name = in.readString();
        time = in.readLong();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (url != null ? !url.equals(image.url) : image.url != null) return false;
        return !(name != null ? !name.equals(image.name) : image.name != null);

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeLong(time);
    }
}
