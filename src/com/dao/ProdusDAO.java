package com.dao;

import com.business.Validare;
import com.business.ValidareProdus;
import com.connection.ConnectionDb;
import com.model.Produs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdusDAO {
    private final static String findStatementString = "SELECT * FROM schooldb.produs;";
    private final static String insertStatementString = "INSERT INTO `schooldb`.`produs` (`id`,`denumire`, `cantitate`, `pret`) VALUES (?,?, ?, ?);";
    private final static String deleteStatementString = "DELETE FROM `schooldb`.`produs` WHERE id=?;";
    private final static String updateStatementString = "UPDATE schooldb.produs SET denumire=?,cantitate=? ,pret=? WHERE id=?;";

    public ArrayList<Object> listaProduse = new ArrayList<Object>();
    private ValidareProdus val = new ValidareProdus();

    public ArrayList<Object> addProdus() {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement findStatement = null;
        ResultSet res = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            res = findStatement.executeQuery();
            listaProduse.clear();
            while (res.next()) {
                listaProduse.add(new Produs(res.getInt("id"), res.getString("denumire"), res.getInt("cantitate"), res.getDouble("pret")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(findStatement);
            ConnectionDb.close(res);
        }

        return listaProduse;

    }

    private ArrayList<Integer> getID() {

        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Object pr : addProdus()) {
            ids.add(((Produs)pr).getId());
        }
        return ids;
    }

    public void insert(Produs produs) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement insertStatement = null;
        val.validareCampuri(produs, getID());
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setInt(1, produs.getId());
            insertStatement.setString(2, produs.getDenumire());
            insertStatement.setInt(3, produs.getCantitate());
            insertStatement.setDouble(4, produs.getPret());
            insertStatement.executeUpdate();
            listaProduse.add(produs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(insertStatement);
        }
    }

    public void delete(Produs produs) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, produs.getId());
            deleteStatement.executeUpdate();
            listaProduse.remove(produs);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(deleteStatement);
        }
    }

    public void update(Produs produs) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement updateStatement = null;

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, produs.getDenumire());
            updateStatement.setInt(2, produs.getCantitate());
            updateStatement.setDouble(3, produs.getPret());
            updateStatement.setInt(4, produs.getId());
            updateStatement.executeUpdate();
            for (Object pr : listaProduse) {
                if (((Produs)pr).getId() == produs.getId()) {
                    ((Produs)pr).setDenumire(produs.getDenumire());
                    ((Produs)pr).setCantitate(produs.getCantitate());
                    ((Produs)pr).setPret(produs.getPret());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(updateStatement);
            ConnectionDb.close(dbConnection);
        }


    }

}
