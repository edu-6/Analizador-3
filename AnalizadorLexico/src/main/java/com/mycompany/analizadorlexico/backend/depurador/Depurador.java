/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend.depurador;

import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class Depurador {
    private String espacioMedio = "                                                 ";
    private String espacio = "                                                             ";
    private String separador = espacioMedio+ "_________________________________________________________________"+ "\n";
    private ArrayList<Movimiento> movimientos;
    int indiceActual = 0;
    public Depurador(ArrayList<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
    
    
    public String avanzarUnPaso() {
        if (!movimientos.isEmpty()) {
            if(indiceActual == 0){
                String contenido  = separador + movimientos.get(indiceActual).getMovimiento() + separador;
                indiceActual++;
                return contenido;
            }else if (indiceActual + 1 < movimientos.size()) {
                indiceActual++;
                return separador + movimientos.get(indiceActual).getMovimiento() + separador;
            }
        }

        return separador + espacio + "FIN DEL AUTOMATA" + "\n" + separador;
    }
    
    public String retrocederUnPaso() {
        if (!movimientos.isEmpty()) {
            if (indiceActual - 1 >= 0) {
                indiceActual--;
                return separador + movimientos.get(indiceActual).getMovimiento() + separador;
            }
        }
        return separador + espacio + "NO HAY MOVIMIENTOS ANTERIORES " + "\n" + separador;
    }

    public String reiniciarAnalsis(){
        indiceActual = 0;
        if(!movimientos.isEmpty()){
            return separador+ movimientos.get(indiceActual).getMovimiento()+separador;
        }else{
            return separador+espacio+ " AUTOMATA VACIO" +"\n"+separador;
        }
    }
    
    public String ejecutarHastaGuardarToken() {
        if (!movimientos.isEmpty()) {
            for (int i = indiceActual; i < movimientos.size(); i++) {
                Movimiento movimiento = movimientos.get(i);
                if (movimiento.isEsFinToken()) {
                    String mensaje = espacio + " SE EJECUTÓ HASTA EL FINAL DEL TOKEN" + "\n";
                    indiceActual = i;
                    return separador + mensaje + movimiento.getMovimiento() + separador;
                }
            }
        }
        return separador + espacio + " NO SE ENCONTRÓ UN FIN DE TOKEN" + "\n" + separador;
    }
}
