package com.presentation;

import com.dao.ClientDAO;
import com.model.Client;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    ArrayList<Client> clienti = new ArrayList<>();
    ClientDAO c1 = new ClientDAO();
    Object[] coloane = {"ID", "Name", "Email", "Telefon"};

    private JFrame frame = new JFrame("Order Management");
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel inpanel1 = new JPanel();
    private JPanel inpanel2 = new JPanel();

    private JLabel id = new JLabel("ID Client: ", JLabel.LEFT);
    private JLabel nume = new JLabel("Nume Client: ", JLabel.LEFT);
    private JLabel email = new JLabel("Email Client: ", JLabel.LEFT);
    private JLabel telefon = new JLabel("Telefon Client: ", JLabel.LEFT);
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

        panel1.add(inpanel1);  // inpanel contine campuri de completat pentru client
        panel1.add(inpanel2); // inpanel 2 contine butoanele pentru client

        inpanel1.add(id);
        inpanel1.add(idField);
        inpanel1.add(nume);
        inpanel1.add(numeField);
        inpanel1.add(email);
        inpanel1.add(emailField);
        inpanel1.add(telefon);
        inpanel1.add(telefonField);
        inpanel2.add(butonInserare);
        inpanel2.add(butonStergere);
        inpanel2.add(butonUpdate);
        panel2.add(new JScrollPane(table));

    }

    public void addToTable() {
        clienti = c1.addClienti();
        Object[] date = new Object[4];

        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Telefon");



        for (int i = 0; i < clienti.size(); i++) {
            date[0] = clienti.get(i).getIdClient();
            date[1] = clienti.get(i).getNume();
            date[2] = clienti.get(i).getEmail();
            date[3] = clienti.get(i).getTelefon();
            model.addRow(date);
        }

    }


    public void operatiiButoane() {
        butonInserare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.insert(new Client(Integer.parseInt(idField.getText()), numeField.getText(), emailField.getText(), telefonField.getText()));
                  addToTable();
            }
        });

        butonStergere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.delete(new Client(Integer.parseInt(idField.getText()), numeField.getText(), emailField.getText(), telefonField.getText()));
                addToTable();
            }
        });

        butonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.update(new Client(Integer.parseInt(idField.getText()), numeField.getText(), emailField.getText(), telefonField.getText()));
                addToTable();
            }
        });
    }
}