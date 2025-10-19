/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analizadorlexico.backend;

import com.mycompany.analizadorlexico.backend.automata.TipoToken;
import com.mycompany.analizadorlexico.backend.automata.Token;
import com.mycompany.analizadorlexico.backend.automata.TokenRecuento;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class CreadorReportes {

    public ArrayList<Token> generarReportes(ArrayList<Token> lista) {

        return null;
    }
    
    public ArrayList<TokenRecuento> generarRecuentoLexemas(ArrayList<Token> tokens){
         ArrayList<TokenRecuento> nueva = new ArrayList();
         for (Token token : tokens) {
            if(!token.isYaFueContado()){ // si no ha sido contado
                int cantidad = contarLexema(token.getLexema(), tokens);
                nueva.add(new TokenRecuento(token.getLexema(), cantidad, token.getTipoToken()));
            }
        }
         
         return nueva;
    }
    
    private int contarLexema(String lexema, ArrayList<Token> tokens){
        int contador = 0;
        for (Token token : tokens) {
            if(!token.isYaFueContado() && lexema.equals(token.getLexema())){
                token.setYaFueContado(true);
                contador++;
            }
        }
        return contador;
    }

    public boolean hayErrores(ArrayList<Token> lista) {
        for (Token token : lista) {
            if (token.getTipoToken().equals(TipoToken.ERROR)) { // si es tipo error
                return true;
            }
        }
        return false;
    }

    public ArrayList<Token> generarListaErrores(ArrayList<Token> lista) {
        ArrayList<Token> nuevaLista = unificarErrores(lista); // unifica los errores
        return filtrarErrores(nuevaLista);  // elimina lo que no es error

    }

    private ArrayList<Token> filtrarErrores(ArrayList<Token> lista) {
        ArrayList<Token> nueva = new ArrayList();
        for (Token token : lista) {
            if (token.getTipoToken().equals(TipoToken.ERROR)) {
                nueva.add(token);
            }
        }
        return nueva;
    }
    
    
    public ArrayList<Token> filtrarComentarios(ArrayList<Token> lista) {
        ArrayList<Token> nueva = new ArrayList();
        for (Token token : lista) {
            if (!token.getTipoToken().equals(TipoToken.COMENTARIO_LINEA) && !token.getTipoToken().equals(TipoToken.COMENTARIO_BLOQUE)) {
                nueva.add(token);
            }
        }
        return nueva;
    }
    
    
    /**
     * Une a los tokens de error seguidos
     *
     * @param lista
     * @return
     */
    private ArrayList<Token> unificarErrores(ArrayList<Token> lista) {
        Token tokenError = null;
        ArrayList<Token> nuevos = new ArrayList();
        for (Token token : lista) {
            if (token.getTipoToken().equals(TipoToken.ERROR)) {
                if (tokenError == null) {
                    tokenError = token;
                } else {
                    tokenError.concatenarToken(token);
                }
            } else {
                if (tokenError != null) {
                    nuevos.add(tokenError);
                    tokenError = null; // vaciar token error 
                }
                nuevos.add(token);
            }
        }

        if (tokenError != null) { // para el ultimo token
            nuevos.add(tokenError);
            tokenError = null; // vaciar token error 
        }
        return nuevos;
    }

    public void crearReporteGeneral(ArrayList<Token> lista) {
        int cantidadErrores = 0;
        String porcentajeBueno = "";
        ArrayList<Token> listaErrores;
        if(hayErrores(lista)){
            listaErrores = generarListaErrores(lista);
            cantidadErrores = listaErrores.size();
        }
        
        ArrayList<Token> listaTodo = this.unificarErrores(lista); // primero unificar los errores
        listaTodo = this.filtrarComentarios(lista);// quitar los comentarios
        
        int total = listaTodo.size();
        int errores = cantidadErrores;
        
        int buenos =  total -errores;
        if(total != 0){
            double porcentaje = (buenos*100.0)/total;
            //porcentajeBueno = porcentaje+"%             ";
            porcentajeBueno = String.format("%.2f%%", porcentaje);
        }
    }
}
