package phatdtph37313.fpoly.lab5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterShoe extends RecyclerView.Adapter<AdapterShoe.ViewHolder> {

    Context context;
    List<ShoeDTO> list;
    private List<ShoeDTO> filteredShoeList;

    // Constructor và các phương thức khác của Adapter

    public void filterList(List<ShoeDTO> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public AdapterShoe(Context context, List<ShoeDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String id = list.get(position).get_id();
        holder.tvNameShoe.setText(String.valueOf(list.get(position).getName()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_mess, null, false);
                builder.setView(view);

                AlertDialog dialog = builder.create();

                Window window = dialog.getWindow();
                assert window != null;
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                AppCompatButton btnHuy, btnXacNhan;
                btnHuy = view.findViewById(R.id.btnHuy);
                btnXacNhan = view.findViewById(R.id.btnXacNhan);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(APIService.DOMAIN)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);

                        Call<ShoeDTO> call = apiService.deleteShoe(id);

                        call.enqueue(new Callback<ShoeDTO>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onResponse(Call<ShoeDTO> call, Response<ShoeDTO> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                    MainActivity.CallAPI(retrofit);
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<ShoeDTO> call, Throwable t) {
                                Log.e("cccccc", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                });
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewCreateAndAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("titleEdit", "Update shoe");
                intent.putExtra("titleBtnEdit", "Update");
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("id", list.get(position).get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<ShoeDTO> newData) {
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameShoe;
        Button btnEdit, btnDelete;
        ShapeableImageView ivShoe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameShoe = itemView.findViewById(R.id.tvName);
            ivShoe = itemView.findViewById(R.id.ivShoe);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
