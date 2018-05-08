package com.business;

import com.model.Client;

import javax.swing.*;
import java.util.ArrayList;

public class ValidareClient implements Validare{

    public void validareCampuri(Object client, ArrayList<Integer> ids)
    {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == ((Client) client).getIdClient() || ((Client) client).getIdClient()<=0) {
                JOptionPane.showMessageDialog(null, "ID Incorect!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
