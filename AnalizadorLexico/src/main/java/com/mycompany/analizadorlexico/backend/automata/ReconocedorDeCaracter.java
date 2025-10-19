/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.automata;

/**
 *
 * @author edu
 */
public class ReconocedorDeCaracter {
    private static final int DIGITO = 0;
    private static final int LETRA = 1;
    private static final int PUNTO = 2;
    private static final int SIGNO_DIVISION = 3;
    private static final int ASTERISCO = 4;
    private static final int SIGNO_AGRUPACION = 5;
    private static final int OPERADOR = 6;
    private static final int SIGNO_PUNTUACION = 7;
    private static final int COMILLA = 8;
    private static final int ESPACIO = 9;
    private static final int TABULACION = 10;
    private static final int SALTO_LINEA = 11;
    private static final int DESCONOCIDO = 12;
    
    
    public int getTipoCaracter(char c){
        int valor = DESCONOCIDO;
        if (c == '.')
            return PUNTO;
        if(c == '\n')
            return SALTO_LINEA;
        if(c == '\t')
            return TABULACION;
        if (Character.isWhitespace(c))
            return ESPACIO;
        if(c == '"')
            return COMILLA;
        if(c == '*')
            return ASTERISCO;
        if(c == '/')
            return SIGNO_DIVISION;
        if (Character.isDigit(c))
            return DIGITO;
        if (Character.isLetter(c))
            return LETRA;
        if(esSignoAgrupacion(c))
            return SIGNO_AGRUPACION;
        if(esOperador(c))
            return OPERADOR;
        if(esSignoPuntuacion(c))
            return SIGNO_PUNTUACION;
        return valor;
    }
    
    private boolean esSignoAgrupacion(char c){
        return  c == '{' || c == '}' || c == '[' || c == ']' || c == '(' || c == ')';
    }
    
    private boolean esOperador(char c){
        return  c == '%' || c == '=' || c == '+' || c == '-';
    }
    
    private boolean esSignoPuntuacion(char c){
        return  c == ',' || c == ';' || c == ':';
    }
    
}
