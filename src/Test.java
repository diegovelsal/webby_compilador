
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import lex_par.WebbyLexer;
import lex_par.WebbyParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import sem.SemanticVisitor;
import sem.DirFunc;

public class Test {
    public static void main(String[] args) throws IOException {
        File testsFolder = new File("tests");

        // Crear el directorio de funciones (tiene un mapa de funciones y sus tablas de variables)

        for (File file : Objects.requireNonNull(testsFolder.listFiles((dir, name) -> name.endsWith(".web")))) {
            System.out.println("=== Parsing file: " + file.getName() + " ===");

            // Leer contenido del archivo .web
            String input = Files.readString(file.toPath());

            try {
                // Lexer y parser
                CharStream inputStream = CharStreams.fromString(input);
                WebbyLexer lexer = new WebbyLexer(inputStream);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                WebbyParser parser = new WebbyParser(tokens);

                // Remover los listeners de error por defecto
                parser.removeErrorListeners();
                
                // Agregar un listener de errores detallados
                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
                            int line, int charPositionInLine, String msg, RecognitionException e) {
                        System.err.println("✗ Error de sintaxis en " + file.getName() + " en la línea " + line + ", columna " 
                                + charPositionInLine + ": " + msg);
                    }
                });

                // Cambia `programa()` por tu regla de inicio
                ParseTree tree = parser.programa();

                // Imprimir árbol de sintaxis
                System.out.println(tree.toStringTree(parser));
                System.out.println("✓ Parsed successfully.\n");

                // Aquí es donde entra la semántica
                // Utilizamos un visitante para recorrer el árbol y realizar la validación semántica
                SemanticVisitor semanticVisitor = new SemanticVisitor(); // Clase de visitante que hace la validación
                semanticVisitor.visit(tree); // Recorre el árbol y realiza la validación semántica

            } catch (Exception e) {
                System.err.println("✗ Error parsing " + file.getName() + ": " + e.getMessage());
            }
        }
    }
}
