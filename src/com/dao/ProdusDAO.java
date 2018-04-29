package com.dao;

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

    public ArrayList<Produs> listaProduse = new ArrayList<Produs>();

    public ArrayList<Produs> addProdus() {
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

    public void insert(Produs produs) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setInt(1,produs.getId());
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
            for (Produs pr : listaProduse) {
                if (pr.getId() == produs.getId()) {
                    pr.setDenumire(produs.getDenumire());
                    pr.setCantitate(produs.getCantitate());
                    pr.setPret(produs.getPret());
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
