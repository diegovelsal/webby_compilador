package sem;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

import lex_par.WebbyParser;
import lex_par.WebbyParserBaseVisitor;
import sem.funcs_vars.DirFunc;
import sem.funcs_vars.VarType;
import sem.exps.Quadruple;
import sem.exps.SemanticStackContext;

public class SemanticVisitor extends WebbyParserBaseVisitor<String> {
    private DirFunc dirFunc;
    private List<Quadruple> quadruples = new ArrayList<>();
    private SemanticStackContext stackContext = new SemanticStackContext();
    private int tempCounter = 0; // Contador para variables temporales

    private String generateTemp() {
        return "t" + (tempCounter++);
    }

    public List<Quadruple> getQuadruples() {
        return quadruples;
    }

    public SemanticVisitor() { }

    @Override
    public String visitPrograma(WebbyParser.ProgramaContext ctx) {
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
    public String visitFuncs(WebbyParser.FuncsContext ctx) {
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
    public String visitVars(WebbyParser.VarsContext ctx) {
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
    public String visitParams(WebbyParser.ParamsContext ctx) {
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
    public String visitAssign(WebbyParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        if (!dirFunc.variableLocalExists(id) && !dirFunc.variableGlobalExists(id)) {
            // Si la variable no existe en el ámbito local ni en el global, lanzamos una excepción
            throw new RuntimeException("Error: Variable '" + id + "' usada en asignación sin ser declarada.");
        }

        String temp = visit(ctx.expresion());  // visitar la expresión del lado derecho
        
        // Generar un cuádruplo para la asignación
        quadruples.add(new Quadruple("=", temp, "_", id));        return null;
    }

    @Override
    public String visitFactor(WebbyParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            if (!dirFunc.variableLocalExists(id) && !dirFunc.variableGlobalExists(id)) {
                throw new RuntimeException("Error: Variable '" + id + "' usada sin ser declarada.");
            }

            VarType varType = dirFunc.getVariableType(id);
            stackContext.pushOperand(id, varType);
            return id;

        } else if (ctx.cte() != null) {
            if (ctx.cte().CTE_INT() != null) {
                String intValue = ctx.cte().CTE_INT().getText();
                stackContext.pushOperand(intValue, VarType.INT);
                return intValue;
            } else if (ctx.cte().CTE_FLOAT() != null) {
                String floatValue = ctx.cte().CTE_FLOAT().getText();
                stackContext.pushOperand(floatValue, VarType.FLOAT);
                return floatValue;
            }

        } else if (ctx.expresion() != null) {
            // Subexpresión entre paréntesis
            return visit(ctx.expresion());
        }

        return null; // No debería llegar aquí
    }

    @Override
    public String visitTermino(WebbyParser.TerminoContext ctx) {
        String left = visit(ctx.factor(0));

        for (int i = 1; i < ctx.factor().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText();
            stackContext.pushOperator(operator);

            String right = visit(ctx.factor(i));
            String op = stackContext.popOperator();

            String temp = generateTemp();
            quadruples.add(new Quadruple(op, left, right, temp));
            stackContext.pushOperand(temp, VarType.INT);

            left = temp;  // Para la siguiente operación
        }

        return left;
    }

    @Override
    public String visitExpresion(WebbyParser.ExpresionContext ctx) {
        String left = visit(ctx.exp(0));  // Primera parte (a la izquierda)
        
        // Si hay una comparación
        if (ctx.getChildCount() > 1) {
            for (int i = 1; i < ctx.exp().size(); i++) {
                String operator = ctx.getChild(i * 2 - 1).getText();  // LESS, GREATER, NOTEQUAL
                String right = visit(ctx.exp(i));

                String temp = generateTemp();
                quadruples.add(new Quadruple(operator, left, right, temp));

                left = temp;  // Por si hay múltiples comparaciones encadenadas
            }
        }

        return left;
    }

    @Override
    public String visitExp(WebbyParser.ExpContext ctx) {
        String left = visit(ctx.termino(0));

        for (int i = 1; i < ctx.termino().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText();
            stackContext.pushOperator(operator);

            String right = visit(ctx.termino(i));
            String op = stackContext.popOperator();

            String temp = generateTemp();
            quadruples.add(new Quadruple(op, left, right, temp));
            stackContext.pushOperand(temp, VarType.INT);

            left = temp;
        }

        return left;
    }

    @Override
    public String visitPrint_arg(WebbyParser.Print_argContext ctx) {
        if (ctx.CTE_STRING() != null) {
            String str = ctx.CTE_STRING().getText();
            quadruples.add(new Quadruple("PRINT", "_", "_", str));
        } else if (ctx.expresion() != null) {
            String result = visit(ctx.expresion());
            quadruples.add(new Quadruple("PRINT", "_", "_", result));
        }
        return null;
    }

    @Override
    public String visitCondition(WebbyParser.ConditionContext ctx) {
        visit(ctx.expresion());
        visit(ctx.body(0));
        visit(ctx.body(1));
        return null;
    }

    @Override
    public String visitCycle(WebbyParser.CycleContext ctx) {
        visit(ctx.expresion());
        visit(ctx.body());
        return null;
    }



}
