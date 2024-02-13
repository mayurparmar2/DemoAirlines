package com.demo.example.model;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public String fromCountryLangList(List<Airline> value) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Airline>>() {}.getType();
        return gson.toJson(value, type);
    }

    @TypeConverter
    public List<Airline> toCountryLangList(String value) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Airline>>() {}.getType();
        return gson.fromJson(value, type);
    }

    public <T> T fromJson(Gson gson, String json, Class<T> classOfT) {
        Type type = new TypeToken<T>() {}.getType();
        return gson.fromJson(json, type);
    }
}
