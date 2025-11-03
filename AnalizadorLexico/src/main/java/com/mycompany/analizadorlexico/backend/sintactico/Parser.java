package com.mycompany.analizadorlexico.backend.sintactico;

import com.mycompany.analizadorlexico.backend.automata.TipoToken;
import com.mycompany.analizadorlexico.backend.automata.Token;
import java.util.*;

public class Parser {
    StringBuilder logs = new StringBuilder();
    private  List<Token> tokens;
    private ArrayList<ErrorSintactico> errores = new ArrayList<>();
    private int index = 0;
    private Token ultimoToken;

    private  String[] noTerminales = {"P", "L", "K", "DE", "DT", "AS", "ES", "M", "EX", "EX'","T", "T'", "F"};
    private  String[] terminales = {"DEFINIR", "id", "COMO", "ESCRIBIR", "entero", "decimal", "cadena", "tokenEntero","tokenDecimal","tokenCadena","=", "(", ")", "+", "-", "*", "/", ";", "$"};

    private  String[][] tabla = new String[noTerminales.length][terminales.length];

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        inicializarTabla();
    }

    private void inicializarTabla() {
        // Inicializar todo con ERROR
        for (int i = 0; i < noTerminales.length; i++) {
            Arrays.fill(tabla[i], "ERROR");
        }

        // P → L
        set("P", "DEFINIR", "L");
        set("P", "id", "L");
        set("P", "ESCRIBIR", "L");
        set("P", "$", "L");

        // L → K L | ε
        set("L", "DEFINIR", "K L");
        set("L", "id", "K L");
        set("L", "ESCRIBIR", "K L");
        set("L", "$", "ε");

        // K → DE | AS | ES
        set("K", "DEFINIR", "DE");
        set("K", "id", "AS");
        set("K", "ESCRIBIR", "ES");
        
        // DE → DEFINIR id COMO DT ;
        set("DE", "DEFINIR", "DEFINIR id COMO DT ;");
        
        // DT → entero | decimal | cadena;
        set("DT", "entero", "entero");
        set("DT", "decimal", "decimal");
        set("DT", "cadena", "cadena");
        
        // AS → id = M ;
        set("AS", "id", "id = M ;");

        // ES → ESCRIBIR ( M ) ;
        set("ES", "ESCRIBIR", "ESCRIBIR ( M ) ;");
        
        // M → EX | tokenCadena
        set("M", "id", "EX");
        set("M", "tokenEntero", "EX");
        set("M", "tokenDecimal", "EX");
        set("M", "tokenCadena", "tokenCadena");
        set("M", "(", "EX");
        
        // EX → T EX'
        set("EX", "id", "T EX'");
        set("EX", "tokenEntero", "T EX'");
        set("EX", "tokenDecimal", "T EX'");
        set("EX", "(", "T EX'");
        
        // EX' → + T EX' | - T EX' | ε
        set("EX'", ")", "ε");
        set("EX'", "+", "+ T EX'");
        set("EX'", "-", "- T EX'");
        set("EX'", ";", "ε");
        
        // T → F T'
        set("T", "id", "F T'");
        set("T", "tokenEntero", "F T'");
        set("T", "tokenDecimal", "F T'");
        set("T", "(", "F T'");
        
        // T' → * F T' | / F T' | ε
        set("T'", ")", "ε");
        set("T'", "+", "ε");
        set("T'", "-", "ε");
        set("T'", "*", "* F T'");
        set("T'", "/", "/ F T'");
        set("T'", ";", "ε");
        
        // F → id| tokenEntero | tokenDecimal | ( EX )
        set("F", "id", "id");
        set("F", "tokenEntero", "tokenEntero");
        set("F", "tokenDecimal", "tokenDecimal");
        set("F", "(", "( EX )");
        
    }

    private void set(String noTerminal, String terminal, String produccion) {
        int i = indexOf(noTerminales, noTerminal);
        int j = indexOf(terminales, terminal);
        if (i != -1 && j != -1) {
            tabla[i][j] = produccion;
        }
    }

    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(val)) return i;
        return -1;
    }

    private String lookahead() {
        if (index < tokens.size()){
            this.ultimoToken = tokens.get(index);
            return mapToken(tokens.get(index).getTipoToken());
        }
            
        return "$";
    }

    private String mapToken(TipoToken tipo) {
        return switch (tipo) {
            case DEFINIR -> "DEFINIR";
            case COMO -> "COMO";
            case ESCRIBIR -> "ESCRIBIR";
            case DATO_DECIMAL -> "decimal";
            case DATO_CADENA -> "cadena";
            case DATO_ENTERO -> "entero";
            case IDENTIFICADOR -> "id";
            case ENTERO -> "tokenEntero";
            case DECIMAL -> "tokenDecimal";
            case CADENA -> "tokenCadena";
            case PUNTO_COMA -> ";";
            case IGUAL -> "=";
            case MAS -> "+";
            case MENOS -> "-";
            case POR -> "*";
            case DIVISION -> "/";
            case P_APERTURA -> "(";
            case P_CIERRE -> ")";
            default -> "ERROR";
        };
    }
    
    
    public void parsear(){
        while(index <tokens.size()){
             boolean var = parse();
             index++;
            /* boolean var = parse();
             index++;
             if(!var){
                 encontrarTokenRelevante();
             }*/
        }
        
    }
    
    private boolean esRelevante(TipoToken tipo){
        switch (tipo) {
            case TipoToken.DECIMAL:
                return true;
            case TipoToken.ESCRIBIR:
                return true;
            case TipoToken.IDENTIFICADOR:
                return true;
            default:
                return false;
        }
    }
    
    private void encontrarTokenRelevante(){
        
        while( index < tokens.size() && !esRelevante(tokens.get(index).getTipoToken())){
            index++;
        }
    }

    private boolean parse() {
        Stack<String> pila = new Stack<>();
        pila.push("$");
        pila.push("P");

        //System.out.printf("%-45s %-20s %-30s%n", "PILA", "ENTRADA", "ACCIÓN");
        //System.out.println("--------------------------------------------------------------------------------------");
        logs.append(String.format("%-45s %-20s %-30s%n", "PILA", "ENTRADA", "ACCIÓN"));
        logs.append("--------------------------------------------------------------------------------------\n");
        

        while (!pila.isEmpty()) {
            String cima = pila.peek();
            String tokenActual = lookahead();

            logs.append(String.format("%-45s %-20s ", pila.toString(), tokenActual));
            //System.out.printf("%-45s %-20s ", pila.toString(), tokenActual);

            if (esTerminal(cima)) {
                if (cima.equals(tokenActual)) {
                    pila.pop();
                    index++;
                    logs.append(String.format("match(%s)%n", tokenActual));
                    //System.out.println("match(" + tokenActual + ")");
                } else {
                    this.guardarError(cima,1);
                    logs.append(String.format("Error: se esperaba %s pero llegó %s%n", cima, tokenActual));
                    return false;
                }
            } else {
                int i = indexOf(noTerminales, cima);
                int j = indexOf(terminales, tokenActual);
                if (i == -1 || j == -1) {
                    logs.append(" Error: símbolo no reconocido\n");
                    return false;
                }

                String produccion = tabla[i][j];
                if (produccion.equals("ERROR")) {
                    //this.guardarError(cima,2); no se guardan los errores de este tipo
                    logs.append(String.format("Error: no hay regla para [%s, %s]%n", cima, tokenActual));
                    return false;
                }

                pila.pop();

                if (!produccion.equals("ε")) {
                    String[] simbolos = produccion.trim().split("\\s+");
                    for (int k = simbolos.length - 1; k >= 0; k--) {
                        pila.push(simbolos[k]);
                    }
                }
                logs.append(String.format("%s -> %s%n", cima, produccion));
            }
        }
        logs.append("\n Análisis sintáctico exitoso\n");
        return true;
    }

    private boolean esTerminal(String simbolo) {
        return Arrays.asList("DEFINIR", "id", "COMO", "ESCRIBIR", "entero", "decimal", "cadena", "tokenEntero","tokenDecimal","tokenCadena","=", "(", ")", "+", "-", "*", "/", ";", "$").contains(simbolo);
    }

    public String getLogs() {
        return this.logs.toString();
    }
    
    
    private void guardarError(String cima, int tipo){
        if(ultimoToken != null){
            this.errores.add(new ErrorSintactico( ultimoToken, cima, tipo));
        }
    }
    
    
    public ArrayList<ErrorSintactico> getErrores(){
        return this.errores;
    }
    
    
}
