package sem;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

import lex_par.WebbyParser;
import lex_par.WebbyParserBaseVisitor;
import mem.MemoryManager;
import sem.funcs_vars.DirFunc;
import sem.funcs_vars.VarType;
import sem.exps.Quadruple;
import sem.exps.CuboSemantico;
import sem.exps.SemanticStackContext;

public class SemanticVisitor extends WebbyParserBaseVisitor<String> {
    private DirFunc dirFunc;
    private List<Quadruple> quadruples = new ArrayList<>();
    private SemanticStackContext stackContext = new SemanticStackContext();
    private int tempCounter = 0; // Contador para variables temporales
    private List<String> memoryQuadruples = new ArrayList<>();
    private CuboSemantico cuboSemantico = new CuboSemantico();
    private MemoryManager memoria = new MemoryManager();

    private String generateTemp() {
        return "t" + (tempCounter++);
    }

    public List<Quadruple> getQuadruples() {
        return quadruples;
    }

    public List<String> getMemoryQuadruples() {
        return memoryQuadruples;
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

        // 2. Cambiar la función al current
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

                // Asignar dirección de memoria
                if (dirFunc.getCurrentFunction().equals(dirFunc.getGlobalScopeName())) {
                    memoria.assignGlobal(id, varType);
                } else {
                    memoria.assignLocal(dirFunc.getCurrentFunction(), id, varType);
                }
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

            // Asignar dirección de memoria
            memoria.assignLocal(dirFunc.getCurrentFunction(), id, varType);
        }

