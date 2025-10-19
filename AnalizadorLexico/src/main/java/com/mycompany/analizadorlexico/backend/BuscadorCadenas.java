/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend;

import com.mycompany.analizadorlexico.backend.exceptions.CampoVacioException;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;


/**
 *
 * @author edu
 */
public class BuscadorCadenas {

    public void buscarCadena(String cadenaBuscada, JTextPane areaTexto) throws CampoVacioException {
        if (cadenaBuscada.isBlank()) {
            throw new CampoVacioException();
        }

        limpiar(areaTexto); // limpiar todo lo anterior
        int indiceActual = 0;
        String texto = areaTexto.getText();

        // concactenar cadena
        while (indiceActual + cadenaBuscada.length() - 1 < texto.length()) {
            String cadena = "";
            int indiceInicio = indiceActual;

            for (int i = 0; i < cadenaBuscada.length(); i++) { // concatenar
                cadena += texto.charAt(indiceActual + i);
                //System.out.println("cadena:" + cadena);
            }

            if (cadena.equals(cadenaBuscada)) {
                //System.out.println(" coindice");
                indiceActual += cadenaBuscada.length(); // aumentar el indice
                resaltar(areaTexto, indiceInicio, indiceActual);
            } else {
                indiceActual++;
            }
        }

    }

    public void limpiar(JTextPane areaTexto) {
        areaTexto.getHighlighter().removeAllHighlights(); 
    }

    private void resaltar(JTextPane areaTexto, int indiceInicial, int indiceFinal)  {

        Highlighter highlighter = areaTexto.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        try {
            highlighter.addHighlight(indiceInicial, indiceFinal, painter); 
        } catch (BadLocationException ex) {
            System.out.println("Error al pintar");
        }

    }
}
