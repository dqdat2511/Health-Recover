package com.example.cssk.object;

public class Thuoc {

    String thuoc_id;
    String tenthuoc;
    String lieuluong;
    public int soluong;
    int thuocImageid;
    String tenBenh;


    public Thuoc() {
    }

    public Thuoc(String thuoc_id, String tenthuoc, String lieuluong, int soluong, int thuocImageid, String tenBenh) {
        this.thuoc_id = thuoc_id;
        this.tenthuoc = tenthuoc;
        this.lieuluong = lieuluong;
        this.soluong = soluong;
        this.thuocImageid = thuocImageid;
        this.tenBenh = tenBenh;
    }

    public String getThuoc_id() {
        return thuoc_id;
    }

    public void setThuoc_id(String thuoc_id) {
        this.thuoc_id = thuoc_id;
    }

    public String getTenthuoc() {
        return tenthuoc;
    }

    public void setTenthuoc(String tenthuoc) {
        this.tenthuoc = tenthuoc;
    }

    public String getLieuluong() {
        return lieuluong;
    }

    public void setLieuluong(String lieuluong) {
        this.lieuluong = lieuluong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getThuocImageid() {
        return thuocImageid;
    }

    public void setThuocImageid(int thuocImageid) {
        this.thuocImageid = thuocImageid;
    }

    public String getTenBenh() {
        return tenBenh;
    }

    public void setTenBenh(String tenBenh) {
        this.tenBenh = tenBenh;
    }
}
