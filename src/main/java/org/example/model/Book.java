package org.example.model;

public class Book {
    private int id; // Tambahkan atribut id
    private String judul;
    private String pengarang;
    private String penerbit;

    public Book(int id, String judul, String pengarang, String penerbit) {
        this.id = id; // Inisialisasi id
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }
}