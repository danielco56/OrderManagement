package com.presentation;

import com.dao.ClientDAO;
import com.dao.ComandaDAO;
import com.dao.ProdusDAO;
import com.model.Client;
import com.model.Comanda;
import com.model.Produs;
import sun.applet.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIOrders {
    ArrayList<Comanda> comenzi = new ArrayList<>();
    ComandaDAO com1 = new ComandaDAO();
    ProdusDAO prod1 = new ProdusDAO();
    GUIProdus guiProdus=new GUIProdus();

    private JFrame frame = new JFrame("Order Management");
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel inpanel1 = new JPanel();
    private JPanel inpanel2 = new JPanel();

    private JLabel nume = new JLabel("Nume Produs: ", JLabel.LEFT);
    private JLabel idClient = new JLabel("ID Client: ", JLabel.LEFT);
    private JLabel cantitate = new JLabel("Cantitate Produs: ", JLabel.LEFT);
    private JLabel facturaLabel = new JLabel("ID comanda pentru factura : ", JLabel.LEFT);
    public JButton butonInserare = new JButton("Adaugare Comanda");
    private JButton facturaButton = new JButton("Generare Factura");


    private final JTextField numeField = new JTextField(6);
    private final JTextField idClientField = new JTextField(6);
    private final JTextField cantitateField = new JTextField(6);
    private final JTextField facturaField = new JTextField(6);


    private JTable table = new JTable();
    private DefaultTableModel model = (DefaultTableModel) table.getModel();

    public void afisareGUI() {


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 300));
        frame.setBackground(Color.gray);
        frame.setLayout(new GridLayout(2, 1));

        panel1.setLayout(new GridLayout(3, 1));
        inpanel1.setLayout(new GridLayout(3, 2));
        inpanel2.setLayout(new GridLayout(1, 3));

        frame.add(panel1);
        frame.add(panel2);

        frame.pack();
        frame.setVisible(true);
    }

    public void adaugareComponente() {

        panel1.add(inpanel1);

        inpanel1.add(nume);
        inpanel1.add(numeField);
        inpanel1.add(idClient);
        inpanel1.add(idClientField);
        inpanel1.add(cantitate);
        inpanel1.add(cantitateField);
        panel1.add(butonInserare);
        panel1.add(inpanel2);
        inpanel2.add(facturaLabel);
        inpanel2.add(facturaField);
        inpanel2.add(facturaButton);
        panel2.add(new JScrollPane(table));

    }

    public void addToTable() {
        comenzi = com1.addComanda();
        Object[] date = new Object[5];

        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID Comanda");
        model.addColumn("Nume Produs");
        model.addColumn("ID Client");
        model.addColumn("Cantitate");
        model.addColumn("Total");


        for (int i = 0; i < comenzi.size(); i++) {
            date[0] = comenzi.get(i).getIdorder();
            date[1] = comenzi.get(i).getNumeProdus();
            date[2] = comenzi.get(i).getIdClient();
            date[3] = comenzi.get(i).getCantitate();
            date[4] = comenzi.get(i).getTotal();
            model.addRow(date);
        }

    }

    public void operatiiButoane() {

        butonInserare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prod1.addProdus();
                for (Produs pr : prod1.listaProduse) {
                    if(pr.getDenumire().equals(numeField.getText()))
                        prod1.update(new Produs(pr.getId(),pr.getDenumire(),pr.getCantitate()-Integer.parseInt(cantitateField.getText()),pr.getPret()));
                }
                com1.insert(new Comanda(numeField.getText(), Integer.parseInt(idClientField.getText()), Integer.parseInt(cantitateField.getText())));
                addToTable();
            }
        });
    }
}
