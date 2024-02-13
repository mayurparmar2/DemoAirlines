package com.demo.example.repository;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.demo.example.model.YourDataModel;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private MyRepository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public LiveData<List<YourDataModel>> getAllData(int page, int size) {
        return repository.getAllData(page, size);
    }
}
