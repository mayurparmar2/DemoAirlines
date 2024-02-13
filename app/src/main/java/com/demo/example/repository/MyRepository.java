package com.demo.example.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.example.db.YourDao;
import com.demo.example.db.YourRoomDatabase;
import com.demo.example.model.YourDataModel;
import com.demo.example.network.ApiService;
import com.demo.example.network.ApiServiceGenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepository {
    private ApiService apiService;
    private YourDao yourDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MyRepository(Context context) {
        apiService = ApiServiceGenerator.createService(ApiService.class);
        YourRoomDatabase database = YourRoomDatabase.getDatabase(context);
        yourDao = database.yourDao();
    }
    public LiveData<List<YourDataModel>> getAllData(int page, int size) {
        MutableLiveData<List<YourDataModel>> data = new MutableLiveData<>();
        if (page == 1) {
            executorService.execute(() -> {
                List<YourDataModel> dataList = yourDao.getAllData2();
                if (dataList != null && !dataList.isEmpty()) {

                    data.postValue(dataList);
                }
            });
        }
        loadData(page, size, data);
        return data;
    }
    private void loadData(int page, int size, MutableLiveData<List<YourDataModel>> data) {
        apiService.getAirlines(page, size).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<YourDataModel> newData = response.body().getData();
                    Log.e("MTAG", "onResponse : page : " + page);
                    data.postValue(newData);
                }
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("MTAG", "onFailure: " + t.getMessage());
                loadData(page, size, data);
            }
        });

    }
}
