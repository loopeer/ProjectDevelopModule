package com.loopeer.bottomimagepicker;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class ImageFolder implements Parcelable {

    public String dir;
    public String firstImagePath;
    public String name;
    public int count;
    public List<Image> images;

    public ImageFolder() {
        images = new ArrayList();
    }

    protected ImageFolder(Parcel in) {
        dir = in.readString();
        firstImagePath = in.readString();
        name = in.readString();
        count = in.readInt();
    }

    public static final Creator<ImageFolder> CREATOR = new Creator<ImageFolder>() {
        @Override
        public ImageFolder createFromParcel(Parcel in) {
            return new ImageFolder(in);
        }

        @Override
        public ImageFolder[] newArray(int size) {
            return new ImageFolder[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageFolder that = (ImageFolder) o;

        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = dir != null ? dir.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dir);
        dest.writeString(firstImagePath);
        dest.writeString(name);
        dest.writeInt(count);
    }
}