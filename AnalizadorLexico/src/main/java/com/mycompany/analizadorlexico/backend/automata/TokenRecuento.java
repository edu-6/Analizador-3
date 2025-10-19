/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.automata;

/**
 *
 * @author edu
 */
public class TokenRecuento {
    
    private String lexema;
    private int cantidad;
    private TipoToken tipoToken;

    public TokenRecuento(String lexema, int cantidad, TipoToken tipoToken) {
        this.lexema = lexema;
        this.cantidad = cantidad;
        this.tipoToken = tipoToken;
    }

    public String getLexema() {
        return lexema;
    }

    public int getCantidad() {
        return cantidad;
    }

    public TipoToken getTipoToken() {
        return tipoToken;
    }
    
    
    
    
    
}
