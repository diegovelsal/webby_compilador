parser grammar WebbyParser;

options { tokenVocab=WebbyLexer; }

// Reglas principales

programa
    : PROGRAM ID SEMICOLON vars funcs MAIN body END
    ;

vars
    : VAR COLON tipo id_list SEMICOLON vars
    | // vacío (ε)
    ;

id_list
    : ID (COMMA ID)*
    ;

tipo
    : INT
    | FLOAT
    ;

funcs
    : VOID ID LPAREN (tipo)? ID RPAREN COLON vars body funcs
    | // vacío (ε)
    ;

body
    : LBRACE statement RBRACE
    ;

statement
    : assign
    | condition
    | cycle
    | print_stmt
    ;

assign
    : ID ASSIGN expresion SEMICOLON
    ;

print_stmt
    : PRINT LPAREN expresion RPAREN SEMICOLON
    ;

condition
    : IF LPAREN expresion RPAREN body ELSE body
    ;

cycle
    : WHILE LPAREN expresion RPAREN DO body
    ;

expresion
    : exp ( (LESS | GREATER | EQUAL | NOTEQUAL) exp )?
    ;

exp
    : termino ( (ADD | SUB) termino )*
    ;

termino
    : factor ( (MUL | DIV) factor )*
    ;

factor
    : (ADD | SUB)? (ID | cte | LPAREN expresion RPAREN)
    ;

cte
    : CTE_INT
    | CTE_FLOAT
    | CTE_STRING
    ;
