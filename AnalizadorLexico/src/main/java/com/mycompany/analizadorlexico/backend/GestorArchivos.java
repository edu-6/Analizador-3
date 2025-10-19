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
public class GestorArchivos {

    private File currentFile;
    private boolean fileIsSaved;
    private File jsonConfigFile;
    private boolean jsonIsSaved;

    public GestorArchivos() {
        this.fileIsSaved = true;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
        this.fileIsSaved = true;
    }

    public void setFileIsSaved(boolean fileIsSaved) {
        this.fileIsSaved = fileIsSaved;
    }

    public void setJsonConfigFile(File jsonConfigFile) {
        this.jsonConfigFile = jsonConfigFile;
    }

    public void setJsonIsSaved(boolean jsonIsSaved) {
        this.jsonIsSaved = jsonIsSaved;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public boolean isFileIsSaved() {
        return fileIsSaved;
    }

    public File getJsonConfigFile() {
        return jsonConfigFile;
    }

    public boolean isJsonIsSaved() {
        return jsonIsSaved;
    }

    public boolean hayCambiosSinGuardar() {
        return currentFile != null && !fileIsSaved;
    }

    public void guardarCambiosEnArchivo(String texto) {
        try (FileWriter writer = new FileWriter(currentFile)) {
            writer.write(texto);
        } catch (IOException e) {
        }
    }
    
    public boolean hayArchivoJson(){
        return this.jsonConfigFile != null;
    }
}
