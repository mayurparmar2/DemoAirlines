package com.demo.example.model;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("currentPage")
    private int currentPage;

    @SerializedName("currentItems")
    private int currentItems;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalItems")
    private int totalItems;

    // Getters
}
