import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;

public class TestWebby {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 8; i++) {
            String fileName = "tests/test" + i + ".web";
            System.out.println("\n--- Probando archivo: " + fileName + " ---");

            try {
                CharStream input = CharStreams.fromFileName(fileName);
                WebbyLexer lexer = new WebbyLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                WebbyParser parser = new WebbyParser(tokens);

                // Capturar errores de sintaxis
                parser.removeErrorListeners();
                parser.addErrorListener(new DiagnosticErrorListener());

                ParseTree tree = parser.programa(); // Regla inicial
                System.out.println("Sintaxis válida");
                System.out.println("Árbol: " + tree.toStringTree(parser));
            } catch (Exception e) {
                System.out.println("Error en archivo: " + fileName);
                System.out.println(e.getMessage());
            }
        }
    }
}
