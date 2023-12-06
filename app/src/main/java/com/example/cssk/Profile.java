package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cssk.object.Tri_Lieu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Profile extends AppCompatActivity {
    TextView txtTen, txtSex, txtMD, txtxembenh,txtDem;
    Button btnLogout,btnXoa;
    ImageView imgvatar, imgCircel;
    GoogleSignInAccount signInAccount;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    Toolbar toolbar;
List<Tri_Lieu> triLieuList = new ArrayList<>();
View benhlayout;
boolean Connect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thông tin của bạn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(Profile.this, MainActivity.class);
                startActivityForResult(main ,1);
                finish();
            }
        });
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        Benh();
        DataProfile();

        if(signInAccount != null){
           // txtTen.setText(signInAccount.getDisplayName());
           // txtMail.setText(signInAccount.getEmail());
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InternetChecking();
                if(Connect!= false){Deletedata();}
            }
        });
        benhlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trieuchung = new Intent(Profile.this, TrieuChung.class);
                startActivityForResult(trieuchung, 1);
                finish();
            }
        });
    }
    private void InternetChecking(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo != null && networkInfo.isConnected() == true)){
            Connect = false;
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout,"Không thành công vui lòng kiểm tra kết nối",Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();
        }else {
            Connect = true;
        }

    }
    private void Deletedata() {

        new AlertDialog.Builder( this)
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có chắc muốn Xóa Thông Tin của mình")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        accRef.child(signInAccount.getId()).child("hoTen").setValue("");
                        accRef.child(signInAccount.getId()).child("sex").removeValue();
                        accRef.child(signInAccount.getId()).child("date").setValue("");
                        accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").removeValue();
                        Toast.makeText(Profile.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void Benh() {

        accRef.child(signInAccount.getId()).child("benh_ly").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<String> ListBenh = new ArrayList<>();
                    for (DataSnapshot benh :snapshot.getChildren()) {
                        String tenbenh = benh.getValue().toString();
                        ListBenh.add(tenbenh);
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String mucDo= snapshot.getValue().toString();
                    txtMD.setText(mucDo);
                }else {
                    txtMD.setText(" ");
                }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    private void init(){
        txtSex = findViewById(R.id.txtGioiTinh);
      //  edtDate = (EditText) findViewById(R.id.edtNamSinh);
        btnLogout = findViewById(R.id.btn_logout);
        txtTen = findViewById(R.id.txtName);
        //txtMail = findViewById(R.id.txtMail);
        imgCircel=findViewById(R.id.imgTron);
        txtMD = findViewById(R.id.txtMucDo);
        btnXoa=findViewById(R.id.btnxoa);
        toolbar = findViewById(R.id.profileToolbar);
        imgvatar=findViewById(R.id.imAVT);
        txtxembenh=findViewById(R.id.tvXemBenh);
        txtDem=findViewById(R.id.txtDem);
        benhlayout=findViewById(R.id.benhLayout);
    }

    private void DataProfile() {

        accRef.child(signInAccount.getId()).child("sex").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                String Sex = snapshot.getValue().toString();
                txtSex.setText(Sex);
                }
                else {
                txtSex.setText("");}
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                String date= snapshot.getValue().toString();
                txtMD.setText(date);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                String name = snapshot.getValue().toString();
                txtTen.setText(name);}
                else {
                    txtTen.setText(signInAccount.getDisplayName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("Tri_Lieu").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Tri_Lieu tri_lieu = dataSnapshot.getValue(Tri_Lieu.class);
                        triLieuList.add(tri_lieu);
                        String res = triLieuList.stream().collect(Collectors.counting()).toString();
                        txtDem.setText(res);
                    }
                }else {
                    txtDem.setVisibility(View.GONE);
                    imgCircel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Uri photourl = signInAccount.getPhotoUrl();
        Glide.with(this).load(photourl).error(R.drawable.login).into(imgvatar);

    }
}
