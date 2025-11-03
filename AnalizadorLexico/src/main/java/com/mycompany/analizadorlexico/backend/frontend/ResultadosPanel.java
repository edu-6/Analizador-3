/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.frontend;
import java.awt.BorderLayout;

/**
 *
 * @author edu
 */
public class ResultadosPanel extends javax.swing.JPanel {

    private AnalizadorFrame analizador;
    private PanelOpciones panelOpciones;
    private ReportesPanel panelReportes;
    private DepuracionPanel panelDepuracion;
    /**
     * Creates new form ResultadosPanel
     */
    public ResultadosPanel(AnalizadorFrame analizador) {
        initComponents();
        this.analizador = analizador;
        this.setLayout( new BorderLayout());
        this.panelReportes = new ReportesPanel(analizador);
        this.panelDepuracion = new DepuracionPanel();
        this.panelOpciones = new PanelOpciones(analizador,this,panelReportes, panelDepuracion);
        this.add(panelOpciones, BorderLayout.CENTER);
        
        this.add(panelReportes, BorderLayout.SOUTH);
        this.analizador = analizador;
        
        
    }
    
    public PanelOpciones getPanelOpciones(){
        return panelOpciones;
    }
    
    public void irAlPanelDepurador(){
        this.remove(panelReportes);
        this.add(panelDepuracion, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }
    
    public void irAlPanelReportes(){
        this.remove(panelDepuracion);
        this.add(panelReportes, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1044, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
