/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.automata;
import java.awt.Color;

/**
 *
 * @author edu
 */
public class Token {
    private String mensaje;
    private boolean yaFueContado = false;
    private TipoToken tipoToken;
    private String lexema;
    private int fila;
    private int columna;
    private int indiceInicio;
    private int indiceFin;
    private Color color;
    public Token(TipoToken tipoToken, String lexema, int fila, int columna, int indiceInicio, int indiceFin, String mensaje) {
        this.tipoToken = tipoToken;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
        this.indiceInicio = indiceInicio;
        this.indiceFin = indiceFin;
        this.mensaje = mensaje;
        definirColor(tipoToken);
    }
    
    
    public void concatenarToken(Token token){
        this.lexema += token.getLexema();
        int indiceFin = token.getIndiceFin();
        this.indiceFin = indiceFin;
    }

    public TipoToken getTipoToken() {
        return tipoToken;
    }

    public String getLexema() {
        return lexema;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getIndiceInicio() {
        return indiceInicio;
    }

    public int getIndiceFin() {
        return indiceFin;
    }

    public String getMensaje() {
        return mensaje;
    }
    

    public void setTipoToken(TipoToken tipoToken) {
        this.tipoToken = tipoToken;
    }

    public boolean isYaFueContado() {
        return yaFueContado;
    }

    public void setYaFueContado(boolean yaFueContado) {
        this.yaFueContado = yaFueContado;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setIndiceInicio(int indiceInicio) {
        this.indiceInicio = indiceInicio;
    }

    public void setIndiceFin(int indiceFin) {
        this.indiceFin = indiceFin;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    private void definirColor(TipoToken tipoToken) {
        switch (tipoToken) {
            case tipoToken.IDENTIFICADOR:
                this.color = new Color(139, 69, 19);
                break;
            case tipoToken.ENTERO:
                this.color = Color.GREEN;
                break;
            case tipoToken.DECIMAL:
                this.color = Color.BLACK;
                break;
            case tipoToken.CADENA:
                this.color = Color.ORANGE;
                break;
            case tipoToken.PALABRARESERVADA:
                this.color = Color.BLUE;
                break;
            case tipoToken.PUNTUACION:
                this.color = Color.CYAN;
                break;
            case tipoToken.AGRUPACION:
                this.color = new Color(128, 0, 128);
                break;
            case tipoToken.OPERADOR:
                this.color = Color.YELLOW;
                break;
            case tipoToken.COMENTARIO_LINEA:
                this.color = new Color(0, 100, 0);
                break;
            case tipoToken.COMENTARIO_BLOQUE:
                this.color = new Color(0, 100, 0);
                break;
            case tipoToken.ERROR:
                this.color = new Color(255, 0, 0);
                break;
            case tipoToken.DATO_ENTERO:
                this.color = Color.BLUE;
                break;
            case tipoToken.DATO_DECIMAL:
                this.color = Color.BLUE;
                break;
            case tipoToken.DATO_CADENA:
                this.color = Color.BLUE;
                break;
            case tipoToken.ESCRIBIR:
                this.color = Color.BLUE;
                break;
            case tipoToken.DEFINIR:
                this.color = Color.BLUE;
                break;
            case tipoToken.COMO:
                this.color = Color.BLUE;
                break;
            case tipoToken.PUNTO_COMA:
                this.color = Color.CYAN;
                break;
            case tipoToken.MAS:
                this.color = Color.YELLOW;
                break;
            case tipoToken.MENOS:
                this.color = Color.YELLOW;
                break;
            case tipoToken.POR:
                this.color = Color.YELLOW;
                break;
            case tipoToken.DIVISION:
                this.color = Color.YELLOW;
                break;
            case tipoToken.IGUAL:
                this.color = Color.YELLOW;
                break;
            case tipoToken.P_APERTURA:
                this.color = new Color(128, 0, 128);
                break;
            case tipoToken.P_CIERRE:
               this.color = new Color(128, 0, 128);
                break;
        }
    }

    public Color getColor() {
        return color;
    }
    
    
    

    public void selfDescribe() {
        System.out.println("----------------------------------------------------");
        System.out.println("Token de tipo: " + tipoToken);
        System.out.println("lexema: " + lexema);
        System.out.println("fila: " + fila);
        System.out.println("columna: " + columna);
        System.out.println("inicio: " + indiceInicio);
        System.out.println("fin: " + indiceFin);
    }

}
