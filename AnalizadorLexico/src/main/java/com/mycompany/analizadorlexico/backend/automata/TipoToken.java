/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.automata;

/**
 *
 * @author edu
 */
public enum TipoToken {
    IDENTIFICADOR,
    ENTERO,
    DECIMAL,
    ERROR,
    PALABRARESERVADA,
    COMENTARIO_LINEA,
    COMENTARIO_BLOQUE,
    CADENA,
    AGRUPACION,
    PUNTUACION,
    OPERADOR,
    
    DATO_ENTERO,
    DATO_DECIMAL,
    DATO_CADENA,
    ESCRIBIR,
    DEFINIR,
    COMO,
    
    PUNTO_COMA,
    MAS,
    MENOS,
    POR,
    DIVISION,
    IGUAL,
    P_APERTURA,
    P_CIERRE

}
