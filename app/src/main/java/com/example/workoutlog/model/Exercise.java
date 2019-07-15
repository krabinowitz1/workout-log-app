package com.example.workoutlog.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.workoutlog.db.MyTypeConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "exercise_table")
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String workoutName;

    public String name;

    public int numSets;

    public ArrayList<String> reps = new ArrayList<>();

    public ArrayList<String> weights = new ArrayList<>();

    private static final transient String EMPTY_FIELD = " ";

    private static final transient int MINIMUM_SETS = 1;

    @Ignore
    public List<ExerciseSet> exerciseSetList;

    public Exercise(String name) {
        //this(UUID.randomUUID().toString(), name);
        weights.add(EMPTY_FIELD);
        reps.add(EMPTY_FIELD);
        this.name = name;
        numSets = MINIMUM_SETS;
        exerciseSetList = new ArrayList<>();
    }

    /*
    public Exercise(String name) {
        //this.id = id;
        this.name = name;
        exerciseSetList = new ArrayList<>();
    }
    */


    /*
    protected Exercise(Parcel in) {
        id = in.readString();
        workoutName = in.readString();
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
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    /*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(workoutName);
        dest.writeString(name);
        dest.writeInt(sets);
        dest.writeStringList(reps);
        dest.writeStringList(weights);
    }
    */
}

