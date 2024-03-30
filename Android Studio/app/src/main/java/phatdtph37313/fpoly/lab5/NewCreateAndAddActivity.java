package phatdtph37313.fpoly.lab5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewCreateAndAddActivity extends AppCompatActivity {
    private static final int MY_RES_CODE = 10;
    TextView tvTitle;
    ImageView ivBack, ivImageShoe;
    AppCompatButton btnNewAndEdit;
    EditText edName, edPrice, edBrand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create_and_add);
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBackCreUp);
        btnNewAndEdit = findViewById(R.id.btnNewAndEdit);
        edName = findViewById(R.id.edName);
        ivImageShoe = findViewById(R.id.ivImageShoe);

        ChangeUI();

        btnNewAndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleAdd = getIntent().getStringExtra("titleAdd");
                if (titleAdd == null) {
                    UpdateShoe();
                } else {
                    CreateShoe();
                }
            }
        });


    }

    @SuppressLint("ObsoleteSdkInt")
    private void ChooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MY_RES_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_RES_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivImageShoe.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Thêm sản phẩm mới
    private void CreateShoe() {
        String name = edName.getText().toString();
        if (CheckCreateShoe()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Call<ShoeDTO> call = apiService.createShoe(new ShoeDTO(name, "url nè"));

            call.enqueue(new Callback<ShoeDTO>() {
                @Override
                public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NewCreateAndAddActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ShoeDTO> call, Throwable t) {
                    Log.e("zzzzz", "onFailure: " + t.getMessage());
                }
            });
        }
    }


    private boolean CheckCreateShoe() {
        // Viết mã kiểm tra dữ liệu nhập vào ở đây
        return true;
    }

    private void UpdateShoe() {
        String name = edName.getText().toString();
        String id = getIntent().getStringExtra("id");

        if (CheckCreateShoe()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.DOMAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService apiService = retrofit.create(APIService.class);

            Call<ShoeDTO> call = apiService.updateShoe(id, new ShoeDTO(name,"url nè"));

            call.enqueue(new Callback<ShoeDTO>() {
                @Override
                public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(NewCreateAndAddActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ShoeDTO> call, Throwable t) {
                    Log.e("zzzzz", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void ChangeUI() {
        String titleAdd = getIntent().getStringExtra("titleAdd");
        String titleBtnAdd = getIntent().getStringExtra("titleBtnAdd");
        String titleUpdate = getIntent().getStringExtra("titleEdit");
        String titleBtnUp = getIntent().getStringExtra("titleBtnEdit");
        String name = getIntent().getStringExtra("name");
        if (titleUpdate == null) {
            tvTitle.setText(titleAdd);
            btnNewAndEdit.setText(titleBtnAdd);
        } else {
            tvTitle.setText(titleUpdate);
            edName.setText(name);
            btnNewAndEdit.setText(titleBtnUp);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCreateAndAddActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}