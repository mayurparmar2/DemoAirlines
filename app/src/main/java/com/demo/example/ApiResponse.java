package com.demo.example;

import com.demo.example.model.Pagination;
import com.demo.example.model.YourDataModel;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    @SerializedName("data")
    private List<YourDataModel> data;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<YourDataModel> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}

