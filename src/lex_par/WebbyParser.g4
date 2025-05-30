parser grammar WebbyParser;

options { tokenVocab=WebbyLexer; }

// Reglas principales

programa
    : PROGRAM ID SEMICOLON (vars)? (funcs_list)? MAIN body END
    ;

vars
    : VAR (var_decl)+
    ;

var_decl
    : id_list COLON type SEMICOLON 
    ;

id_list
    : ID (COMMA ID)*
    ;

type
    : INT
    | FLOAT
    ;

params
    : ID COLON type (COMMA ID COLON type)*
    ;

funcs_list
    : funcs+
    ;

funcs
    : (type | VOID) ID LPAREN (params)? RPAREN LBRACK (vars)? body RBRACK SEMICOLON
    ;

body
    : LBRACE (statement)* RBRACE
    ;

statement
    : assign_stmt
    | condition
    | cycle
    | f_call_stmt
    | print
    | return
    | for
    ;

return
    : RETURN (expresion)? SEMICOLON
    ;

assign_stmt
    : assign SEMICOLON
    ;

assign
    : ID ASSIGN expresion
    ;

print
    : PRINT LPAREN print_args RPAREN SEMICOLON
    ;

print_args
    : print_arg (COMMA print_arg)*
    ;

print_arg
    : expresion
    | CTE_STRING
    ;

condition
    : IF LPAREN expresion RPAREN body ELSE body SEMICOLON
    ;

cycle
    : WHILE LPAREN expresion RPAREN DO body SEMICOLON
    ;

for
    : FOR LPAREN assign SEMICOLON expresion SEMICOLON assign RPAREN body SEMICOLON
    ;

expresion
    : exp ( (LESS | GREATER | NOTEQUAL) exp )*
    ;

exp
    : termino ( (ADD | SUB) termino )*
    ;

termino
    : factor ( (MUL | DIV) factor )*
    ;

factor
    : (ADD | SUB)? (ID | cte | f_call | LPAREN expresion RPAREN)
    ;

cte
    : CTE_INT
    | CTE_FLOAT
    ;

f_call_stmt
    : f_call SEMICOLON
    ;

f_call
    : ID LPAREN args? RPAREN
    ;

args
    : expresion (COMMA expresion)*
    ;