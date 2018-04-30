package com.dao;

import com.business.ValidareClient;
import com.connection.ConnectionDb;
import com.model.Client;

import java.sql.*;
import java.util.ArrayList;

public class ClientDAO {
    private final static String findStatementString = "SELECT * FROM schooldb.client;";
    private final static String insertStatementString = "INSERT INTO `schooldb`.`client` (`id`, `nume`, `email`, `telefon`) VALUES (?, ?, ?, ?);\n";
    private final static String deleteStatementString = "DELETE FROM `schooldb`.`client` WHERE id=?;";
    private final static String updateStatementString = "UPDATE schooldb.client SET nume=?,email=? ,telefon=? WHERE id=?;";
    public ArrayList<Client> listaClienti = new ArrayList<Client>();
    private ValidareClient val = new ValidareClient();

    public ArrayList<Client> addClienti() {

        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement findStatement = null;
        ResultSet res = null;

        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            res = findStatement.executeQuery();
            listaClienti.clear();
            while (res.next()) {
                listaClienti.add(new Client(res.getInt("id"), res.getString("nume"), res.getString("email"), res.getString("telefon")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(findStatement);
            ConnectionDb.close(res);
        }

        return listaClienti;
    }

    private ArrayList<Integer> getID() {

        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Client cl : addClienti()) {
            ids.add(cl.getIdClient());
        }
        return ids;
    }

    public void insert(Client client) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement insertStatement = null;
        val.validareCampuri(client, getID());
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setInt(1, client.getIdClient());
            insertStatement.setString(2, client.getNume());
            insertStatement.setString(3, client.getEmail());
            insertStatement.setString(4, client.getTelefon());
            insertStatement.executeUpdate();
            listaClienti.add(client);
            System.out.println("ADDED");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(insertStatement);
        }
    }

    public void delete(Client client) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, client.getIdClient());
            deleteStatement.executeUpdate();
            System.out.println("STERS");
            listaClienti.remove(client);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(deleteStatement);
        }
    }

    public void update(Client client) {
        Connection dbConnection = ConnectionDb.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setInt(4, client.getIdClient());
            updateStatement.setString(1, client.getNume());
            updateStatement.setString(2, client.getEmail());
            updateStatement.setString(3, client.getTelefon());
            updateStatement.executeUpdate();
            System.out.println("UPDATED");
            for (Client cl : listaClienti) {
                if (cl.getIdClient() == client.getIdClient()) {
                    cl.setEmail(client.getEmail());
                    cl.setNume(client.getNume());
                    cl.setTelefon(client.getTelefon());
                }

            }
            System.out.println("Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDb.close(dbConnection);
            ConnectionDb.close(updateStatement);
        }
    }
}
