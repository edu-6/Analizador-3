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
    private String errorDecimalPegado = " un numero o caracter de escape";
    private String errorGeneralizado = "cualquier caracter permitido";
    private String errorNumero = "un numero";
    private String errorCaracter = "un caracter de escape";
    private String errorComentarioBloque = "(/)";
    private String errorComentarioBloque2 = " (*/)";
    private String errorCadena = " un \" ";
    private String errorReservada = "un caracter de escape";
    private String errorIdentificador = " caracter de escape, letra o digito";
    
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

DATO_ENTERO = "entero" | "ENTERO"
DATO_DECIMAL = "decimal" | "DECIMAL"
DATO_CADENA = "cadena" | "CADENA"

ESCRIBIR = "ESCRIBIR"| "escribir"
DEFINIR = "DEFINIR" | "definir"
COMO = "COMO" | "como"

RESERVADA = "SI"|"si"|"ENTONCES"|"entonces"
RESERVADA_PEGADA = {RESERVADA}[^a-zA-Z0-9\s]
IDENTIFICADOR = [:jletter:] [:jletterdigit:]*
IDENTIFICADOR_PEGADO = {IDENTIFICADOR}[^a-zA-Z0-9\s] | {DATO_ENTERO}[^a-zA-Z0-9\s] | {DATO_DECIMAL}[^a-zA-Z0-9\s] | {DATO_CADENA}[^a-zA-Z0-9\s] |  {DEFINIR}[^a-zA-Z0-9\s] | {ESCRIBIR}[^a-zA-Z0-9\s] |  {COMO}[^a-zA-Z0-9\s]
DIGITO = [0-9]
ENTERO = {DIGITO}+
DECIMAL = {DIGITO}+\.{DIGITO}+
DECIMAL_PEGADO = {DIGITO}+\.{DIGITO}+[^0-9\s]
NUM_MAL = {DIGITO}+\.?[^0-9\s]? // de todo lo que puede haber mal


CADENA = \"([^\"])*\"
CADENA_INCOMPLETA = \"([^\"])*

COMENTARIO_LINEA = "//"[^\r\n]*  


//COMENTARIO BLOQUE
COMENTARIO_BLOQUE = "/*" [^*]* "*" ([^/][^*]* "*")* "/"
COMENTARIO_BLOQUE_ERRONEO = "/*" [^*]* "*" ([^/][^*]* "*")*
COMENTARIO_BLOQUE_ERRONEO_2 = "/*" [^*]*

PUNTO_COMA = ";"
MAS = "+"
MENOS = "-"
POR = "*"
DIVISION = "/"
IGUAL = "="
P_APERTURA = "("
P_CIERRE = ")"

SIGNO_PUNTUACION = "." | ","| ":"
OPERADOR_ARITMETICO =  "%"
AGRUPACION = "[" | "]" | "{" | "}"

PUNTUACION_PEGADO        = {SIGNO_PUNTUACION} [^ \t\n] | {PUNTO_COMA} [^ \t\n]
OPERADOR_PEGADO     = {OPERADOR_ARITMETICO} [^ \t\n] | {MAS} [^ \t\n] |{MENOS} [^ \t\n] | {POR} [^ \t\n] | {DIVISION} [^ \t\n] |{IGUAL} [^ \t\n]
AGRUPACION_PEGADO   = {AGRUPACION} [^ \t\n] | {P_APERTURA} [^ \t\n] | {P_CIERRE} [^ \t\n]

%%
/*ACCIONES*/
{LineTerminator}    {/*IGNORARLO*/}
{WhiteSpace}        {/*IGNORAMOS*/}

{DATO_ENTERO}                   {guardarToken(TipoToken.DATO_ENTERO,null);}
{DATO_DECIMAL}                  {guardarToken(TipoToken.DATO_DECIMAL,null);}
{DATO_CADENA}                   {guardarToken(TipoToken.DATO_CADENA,null);}
{ESCRIBIR}                      {guardarToken(TipoToken.ESCRIBIR,null);}
{DEFINIR}                       {guardarToken(TipoToken.DEFINIR,null);}
{COMO}                          {guardarToken(TipoToken.COMO,null);}


{RESERVADA}                     {guardarToken(TipoToken.PALABRARESERVADA,null);}
{RESERVADA_PEGADA}              {guardarToken(TipoToken.ERROR,errorReservada);}
{IDENTIFICADOR}                 {guardarToken(TipoToken.IDENTIFICADOR,null);}
{IDENTIFICADOR_PEGADO}          {guardarToken(TipoToken.ERROR,errorIdentificador);}
{ENTERO}                        {guardarToken(TipoToken.ENTERO,null);}
{DECIMAL}                       {guardarToken(TipoToken.DECIMAL,null);}
{DECIMAL_PEGADO}                {guardarToken(TipoToken.ERROR,errorDecimalPegado);}
{NUM_MAL}                       {guardarToken(TipoToken.ERROR,errorNumero);}

{CADENA}                        {guardarToken(TipoToken.CADENA,null);}
{CADENA_INCOMPLETA}             {guardarToken(TipoToken.ERROR,errorCadena);}
{COMENTARIO_LINEA}              {guardarToken(TipoToken.COMENTARIO_LINEA,null);}
{COMENTARIO_BLOQUE}             {guardarToken(TipoToken.COMENTARIO_BLOQUE,null);}
{COMENTARIO_BLOQUE_ERRONEO}     {guardarToken(TipoToken.ERROR,errorComentarioBloque);}
{COMENTARIO_BLOQUE_ERRONEO_2}   {guardarToken(TipoToken.ERROR,errorComentarioBloque2);}

{PUNTO_COMA}                    {guardarToken(TipoToken.PUNTO_COMA,null);}
{MAS}                           {guardarToken(TipoToken.MAS,null);}
{MENOS}                         {guardarToken(TipoToken.MENOS,null);}
{POR}                           {guardarToken(TipoToken.POR,null);}
{DIVISION}                      {guardarToken(TipoToken.DIVISION,null);}
{IGUAL}                         {guardarToken(TipoToken.IGUAL,null);}
{P_APERTURA}                    {guardarToken(TipoToken.P_APERTURA,null);}
{P_CIERRE}                      {guardarToken(TipoToken.P_CIERRE,null);}


{SIGNO_PUNTUACION}              {guardarToken(TipoToken.PUNTUACION,null);}
{OPERADOR_ARITMETICO}           {guardarToken(TipoToken.OPERADOR,null);}
{AGRUPACION}                    {guardarToken(TipoToken.AGRUPACION,null);}

{PUNTUACION_PEGADO}             {guardarToken(TipoToken.ERROR,errorCaracter);}
{OPERADOR_PEGADO}               {guardarToken(TipoToken.ERROR,errorCaracter);}
{AGRUPACION_PEGADO}             {guardarToken(TipoToken.ERROR,errorCaracter);}


{DIGITO}            {/**/} // ignorar // para no tener problemas 

[^]                 {guardarToken(TipoToken.ERROR,errorGeneralizado);}
