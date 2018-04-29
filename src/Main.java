import com.connection.ConnectionDb;
import com.dao.ClientDAO;
import com.model.Client;
import com.presentation.GUI;
import com.presentation.GUIOrders;
import com.presentation.GUIProdus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        GUI interfat = new GUI();
        GUIProdus interfataP=new GUIProdus();
        GUIOrders interfataO=new GUIOrders();

        interfat.addToTable();
        interfataP.addToTable();
        interfataO.addToTable();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                interfat.afisareGUI();
                interfat.adaugareComponente();
                interfat.operatiiButoane();
            }
        });

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                interfataP.afisareGUI();
                interfataP.adaugareComponente();
                interfataP.operatiiButoane();
            }
        });

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                interfataO.afisareGUI();
                interfataO.adaugareComponente();
                interfataO.operatiiButoane();
            }
        });
    }
}
