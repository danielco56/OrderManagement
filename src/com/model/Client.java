package com.model;

public class Client {

    private int idClient;
    private String nume;
    private String email;
    private String telefon;

    public Client(int idClient, String nume, String email, String telefon) {
        this.idClient = idClient;
        this.nume = nume;
        this.email = email;
        this.telefon = telefon;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNume() {
        return nume;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
