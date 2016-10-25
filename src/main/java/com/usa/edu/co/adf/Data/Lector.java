/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usa.edu.co.adf.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Santiago
 */
public class Lector {

    private BufferedReader dataOrigin;

    public Lector(FileReader fr) {
        this.dataOrigin = new BufferedReader(fr);
    }

    public List<String> leerArchivo() {
        List<String> lista = new LinkedList<>();
        String linea = "";
        try {
            while ((linea = this.dataOrigin.readLine()) != null) {
                lista.add(linea);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

}
