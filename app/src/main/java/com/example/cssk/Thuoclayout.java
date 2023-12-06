package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cssk.adapter.ThuocAdapter;
import com.example.cssk.object.Thuoc;
import com.example.cssk.object.Tri_Lieu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Thuoclayout extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    RecyclerView thuocRv;
    ThuocAdapter thuocAdapter;
    List<Thuoc> thuocList = new ArrayList<>();
    GoogleSignInAccount signInAccount;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuoclayout);
        init();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thuốc của bạn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        thuocRv = (RecyclerView) findViewById(R.id.thuocRv);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        thuocAdapter = new ThuocAdapter(thuocList, this);
        thuocRv.setAdapter(thuocAdapter);
        thuocRv.setLayoutManager(linearLayoutManager);
        accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    accRef.child(signInAccount.getId()).child("Thuoc").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    Thuoc thuoc = dataSnapshot.getValue(Thuoc.class);
                                    thuocList.add(thuoc);
                                }
                            }
                            thuocAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(parentLayout,"Vui lòng cập nhật mức độ để có phương pháp trị liệu tốt nhất",Snackbar.LENGTH_LONG);
                    snackbar.setDuration(5000);
                    snackbar.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void init(){
        toolbar= findViewById(R.id.thuoctoolbar);
    }
    @Override
    protected void onResume() {
        super.onResume();
        thuocAdapter.notifyDataSetChanged();
    }
}