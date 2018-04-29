package com.model;

public class Comanda {
    private int idorder;
    private String numeProdus;
    private int idClient;
    private int cantitate;
    private double total;

    public Comanda(int idorder, String numeProdus, int idClient, int cantitate, double total) {
        this.idorder = idorder;
        this.numeProdus = numeProdus;
        this.idClient = idClient;
        this.cantitate = cantitate;
        this.total = total;
    }

    public Comanda(String numeProdus, int idClient, int cantitate, double total) {
        this.numeProdus = numeProdus;
        this.idClient = idClient;
        this.cantitate = cantitate;
        this.total = total;
    }

    public Comanda(String numeProdus, int idClient, int cantitate) {
        this.numeProdus = numeProdus;
        this.idClient = idClient;
        this.cantitate = cantitate;
    }

    public int getIdorder() {
        return idorder;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public double getTotal() {
        return total;
    }

    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
