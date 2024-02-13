package com.demo.example;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.example.Adapter.YourAdapter;
import com.demo.example.db.YourDao;
import com.demo.example.db.YourRoomDatabase;
import com.demo.example.repository.MyViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private MyViewModel viewModel;
    private YourAdapter adapter;
    private int currentPage = 1;
    public boolean isLoading = false;
    private boolean isLastPage = false;
    private EditText searchEditText;
    private ProgressBar loadingPB;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView); 
        searchEditText = findViewById(R.id.search); 

        loadingPB = findViewById(R.id.idPBLoading);
        adapter = new YourAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 5) {  

                        Log.e("MTAG", "onScrolled : " + currentPage);

                        loadMoreData();
                    }
                }
            }
        });
        
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        viewModel.getAllData(currentPage, 5).observe(this, newData -> {
            adapter.addData(newData);
            loadingPB.setVisibility(View.GONE);
            isLoading = false;
            if (newData.size() < 5) {
                isLastPage = true;
            }
        });
    }

    private void loadMoreData() {
        isLoading = true;
        loadingPB.setVisibility(View.VISIBLE);
        viewModel.getAllData(currentPage, 5).observe(this, newData -> {
            if (newData != null && newData.size() > 0) {
                adapter.addData(newData);

                if (currentPage > 10) {
                    YourRoomDatabase database = YourRoomDatabase.getDatabase(getApplicationContext());
                    YourDao yourDao = database.yourDao();
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        yourDao.insertAll(adapter.getDataList());
                    });
                }
                currentPage++;
            } else {
                isLastPage = true;
            }
            loadingPB.setVisibility(View.GONE);
            isLoading = false;
        });
    }
}