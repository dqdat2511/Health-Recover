package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class KhaiBao extends AppCompatActivity {
    EditText EdtDate,txtHoTen;
    RadioGroup RdnGioiTinh,rdnMucDo;
    RadioButton selectedSex, selectedMucDo,rdnNam,rdnNu;
    Button btnLamMoi,btnLuu,btnCapnhat;
    GoogleSignInAccount signInAccount;
    Toolbar toolbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    DatabaseReference thuocRef;
    boolean Connect, Benh;
    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khai_bao);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        init();
        setSupportActionBar(toolbar);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        toolbar.setTitle("Khai báo sức khỏe");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getProfile();
        getBenh();
        thuocRef = database.getReference("Account/"+signInAccount.getId()+"/Thuoc");
        EdtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try {
                    ChonNgay();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        RdnGioiTinh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                selectedSex = (RadioButton) findViewById(selectedId);
            }
        });
        rdnMucDo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedMDId = radioGroup.getCheckedRadioButtonId();
                selectedMucDo =(RadioButton) findViewById(selectedMDId);
            }
        });
        btnLamMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EdtDate.setText("");
                txtHoTen.setText("");
                RdnGioiTinh.clearCheck();
                rdnMucDo.clearCheck();
            }
        });
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent benhly = new Intent(getApplicationContext(), TrieuChung.class);
                startActivityForResult(benhly, 0);
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                InternetChecking();
                getBenh();
                final Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat simpleDateFormate = new SimpleDateFormat("dd/MM/yyyy");
                String CurrentDate = simpleDateFormate.format(currentDate.getTime());
                if(Connect != false){
                    if(RdnGioiTinh.getCheckedRadioButtonId() == -1){
                        Toast.makeText(KhaiBao.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else if(txtHoTen.getText().toString().trim().matches("")){
                        Toast.makeText(KhaiBao.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                    }else if (EdtDate.getText().toString().compareTo(CurrentDate)>0 ||
                            EdtDate.getText().toString().compareTo(CurrentDate) == 0 ){
                        Toast.makeText(KhaiBao.this, "Ngày sinh không hơp lệ", Toast.LENGTH_SHORT).show();
                    }else if(EdtDate.getText().toString().trim().matches("")){
                        Toast.makeText(KhaiBao.this, "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
                    }else if(Benh != false &&  rdnMucDo.getCheckedRadioButtonId() == -1){
                        Toast.makeText(KhaiBao.this, "Vui lòng chọn mức độ của bạn", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Pushdata();
                        finish();
                    }
                }
            }
        });
    }


    private void InternetChecking(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(!(networkInfo != null && networkInfo.isConnected() == true)){
            Connect = false;
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout,"Không thành công, vui lòng kiểm tra kết nối",Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
            } else {
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivityForResult(panelIntent, 1);
            }
        }else {
            Connect = true;
        }
    }


    private void Pushdata() {
            accRef.child(signInAccount.getId()).child("hoTen").setValue(txtHoTen.getText().toString());
            accRef.child(signInAccount.getId()).child("sex").setValue(selectedSex.getText().toString());
            accRef.child(signInAccount.getId()).child("date").setValue(EdtDate.getText().toString());
            if(Benh != false && rdnMucDo.getCheckedRadioButtonId() != -1) {
                 accRef.child(signInAccount.getId()).child("benh_ly").child("MucDo").setValue(selectedMucDo.getText());
            }
            Toast.makeText(KhaiBao.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
    }
    public void getBenh(){
    accRef.child(signInAccount.getId()).child("benh_ly").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(!snapshot.exists()){
                Benh = false;
            }else {
                Benh = true;
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });
}

    private void getProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accRef.child(signInAccount.getId()).child("hoTen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                String name = snapshot.getValue().toString();
                txtHoTen.setText(name);
                }else
                {
                txtHoTen.setText(signInAccount.getDisplayName());
                }

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
                EdtDate.setText(date);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accRef.child(signInAccount.getId()).child("sex").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){;
                    if(snapshot.getValue().toString().trim().matches("Nữ")){
                        rdnNu.setChecked(true);
                    }else {
                        rdnNam.setChecked(true);}
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang =calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(KhaiBao.this, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormate = new SimpleDateFormat("dd/MM/yyyy");
                EdtDate.setText(simpleDateFormate.format(calendar.getTime()));
            }
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    private void init(){
        EdtDate = (EditText) findViewById(R.id.edtDate);
        RdnGioiTinh=(RadioGroup) findViewById(R.id.rdnGioiTinh);
        rdnMucDo=(RadioGroup) findViewById(R.id.rdnMucDo);
        btnLamMoi=(Button) findViewById(R.id.btnlammoi);
        btnLuu=(Button) findViewById(R.id.btnLuu);
        txtHoTen =(EditText) findViewById(R.id.txtTen);
        btnCapnhat=(Button) findViewById(R.id.btnbenhly);
        rdnNam=(RadioButton) findViewById(R.id.rdnNam);
        rdnNu=(RadioButton) findViewById(R.id.RdnNu);
        toolbar=(Toolbar) findViewById(R.id.khaibaoToolbar);
    }
}