/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.frontend;
import com.mycompany.analizadorlexico.backend.ExportadorArchivo;
import com.mycompany.analizadorlexico.backend.GestorArchivos;
import com.mycompany.analizadorlexico.backend.LectorDeArchivos;
import com.mycompany.analizadorlexico.backend.automata.Automata;
import com.mycompany.analizadorlexico.backend.automata.Token;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author edu
 */
public class AnalizadorFrame extends javax.swing.JFrame {

    //Frontend
    private EditorArea editorArea;
    private ResultadosPanel resultadosPanel;

    //Backend
    private GestorArchivos gestorArchivos;
    private LectorDeArchivos lectorArchivos;
    private ExportadorArchivo exportador;

    /**
     * Creates new form AnalizadorFrame
     */
    public AnalizadorFrame() {
        initComponents();
        this.setPreferredSize(new Dimension(1000, 780));
        this.panelFondo.setLayout(new BorderLayout());

        //Backend
        this.gestorArchivos = new GestorArchivos();
        this.lectorArchivos = new LectorDeArchivos();
        this.exportador = new ExportadorArchivo();

        //Frontend
        this.editorArea = new EditorArea(gestorArchivos, lectorArchivos, this);
        this.panelFondo.add(editorArea, BorderLayout.CENTER);
        this.resultadosPanel = new ResultadosPanel(this);
        this.panelFondo.add(resultadosPanel, BorderLayout.SOUTH);
    }

    public JTextPane getEditorTextPane() {
        return this.editorArea.getEditorTextPane();
    }

    private void intentarGuardarCambios() {
        if (gestorArchivos.hayCambiosSinGuardar()) {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Desea guardar los cambios?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                String texto = editorArea.getEditorText(); // obtener el nuevo texto
                gestorArchivos.guardarCambiosEnArchivo(texto);
            }
        }
    }
    
    public EditorArea getEditorArea(){
        return this.editorArea;
    }

    public ArrayList<Token> analizar() {
        Automata automata = new Automata();
        return automata.Analizar2(this.editorArea.getEditorTextPane().getText());
    }
    
    public void actualizarPosicionCursor(int fila, int columna){
        this.resultadosPanel.getPanelOpciones().actualizarPosicion(fila, columna);
    }


    public void volverAlPanelAnalizador() {
        this.panelFondo.removeAll();
        this.panelFondo.add(editorArea, BorderLayout.CENTER);
        this.panelFondo.add(resultadosPanel, BorderLayout.SOUTH);
        this.panelFondo.revalidate();
        this.panelFondo.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        cargarArchivo = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        exportarTexto = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelFondo.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 871, Short.MAX_VALUE)
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenuBar1.setFont(new java.awt.Font("Fira Sans", 0, 24)); // NOI18N

        cargarArchivo.setText("Cargar archivo");
        cargarArchivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarArchivoMouseClicked(evt);
            }
        });
        jMenuBar1.add(cargarArchivo);

        jMenu3.setText("Nuevo archivo");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(jMenu1);

        exportarTexto.setText("Exportar texto");
        exportarTexto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportarTextoMouseClicked(evt);
            }
        });
        jMenuBar1.add(exportarTexto);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarArchivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarArchivoMouseClicked
        intentarGuardarCambios(); // se inentan guardar cambios por si habían

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int opcion = fileChooser.showDialog(this, "Cargar");
        if (opcion == JFileChooser.APPROVE_OPTION) {
            gestorArchivos.setCurrentFile(fileChooser.getSelectedFile());
            editorArea.cargarTexto();
            gestorArchivos.setFileIsSaved(true);
        }

    }//GEN-LAST:event_cargarArchivoMouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        intentarGuardarCambios();
        editorArea.limpiarEditorArea();
        gestorArchivos.setCurrentFile(null);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void exportarTextoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportarTextoMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo de texto");
        int opcion = fileChooser.showSaveDialog(null);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".txt")) {
                archivo = new File(archivo.getAbsolutePath() + ".txt");
            }
            String texto = this.editorArea.getEditorTextPane().getText();
            this.exportador.escribirArchivo(texto, archivo);
        }
    }//GEN-LAST:event_exportarTextoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu cargarArchivo;
    private javax.swing.JMenu exportarTexto;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel panelFondo;
    // End of variables declaration//GEN-END:variables
}
