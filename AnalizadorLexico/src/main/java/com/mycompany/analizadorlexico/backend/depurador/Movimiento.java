/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.depurador;

import com.mycompany.analizadorlexico.backend.automata.Token;

/**
 *
 * @author edu
 */
public class Movimiento {
    private Token token;
    private boolean esFinToken;
    private int estadoActual;
    private char caracterLeido;
    private int estadoAlQueTransita;
    private String lexema;

    public Movimiento(boolean esFinToken, int estadoActual, char caracterLeido, int estadoAlQueTransita, String lexema, Token token) {
        this.esFinToken = esFinToken;
        this.estadoActual = estadoActual;
        this.caracterLeido = caracterLeido;
        this.estadoAlQueTransita = estadoAlQueTransita;
        this.lexema = lexema;
        this.token = token;
    }
    
    
    public String getMovimiento(){
        
        String espacio = "                                                             ";
        String espacioMedio = "                                                 ";
        String actualEstado;
        String separacion =espacioMedio+ "_________________________________________________________________"+ "\n";
        if(estadoActual == -1){
            actualEstado =espacio+"ESTADO ERROR ";
        }else{
            actualEstado = String.valueOf(estadoActual);
        }
        String estadoAnterior =espacio+"Estado actual: "+ actualEstado + "\n";
        String estadoActual =espacio+"Estado  al que transita: "+ estadoAlQueTransita + "\n";
        String contenido =espacio+ "Caracter leido : "+ caracterLeido + "\n";
        contenido+= espacio+ "Formación lexema: "+ lexema+ "\n";
        
        String guardadoToken = "";
        if(esFinToken){
            if(estadoAlQueTransita == 0 ){ // si volvió a 0
                guardadoToken ="\n"+"\n"+espacio+ " --- Ha regresado al estado 0 "+"\n";
            }else if(estadoAlQueTransita == -1){
                guardadoToken ="\n"+"\n"+espacio+ " ---El estado "+ estadoAlQueTransita +" ES ESTADO ERROR"+ "\n"; 
            }else{
                guardadoToken ="\n"+"\n"+espacio+ " ---El estado "+ estadoAlQueTransita +" es de aceptacion"+ "\n"; 
            }
            
            guardadoToken+=espacio+"Nuevo token"+ "\n"+espacio
                +"Tipo: "+token.getTipoToken()+ " Lexema : "+ token.getLexema()+ " Fila: "+ token.getFila()+ " Columna: "+ token.getColumna() + "\n";
        }
        return separacion+ estadoAnterior + estadoActual + contenido + guardadoToken;
    }

    public boolean isEsFinToken() {
        return esFinToken;
    }
    
}