        return null;
    }


    @Override
    public String visitAssign(WebbyParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        // Verificar que la variable existe
        if (!dirFunc.variableLocalExists(id) && !dirFunc.variableGlobalExists(id)) {
            throw new RuntimeException("Error: Variable '" + id + "' usada en asignación sin ser declarada.");
        }

        // Obtener tipo declarado de la variable destino
        VarType varType = dirFunc.getVariableType(id);

        // Visitar la expresión (lado derecho)
        String result = visit(ctx.expresion());  // Esto ya hace pushOperand()

        // Obtener tipo de la expresión
        VarType exprType = stackContext.popType();
        String exprValue = stackContext.popOperand();

        // Validar con el cubo semántico
        VarType assignResult = cuboSemantico.getResultType("=", varType, exprType);
        if (assignResult == null) {
            throw new RuntimeException("Error de tipo: No se puede asignar " + exprType + " a variable de tipo " + varType);
        }

        // Generar cuádruplo de asignación
        quadruples.add(new Quadruple("=", exprValue, "_", id));

        memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));
        return null;
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
                if (!memoria.isConstantRegistered(intValue)) {
                    memoria.assignConst(intValue, VarType.INT);
                }
                return intValue;
            } else if (ctx.cte().CTE_FLOAT() != null) {
                String floatValue = ctx.cte().CTE_FLOAT().getText();
                stackContext.pushOperand(floatValue, VarType.FLOAT);
                if (!memoria.isConstantRegistered(floatValue)) {
                    memoria.assignConst(floatValue, VarType.FLOAT);
                }
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
        // Visita el primer factor (esto ya empuja su operando a la pila)
        visit(ctx.factor(0));

        for (int i = 1; i < ctx.factor().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText(); // "*" o "/"
            stackContext.pushOperator(operator);

            // Visita el siguiente factor (esto también empuja su operando)
            visit(ctx.factor(i));

            // Saca operandos y tipos
            String rightOperand = stackContext.popOperand();
            VarType rightType = stackContext.popType();

            String leftOperand = stackContext.popOperand();
            VarType leftType = stackContext.popType();

            String op = stackContext.popOperator();

            // Validar operación (puedes tener una tabla semántica, o simplemente unifica tipos aquí)
            VarType resultType = cuboSemantico.getResultType(op, leftType, rightType); // Por ejemplo, si hay FLOAT, gana FLOAT

            // Generar temporal y asignarle dirección
            String tempName = generateTemp(); // p.ej. t1, t2, etc.
            memoria.assignTemp(tempName, resultType);

            // Crear el cuádruplo
            quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName));
            memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

            // Empujar el resultado a la pila
            stackContext.pushOperand(tempName, resultType);
        }

        // El resultado queda en la cima de la pila
        return stackContext.peekOperand();
    }

    @Override
    public String visitExpresion(WebbyParser.ExpresionContext ctx) {
        // Visita el primer operando (exp)
        visit(ctx.exp(0));

        // Si hay comparación (ej. <, >, !=)
        if (ctx.getChildCount() > 1) {
            for (int i = 1; i < ctx.exp().size(); i++) {
                String operator = ctx.getChild(i * 2 - 1).getText();  // "<", ">", "!="
                stackContext.pushOperator(operator);

                visit(ctx.exp(i));  // Visita el segundo operando

                // Saca operandos y tipos
                String rightOperand = stackContext.popOperand();
                VarType rightType = stackContext.popType();

                String leftOperand = stackContext.popOperand();
                VarType leftType = stackContext.popType();

                String op = stackContext.popOperator();

                // Validación semántica
                VarType resultType = cuboSemantico.getResultType(op, leftType, rightType);
                if (resultType == null) {
                    throw new RuntimeException("Error semántico: tipos incompatibles " + leftType + " " + op + " " + rightType);
                }

                // Crear temporal de tipo BOOL
                String tempName = generateTemp();
                memoria.assignTemp(tempName, resultType);  // BOOL

                // Crear cuádruplo
                quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName));
                memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

                // Empujar resultado a la pila
                stackContext.pushOperand(tempName, resultType);
            }
        }

        return stackContext.peekOperand(); // Valor booleano final
    }


    @Override
    public String visitExp(WebbyParser.ExpContext ctx) {
        // Visita el primer término (esto ya empuja su operando a la pila)
        visit(ctx.termino(0));

        for (int i = 1; i < ctx.termino().size(); i++) {
            String operator = ctx.getChild(i * 2 - 1).getText(); // "+" o "-"
            stackContext.pushOperator(operator);

            // Visita el siguiente término (esto también empuja su operando)
            visit(ctx.termino(i));

            // Saca operandos y tipos
            String rightOperand = stackContext.popOperand();
            VarType rightType = stackContext.popType();

            String leftOperand = stackContext.popOperand();
            VarType leftType = stackContext.popType();

            String op = stackContext.popOperator();

            // Validar con el cubo semántico
            VarType resultType = cuboSemantico.getResultType(op, leftType, rightType);
            if (resultType == null) {
                throw new RuntimeException("Error semántico: tipos incompatibles " + leftType + " " + op + " " + rightType);
            }

            // Crear temporal
            String tempName = generateTemp();
            memoria.assignTemp(tempName, resultType);

            // Cuádruplo
            quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName));
            memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

            // Empuja resultado
            stackContext.pushOperand(tempName, resultType);
        }

        return stackContext.peekOperand();
    }


    @Override
    public String visitPrint_arg(WebbyParser.Print_argContext ctx) {
        if (ctx.CTE_STRING() != null) {
            String str = ctx.CTE_STRING().getText();

            // Asegurarse de que esté registrada como constante
            if (!memoria.isConstantRegistered(str)) {
                memoria.assignConst(str, VarType.STRING);
            }

            quadruples.add(new Quadruple("PRINT", "_", "_", str));
            memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        } else if (ctx.expresion() != null) {
            String result = visit(ctx.expresion());
            quadruples.add(new Quadruple("PRINT", "_", "_", result));
            memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));
        }

        return null;
    }

    @Override
    public String visitCondition(WebbyParser.ConditionContext ctx) {
        // 1. Visitar la expresión y obtener resultado de la condición
        String conditionResult = visit(ctx.expresion());

        // 2. Crear GOTOF con salto pendiente
        int gotofIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTOF", conditionResult, "", "#-1"));  // marcador temporal
        memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        // 3. Visitar body verdadero (if)
        visit(ctx.body(0));

        // 4. Crear GOTO al final del if/else
        int gotoEndIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTO", "", "", "#-1"));  // marcador temporal
        memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        // 5. Rellenar el salto del GOTOF (posición del else)
        int elseStart = quadruples.size();
        Quadruple gotof = quadruples.get(gotofIndex);
        quadruples.set(gotofIndex, new Quadruple("GOTOF", conditionResult, "", "#" + elseStart));
        memoryQuadruples.set(gotofIndex, quadruples.get(gotofIndex).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        // 6. Visitar body falso (else)
        visit(ctx.body(1));

        // 7. Rellenar el salto del GOTO al final
        int end = quadruples.size();
        Quadruple gotoEnd = quadruples.get(gotoEndIndex);
        quadruples.set(gotoEndIndex, new Quadruple("GOTO", "", "", "#" + end));
        memoryQuadruples.set(gotoEndIndex, quadruples.get(gotoEndIndex).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        return null;
    }

    @Override
    public String visitCycle(WebbyParser.CycleContext ctx) {
        // 1. Guardar el inicio del ciclo
        int loopStart = quadruples.size();

        // 2. Evaluar condición
        String conditionResult = visit(ctx.expresion());

        // 3. Crear GOTOF con salto pendiente
        int gotofIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTOF", conditionResult, "", "#-1")); // marcador temporal
        memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));


        // 4. Visitar el cuerpo del ciclo
        visit(ctx.body());

        // 5. Agregar GOTO para regresar al inicio del ciclo
        quadruples.add(new Quadruple("GOTO", "", "", "#" + loopStart));
        memoryQuadruples.add(quadruples.get(quadruples.size() - 1).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        // 6. Rellenar el salto del GOTOF con la posición actual
        int end = quadruples.size();
        Quadruple gotof = quadruples.get(gotofIndex);
        quadruples.set(gotofIndex, new Quadruple("GOTOF", conditionResult, "", "#" + end));
        memoryQuadruples.set(gotofIndex, quadruples.get(gotofIndex).toMemoryString(memoria, dirFunc.getCurrentFunction()));

        return null;
    }
}
