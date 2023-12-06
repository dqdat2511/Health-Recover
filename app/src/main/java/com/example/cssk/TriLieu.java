package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cssk.adapter.ThuocAdapter;
import com.example.cssk.adapter.TriLieuAdapter;
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

public class TriLieu extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    RecyclerView trilieuRv;
    TriLieuAdapter triLieuAdapter;
    List<Tri_Lieu> triLieuList = new ArrayList<>();
    GoogleSignInAccount signInAccount;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tri_lieu);
        init();
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Trị liệu");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        trilieuRv = (RecyclerView) findViewById(R.id.rvTriLieu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        triLieuAdapter = new TriLieuAdapter(triLieuList, this);
        trilieuRv.setAdapter(triLieuAdapter);


        trilieuRv.setLayoutManager(linearLayoutManager);
        accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    accRef.child(signInAccount.getId()).child("Tri_Lieu").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    Tri_Lieu tri_lieu = dataSnapshot.getValue(Tri_Lieu.class);
                                    triLieuList.add(tri_lieu);
                                }
                            }
                            triLieuAdapter.notifyDataSetChanged();
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
        toolbar = findViewById(R.id.trilieutoolbar);
}

    @Override
    protected void onResume() {
        triLieuAdapter.notifyDataSetChanged();
        super.onResume();

    }
}
