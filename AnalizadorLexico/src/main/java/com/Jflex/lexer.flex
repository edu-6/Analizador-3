/*CABECERA*/
package com.example.Jflex;
import com.example.Token.TipoToken;
import com.example.Token.Token;
import java.util.ArrayList;


%%
/*CONFIGURACIONES*/
%class Lexer
%public
%unicode
%line
%column
%type Token

/*CODIGO JAVA*/
%{
    ArrayList<Token> tokens = new ArrayList<>();
    public void imprimirToken(String tipoToken,String lexema,int linea, int columna){
        System.out.println(tipoToken + " lexema: " + lexema + "  linea: " + linea + " columna: " + columna);
    }

    public guardarToken(Token t){
        tokens.add(t);
    }

    public ArrayList<Token> getTokens(){
        return tokens;
    }
    
%}

/*EXPRESIONES REGULARES */

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

COMENTARIO_BLOQUE = "/\\*" ([^*]|\*+[^*/])* "\\*/"
COMENTARIO_BLOQUE_ERRONEO = "/\\*" ([^*]|\*+[^*/])* {EOF}


SIGNO_PUNTUACION = "." | "," | ";" | ":"
OPERADOR_ARITMETICO = "+" | "-" | "*" | "/" | "%" | "="
AGRUPACION = "(" | ")" | "[" | "]" | "{" | "}"


LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

PUNTUACION_PEGADO        = {SIGNO_PUNTUACION} [^ \t\n]
OPERADOR_PEGADO     = {OPERADOR_ARITMETICO} [^ \t\n]
AGRUPACION_PEGADO   = {AGRUPACION} [^ \t\n]


/*ACCIONES*/
//"escribir"          {guardarToken(new Token(TipoToken.ESCRIBIR,yytext(),yyline+1,yycolumn+1));}
//"entero"            {guardarToken(new Token(TipoToken.ENTERO,yytext(),yyline+1,yycolumn+1));}
{RESERVADA}         {guardarToken(new Token(TipoToken.RESERVADA,yytext(),yyline+1,yycolumn+1));}
{IDENTIFICADOR}     {guardarToken(new Token(TipoToken.ID,yytext(),yyline+1,yycolumn+1));}
{DIGITO}            {/**/}
{ENTERO}            {guardarToken(new Token(TipoToken.NUMERO,yytext(),yyline+1,yycolumn+1));}
{DECIMAL}           {guardarToken(new Token(TipoToken.DECIMAL,yytext(),yyline+1,yycolumn+1));}
{NUM_MAL}           {guardarToken(new Token(TipoToken.ERROR,yytext(),yyline+1,yycolumn+1));}

// FALTAN LOS DEMÁS
{LineTerminator}    {/*IGNORARLO*/}
{WhiteSpace}        {/*IGNORAMOS*/}
[^]                 {System.out.println("CARACTER INVALIDO");}
