/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.frontend;

import com.mycompany.analizadorlexico.backend.CreadorReportes;
import com.mycompany.analizadorlexico.backend.automata.Lexer;
import com.mycompany.analizadorlexico.backend.automata.Token;
import com.mycompany.analizadorlexico.backend.automata.TokenRecuento;
import com.mycompany.analizadorlexico.backend.sintactico.ErrorSintactico;
import com.mycompany.analizadorlexico.backend.sintactico.Parser;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edu
 */
public class ReportesPanel extends javax.swing.JPanel {

    private CreadorReportes creadorReportes;
    private JTable tablaLexemas;
    private JTable tablaTokens;
    private JTable tablaErrores;
    private JTextArea consola;
    private AnalizadorFrame framePrincipal;
    private ArrayList<ErrorSintactico> erroresSintacticos = null;

    /**
     * Creates new form ReportesPanel
     */
    public ReportesPanel(AnalizadorFrame framePricipal) {
        initComponents();
        this.creadorReportes = new CreadorReportes();
        this.framePrincipal = framePricipal;
    }

    public void crearReportes(ArrayList<Token> lista) {
        //this.consola = this.crearConsola(logs);
        if(lista == null){
            return;
        }
        this.jScrollPane1.setViewportView(null);
        boolean hayErrores = this.creadorReportes.hayErrores(lista);
        boolean botonesActivos = false;

        String[] columnas = {"Tipo", "Lexema", "Fila", "Columna"};

        if (hayErrores) {
            ArrayList<Token> errores = this.creadorReportes.generarListaErrores(lista);
            this.tablaErrores = crearTablaErrores(errores);
            this.jScrollPane1.setViewportView(tablaErrores);
        } else {

            botonesActivos = true;
            this.tablaLexemas = crearTablaLexemas(creadorReportes.generarRecuentoLexemas(creadorReportes.filtrarComentarios(lista)));
            this.tablaTokens = crearTablaTokens(creadorReportes.filtrarComentarios(lista)); // se filtran los comentarios
            this.jScrollPane1.setViewportView(tablaTokens);
        }
        
        this.btnTokens.setEnabled(botonesActivos);
        this.btnRecuento.setEnabled(botonesActivos);
        this.bntSintactico.setEnabled(botonesActivos);

        this.revalidate();
        this.repaint();
        
    }
    
    private void analizarSintacticamente(){
        Lexer lexer =  new Lexer(new StringReader(framePrincipal.getEditorArea().getEditorTextPane().getText()));
        try {
            lexer.yylex();
            ArrayList<Token> tokens = lexer.getTokens();
            boolean hayErrores = this.creadorReportes.hayErrores(tokens);
            if(!hayErrores){ //errores lexicos
                ArrayList<Token> lista = creadorReportes.filtrarComentarios(tokens);
                List<Token> lista2 = new ArrayList<>(lista); // parsear a lista
                Parser parser = new Parser(lista2);
                parser.parsear();
                this.erroresSintacticos = parser.getErrores();
                JTextArea consola = crearConsola(parser.getLogs());
                this.jScrollPane1.setViewportView(consola);
            }else{
                this.jScrollPane1.setViewportView(null);
            }
        } catch (IOException ex) {
            //
        }
    }
    
    private void mostrarErroresSintacticos(){
        
        if(this.erroresSintacticos != null){
            String contenido = "";
            for (ErrorSintactico error : erroresSintacticos) {
                contenido+= error.getError()+"\n";
            }
            JTextArea consola = crearConsola(contenido);
            this.jScrollPane1.setViewportView(consola);
        }
    }

    private JTable crearTablaTokens(ArrayList<Token> lista) {
        String[] columnas = {"Tipo", "Lexema", "Fila", "Columna"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Token t : lista) {
            Object[] fila = {t.getTipoToken(), t.getLexema(), t.getFila(), t.getColumna()};
            modelo.addRow(fila);
        }
        return new JTable(modelo);
    }
    
    private JTable crearTablaLexemas(ArrayList<TokenRecuento> lista) {
        String[] columnas = { "Lexema","Tipo","Cantidad: "};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (TokenRecuento t : lista) {
            Object[] fila = { t.getLexema(),t.getTipoToken(), t.getCantidad()};
            modelo.addRow(fila);
        }
        return new JTable(modelo);
    }
    
    
    private JTable crearTablaErrores(ArrayList<Token> lista) {
        String[] columnas = {"Error", "Fila", "Columna", "Esperado: "};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Token t : lista) {
            Object[] fila = { t.getLexema(),t.getFila(), t.getColumna(), t.getMensaje()};
            modelo.addRow(fila);
        }
        return new JTable(modelo);
    }
    
    private JTextArea crearConsola(String contenido) {
        JTextArea consola = new JTextArea();
        consola.setEditable(false);          
        consola.setLineWrap(true);           
        consola.setWrapStyleWord(true);      
        consola.setBackground(new Color(230, 230, 230)); 
        consola.setForeground(Color.BLACK);  // texto negro
        consola.setFont(new Font("Monospaced", Font.PLAIN, 20));
        consola.append(contenido);
        return consola;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnRecuento = new javax.swing.JButton();
        btnTokens = new javax.swing.JButton();
        bntSintactico = new javax.swing.JButton();
        btnErrorSintactico = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        btnRecuento.setBackground(new java.awt.Color(204, 204, 204));
        btnRecuento.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        btnRecuento.setText("Recuento Lexemas");
        btnRecuento.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRecuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuentoActionPerformed(evt);
            }
        });

        btnTokens.setBackground(new java.awt.Color(204, 204, 204));
        btnTokens.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        btnTokens.setText("Tokens");
        btnTokens.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnTokens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTokensActionPerformed(evt);
            }
        });

        bntSintactico.setBackground(new java.awt.Color(204, 204, 204));
        bntSintactico.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        bntSintactico.setText("Analisis sintactico");
        bntSintactico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bntSintactico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSintacticoActionPerformed(evt);
            }
        });

        btnErrorSintactico.setBackground(new java.awt.Color(204, 204, 204));
        btnErrorSintactico.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        btnErrorSintactico.setText("Errores sintacticos");
        btnErrorSintactico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnErrorSintactico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnErrorSintacticoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(btnRecuento, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTokens, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bntSintactico, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnErrorSintactico, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTokens, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRecuento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntSintactico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnErrorSintactico, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuentoActionPerformed
        this.jScrollPane1.setViewportView(tablaLexemas);
    }//GEN-LAST:event_btnRecuentoActionPerformed

    private void btnTokensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTokensActionPerformed
       this.jScrollPane1.setViewportView(tablaTokens);
    }//GEN-LAST:event_btnTokensActionPerformed

    private void bntSintacticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSintacticoActionPerformed
        this.analizarSintacticamente();
    }//GEN-LAST:event_bntSintacticoActionPerformed

    private void btnErrorSintacticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnErrorSintacticoActionPerformed
        this.mostrarErroresSintacticos();
    }//GEN-LAST:event_btnErrorSintacticoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSintactico;
    private javax.swing.JButton btnErrorSintactico;
    private javax.swing.JButton btnRecuento;
    private javax.swing.JButton btnTokens;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
