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
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GUIOrders {
    private ArrayList<Comanda> comenzi = new ArrayList<>();
    private ComandaDAO com1 = new ComandaDAO();
    private ProdusDAO prod1 = new ProdusDAO();
    private ClientDAO clie1 = new ClientDAO();
    private GUIProdus guiProdus = new GUIProdus();

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
    public void addToTable(ArrayList<Object> lista) {


        int i = 0,j=0;
        model.setRowCount(0);
        model.setColumnCount(0);

        for (Field field : lista.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                j++;
                model.addColumn(field.getName());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        Object[] date = new Object[j];
        for (Object object : lista) {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value;
                try {

                    value = field.get(object);
                    date[i] = value;
                    i++;
                    if (i == j) {
                        i = 0;
                        model.addRow(date);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void operatiiButoane() {

        butonInserare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prod1.addProdus();
                for (Object pr : prod1.listaProduse) {
                    if (((Produs)pr).getDenumire().equals(numeField.getText()) && ((Produs)pr).getCantitate() >= Integer.parseInt(cantitateField.getText())) {
                        prod1.update(new Produs(((Produs)pr).getId(), ((Produs)pr).getDenumire(), ((Produs)pr).getCantitate() - Integer.parseInt(cantitateField.getText()), ((Produs)pr).getPret()));
                        com1.insert(new Comanda(numeField.getText(), Integer.parseInt(idClientField.getText()), Integer.parseInt(cantitateField.getText())));
                        addToTable(com1.addComanda());
                    } else if (((Produs)pr).getDenumire().equals(numeField.getText()) && ((Produs)pr).getCantitate() < Integer.parseInt(cantitateField.getText())) {
                        JOptionPane.showMessageDialog(frame, "Cantitatea este indisponibila!");

                    }

                }
            }
        });

        facturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComandaDAO comTest = new ComandaDAO();
                ClientDAO clieTest = new ClientDAO();
                ProdusDAO proTest = new ProdusDAO();

                proTest.addProdus();
                clieTest.addClienti();
                comTest.addComanda();

                double total = 0;
                int cantitate = 0;
                double pret = 0;
                int idClient = 0;
                String numeClient = "";
                String numeProdus = "";

                for (Object com : comTest.listaComenzi) {
                    if (((Comanda) com).getIdorder() == Integer.parseInt(facturaField.getText()))
                        total = ((Comanda) com).getTotal();
                    cantitate = ((Comanda) com).getCantitate();
                    numeProdus = ((Comanda) com).getNumeProdus();
                    idClient = ((Comanda) com).getIdClient();
                }

                for (Object cl : clieTest.listaClienti) {
                    if (((Client) cl).getIdClient() == idClient) {
                        numeClient = ((Client) cl).getNume();
                    }
                }


                createInvoice(numeClient, numeProdus, total / cantitate, cantitate, total);

            }
        });
    }

    private void createInvoice(String numeClient, String numeProdus, double pret, int cantitate, double total) {


        try {
            FileWriter writer = new FileWriter("Factura.txt");
            writer.write("Comanda Nr: " + facturaField.getText() + " ");
            writer.write(System.lineSeparator());
            writer.write("Clientul: " + numeClient + " ");
            writer.write(System.lineSeparator());
            writer.write("Nume produs: " + numeProdus + "     " + cantitate + " X " + pret + " ");
            writer.write(System.lineSeparator());
            writer.write("_______________________________________" + " ");
            writer.write(System.lineSeparator());
            writer.write("TOTAL: " + total + " ");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
