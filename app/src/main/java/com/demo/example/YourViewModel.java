package com.demo.example;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.demo.example.model.YourDataModel;
import com.demo.example.repository.YourRepository;

import java.util.List;

public class YourViewModel extends AndroidViewModel {

    private YourRepository repository;

    public YourViewModel(@NonNull Application application) {
        super(application);
        repository = new YourRepository(application);
    }

    public LiveData<List<YourDataModel>> getAllData(int page, int size) {
        return repository.getAllData(page, size);
    }
}
