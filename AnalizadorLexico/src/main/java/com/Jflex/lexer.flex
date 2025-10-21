/*CABECERA*/
package com.mycompany.analizadorlexico.backend.automata;
import  com.mycompany.analizadorlexico.backend.automata.TipoToken;
import  com.mycompany.analizadorlexico.backend.automata.Token;
import java.util.ArrayList;


%%
/*CONFIGURACIONES*/
%class Lexer
%public
%unicode
%line
%column
%char
%type Token

/*CODIGO JAVA*/
%{
    private String errorGeneralizado = "cualquier caracter permitido";
    private String errorNumero = "un numero";
    private String errorCaracter = "un caracter de escape";
    private String errorComentarioBloque = " (/) o (*/)";
    private String errorCadena = " un \" ";
    
    ArrayList<Token> tokens = new ArrayList<>();
    private void imprimirToken(String tipoToken,String lexema,int linea, int columna){
        System.out.println(tipoToken + " lexema: " + lexema + "  linea: " + linea + " columna: " + columna);
    }

    private void  guardarToken(TipoToken tipo, String mensaje){
        Token token = new Token(tipo,yytext(),yyline+1,yycolumn+1,yychar,yychar + yylength(),mensaje);
        tokens.add(token);
    }

    public ArrayList<Token> getTokens(){
        return tokens;
    }

%}

/*EXPRESIONES REGULARES */
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

RESERVADA = "SI"|"si"|"ENTONCES"|"entonces"|"entero"|"numero"|"cadena"|"ESCRIBIR"|"escribir"|"DEFINIR"|"COMO"
IDENTIFICADOR = [:jletter:] [:jletterdigit:]*
DIGITO = [0-9]
ENTERO = {DIGITO}+
DECIMAL = {DIGITO}+\.{DIGITO}+  
NUM_MAL = {DIGITO}+\.?[^0-9]? // de todo lo que puede haber mal

CADENA = \"([^\"])*\"
CADENA_INCOMPLETA = \"([^\"])*

COMENTARIO_LINEA = "//"[^\r\n]*  


//COMENTARIO BLOQUE
COMENTARIO_BLOQUE = "/\\*" ([^*]|\*+[^*/])* "\\*/"
COMENTARIO_BLOQUE_ERRONEO = "/\\*" ([^*]|\*+[^*/])*


SIGNO_PUNTUACION = "." | "," | ";" | ":"
OPERADOR_ARITMETICO = "+" | "-" | "*" | "/" | "%" | "="
AGRUPACION = "(" | ")" | "[" | "]" | "{" | "}"

PUNTUACION_PEGADO        = {SIGNO_PUNTUACION} [^ \t\n]
OPERADOR_PEGADO     = {OPERADOR_ARITMETICO} [^ \t\n]
AGRUPACION_PEGADO   = {AGRUPACION} [^ \t\n]

%%
/*ACCIONES*/
{LineTerminator}    {/*IGNORARLO*/}
{WhiteSpace}        {/*IGNORAMOS*/}

{RESERVADA}         {guardarToken(TipoToken.PALABRARESERVADA,null);}
{IDENTIFICADOR}     {guardarToken(TipoToken.IDENTIFICADOR,null);}
{DIGITO}            {/**/} // ignorar
{ENTERO}            {guardarToken(TipoToken.ENTERO,null);}
{DECIMAL}           {guardarToken(TipoToken.DECIMAL,null);}
{NUM_MAL}           {guardarToken(TipoToken.ERROR,errorNumero);}

{CADENA}                        {guardarToken(TipoToken.CADENA,null);}
{CADENA_INCOMPLETA}             {guardarToken(TipoToken.ERROR,errorCadena);}
{COMENTARIO_LINEA}              {guardarToken(TipoToken.COMENTARIO_LINEA,null);}
{COMENTARIO_BLOQUE}             {guardarToken(TipoToken.COMENTARIO_BLOQUE,null);}
{COMENTARIO_BLOQUE_ERRONEO}     {guardarToken(TipoToken.ERROR,errorComentarioBloque);}

{SIGNO_PUNTUACION}              {guardarToken(TipoToken.PUNTUACION,null);}
{OPERADOR_ARITMETICO}           {guardarToken(TipoToken.OPERADOR,null);}
{AGRUPACION}                    {guardarToken(TipoToken.AGRUPACION,null);}

{PUNTUACION_PEGADO}             {guardarToken(TipoToken.ERROR,errorCaracter);}
{OPERADOR_PEGADO}               {guardarToken(TipoToken.ERROR,errorCaracter);}
{AGRUPACION_PEGADO}             {guardarToken(TipoToken.ERROR,errorCaracter);}

[^]                 {guardarToken(TipoToken.ERROR,errorGeneralizado);}
