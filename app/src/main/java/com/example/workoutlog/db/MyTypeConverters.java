package com.example.workoutlog.db;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import androidx.room.TypeConverter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyTypeConverters {
    @TypeConverter
    public static String fromList(List<String> list) {
        String value = "";

        for(String s : list) {
            value += s + ",";
        }

        return value;
    }

    @TypeConverter
    public static ArrayList<String> toList(String value) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(value.split(",")));
        for(String s : list) {
        }
        return list;
    }

    /*
    @TypeConverter
    public static String fromStringList(List<String> strings) {
        if(strings == null) {
            return null;
        }

        StringWriter result = new StringWriter();
        JsonWriter json = new JsonWriter(result);

        try {
            json.beginArray();

            for(String s : strings) {
                json.value(s);
            }

            json.endArray();
            json.close();
        }

        catch (IOException e) {
            Log.e(TAG, "Exception creating JSON", e);
        }

        return result.toString();
    }

    /*
    @TypeConverter
    public static ArrayList<String> toStringList(String strings) {
        if(strings == null) {
            return null;
        }

        StringReader reader = new StringReader(strings);
        JsonReader json = new JsonReader(reader);
        ArrayList<String> result = new ArrayList<>();

        try {
           json.beginArray();

           while (json.hasNext()) {
               result.add(json.nextString());
           }
        }

        catch (IOException e) {
            Log.e(TAG, "Exception parsing JSON", e);
        }

        return result;
    }
    */
}