package com.example.workoutlog.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList<String> list = new ArrayList<>(Arrays.asList(value.split("\\s*,\\s*")));
        return list;
    }
}
