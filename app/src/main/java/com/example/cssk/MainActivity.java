package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cssk.object.Tri_Lieu;
import com.example.cssk.object.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout ImageBtnKeKhai, ImageBtnThuoc, ImageBtnTriLieu, ImageBtnTuVan;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTen,txtDem;
    ImageView imgHinh, img_nav,imgHinhDem;
    GoogleSignInAccount signInAccount;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        init();
        checkmail();
        showProfile();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        MainFuntion();
       InternetChecking();


    }
    private void InternetChecking(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo != null && networkInfo.isConnected() == true)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Kiểm tra kết nối ")
                    .setMessage("Bạn đang offline, vui lòng kiểm tra lại đường truyền")
                    .setPositiveButton("Thử lại",((dialogInterface, i) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }))
                    .setCancelable(false)
                    .show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    private void MainFuntion(){
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(), Profile.class) ;
                startActivityForResult(profile,0);
            }
        });
        ImageBtnKeKhai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kekhai = new Intent(getApplicationContext(), KhaiBao.class);
                startActivityForResult(kekhai, 0);
            }
        });

        ImageBtnThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thuoc = new Intent(getApplicationContext(), Thuoclayout.class);
                startActivityForResult(thuoc, 0);
            }
        });

        ImageBtnTriLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trilieu = new Intent(getApplicationContext(), TriLieu.class);
                startActivityForResult(trilieu, 0);
            }
        });
        ImageBtnTuVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tuvan = new Intent(getApplicationContext(), Map_Activity.class);
                startActivityForResult(tuvan, 0);
            }
        });
    }


    private void checkmail()  {
        accRef.orderByChild("user_id").equalTo(signInAccount.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    User newAcc = new User();
                    newAcc.setUser_id(signInAccount.getId());
                    newAcc.setEmail(signInAccount.getEmail());
                    newAcc.setHoTen(signInAccount.getDisplayName());
                    accRef.child(signInAccount.getId()).setValue(newAcc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showProfile() {

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null) {
                accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            String name = snapshot.getValue().toString();
                            txtTen.setText(name);
                        }
                        else {
                            txtTen.setText(signInAccount.getDisplayName());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            Uri photourl = signInAccount.getPhotoUrl();
            Glide.with(this).load(photourl).error(R.drawable.login).into(imgHinh);
            accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        accRef.child(signInAccount.getId()).child("Tri_Lieu").addListenerForSingleValueEvent(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                List<Tri_Lieu>triLieuList =new ArrayList<>();
                                long res = 0;
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        Tri_Lieu tri_lieu = dataSnapshot.getValue(Tri_Lieu.class);
                                        triLieuList.add(tri_lieu);
                                        res = triLieuList.stream().collect(Collectors.counting());
                                        txtDem.setText(String.valueOf(res));
                                    }
                                }else {
                                    txtDem.setVisibility(View.GONE);
                                    imgHinhDem.setVisibility(View.GONE);

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
    }



    public void init() {
        ImageBtnKeKhai = (RelativeLayout) findViewById(R.id.image_btnkekhai);
        ImageBtnThuoc = (RelativeLayout) findViewById(R.id.image_btnthuoc);
        ImageBtnTriLieu = (RelativeLayout) findViewById(R.id.image_btntrilieu);
        ImageBtnTuVan = (RelativeLayout) findViewById(R.id.image_btntuvan);
        txtTen= findViewById(R.id.txtTenHienThi);
        imgHinh=findViewById(R.id.imgIamgePF);
        img_nav=findViewById(R.id.nav_hinh);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar= (Toolbar) findViewById(R.id.menu);
        txtDem=findViewById(R.id.txtDemTL);
        imgHinhDem=findViewById(R.id.imgHinhTron);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_healthrp:
                Intent khaibao = new Intent(this, KhaiBao.class);
                startActivity(khaibao);
                break;
            case R.id.nav_medical:
                Intent thuoc = new Intent(getApplicationContext(), Thuoclayout.class);
                startActivityForResult(thuoc, 0);
                break;
            case R.id.nav_trilieu:
                Intent trilieu = new Intent(getApplicationContext(), TriLieu.class);
                startActivityForResult(trilieu, 0);
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Shared",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(this, Profile.class);
                startActivity(profile);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}