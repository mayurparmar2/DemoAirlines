package com.demo.example.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.example.ApiResponse;
import com.demo.example.ApiService;
import com.demo.example.db.YourDao;
import com.demo.example.db.YourRoomDatabase;
import com.demo.example.model.YourDataModel;
import com.demo.example.network.ApiServiceGenerator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourRepository {
    private ApiService apiService;
    private YourDao yourDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public YourRepository(Context context) {
        apiService = ApiServiceGenerator.createService(ApiService.class);
        YourRoomDatabase database = YourRoomDatabase.getDatabase(context);
        yourDao = database.yourDao();
    }

    public LiveData<List<YourDataModel>> getAllData(int page, int size) {
        MutableLiveData<List<YourDataModel>> data = new MutableLiveData<>();

        // Check if data is available in Room Database
        if (page == 1) {
            executorService.execute(() -> {
//                LiveData<List<YourDataModel>> cachedData = yourDao.getAllData();
//                List<YourDataModel> dataList = cachedData.getValue();
                List<YourDataModel> dataList = yourDao.getAllData2();
                if (dataList != null && !dataList.isEmpty()) {
                    // If data is available offline, post it to LiveData
                    data.postValue(dataList);
                }
            });
        }

        // Make a Retrofit API call
        apiService.getAirlines(page, size).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<YourDataModel> newData = response.body().getData();
//
//                    Log.e("MTAG", "onResponse " +newData.get(0).getName());
//                    Log.e("MTAG", "onResponse " +newData.get(0).getTrips());
//                    Log.e("MTAG", "onResponse " +newData.get(0).getId());
                    // Save data to Room database for offline functionality
                    Log.e("MTAG", "onResponse : page : " +page);

                    if (page == 5) {

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                yourDao.insert(newData);
//                                LiveData<List<YourDataModel>> listLiveData =  yourDao.getAllData();
                                List<YourDataModel> dataList = yourDao.getAllData2();
                                Log.e("MTAG", "onResponse: " +dataList.size());
                            }
                        });



                    }

                    data.postValue(newData);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle failure
            }
        });

        return data;
    }
}
