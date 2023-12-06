package com.example.cssk.adapter;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cssk.R;
import com.example.cssk.object.Thuoc;

import java.util.ArrayList;
import java.util.List;

public class ThuocAdapter extends RecyclerView.Adapter<ThuocAdapter.ThuocViewHolder> {

    public List<Thuoc> thuocList = new ArrayList<>();
    public Context mContext;

    public ThuocAdapter(List<Thuoc> thuocList, Context mContext) {
        this.thuocList = thuocList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ThuocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thuoc_item2, parent, false);
        return new ThuocViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ThuocViewHolder holder, int position) {

        Thuoc item = thuocList.get(position);
        holder.tvTenThuoc.setText(item.getTenthuoc());
        holder.tvSoLuong.setText("Số lượng: " + item.soluong);
        holder.tvLieuLuong.setText("Liều lượng: " + item.getLieuluong());
        holder.imgThuoc.setImageResource(item.getThuocImageid());

        holder.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchName = item.getTenthuoc();
                if(!searchName.equals("")){
                    searchThuocNet("Chi tiết thuốc "+searchName);
                }
            }
        });
    }

    private void searchThuocNet(String word){
        try{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, word);
            mContext.startActivity(intent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
            searchThuocNetCompat(word);
        }
    }
    private void searchThuocNetCompat(String word){
        try{
            Uri uri = Uri.parse("https://www.google.com.vn/search?q=" + word);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
            Toast.makeText(mContext, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (thuocList != null){
            return thuocList.size();
        }
        return 0;
    }

    public class ThuocViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgThuoc,imgSearch;
        private TextView tvTenThuoc, tvSoLuong,tvLieuLuong;
        public ThuocViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSearch= itemView.findViewById(R.id.img_search);
            imgThuoc = itemView.findViewById(R.id.ivThuoc);
            tvTenThuoc = itemView.findViewById(R.id.tvTenThuoc);
            tvLieuLuong=itemView.findViewById(R.id.tvLieuLuong);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}
