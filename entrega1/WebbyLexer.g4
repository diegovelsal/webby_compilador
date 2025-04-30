lexer grammar WebbyLexer;

// Palabras clave
PROGRAM : 'program';
MAIN    : 'main';
END     : 'end';
VAR     : 'var';
PRINT   : 'print';
IF      : 'if';
ELSE    : 'else';
WHILE   : 'while';
DO      : 'do';
VOID    : 'void';
INT     : 'int';
FLOAT   : 'float';

// Identificadores y Constantes
ID        : [a-z_][a-zA-Z0-9_]*;
CTE_INT   : [0-9]+;
CTE_FLOAT : [0-9]+ '.' [0-9]+;
CTE_STRING: '"' ( ~["\\] | '\\' . )* '"' ;

// Operadores aritméticos
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';

// Operadores de comparación
ASSIGN    : '=';
EQUAL     : '==';
NOTEQUAL  : '!=';
LESS      : '<';
GREATER   : '>';

// Delimitadores
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMICOLON : ';';
COLON : ':';
COMMA : ',';

// Ignorar espacios, tabs, saltos de línea
WS : [ \t\r\n]+ -> skip;
