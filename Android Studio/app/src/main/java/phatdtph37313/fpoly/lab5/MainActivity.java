package phatdtph37313.fpoly.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import phatdtph37313.fpoly.lab5.APIService;
import phatdtph37313.fpoly.lab5.AdapterShoe;
import phatdtph37313.fpoly.lab5.ShoeDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static List<ShoeDTO> list = new ArrayList<>();
    static AdapterShoe adapterShoe;
    static RecyclerView recyclerView;
    FloatingActionButton floaAdd;
    EditText edtSearch;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcvList);
        floaAdd = findViewById(R.id.floatAdd);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        //Connect
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Call Api Retrofit
        CallAPI(retrofit);

        //setOnclick add
        floaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCreateAndAddActivity.class);
                intent.putExtra("titleAdd", "Create shoe");
                intent.putExtra("titleBtnAdd", "Create");
                startActivity(intent);
                finish();
            }
        });

        //Search button click listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter(edtSearch.getText().toString().trim());
            }
        });

        //Text change listener for live search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    public static void CallAPI(Retrofit retrofit) {
        //Khai báo API Service
        APIService apiService = retrofit.create(APIService.class);

        Call<ArrayList<ShoeDTO>> call = apiService.getShoe();

        call.enqueue(new Callback<ArrayList<ShoeDTO>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ShoeDTO>> call, @NonNull Response<ArrayList<ShoeDTO>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    adapterShoe = new AdapterShoe(recyclerView.getContext(), list);
                    LinearLayoutManager linearLayoutManager =
                            new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterShoe);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ShoeDTO>> call, @NonNull Throwable t) {
                Log.e("zzzz", "onFailure: " + t.getMessage());
            }
        });
    }

    private void filter(String text) {
        ArrayList<ShoeDTO> filteredList = new ArrayList<>();
        for (ShoeDTO item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterShoe.filterList(filteredList);
    }
}
