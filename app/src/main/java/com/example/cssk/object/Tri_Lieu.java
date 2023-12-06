package com.example.cssk.object;

public class Tri_Lieu {
    String trilieu_id;
    String tenbenh;
    String cachtrilieu;

    public Tri_Lieu() {
    }

    public Tri_Lieu(String trilieu_id, String tenbenh, String cachtrilieu) {
        this.trilieu_id = trilieu_id;
        this.tenbenh = tenbenh;
        this.cachtrilieu = cachtrilieu;
    }

    public String getTrilieu_id() {
        return trilieu_id;
    }

    public void setTrilieu_id(String trilieu_id) {
        this.trilieu_id = trilieu_id;
    }

    public String getTenbenh() {
        return tenbenh;
    }

    public void setTenbenh(String tenbenh) {
        this.tenbenh = tenbenh;
    }

    public String getCachtrilieu() {
        return cachtrilieu;
    }

    public void setCachtrilieu(String cachtrilieu) {
        this.cachtrilieu = cachtrilieu;
    }
}
