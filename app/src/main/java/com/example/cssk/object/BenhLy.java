package com.example.cssk.object;

public class BenhLy {
    String BenhLy_Id;
    String TenBenhLy;
    String MucDo;

    public BenhLy() {
    }

    public BenhLy(String benhLy_Id, String tenBenhLy, String mucDo) {
        BenhLy_Id = benhLy_Id;
        TenBenhLy = tenBenhLy;
        MucDo = mucDo;
    }

    public String getBenhLy_Id() {
        return BenhLy_Id;
    }

    public void setBenhLy_Id(String benhLy_Id) {
        BenhLy_Id = benhLy_Id;
    }

    public String getTenBenhLy() {
        return TenBenhLy;
    }

    public void setTenBenhLy(String tenBenhLy) {
        TenBenhLy = tenBenhLy;
    }

    public String getMucDo() {
        return MucDo;
    }

    public void setMucDo(String mucDo) {
        MucDo = mucDo;
    }

    @Override
    public String toString() {
        return "BenhLy{" +
                "BenhLy_Id='" + BenhLy_Id + '\'' +
                ", TenBenhLy='" + TenBenhLy + '\'' +
                ", MucDo='" + MucDo + '\'' +
                '}';
    }
}
