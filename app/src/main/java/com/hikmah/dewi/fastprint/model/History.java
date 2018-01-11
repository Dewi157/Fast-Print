package com.hikmah.dewi.fastprint.model;

/**
 * Created by dewi on 10/31/2017.
 */

public class History {
    String nama_rp;
    String tanggal_rp;
    String bayar_rp;
    String status_rp;

    public History() {
    }

    public History(String nama_rp, String tanggal_rp, String bayar_rp, String status_rp) {
        this.nama_rp = nama_rp;
        this.tanggal_rp = tanggal_rp;
        this.bayar_rp = bayar_rp;
        this.status_rp = status_rp;
    }

    public void setNama_rp(String nama_rp) {
        this.nama_rp = nama_rp;
    }

    public void setTanggal_rp(String tanggal_rp) {
        this.tanggal_rp = tanggal_rp;
    }

    public void setBayar_rp(String bayar_rp) {
        this.bayar_rp = bayar_rp;
    }

    public void setStatus_rp(String status_rp) {
        this.status_rp = status_rp;
    }

    public String getNama_rp() {
        return nama_rp;
    }

    public String getTanggal_rp() {
        return tanggal_rp;
    }

    public String getBayar_rp() {
        return bayar_rp;
    }

    public String getStatus_rp() {
        return status_rp;
    }

}
