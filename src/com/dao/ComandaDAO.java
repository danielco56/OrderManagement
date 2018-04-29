package com.dao;


import com.connection.ConnectionDb;
import com.model.Comanda;
import com.model.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComandaDAO {

    private final static String findStatementString = "SELECT * FROM schooldb.comanda;";
    private final static String insertStatementString = "INSERT INTO `schooldb`.`comanda` (`numeProdus`, `idClient`, `cantitate`,`total`) VALUES (?, ?, ?, ?);";

    public ArrayList<Comanda> listaComenzi = new ArrayList<Comanda>();

    public ArrayList<Comanda> addComanda() {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement findStatement = null;
        ResultSet res = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            res = findStatement.executeQuery();
            listaComenzi.clear();
            while (res.next()) {
                listaComenzi.add(new Comanda(res.getInt("idcomanda"), res.getString("numeProdus"), res.getInt("idClient"), res.getInt("cantitate"), res.getDouble("total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(findStatement);
            ConnectionDb.close(res);
        }

        return listaComenzi;

    }

    public void insert(Comanda comanda) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement insertStatement = null;
        try {
            calculTotal(comanda);
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setString(1, comanda.getNumeProdus());
            insertStatement.setInt(2, comanda.getIdClient());
            insertStatement.setInt(3, comanda.getCantitate());
            insertStatement.setDouble(4, calculTotal(comanda));
            insertStatement.executeUpdate();
            addComanda();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(insertStatement);
        }
    }

    private double calculTotal(Comanda comanda) {
        double total = 0;
        double pret = 0;
        ProdusDAO prod = new ProdusDAO();
        prod.addProdus();
        for (Produs pr : prod.listaProduse) {
            System.out.println(pr.getDenumire());
            if (pr.getDenumire().equals(comanda.getNumeProdus()))
                pret = pr.getPret();
        }
        total = comanda.getCantitate() * pret;

        return total;
    }

}
