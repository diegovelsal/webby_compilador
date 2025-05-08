package sem;

import org.antlr.v4.runtime.tree.TerminalNode;

import lex_par.WebbyParser;
import lex_par.WebbyParserBaseVisitor;

public class SemanticVisitor extends WebbyParserBaseVisitor<Void> {
    private DirFunc dirFunc;

    public SemanticVisitor() { }

    @Override
    public Void visitPrograma(WebbyParser.ProgramaContext ctx) {
        String globalName = ctx.ID().getText();

        dirFunc = new DirFunc(globalName); 

        // Primero visita las variables globales (si hay)
        if (ctx.vars() != null) {
            visit(ctx.vars());
        }

        // Luego las funciones (si hay)
        if (ctx.funcs_list() != null) {
            visit(ctx.funcs_list());
        }

        // Antes de visitar MAIN, aseguramos que currentFunction vuelva a ser el programa
        dirFunc.setCurrentFunction(globalName);

        // Visita el cuerpo principal del programa
        visit(ctx.body());

        return null;
    }

    @Override
    public Void visitFuncs(WebbyParser.FuncsContext ctx) {
        String funcName = ctx.ID().getText();

        // 1. Registrar la función
        if (!dirFunc.addFunction(funcName)) {
            throw new RuntimeException("Error: Función '" + funcName + "' ya fue declarada.");
        }

        // 2. Guardar la función anterior y cambiar el current
        String prevFunction = dirFunc.getCurrentFunction();
        dirFunc.setCurrentFunction(funcName);

        // 3. Registrar parámetros si existen
        if (ctx.params() != null) {
            visit(ctx.params());
        }

        // 4. Registrar variables locales si existen
        if (ctx.vars() != null) {
            visit(ctx.vars());
        }

        // 5. Visitar el cuerpo
        visit(ctx.body());

        return null;
    }


    @Override
    public Void visitVars(WebbyParser.VarsContext ctx) {
        for (WebbyParser.Var_declContext varDecl : ctx.var_decl()) {
            String typeStr = varDecl.type().getText();
            VarType varType = VarType.valueOf(typeStr.toUpperCase()); // INT -> VarType.INT

            for (TerminalNode idToken : varDecl.id_list().ID()) {
                String id = idToken.getText();

                if (dirFunc.variableLocalExists(id)) {
                    throw new IllegalArgumentException("La variable '" + id + "' ya ha sido declarada en el ámbito local.");
                }

                dirFunc.addVariable(id, varType);
            }
        }
        return null;
    }

    @Override
    public Void visitParams(WebbyParser.ParamsContext ctx) {
        // El primer ID y type
        String firstId = ctx.ID(0).getText();
        String firstTypeStr = ctx.type(0).getText();
        VarType firstVarType = VarType.valueOf(firstTypeStr.toUpperCase());

        if (dirFunc.variableLocalExists(firstId)) {
            throw new IllegalArgumentException("El parámetro '" + firstId + "' ya ha sido declarado en el ámbito local.");
        }
        dirFunc.addVariable(firstId, firstVarType);

        // Los siguientes IDs y types en paralelo
        for (int i = 1; i < ctx.ID().size(); i++) {
            String id = ctx.ID(i).getText();
            String typeStr = ctx.type(i).getText();
            VarType varType = VarType.valueOf(typeStr.toUpperCase());

            if (dirFunc.variableLocalExists(id)) {
                throw new IllegalArgumentException("El parámetro '" + id + "' ya ha sido declarado en el ámbito local.");
            }
            dirFunc.addVariable(id, varType);
        }

        return null;
    }


    @Override
    public Void visitAssign(WebbyParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        if (!dirFunc.variableLocalExists(id) && !dirFunc.variableGlobalExists(id)) {
            // Si la variable no existe en el ámbito local ni en el global, lanzamos una excepción
            throw new RuntimeException("Error: Variable '" + id + "' usada en asignación sin ser declarada.");
        }

        visit(ctx.expresion());  // visitar la expresión del lado derecho
        return null;
    }

    @Override
    public Void visitFactor(WebbyParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            if (!dirFunc.variableLocalExists(id) && !dirFunc.variableGlobalExists(id)) {
                throw new RuntimeException("Error: Variable '" + id + "' usada sin ser declarada.");
            }
        }

        // También puedes visitar sub-expresiones si hay paréntesis
        if (ctx.expresion() != null) {
            visit(ctx.expresion());
        }

        return null;
    }

    @Override
    public Void visitPrint_arg(WebbyParser.Print_argContext ctx) {
        if (ctx.expresion() != null) {
            visit(ctx.expresion());
        }
        return null;
    }

    @Override
    public Void visitCondition(WebbyParser.ConditionContext ctx) {
        visit(ctx.expresion());
        visit(ctx.body(0));
        visit(ctx.body(1));
        return null;
    }

    @Override
    public Void visitCycle(WebbyParser.CycleContext ctx) {
        visit(ctx.expresion());
        visit(ctx.body());
        return null;
    }



}
