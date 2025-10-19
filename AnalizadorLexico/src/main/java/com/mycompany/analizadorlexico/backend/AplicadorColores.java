/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend;

import com.mycompany.analizadorlexico.backend.automata.Token;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author edu
 */
public class AplicadorColores {

    public void AplicarColor(JTextPane areaTexto, ArrayList<Token> tokens) {
        for (Token token : tokens) {
            StyledDocument doc = areaTexto.getStyledDocument();
            
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, token.getColor()); // color del texto

            // definir rango
            int inicio = token.getIndiceInicio();
            int fin = token.getIndiceFin();

            doc.setCharacterAttributes(inicio, (fin - inicio)+1, attrs, false);
        }
    }
}
