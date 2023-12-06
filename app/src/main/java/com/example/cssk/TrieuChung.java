package com.example.cssk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.cssk.object.BenhLy;
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

public class TrieuChung extends AppCompatActivity {
    Button btnXoa, btnLuu;
    CheckBox cbx, cbx8, cbx2, cbx3, cbx4,cbx5,cbx6,cbx7,cbx9;
    TextView tvMD;
    List<String> ListBenh = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference accRef = database.getReference("Account");
    DatabaseReference thuocRef;
    Toolbar toolbar;
    DatabaseReference trilieuRef;
    GoogleSignInAccount signInAccount;
    DatabaseReference newthuocRef = database.getReference("Thuoc");
    DatabaseReference newtrilieuRef = database.getReference("Tri_Lieu");
    String tenBenh;
    public StringBuffer result = new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trieu_chung);
        init();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Các Bệnh Sau Covid");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        thuocRef = database.getReference("Account/"+signInAccount.getId()+"/Thuoc");
        trilieuRef = database.getReference("Account/"+signInAccount.getId()+"/Tri_Lieu");
       // test();
        checkBenh();
        if(getIntent().getStringExtra("ten-benh") != null) {
            tenBenh = getIntent().getStringExtra("ten-benh").trim();
        }
        PushThuoc();
        PushTriLieu();
        // push dữ liệu lớn

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbx.isChecked())
                    cbx.setChecked(false);
                if(cbx2.isChecked())
                    cbx2.setChecked(false);
                if(cbx3.isChecked())
                    cbx3.setChecked(false);
                if(cbx4.isChecked())
                    cbx4.setChecked(false);
                if(cbx5.isChecked())
                    cbx5.setChecked(false);
                if(cbx6.isChecked())
                    cbx6.setChecked(false);
                if(cbx7.isChecked())
                    cbx7.setChecked(false);
                if(cbx8.isChecked())
                    cbx8.setChecked(false);
                if(cbx9.isChecked())
                    cbx9.setChecked(false);

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                accRef.child(signInAccount.getId()).child("Thuoc").removeValue();
                accRef.child(signInAccount.getId()).child("Tri_Lieu").removeValue();

                if(cbx.isChecked()){
                   ListBenh.add(cbx.getText().toString());
                }
                if(cbx2.isChecked()){
                    ListBenh.add(cbx2.getText().toString());
                }
                if(cbx3.isChecked()){
                    ListBenh.add(cbx3.getText().toString());
                }
                if(cbx4.isChecked()){
                    ListBenh.add(cbx4.getText().toString());
                }
                if(cbx5.isChecked()){
                    ListBenh.add(cbx5.getText().toString());
                }
                if(cbx6.isChecked()){
                    ListBenh.add(cbx6.getText().toString());
                }
                if(cbx7.isChecked()){
                    ListBenh.add(cbx7.getText().toString());
                }
                if(cbx8.isChecked()){
                    ListBenh.add(cbx8.getText().toString());
                }
                if(cbx9.isChecked()){
                    ListBenh.add(cbx9.getText().toString());
                }
                if (!cbx.isChecked() && !cbx2.isChecked() && !cbx3.isChecked() && !cbx4.isChecked() && !cbx5.isChecked() && !cbx6.isChecked() && !cbx7.isChecked() && !cbx8.isChecked())
                    result.append("Mục đang chọn :0");

                accRef.child(signInAccount.getId()).child("benh_ly").setValue(ListBenh);
                GetthuocbyBenh();
                finish();
            }

        });
    }
    //push dũ liệu lên data
    private void PushThuoc(){
        newthuocRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Thuoc thuocbenzonatate = new Thuoc();
                    thuocbenzonatate.setThuocImageid(R.drawable.benzonatate);
                    thuocbenzonatate.setTenthuoc("benzonatate");
                    thuocbenzonatate.setSoluong(5);
                    thuocbenzonatate.setTenBenh("Ho mãn tính");
                    thuocbenzonatate.setLieuluong("100mg-200mg");
                    String newThuocId1 = newthuocRef.push().getKey();
                    thuocbenzonatate.setThuoc_id(newThuocId1);
                    newthuocRef.child(newThuocId1).setValue(thuocbenzonatate);

                    Thuoc thuocproair = new Thuoc();
                    thuocproair.setThuocImageid(R.drawable.proair);
                    thuocproair.setTenthuoc("proair");
                    thuocproair.setSoluong(1);
                    thuocproair.setTenBenh("Ho mãn tính");
                    thuocproair.setLieuluong("2/ngày");
                    String newThuocId2 = newthuocRef.push().getKey();
                    thuocproair.setThuoc_id(newThuocId2);
                    newthuocRef.child(newThuocId2).setValue(thuocproair);

                    Thuoc thuocmucinex = new Thuoc();
                    thuocmucinex.setThuocImageid(R.drawable.mucinex);
                    thuocmucinex.setTenthuoc("mucinex");
                    thuocmucinex.setSoluong(4);
                    thuocmucinex.setTenBenh("Ho mãn tính");
                    thuocmucinex.setLieuluong("2/ngày");
                    String newThuocId3 = newthuocRef.push().getKey();
                    thuocmucinex.setThuoc_id(newThuocId3);
                    newthuocRef.child(newThuocId3).setValue(thuocmucinex);

                    Thuoc thuocibuprofen  = new Thuoc();
                    thuocibuprofen.setThuocImageid(R.drawable.ibuprofen);
                    thuocibuprofen.setTenthuoc("ibuprofen");
                    thuocibuprofen.setTenBenh("Đau nhức xương khớp");
                    thuocibuprofen.setSoluong(4);
                    thuocibuprofen.setLieuluong("2/ngày");
                    String newThuocId4 = newthuocRef.push().getKey();
                    thuocibuprofen.setThuoc_id(newThuocId4);
                    newthuocRef.child(newThuocId4).setValue(thuocibuprofen);

                    Thuoc thuocacetaminophen   = new Thuoc();
                    thuocacetaminophen.setThuocImageid(R.drawable.acetaminophen);
                    thuocacetaminophen.setTenthuoc("acetaminophen");
                    thuocacetaminophen.setTenBenh("Đau nhức xương khớp");
                    thuocacetaminophen.setSoluong(4);
                    thuocacetaminophen.setLieuluong("2/ngày");
                    String newThuocId5 = newthuocRef.push().getKey();
                    thuocacetaminophen.setThuoc_id(newThuocId5);
                    newthuocRef.child(newThuocId5).setValue(thuocacetaminophen);

                    Thuoc thuocsalbutamol   = new Thuoc();
                    thuocsalbutamol.setThuocImageid(R.drawable.salbutamol);
                    thuocsalbutamol.setTenthuoc("salbutamol");
                    thuocsalbutamol.setTenBenh("Khó thở");
                    thuocsalbutamol.setSoluong(4);
                    thuocsalbutamol.setLieuluong("2/ngày");
                    String newThuocId6 = newthuocRef.push().getKey();
                    thuocsalbutamol.setThuoc_id(newThuocId6);
                    newthuocRef.child(newThuocId6).setValue(thuocsalbutamol);

                    Thuoc thuocterbutaline   = new Thuoc();
                    thuocterbutaline.setThuocImageid(R.drawable.brycanyl);
                    thuocterbutaline.setTenthuoc("terbutaline");
                    thuocterbutaline.setTenBenh("Khó thở");
                    thuocterbutaline.setSoluong(4);
                    thuocterbutaline.setLieuluong("2/ngày");
                    String newThuocId7 = newthuocRef.push().getKey();
                    thuocterbutaline.setThuoc_id(newThuocId7);
                    newthuocRef.child(newThuocId7).setValue(thuocterbutaline);

                    Thuoc thuocipratropium   = new Thuoc();
                    thuocipratropium.setThuocImageid(R.drawable.ipratropium);
                    thuocipratropium.setTenthuoc("ipratropium");
                    thuocipratropium.setTenBenh("Khó thở");
                    thuocipratropium.setSoluong(4);
                    thuocipratropium.setLieuluong("2/ngày");
                    String newThuocId8 = newthuocRef.push().getKey();
                    thuocipratropium.setThuoc_id(newThuocId8);
                    newthuocRef.child(newThuocId8).setValue(thuocipratropium);


                    Thuoc thuocVerapamil   = new Thuoc();
                    thuocVerapamil.setThuocImageid(R.drawable.verapamil);
                    thuocVerapamil.setTenthuoc("Verapamil");
                    thuocVerapamil.setTenBenh("Tim đập nhanh");
                    thuocVerapamil.setSoluong(4);
                    thuocVerapamil.setLieuluong("2/ngày");
                    String newThuocId18 = newthuocRef.push().getKey();
                    thuocVerapamil.setThuoc_id(newThuocId18);
                    newthuocRef.child(newThuocId18).setValue(thuocVerapamil);

                    Thuoc thuocMetoprolol   = new Thuoc();
                    thuocMetoprolol.setThuocImageid(R.drawable.metoprolol);
                    thuocMetoprolol.setTenthuoc("Metoprolol");
                    thuocMetoprolol.setTenBenh("Tim đập nhanh");
                    thuocMetoprolol.setSoluong(4);
                    thuocMetoprolol.setLieuluong("2/ngày");
                    String newThuocId9 = newthuocRef.push().getKey();
                    thuocMetoprolol.setThuoc_id(newThuocId9);
                    newthuocRef.child(newThuocId9).setValue(thuocMetoprolol);

                    Thuoc thuocclopidogrel   = new Thuoc();
                    thuocclopidogrel.setThuocImageid(R.drawable.clopidogrel);
                    thuocclopidogrel.setTenthuoc("clopidogrel");
                    thuocclopidogrel.setTenBenh("Tim đập nhanh");
                    thuocclopidogrel.setSoluong(4);
                    thuocclopidogrel.setLieuluong("2/ngày");
                    String newThuocId10 = newthuocRef.push().getKey();
                    thuocclopidogrel.setThuoc_id(newThuocId10);
                    newthuocRef.child(newThuocId10).setValue(thuocclopidogrel);

                    Thuoc thuoccrogaine  = new Thuoc();
                    thuoccrogaine.setThuocImageid(R.drawable.rogaine);
                    thuoccrogaine.setTenthuoc("Rogaine");
                    thuoccrogaine.setTenBenh("Rụng tóc");
                    thuoccrogaine.setSoluong(1);
                    thuoccrogaine.setLieuluong("Xịt sau khi tắm");
                    String newThuocId13 = newthuocRef.push().getKey();
                    thuoccrogaine.setThuoc_id(newThuocId13);
                    newthuocRef.child(newThuocId13).setValue(thuoccrogaine);

                    Thuoc thuocSpironolactone  = new Thuoc();
                    thuocSpironolactone.setThuocImageid(R.drawable.spironolactone);
                    thuocSpironolactone.setTenthuoc("Spironolactone");
                    thuocSpironolactone.setTenBenh("Rụng tóc");
                    thuocSpironolactone.setSoluong(4);
                    thuocSpironolactone.setLieuluong("3/ngày (1 lần 2 viên)");
                    String newThuocId14 = newthuocRef.push().getKey();
                    thuocSpironolactone.setThuoc_id(newThuocId14);
                    newthuocRef.child(newThuocId14).setValue(thuocSpironolactone);

                    Thuoc thuocTagamet = new Thuoc();
                    thuocTagamet.setThuocImageid(R.drawable.tagamet);
                    thuocTagamet.setTenthuoc("Tagamet");
                    thuocTagamet.setTenBenh("Rụng tóc");
                    thuocTagamet.setSoluong(4);
                    thuocTagamet.setLieuluong("3/ngày");
                    String newThuocId15 = newthuocRef.push().getKey();
                    thuocTagamet.setThuoc_id(newThuocId15);
                    newthuocRef.child(newThuocId15).setValue(thuocTagamet);

                    Thuoc thuocFinasteride  = new Thuoc();
                    thuocFinasteride.setThuocImageid(R.drawable.finasteride);
                    thuocFinasteride.setTenthuoc("Finasteride");
                    thuocFinasteride.setTenBenh("Rụng tóc");
                    thuocFinasteride.setSoluong(3);
                    thuocFinasteride.setLieuluong("2/ngày");
                    String newThuocId16 = newthuocRef.push().getKey();
                    thuocFinasteride.setThuoc_id(newThuocId16);
                    newthuocRef.child(newThuocId16).setValue(thuocFinasteride);
                    Thuoc thuoccClonazepam   = new Thuoc();
                    thuoccClonazepam.setThuocImageid(R.drawable.clonazepam);
                    thuoccClonazepam.setTenthuoc("Clonazepam");
                    thuoccClonazepam.setTenBenh("Giảm trí nhớ");
                    thuoccClonazepam.setSoluong(4);
                    thuoccClonazepam.setLieuluong("2/ngày");
                    String newThuocId11 = newthuocRef.push().getKey();
                    thuoccClonazepam.setThuoc_id(newThuocId11);
                    newthuocRef.child(newThuocId11).setValue(thuoccClonazepam);

                    Thuoc thuoccBromazepam   = new Thuoc();
                    thuoccBromazepam.setThuocImageid(R.drawable.bromazepam);
                    thuoccBromazepam.setTenthuoc("Bromazepam");
                    thuoccBromazepam.setTenBenh("Giảm trí nhớ");
                    thuoccBromazepam.setSoluong(4);
                    thuoccBromazepam.setLieuluong("2/ngày");
                    String newThuocId12 = newthuocRef.push().getKey();
                    thuoccBromazepam.setThuoc_id(newThuocId12);
                    newthuocRef.child(newThuocId12).setValue(thuoccBromazepam);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void PushTriLieu(){
newtrilieuRef.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(!snapshot.exists()){
            //
            Tri_Lieu homantinh = new Tri_Lieu();
            homantinh.setTenbenh("Ho mãn tính");
            homantinh.setCachtrilieu("Tập thở và kết hợp các bài tập thể dục tăng khả năng hoạt động của phổi, tránh các khói thuốc làm ảnh hưởng tới phổi");
            String newTrilieuid = newtrilieuRef.push().getKey();
            homantinh.setTrilieu_id(newTrilieuid);
            newtrilieuRef.child(newTrilieuid).setValue(homantinh);

            Tri_Lieu dauxuongkhop = new Tri_Lieu();
            dauxuongkhop.setTenbenh("Đau nhức xương");
            dauxuongkhop.setCachtrilieu("Tập vật lý trị liệu để điều trị phục hồi cho khớp bị đau. Với bệnh nhân lớn tuổi, các bài tập phục hồi đau khớp do COVID-19 có thể lâu hơn, do đó cần kiên trì luyện tập.");
            String newTrilieuid2 = newtrilieuRef.push().getKey();
            dauxuongkhop.setTrilieu_id(newTrilieuid2);
            newtrilieuRef.child(newTrilieuid2).setValue(dauxuongkhop);

            Tri_Lieu khotho = new Tri_Lieu();
            khotho.setTenbenh("Khó thở");
            khotho.setCachtrilieu("Tập các bài tập đơn giản như hít sâu thở ra chậm. Có thể tập thở bất cứ lúc nào: Khi ngồi, nằm, khi tập đi bộ, kết hợp dùng máy thổi Spirometry… Các bài tập thiền (meditation) cũng giúp bệnh nhân thở chậm và thở sâu, giúp phổi hoạt động hiệu quả hơn.");
            String newTriLieuid3 = newtrilieuRef.push().getKey();
            khotho.setTrilieu_id(newTriLieuid3);
            newtrilieuRef.child(newTriLieuid3).setValue(khotho);

            Tri_Lieu timdapnhanh = new Tri_Lieu();
            timdapnhanh.setTenbenh("Tim đập nhanh");
            timdapnhanh.setCachtrilieu("Kiểm tra siêu âm tim, xem kết quả điện tâm. Sau đó có thể kê đơn thuốc giảm nhịp tim. Bệnh nhân cần kết hợp các bài tập thể dục nhẹ nhàng, từ từ để cải thiện nhịp tim.");
            String newTriLieuid4 = newtrilieuRef.push().getKey();
            timdapnhanh.setTrilieu_id(newTriLieuid4);
            newtrilieuRef.child(newTriLieuid4).setValue(timdapnhanh);

            Tri_Lieu matrinho = new Tri_Lieu();
            matrinho.setTenbenh("Giảm trí nhớ");
            matrinho.setCachtrilieu("Đọc sách, chơi các trò chơi kích thích trí nhớ như đánh cờ, học thêm các môn khác như nấu ăn, làm bánh. Giữ cho não bộ hoạt động trở lại bằng các kích thích phản xạ lành mạnh.");
            String newTriLieuid5 = newtrilieuRef.push().getKey();
            matrinho.setTrilieu_id(newTriLieuid5);
            newtrilieuRef.child(newTriLieuid5).setValue(matrinho);

            Tri_Lieu rungtoc = new Tri_Lieu();
            rungtoc.setTenbenh("Rụng tóc");
            rungtoc.setCachtrilieu("Phần lớn tóc rụng sau khi mắc COVID-19 là do chúng ta bị lo lắng và stress. Vì vậy, đa số bệnh nhân sẽ mọc lại tóc trong vài tuần hay vài tháng sau khi hết bệnh. Có thể dùng các thuốc kết hợp như rogaine để xịt kích thích tóc mọc.");
            String newTriLieuid6 = newtrilieuRef.push().getKey();
            rungtoc.setTrilieu_id(newTriLieuid6);
            newtrilieuRef.child(newTriLieuid6).setValue(rungtoc);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

    }

    // lấy dữ liệu từ database truyền sang người dùng
    private void GetthuocbyBenh(){

        newthuocRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Thuoc thuoc = dataSnapshot.getValue(Thuoc.class);
                    String tenbenhthuoc = thuoc.getTenBenh().trim();
                    accRef.child(signInAccount.getId()).child("benh_ly").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                for (DataSnapshot benh :snapshot.getChildren()) {
                                    String tenbenh = benh.getValue().toString().trim();
                                    if (tenbenhthuoc.equals(tenbenh)) {
                                        thuocRef.child(thuoc.getThuoc_id()).setValue(thuoc);
                                    }

                                }

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

        newtrilieuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Tri_Lieu tri_lieu = dataSnapshot.getValue(Tri_Lieu.class);
                    String tentrilieu = tri_lieu.getTenbenh().trim();
                    accRef.child(signInAccount.getId()).child("benh_ly").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                for (DataSnapshot benh :snapshot.getChildren()) {
                                    String tenbenh = benh.getValue().toString().trim();
                                    if (tentrilieu.equals(tenbenh)) {
                                        trilieuRef.child(tri_lieu.getTrilieu_id()).setValue(tri_lieu);
                                    }

                                }

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
    private void checkBenh() {
        accRef.child(signInAccount.getId()).child("benh_ly").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<String> ListBenh = new ArrayList<>();
                    for (DataSnapshot benh :snapshot.getChildren()) {
                        String tenbenh = benh.getValue().toString();
                        ListBenh.add(tenbenh);
                    }
                    if( ListBenh.contains("  Đau nhức xương khớp")){
                        cbx.setChecked(true);
                    }
                    if( ListBenh.contains("  Giảm trí nhớ")){
                        cbx2.setChecked(true);
                    }
                    if( ListBenh.contains("  Rụng tóc")){
                        cbx3.setChecked(true);
                    }
                    if( ListBenh.contains("  Da nổi mẫn")){
                        cbx4.setChecked(true);
                    }
                    if( ListBenh.contains("  Mất mùi, mất vị")){
                        cbx5.setChecked(true);
                    }
                    if( ListBenh.contains("  Mệt mỏi và yếu sức")){
                        cbx6.setChecked(true);
                    }
                    if( ListBenh.contains("  Ho mãn tính")){
                        cbx7.setChecked(true);
                    }
                    if( ListBenh.contains("  Khó thở")){
                        cbx8.setChecked(true);
                    }
                    if( ListBenh.contains("  Tim đập nhanh")){
                        cbx9.setChecked(true);
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
                    String MucDo = snapshot.getValue().toString();
                    tvMD.setText(MucDo);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void homantinh(){

        Thuoc thuocbenzonatate = new Thuoc();
        thuocbenzonatate.setThuocImageid(R.drawable.benzonatate);
        thuocbenzonatate.setTenthuoc("benzonatate");
        thuocbenzonatate.setSoluong(5);
        thuocbenzonatate.setTenBenh("Ho mãn tính");
        thuocbenzonatate.setLieuluong("100mg-200mg");
        String newThuocId1 = thuocRef.push().getKey();
        thuocbenzonatate.setThuoc_id(newThuocId1);
        thuocRef.child(newThuocId1).setValue(thuocbenzonatate);

        Thuoc thuocproair = new Thuoc();
        thuocproair.setThuocImageid(R.drawable.proair);
        thuocproair.setTenthuoc("proair");
        thuocproair.setSoluong(1);
        thuocproair.setTenBenh("Ho mãn tính");
        thuocproair.setLieuluong("2/ngày");
        String newThuocId2 = thuocRef.push().getKey();
        thuocproair.setThuoc_id(newThuocId2);
        thuocRef.child(newThuocId2).setValue(thuocproair);

        Thuoc thuocmucinex = new Thuoc();
        thuocmucinex.setThuocImageid(R.drawable.mucinex);
        thuocmucinex.setTenthuoc("mucinex");
        thuocmucinex.setSoluong(4);
        thuocmucinex.setTenBenh("Ho mãn tính");
        thuocmucinex.setLieuluong("2/ngày");
        String newThuocId3 = thuocRef.push().getKey();
        thuocmucinex.setThuoc_id(newThuocId3);
        thuocRef.child(newThuocId3).setValue(thuocmucinex);

        Tri_Lieu homantinh = new Tri_Lieu();
        homantinh.setTenbenh("Ho mãn tính");
        homantinh.setCachtrilieu("Tập thở và kết hợp các bài tập thể dục tăng khả năng hoạt động của phổi, tránh các khói thuốc làm ảnh hưởng tới phổi");
        String newTrilieuid = trilieuRef.push().getKey();
        homantinh.setTrilieu_id(newTrilieuid);
        trilieuRef.child(newTrilieuid).setValue(homantinh);


    }
    public void daunhucxuong(){
        Thuoc thuocibuprofen  = new Thuoc();
        thuocibuprofen.setThuocImageid(R.drawable.ibuprofen);
        thuocibuprofen.setTenthuoc("ibuprofen");
        thuocibuprofen.setTenBenh("Đau nhức xương");
        thuocibuprofen.setSoluong(4);
        thuocibuprofen.setLieuluong("2/ngày");
        String newThuocId4 = thuocRef.push().getKey();
        thuocibuprofen.setThuoc_id(newThuocId4);
        thuocRef.child(newThuocId4).setValue(thuocibuprofen);

        Thuoc thuocacetaminophen   = new Thuoc();
        thuocacetaminophen.setThuocImageid(R.drawable.acetaminophen);
        thuocacetaminophen.setTenthuoc("acetaminophen");
        thuocacetaminophen.setTenBenh("Đau nhức xương");
        thuocacetaminophen.setSoluong(4);
        thuocacetaminophen.setLieuluong("2/ngày");
        String newThuocId5 = thuocRef.push().getKey();
        thuocacetaminophen.setThuoc_id(newThuocId5);
        thuocRef.child(newThuocId5).setValue(thuocacetaminophen);

        Tri_Lieu dauxuongkhop = new Tri_Lieu();
        dauxuongkhop.setTenbenh("Đau nhức xương");
        dauxuongkhop.setCachtrilieu("Tập vật lý trị liệu để điều trị phục hồi cho khớp bị đau. Với bệnh nhân lớn tuổi, các bài tập phục hồi đau khớp do COVID-19 có thể lâu hơn, do đó cần kiên trì luyện tập.");
        String newTrilieuid2 = trilieuRef.push().getKey();
        dauxuongkhop.setTrilieu_id(newTrilieuid2);
        trilieuRef.child(newTrilieuid2).setValue(dauxuongkhop);
    }
    public void khotho(){

        Thuoc thuocsalbutamol   = new Thuoc();
        thuocsalbutamol.setThuocImageid(R.drawable.salbutamol);
        thuocsalbutamol.setTenthuoc("salbutamol");
        thuocsalbutamol.setTenBenh("Khó thở");
        thuocsalbutamol.setSoluong(4);
        thuocsalbutamol.setLieuluong("2/ngày");
        String newThuocId6 = thuocRef.push().getKey();
        thuocsalbutamol.setThuoc_id(newThuocId6);
        thuocRef.child(newThuocId6).setValue(thuocsalbutamol);

        Thuoc thuocterbutaline   = new Thuoc();
        thuocterbutaline.setThuocImageid(R.drawable.brycanyl);
        thuocterbutaline.setTenthuoc("terbutaline");
        thuocterbutaline.setTenBenh("Khó thở");
        thuocterbutaline.setSoluong(4);
        thuocterbutaline.setLieuluong("2/ngày");
        String newThuocId7 = thuocRef.push().getKey();
        thuocterbutaline.setThuoc_id(newThuocId7);
        thuocRef.child(newThuocId7).setValue(thuocterbutaline);

        Thuoc thuocipratropium   = new Thuoc();
        thuocipratropium.setThuocImageid(R.drawable.ipratropium);
        thuocipratropium.setTenthuoc("ipratropium");
        thuocipratropium.setTenBenh("Khó thở");
        thuocipratropium.setSoluong(4);
        thuocipratropium.setLieuluong("2/ngày");
        String newThuocId8 = thuocRef.push().getKey();
        thuocipratropium.setThuoc_id(newThuocId8);
        thuocRef.child(newThuocId8).setValue(thuocipratropium);

        Tri_Lieu khotho = new Tri_Lieu();
        khotho.setTenbenh("Khó thở");
        khotho.setCachtrilieu("Tập các bài tập đơn giản như hít sâu thở ra chậm. Có thể tập thở bất cứ lúc nào: Khi ngồi, nằm, khi tập đi bộ, kết hợp dùng máy thổi Spirometry… Các bài tập thiền (meditation) cũng giúp bệnh nhân thở chậm và thở sâu, giúp phổi hoạt động hiệu quả hơn.");
        String newTriLieuid3 = trilieuRef.push().getKey();
        khotho.setTrilieu_id(newTriLieuid3);
        trilieuRef.child(newTriLieuid3).setValue(khotho);
    }
    private void timdapnhanh(){
        Thuoc thuocVerapamil   = new Thuoc();
        thuocVerapamil.setThuocImageid(R.drawable.verapamil);
        thuocVerapamil.setTenthuoc("Verapamil");
        thuocVerapamil.setTenBenh("Tim đập nhanh");
        thuocVerapamil.setSoluong(4);
        thuocVerapamil.setLieuluong("2/ngày");
        String newThuocId8 = thuocRef.push().getKey();
        thuocVerapamil.setThuoc_id(newThuocId8);
        thuocRef.child(newThuocId8).setValue(thuocVerapamil);

        Thuoc thuocMetoprolol   = new Thuoc();
        thuocMetoprolol.setThuocImageid(R.drawable.metoprolol);
        thuocMetoprolol.setTenthuoc("Metoprolol");
        thuocMetoprolol.setTenBenh("Tim đập nhanh");
        thuocMetoprolol.setSoluong(4);
        thuocMetoprolol.setLieuluong("2/ngày");
        String newThuocId9 = thuocRef.push().getKey();
        thuocVerapamil.setThuoc_id(newThuocId9);
        thuocRef.child(newThuocId9).setValue(thuocMetoprolol);

        Thuoc thuocclopidogrel   = new Thuoc();
        thuocclopidogrel.setThuocImageid(R.drawable.clopidogrel);
        thuocclopidogrel.setTenthuoc("clopidogrel");
        thuocclopidogrel.setTenBenh("Tim đập nhanh");
        thuocclopidogrel.setSoluong(4);
        thuocclopidogrel.setLieuluong("2/ngày");
        String newThuocId10 = thuocRef.push().getKey();
        thuocclopidogrel.setThuoc_id(newThuocId10);
        thuocRef.child(newThuocId10).setValue(thuocclopidogrel);

        Tri_Lieu timdapnhanh = new Tri_Lieu();
        timdapnhanh.setTenbenh("Tim đập nhanh");
        timdapnhanh.setCachtrilieu("Kiểm tra siêu âm tim, xem kết quả điện tâm. Sau đó có thể kê đơn thuốc giảm nhịp tim. Bệnh nhân cần kết hợp các bài tập thể dục nhẹ nhàng, từ từ để cải thiện nhịp tim.");
        String newTriLieuid4 = trilieuRef.push().getKey();
        timdapnhanh.setTrilieu_id(newTriLieuid4);
        trilieuRef.child(newTriLieuid4).setValue(timdapnhanh);
    }
    private void mattrinho(){
        Thuoc thuoccClonazepam   = new Thuoc();
        thuoccClonazepam.setThuocImageid(R.drawable.clonazepam);
        thuoccClonazepam.setTenthuoc("Clonazepam");
        thuoccClonazepam.setTenBenh("Mất trí nhớ");
        thuoccClonazepam.setSoluong(4);
        thuoccClonazepam.setLieuluong("2/ngày");
        String newThuocId11 = thuocRef.push().getKey();
        thuoccClonazepam.setThuoc_id(newThuocId11);
        thuocRef.child(newThuocId11).setValue(thuoccClonazepam);

        Thuoc thuoccBromazepam   = new Thuoc();
        thuoccBromazepam.setThuocImageid(R.drawable.bromazepam);
        thuoccBromazepam.setTenthuoc("Bromazepam");
        thuoccBromazepam.setTenBenh("Mất trí nhớ");
        thuoccBromazepam.setSoluong(4);
        thuoccBromazepam.setLieuluong("2/ngày");
        String newThuocId12 = thuocRef.push().getKey();
        thuoccBromazepam.setThuoc_id(newThuocId12);
        thuocRef.child(newThuocId12).setValue(thuoccBromazepam);

        Tri_Lieu matrinho = new Tri_Lieu();
        matrinho.setTenbenh("Mất trí nhớ");
        matrinho.setCachtrilieu("Đọc sách, chơi các trò chơi kích thích trí nhớ như đánh cờ, học thêm các môn khác như nấu ăn, làm bánh. Giữ cho não bộ hoạt động trở lại bằng các kích thích phản xạ lành mạnh.");
        String newTriLieuid5 = trilieuRef.push().getKey();
        matrinho.setTrilieu_id(newTriLieuid5);
        trilieuRef.child(newTriLieuid5).setValue(matrinho);
    }
    private void rungtoc(){
        Thuoc thuoccrogaine  = new Thuoc();
        thuoccrogaine.setThuocImageid(R.drawable.rogaine);
        thuoccrogaine.setTenthuoc("Rogaine");
        thuoccrogaine.setTenBenh("Rụng tóc");
        thuoccrogaine.setSoluong(1);
        thuoccrogaine.setLieuluong("Xịt sau khi tắm");
        String newThuocId13 = thuocRef.push().getKey();
        thuoccrogaine.setThuoc_id(newThuocId13);
        thuocRef.child(newThuocId13).setValue(thuoccrogaine);

        Thuoc thuocSpironolactone  = new Thuoc();
        thuocSpironolactone.setThuocImageid(R.drawable.spironolactone);
        thuocSpironolactone.setTenthuoc("Spironolactone");
        thuocSpironolactone.setTenBenh("Rụng tóc");
        thuocSpironolactone.setSoluong(4);
        thuocSpironolactone.setLieuluong("3/ngày (1 lần 2 viên)");
        String newThuocId14 = thuocRef.push().getKey();
        thuocSpironolactone.setThuoc_id(newThuocId14);
        thuocRef.child(newThuocId14).setValue(thuocSpironolactone);

        Thuoc thuocTagamet = new Thuoc();
        thuocTagamet.setThuocImageid(R.drawable.tagamet);
        thuocTagamet.setTenthuoc("Tagamet");
        thuocTagamet.setTenBenh("Rụng tóc");
        thuocTagamet.setSoluong(4);
        thuocTagamet.setLieuluong("3/ngày");
        String newThuocId15 = thuocRef.push().getKey();
        thuocTagamet.setThuoc_id(newThuocId15);
        thuocRef.child(newThuocId15).setValue(thuocTagamet);

        Thuoc thuocFinasteride  = new Thuoc();
        thuocFinasteride.setThuocImageid(R.drawable.finasteride);
        thuocFinasteride.setTenthuoc("Finasteride");
        thuocFinasteride.setTenBenh("Rụng tóc");
        thuocFinasteride.setSoluong(3);
        thuocFinasteride.setLieuluong("2/ngày");
        String newThuocId16 = thuocRef.push().getKey();
        thuocFinasteride.setThuoc_id(newThuocId16);
        thuocRef.child(newThuocId16).setValue(thuocFinasteride);

        Tri_Lieu rungtoc = new Tri_Lieu();
        rungtoc.setTenbenh("Rụng tóc");
        rungtoc.setCachtrilieu("Phần lớn tóc rụng sau khi mắc COVID-19 là do chúng ta bị lo lắng và stress. Vì vậy, đa số bệnh nhân sẽ mọc lại tóc trong vài tuần hay vài tháng sau khi hết bệnh. Có thể dùng các thuốc kết hợp như rogaine để xịt kích thích tóc mọc.");
        String newTriLieuid6 = trilieuRef.push().getKey();
        rungtoc.setTrilieu_id(newTriLieuid6);
        trilieuRef.child(newTriLieuid6).setValue(rungtoc);

    }

    private void init(){
        btnXoa = (Button) findViewById(R.id.btnChonLai);
        btnLuu = (Button) findViewById(R.id.btnTiep);
        cbx = (CheckBox) findViewById(R.id.cbx);
        cbx2 = (CheckBox) findViewById(R.id.cbx2);
        cbx3 = (CheckBox) findViewById(R.id.cbx3);
        cbx4 = (CheckBox) findViewById(R.id.cbx4);
        cbx5 = (CheckBox) findViewById(R.id.cbx5);
        cbx6 = (CheckBox) findViewById(R.id.cbx6);
        cbx7 = (CheckBox) findViewById(R.id.cbx7);
        cbx8 = (CheckBox) findViewById(R.id.cbx8);
        cbx9 = (CheckBox) findViewById(R.id.cbx9);
        toolbar= (Toolbar) findViewById(R.id.trieuchungtoolbar);
        tvMD=(TextView) findViewById(R.id.tvMUCDO);
    }
}
