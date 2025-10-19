/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.sugerencias;

import com.mycompany.analizadorlexico.backend.automata.TipoToken;
import com.mycompany.analizadorlexico.backend.automata.Token;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class AplicadorDeSugerencia {
    
    private String[] palabrasReservadas = {"SI","si", "ENTONCES", "entonces", "PARA", "para", "ESCRIBIR", "escribir"};
    
    public String buscarSugerencia(int fila, int columna, ArrayList<Token> tokens){
        Token token = buscarToken(fila, columna, tokens);
        if(token != null){
            return coincidencia(token.getLexema());
        }
        return null;
    }
    
    private Token buscarToken(int fila, int columna,ArrayList<Token> tokens ){
        
        for (Token token : tokens) {
            if(token.getFila() == fila && token.getColumna() == columna && token.getTipoToken().equals(TipoToken.ERROR)){
                // si coincide fila, columna y error
                return token;
            }
        }
        return null;
    }
    
    private String coincidencia(String lexema){
        int stringMayor;
        int coincidencias = 0;
        for (String palabraReservada : palabrasReservadas) {
            if(lexema.length() >= palabraReservada.length()){
                stringMayor = palabraReservada.length(); /// se pone el menor
            }else{
                stringMayor = lexema.length();
            }
            
            for (int i = 0; i <stringMayor; i++) {
                if(palabraReservada.charAt(i) == lexema.charAt(i)){
                    coincidencias++;
                }
                
            }
            
            if(coincidencias > 1){
                return  "Quizo decir: "+ palabraReservada+ "  ?";
            }else{
                coincidencias = 0;
            }
        }
        
        return null;
    }
    
    
    
}
