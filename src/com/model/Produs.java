package com.model;

public class Produs {

    private int idProdus;
    private String denumire;
    private int cantitate;
    private double pret;

    public Produs(int id, String denumire, int cantitate, double pret) {
        this.idProdus = id;
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public Produs(String denumire, int cantitate, double pret) {
        this.denumire = denumire;
        this.cantitate = cantitate;
        this.pret = pret;
    }

    public int getId() {
        return idProdus;
    }

    public String getDenumire() {
        return denumire;
    }

    public int getCantitate() {
        return cantitate;
    }

    public double getPret() {
        return pret;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
}
