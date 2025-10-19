/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author edu
 */
public class LectorDeArchivos {

    private FileReader reader;
    private BufferedReader buffer;

    public String leerArchivoTexto(File file) {
        String contenido = "";
        try {
            this.reader = new FileReader(file);
            this.buffer = new BufferedReader(reader);
            String linea = buffer.readLine();
            while (linea != null) {
                contenido =contenido+ linea +"\n";
                linea = buffer.readLine();
            }
            return contenido;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return null;
    }
}
