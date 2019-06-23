package com.example.workoutlog.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.workoutlog.db.MyTypeConverters;

import java.util.ArrayList;
import java.util.List;

@Entity(primaryKeys = {"workout", "name"}, tableName = "exercise_table")
public class Exercise implements Parcelable {
    @NonNull
    public String workout;

    @NonNull
    public String name;

    public int sets = 0;

    @TypeConverters(MyTypeConverters.class)
    public ArrayList<String> reps = new ArrayList<>();

    @TypeConverters(MyTypeConverters.class)
    public ArrayList<String> weights = new ArrayList<>();

    public Exercise(String name) {
        this.name = name;

        weights.add("");
        reps.add("");
        sets++;
    }

    protected Exercise(Parcel in) {
        workout = in.readString();
        name = in.readString();
        sets = in.readInt();
        reps = in.createStringArrayList();
        weights = in.createStringArrayList();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workout);
        dest.writeString(name);
        dest.writeInt(sets);
        dest.writeStringList(reps);
        dest.writeStringList(weights);
    }
}

