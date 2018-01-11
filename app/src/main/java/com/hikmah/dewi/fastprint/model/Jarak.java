package com.hikmah.dewi.fastprint.model;

/**
 * Created by dewi on 9/25/2017.
 */

public class Jarak {
    String nama;
    String alamat;
    String jarak;
    String id_rp;
    public  Jarak(){

    }
    public Jarak(String id_rp,String nama, String alamat, String jarak){
        this.id_rp = id_rp;
        this.nama = nama;
        this.alamat = alamat;
        this.jarak = jarak;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getJarak() {
        return jarak;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setId_rp(String id_rp) {
        this.id_rp = id_rp;
    }

    public String getId_rp() {
        return id_rp;
    }
}
