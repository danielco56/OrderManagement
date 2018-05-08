package com.business;

import com.connection.ConnectionDb;
import com.dao.ClientDAO;
import com.dao.ComandaDAO;
import com.dao.ProdusDAO;
import com.model.Client;
import com.presentation.GUI;
import com.presentation.GUIOrders;
import com.presentation.GUIProdus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {



    public static void main(String[] args) {


        GUI interfat = new GUI();
        ClientDAO cl=new ClientDAO();
        ComandaDAO com1=new ComandaDAO();
        ProdusDAO p1=new ProdusDAO();
        GUIProdus interfataP=new GUIProdus();
        GUIOrders interfataO=new GUIOrders();

        interfataO.butonInserare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfataP.addToTable(p1.addProdus());
            }
        });

        interfat.addToTable(cl.addClienti());
        interfataP.addToTable(p1.addProdus());
        interfataO.addToTable(com1.addComanda());

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                interfat.afisareGUI();
                interfat.adaugareComponente();
                interfat.operatiiButoane();
                interfataP.afisareGUI();
                interfataP.adaugareComponente();
                interfataP.operatiiButoane();
                interfataO.afisareGUI();
                interfataO.adaugareComponente();
                interfataO.operatiiButoane();
            }
        });


    }

}
