# Proyecto Compilador Webby

**Nombre:** Diego Velázquez Saldaña
**Matrícula:** A01177877

## 🛠️ Compilación y Ejecución

### Compilar:

```bash
javac -cp ".;libs/antlr-4.13.2-complete.jar" src/lex_par/*.java src/mem/*.java src/sem/exps/*.java src/sem/funcs_vars/*.java src/sem/*.java src/vm/*.java src/*.java
```

### Ejecutar:

```bash
java -cp ".;libs/antlr-4.13.2-complete.jar;src" Main  
```