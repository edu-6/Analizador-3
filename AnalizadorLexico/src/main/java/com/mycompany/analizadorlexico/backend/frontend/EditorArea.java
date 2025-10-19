/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.frontend;

import com.mycompany.analizadorlexico.backend.AplicadorColores;
import com.mycompany.analizadorlexico.backend.GestorArchivos;
import com.mycompany.analizadorlexico.backend.LectorDeArchivos;
import com.mycompany.analizadorlexico.backend.automata.Token;
import com.mycompany.analizadorlexico.backend.sugerencias.AplicadorDeSugerencia;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author edu
 */
public class EditorArea extends javax.swing.JPanel {

    //Backend
    private GestorArchivos gestor;
    private LectorDeArchivos lector;
    private AplicadorColores aplicadorColores;
    private AnalizadorFrame analizadorFrame;
    private  ArrayList<Token> lista; // guardar
    private AplicadorDeSugerencia aplicadorSugerencia = new AplicadorDeSugerencia();

    /**
     * Creates new form EditorArea
     */
    public EditorArea(GestorArchivos gestor, LectorDeArchivos lector, AnalizadorFrame analizadorFrame) {
        initComponents();

        //Backend
        this.gestor = gestor;
        this.lector = lector;

        //Frontend
        this.lineaTextPane.setEditable(false);
        ocultarBarrasScroll();
        configurarEventos();
        this.aplicadorColores = new AplicadorColores();
        this.analizadorFrame = analizadorFrame;

        // ultimos cambios
        this.setPreferredSize(new Dimension(500, 500));
    }
    
    public AplicadorDeSugerencia getAplicadorSugerencia(){
        return this.aplicadorSugerencia;
    }
    
    public ArrayList<Token> getTokens(){
        return this.lista;
    }

    private void registrarCambioEnTexto() {
        this.gestor.setFileIsSaved(false);
        actualizarNumeroLineas(editorTextPane.getText());
    }

    private void configurarEventos() {
        
        Document doc = editorTextPane.getDocument();
        EditorArea editorArea = this;
        editorTextPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    int pos = editorTextPane.viewToModel2D(e.getPoint());
                    if (pos >= 0) {
                        Element root = editorTextPane.getDocument().getDefaultRootElement();
                        int linea = root.getElementIndex(pos) + 1;
                        int columna = pos - root.getElement(linea - 1).getStartOffset() + 1;
                        
                        if (lista != null) {
                            String sugerencia = editorArea.getAplicadorSugerencia().buscarSugerencia(linea, columna - 1, lista);
                            if (sugerencia != null) {
                                editorTextPane.setToolTipText(sugerencia);
                                ToolTipManager.sharedInstance().mouseMoved(e);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });




        editorTextPane.addCaretListener(e -> {
            int posicion = e.getDot();
            try {
                // obtener el root del documento (representa las líneas)
                Element root = editorTextPane.getDocument().getDefaultRootElement();

                // la línea es el índice del hijo que contiene la posición
                int linea = root.getElementIndex(posicion);

                // columna = posición actual - inicio de la línea
                int columna = posicion - root.getElement(linea).getStartOffset();

                this.analizadorFrame.actualizarPosicionCursor(linea + 1, columna);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                registrarCambioEnTexto();
                SwingUtilities.invokeLater(() -> {
                        pintar();

                });
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                registrarCambioEnTexto();
                SwingUtilities.invokeLater(() -> {
                        pintar();
                });
            }

            @Override
            public void changedUpdate(DocumentEvent de) {

            }
        });
    }

    private void pintar() {
        ArrayList<Token> lista = this.analizadorFrame.analizar();
        this.lista = lista; // GUARDAR LA LISTA
        this.aplicadorColores.AplicarColor(editorTextPane, lista);
    }

    public void cargarTexto() {
        String texto = this.lector.leerArchivoTexto(gestor.getCurrentFile());
        this.editorTextPane.setText(texto);
        actualizarNumeroLineas(texto);
    }

    public void limpiarEditorArea() {
        this.editorTextPane.setText("");
        actualizarNumeroLineas("");
    }

    private void actualizarNumeroLineas(String texto) {
        String[] saltosDeLinea = texto.split("\n");
        int numeroLineas = saltosDeLinea.length;
        String textoLineas = "";
        for (int i = 0; i < saltosDeLinea.length; i++) {
            textoLineas = textoLineas + (i + 1) + "\n";
        }

        this.lineaTextPane.setText(textoLineas);
    }

    public String getEditorText() {
        return this.editorTextPane.getText();
    }


    private void ocultarBarrasScroll() {
        int h = javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER; // horzintal oculta
        int v = javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER; // vertical oculta
        jScrollPane2.setHorizontalScrollBarPolicy(h);
        jScrollPane2.setVerticalScrollBarPolicy(v);

        ScrollLIneas.setHorizontalScrollBarPolicy(h);
        ScrollLIneas.setVerticalScrollBarPolicy(v);

        // sincronizar las barras 
        scrollEditor.getVerticalScrollBar().addAdjustmentListener(e -> {
            ScrollLIneas.getVerticalScrollBar().setValue(e.getValue());
        });

        scrollEditor.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // siempre visible
    }

    public JTextPane getEditorTextPane() {
        return editorTextPane;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        ScrollLIneas = new javax.swing.JScrollPane();
        lineaTextPane = new javax.swing.JTextPane();
        scrollEditor = new javax.swing.JScrollPane();
        editorTextPane = new javax.swing.JTextPane();

        jScrollPane2.setViewportView(jTextPane1);

        lineaTextPane.setBackground(new java.awt.Color(204, 255, 255));
        lineaTextPane.setFont(new java.awt.Font("Fira Sans", 0, 24)); // NOI18N
        ScrollLIneas.setViewportView(lineaTextPane);

        editorTextPane.setBackground(new java.awt.Color(204, 204, 204));
        editorTextPane.setFont(new java.awt.Font("Fira Sans", 0, 24)); // NOI18N
        editorTextPane.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                editorTextPaneCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                editorTextPaneInputMethodTextChanged(evt);
            }
        });
        scrollEditor.setViewportView(editorTextPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ScrollLIneas, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollEditor, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ScrollLIneas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
            .addComponent(scrollEditor)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editorTextPaneInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_editorTextPaneInputMethodTextChanged

    }//GEN-LAST:event_editorTextPaneInputMethodTextChanged

    private void editorTextPaneCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_editorTextPaneCaretPositionChanged

    }//GEN-LAST:event_editorTextPaneCaretPositionChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollLIneas;
    private javax.swing.JTextPane editorTextPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane lineaTextPane;
    private javax.swing.JScrollPane scrollEditor;
    // End of variables declaration//GEN-END:variables
}
