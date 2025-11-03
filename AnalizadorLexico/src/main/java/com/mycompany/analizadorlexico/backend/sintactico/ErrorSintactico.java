/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.sintactico;

import com.mycompany.analizadorlexico.backend.automata.Token;

/**
 *
 * @author edu
 */
public class ErrorSintactico {
    private Token token;
    private String cima;
    private int tipo;

    public ErrorSintactico(Token token, String cima, int tipo) {
        this.token = token;
        this.cima = cima;
        this.tipo = tipo;
    }
    public String getError(){
        String posicion =  " Linea:"+ token.getFila()+ " Columna: "+token.getColumna();
        if(tipo == 1){
            return "Error, "+"cerca de "+ token.getLexema() + posicion+ " se esperaba: "+cima;
        }else {
            return "Error, no hay regla para ["+ cima+","+token.getTipoToken()+"]" + posicion;
        }
    }
    
}
