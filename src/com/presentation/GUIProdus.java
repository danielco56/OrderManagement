package com.presentation;

import com.dao.ProdusDAO;
import com.model.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GUIProdus {
    ArrayList<Produs> produse = new ArrayList<>();
    private ProdusDAO p1 = new ProdusDAO();

    private JFrame frame = new JFrame("Order Management");
    private JLabel header = new JLabel("Order Management", SwingConstants.CENTER);
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel inpanel1 = new JPanel();
    private JPanel inpanel2 = new JPanel();

    private JLabel id = new JLabel("ID Produs: ", JLabel.LEFT);
    private JLabel denumire = new JLabel("Denumire Produs: ", JLabel.LEFT);
    private JLabel cantitate = new JLabel("Cantitate Produs: ", JLabel.LEFT);
    private JLabel pret = new JLabel("Pret Produs: ", JLabel.LEFT);
    private JButton butonInserare = new JButton("Inserare");
    private JButton butonUpdate = new JButton("Update");
    private JButton butonStergere = new JButton("Stergere");

    private final JTextField idField = new JTextField(6);
    private final JTextField numeField = new JTextField(6);
    private final JTextField emailField = new JTextField(6);
    private final JTextField telefonField = new JTextField(6);


    private JTable table = new JTable();
    private DefaultTableModel model = (DefaultTableModel) table.getModel();

    public void afisareGUI() {


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 200));
        frame.setBackground(Color.gray);
        frame.setLayout(new GridLayout(1, 2));

        panel1.setLayout(new GridLayout(2, 1));
        inpanel1.setLayout(new GridLayout(4, 2));
        inpanel2.setLayout(new GridLayout(1, 3));
        // frame.add(header);
        frame.add(panel1);
        frame.add(panel2);

        frame.pack();
        frame.setVisible(true);
    }

    public void adaugareComponente() {

        idField.setSize(100, 40);
        emailField.setSize(100, 40);
        numeField.setSize(100, 40);
        telefonField.setSize(100, 40);

        panel1.add(inpanel1);
        panel1.add(inpanel2);

        inpanel1.add(id);
        inpanel1.add(idField);
        inpanel1.add(denumire);
        inpanel1.add(numeField);
        inpanel1.add(cantitate);
        inpanel1.add(emailField);
        inpanel1.add(pret);
        inpanel1.add(telefonField);
        inpanel2.add(butonInserare);
        inpanel2.add(butonStergere);
        inpanel2.add(butonUpdate);

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
                if (idField.getText().equals("") || numeField.getText().equals("") || emailField.getText().equals("") || telefonField.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Toate campurile trebuie sa fie completate!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
                else {
                    p1.insert(new Produs(Integer.parseInt(idField.getText()), numeField.getText(), Integer.parseInt(emailField.getText()), Double.parseDouble(telefonField.getText())));
                    addToTable(p1.addProdus());
                }
            }
        });

        butonStergere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idField.getText().equals("") || numeField.getText().equals("") || emailField.getText().equals("") || telefonField.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Toate campurile trebuie sa fie completate!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
                else {
                    p1.delete(new Produs(Integer.parseInt(idField.getText()), numeField.getText(), Integer.parseInt(emailField.getText()), Double.parseDouble(telefonField.getText())));
                    addToTable(p1.addProdus());
                }
            }
        });

        butonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idField.getText().equals("") || numeField.getText().equals("") || emailField.getText().equals("") || telefonField.getText().equals(""))
                    JOptionPane.showMessageDialog(null, "Toate campurile trebuie sa fie completate!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
                else {
                    p1.update(new Produs(Integer.parseInt(idField.getText()), numeField.getText(), Integer.parseInt(emailField.getText()), Double.parseDouble(telefonField.getText())));
                    addToTable(p1.addProdus());
                }
            }
        });
    }
}
