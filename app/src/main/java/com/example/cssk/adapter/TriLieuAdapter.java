package com.example.cssk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cssk.Full_tri_lieuActivity;
import com.example.cssk.R;
import com.example.cssk.TriLieu;
import com.example.cssk.object.Thuoc;
import com.example.cssk.object.Tri_Lieu;

import java.util.ArrayList;
import java.util.List;

public class TriLieuAdapter extends RecyclerView.Adapter<TriLieuAdapter.TriLieuViewHolder> {
    public List<Tri_Lieu> triLieuList = new ArrayList<>();
    public Context nContext;

    public TriLieuAdapter(List<Tri_Lieu> triLieuList, Context nContext) {
        this.triLieuList = triLieuList;
        this.nContext = nContext;
    }

    @NonNull
    @Override
    public TriLieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tri_lieu_item, parent, false);

        return new TriLieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TriLieuViewHolder holder, int position) {
        Tri_Lieu item = triLieuList.get(position);
        holder.tvTenBenh.setText(item.getTenbenh());
        holder.tvtriLieu.setText(item.getCachtrilieu());
        holder.tvxemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nContext, Full_tri_lieuActivity.class);
                intent.putExtra("ten-benh", item.getTenbenh());
                nContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(triLieuList != null){
            return triLieuList.size();
        }
        return 0;
    }

    public class TriLieuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenBenh, tvtriLieu, tvxemThem;
        public TriLieuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenBenh = itemView.findViewById(R.id.tvTenBenh);
            tvtriLieu = itemView.findViewById(R.id.tvTriLieu);
            tvxemThem = itemView.findViewById(R.id.tvXemThem);
        }
    };
}
