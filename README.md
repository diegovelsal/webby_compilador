# Proyecto Compilador Webby

**Nombre:** Diego Velázquez Saldaña
**Matrícula:** A01177877

## 🛠️ Compilación y Ejecución

### Lexer y Parser:

```bash
java -jar .\libs\antlr-4.13.2-complete.jar -Dlanguage=Java -package lex_par -visitor .\src\lex_par\WebbyLexer.g4 .\src\lex_par\WebbyParser.g4```

### Compilar:

```bash
javac -cp ".;libs/antlr-4.13.2-complete.jar" src/lex_par/*.java src/mem/*.java src/sem/exps/*.java src/sem/funcs_vars/*.java src/sem/*.java src/vm/*.java src/*.java
```

### Ejecutar:

```bash
java -cp ".;libs/antlr-4.13.2-complete.jar;src" Main  
```
