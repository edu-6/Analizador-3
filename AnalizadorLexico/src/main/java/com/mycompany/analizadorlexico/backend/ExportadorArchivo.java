/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author edu
 */
public class ExportadorArchivo {
    public void escribirArchivo(String texto, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(texto);
        } catch (IOException e) {
        }
    }
}
