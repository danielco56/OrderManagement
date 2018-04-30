package com.business;

import com.model.Produs;

import javax.swing.*;
import java.util.ArrayList;

public class ValidareProdus implements Validare {

    public void validareCampuri(Object produs, ArrayList<Integer> ids) {

        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == ((Produs) produs).getId() || ((Produs) produs).getId()<=0) {
                JOptionPane.showMessageDialog(null, "ID Incorect!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
                throw new IllegalArgumentException("ID incorect!");
            }
        }

        if (((Produs) produs).getPret() <= 0) {
            JOptionPane.showMessageDialog(null, "Pret incorect!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
            throw new IllegalArgumentException("Pret incorect!");
        }
        if (((Produs) produs).getCantitate() <= 0) {
            JOptionPane.showMessageDialog(null, "Cantitate incorecta!", "InfoBox: " + "Warning", JOptionPane.INFORMATION_MESSAGE);
            throw new IllegalArgumentException("Cantitate incorecta!");
        }
    }
}
