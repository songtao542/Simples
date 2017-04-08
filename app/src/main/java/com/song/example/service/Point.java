package com.song.example.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangsongtao on 2016/12/25.
 */

public class Point implements Parcelable {
    int x;
    int y;


    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected Point(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point o = (Point) obj;
            if (this.x == o.x && this.y == o.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
        dest.writeInt(this.y);
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel source) {
            return new Point(source);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };
}
