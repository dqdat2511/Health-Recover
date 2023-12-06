package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cssk.adapter.ThuocAdapter;
import com.example.cssk.object.Thuoc;
import com.example.cssk.object.Tri_Lieu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Full_tri_lieuActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    String tenBenh;
    List<Thuoc> thuocList = new ArrayList<>();
    GoogleSignInAccount signInAccount;
    TextView tvtenbenh, tvtrilieu;
    RecyclerView rvthuoc;
    ThuocAdapter thuocAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_tri_lieu);
        init();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Phương pháp điều trị");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        thuocAdapter = new ThuocAdapter(thuocList, this);
        rvthuoc.setAdapter(thuocAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvthuoc.setLayoutManager(linearLayoutManager);
        if(getIntent().getStringExtra("ten-benh") != null) {
            tenBenh = getIntent().getStringExtra("ten-benh").trim();
        }

        accRef.child(signInAccount.getId()).child("Thuoc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    thuocList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Thuoc thuoc = dataSnapshot.getValue(Thuoc.class);
                        if(thuoc.getTenBenh().equals(tenBenh)) {
                            thuocList.add(thuoc);
                        }

                    }
                    thuocAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        accRef.child(signInAccount.getId()).child("Tri_Lieu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(dataSnapshot.getValue(Tri_Lieu.class).getTenbenh().trim().matches(tenBenh)) {
                            Tri_Lieu tri_lieu = dataSnapshot.getValue(Tri_Lieu.class);
                            tvtenbenh.setText("Tên bệnh lý: "+tri_lieu.getTenbenh());
                            tvtrilieu.setText(tri_lieu.getCachtrilieu());
                        }
                    }
//                    if(tri_lieu != null) {
//                        System.out.println(tri_lieu.getTenbenh());
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init(){
        tvtenbenh = findViewById(R.id.tvtenBenh);
        tvtrilieu = findViewById(R.id.tvtriLieu);
        rvthuoc = findViewById(R.id.rvThuoc);
        toolbar= findViewById(R.id.fulltrilieutoolbar);
    }
}