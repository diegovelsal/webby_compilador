package sem;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import lex_par.WebbyParser;
import lex_par.WebbyParserBaseVisitor;
import mem.MemoryManager;
import sem.funcs_vars.DirFunc;
import sem.funcs_vars.ConstTable;
import sem.funcs_vars.VarType;
import sem.exps.Quadruple;
import sem.exps.CuboSemantico;
import sem.exps.SemanticStackContext;

public class SemanticVisitor extends WebbyParserBaseVisitor<String> {
    private DirFunc dirFunc;
    private List<Quadruple> quadruples = new ArrayList<>();
    private SemanticStackContext stackContext = new SemanticStackContext();
    private int tempCounter = 0; // Contador para variables temporales
    private CuboSemantico cuboSemantico = new CuboSemantico();
    private MemoryManager memoria = new MemoryManager();
    private ConstTable constTable = new ConstTable(memoria);
    private LinkedList<String> argumentQueue = new LinkedList<>();
    private LinkedList<VarType> argumentTypeQueue = new LinkedList<>();
    private int controlStructureDepth = 0;
    private boolean hasUnconditionalReturn = false;


    private String generateTemp() {
        return "t" + (tempCounter++);
    }

    public List<Quadruple> getQuadruples() {
        return quadruples;
    }

    public ConstTable getConstTable() {
        return constTable;
    }

    public DirFunc getDirFunc() {
        return dirFunc;
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

        int gotoMainIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTO", "", "", "#-1", dirFunc, constTable)); 

        // Luego las funciones (si hay)
        if (ctx.funcs_list() != null) {
            visit(ctx.funcs_list());
        }

        // Antes de visitar MAIN, aseguramos que currentFunction vuelva a ser el programa
        dirFunc.setCurrentFunction(globalName);
        quadruples.set(gotoMainIndex, new Quadruple("GOTO", "", "", "#" + quadruples.size(), dirFunc, constTable));
        // Visita el cuerpo principal del programa
        visit(ctx.body());

        // Generar cuádruplo de fin de programa
        quadruples.add(new Quadruple("ENDPROG", "", "", "", dirFunc, constTable));
        return null;
    }

    @Override
    public String visitFuncs(WebbyParser.FuncsContext ctx) {
        VarType returnType;
        if (ctx.type() != null) {
            String typeStr = ctx.type().getText();
            returnType = VarType.valueOf(typeStr.toUpperCase());
        } else {
            returnType = VarType.VOID;
        }

        String funcName = ctx.ID().getText();

        // 1. Registrar la función
        if (!dirFunc.addFunction(funcName, returnType)) {
            throw new RuntimeException("Error: Función '" + funcName + "' ya fue declarada.");
        }

        // 2. Cambiar la función al current
        dirFunc.setCurrentFunction(funcName);
        dirFunc.setFunctionStartQuad(funcName, quadruples.size());

        // Reservar dirección para valor de retorno si no es VOID
        if (returnType != VarType.VOID) {
            int retAddr = memoria.assignTempAddress(returnType);
            dirFunc.setReturnAddress(funcName, retAddr);
            dirFunc.addVariable("__return", returnType, retAddr);
        }

        hasUnconditionalReturn = false;

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

        if (returnType != VarType.VOID && !hasUnconditionalReturn) {
            throw new RuntimeException("Error: La función '" + funcName + "' debe tener al menos un 'return' no condicionado.");
        }

        // 6. Limpiar memoria local y temporales
        memoria.resetLocalAndTemp();

        // 7. Generar cuádruplo de fin de función
        quadruples.add(new Quadruple("ENDFUNC", "", "", "", dirFunc, constTable));

        return null;
    }

    @Override
    public String visitVars(WebbyParser.VarsContext ctx) {
        for (WebbyParser.Var_declContext varDecl : ctx.var_decl()) {
            String typeStr = varDecl.type().getText();
            VarType varType = VarType.valueOf(typeStr.toUpperCase());

            for (TerminalNode idToken : varDecl.id_list().ID()) {
                String id = idToken.getText();

                if (dirFunc.variableExistsInCurrentScope(id)) {
                    throw new IllegalArgumentException("La variable '" + id + "' ya ha sido declarada en el ámbito " + dirFunc.getCurrentFunction() + ".");
                }

                int address;
                if (dirFunc.globalIsCurrent()) {
                    address = memoria.assignGlobalAddress(varType);
                } else {
                    address = memoria.assignLocalAddress(varType);
                }

                dirFunc.addVariable(id, varType, address);
            }
        }
        return null;
    }

    @Override
    public String visitParams(WebbyParser.ParamsContext ctx) {
        for (int i = 0; i < ctx.ID().size(); i++) {
            String id = ctx.ID(i).getText();
            String typeStr = ctx.type(i).getText();
            VarType varType = VarType.valueOf(typeStr.toUpperCase());

            if (dirFunc.variableExistsInCurrentScope(id)) {
                throw new IllegalArgumentException("El parámetro '" + id + "' ya ha sido declarado");
            }

            // Obtener dirección y registrar
            int address = memoria.assignLocalAddress(varType);
            dirFunc.addParameter(id, varType, address);
        }

        return null;
    }

    @Override
    public String visitPrint_args(WebbyParser.Print_argsContext ctx) {
        int n = ctx.print_arg().size();

        for (int i = 0; i < n; i++) {
            String val = visit(ctx.print_arg(i));
            String op = (i == n - 1) ? "PRINTLN" : "PRINT";
            quadruples.add(new Quadruple(op, "", "", val, dirFunc, constTable));
        }

        return null;
    }

    @Override
    public String visitPrint_arg(WebbyParser.Print_argContext ctx) {
        if (ctx.CTE_STRING() != null) {
            String str = ctx.CTE_STRING().getText().replace("\"", ""); // Eliminar comillas

            // Asegurarse de que esté registrada como constante
            if (!constTable.hasConstant(str)) {
                constTable.addConstant(str, VarType.STRING);
            }

            return str;
        } else if (ctx.expresion() != null) {
            return visit(ctx.expresion());
        }

        return null;
    }

    @Override
    public String visitCondition(WebbyParser.ConditionContext ctx) {
        // 1. Visitar la expresión y obtener resultado de la condición
        controlStructureDepth++;
        String conditionResult = visit(ctx.expresion());

        // 2. Crear GOTOF con salto pendiente
        int gotofIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTOF", conditionResult, "", "#-1", dirFunc, constTable));  // marcador temporal

        // 3. Visitar body verdadero (if)
        visit(ctx.body(0));

        // 4. Crear GOTO al final del if/else
        int gotoEndIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTO", "", "", "#-1", dirFunc, constTable));  // marcador temporal

        // 5. Rellenar el salto del GOTOF (posición del else)
        int elseStart = quadruples.size();
        quadruples.set(gotofIndex, new Quadruple("GOTOF", conditionResult, "", "#" + elseStart, dirFunc, constTable));

        // 6. Visitar body falso (else)
        visit(ctx.body(1));

        // 7. Rellenar el salto del GOTO al final
        int end = quadruples.size();
        quadruples.set(gotoEndIndex, new Quadruple("GOTO", "", "", "#" + end, dirFunc, constTable));

        controlStructureDepth--;
        return null;
    }

    @Override
    public String visitCycle(WebbyParser.CycleContext ctx) {
        controlStructureDepth++;
        // 1. Guardar el inicio del ciclo
        int loopStart = quadruples.size();

        // 2. Evaluar condición
        String conditionResult = visit(ctx.expresion());

        // 3. Crear GOTOF con salto pendiente
        int gotofIndex = quadruples.size();
        quadruples.add(new Quadruple("GOTOF", conditionResult, "", "#-1", dirFunc, constTable)); // marcador temporal

        // 4. Visitar el cuerpo del ciclo
        visit(ctx.body());

        // 5. Agregar GOTO para regresar al inicio del ciclo
        quadruples.add(new Quadruple("GOTO", "", "", "#" + loopStart, dirFunc, constTable)); // salto al inicio del ciclo

        // 6. Rellenar el salto del GOTOF con la posición actual
        int end = quadruples.size();
        Quadruple gotof = quadruples.get(gotofIndex);
        quadruples.set(gotofIndex, new Quadruple("GOTOF", conditionResult, "", "#" + end, dirFunc, constTable));

        controlStructureDepth--;

        return null;
    }

    @Override
    public String visitReturn(WebbyParser.ReturnContext ctx) {
        String funcName = dirFunc.getCurrentFunction();
        VarType returnType = dirFunc.getFunctionReturnType(funcName);

        if (controlStructureDepth == 0) {
            hasUnconditionalReturn = true;
        }

        if (ctx.expresion() == null) {
            if (returnType != VarType.VOID) {
                throw new RuntimeException("Error: La función '" + funcName + "' debe retornar un valor de tipo " + returnType);
            }

            // Cuádruplo de RETURN sin valor para funciones void
            quadruples.add(new Quadruple("RETURN", "", "", "", dirFunc, constTable));
        } else {
            if (returnType == VarType.VOID) {
                throw new RuntimeException("Error: La función void '" + funcName + "' no debe retornar un valor.");
            }

            // Visitar la expresión (esto hace pushOperand y pushType)
            visit(ctx.expresion());

            // Sacar tipo y dirección de la expresión
            VarType exprType = stackContext.popType();
            String exprValue = stackContext.popOperand();

            // Validar tipo de retorno
            VarType resultType = cuboSemantico.getResultType("=", returnType, exprType);
            if (resultType == null) {
                throw new RuntimeException("Error: Tipo de retorno inválido. Se esperaba " + returnType + ", pero se obtuvo " + exprType);
            }

            // Generar cuádruplo de RETURN con el valor
            quadruples.add(new Quadruple("RETURN", "", "", exprValue, dirFunc, constTable));
        }

        return null;
    }

    @Override
    public String visitAssign(WebbyParser.AssignContext ctx) {
        String id = ctx.ID().getText();

        // Verificar que la variable existe
        if (!dirFunc.variableExists(id)) {
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
        quadruples.add(new Quadruple("=", exprValue, "", id, dirFunc, constTable));
        return null;
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
                String tempName = generateTemp(); // p.ej. t1, t2, etc.
                int tempAddress = memoria.assignTempAddress(resultType);
                dirFunc.addVariable(tempName, resultType, tempAddress);

                // Crear cuádruplo
                quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName, dirFunc, constTable));

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
            String tempName = generateTemp(); // p.ej. t1, t2, etc.
            int tempAddress = memoria.assignTempAddress(resultType);
            dirFunc.addVariable(tempName, resultType, tempAddress);

            // Cuádruplo
            quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName, dirFunc, constTable));

            // Empuja resultado
            stackContext.pushOperand(tempName, resultType);
        }

        return stackContext.peekOperand();
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
            VarType resultType = cuboSemantico.getResultType(op, leftType, rightType); 
            if (resultType == null) {
                throw new RuntimeException("Error semántico: tipos incompatibles " + leftType + " " + op + " " + rightType);
            }

            // Generar temporal y asignarle dirección
            String tempName = generateTemp(); // p.ej. t1, t2, etc.
            int tempAddress = memoria.assignTempAddress(resultType);
            dirFunc.addVariable(tempName, resultType, tempAddress);

            // Crear el cuádruplo
            quadruples.add(new Quadruple(op, leftOperand, rightOperand, tempName, dirFunc, constTable));

            // Empujar el resultado a la pila
            stackContext.pushOperand(tempName, resultType);
        }

        // El resultado queda en la cima de la pila
        return stackContext.peekOperand();
    }

    @Override
    public String visitFactor(WebbyParser.FactorContext ctx) {
        boolean isNegative = ctx.SUB() != null;
        boolean isPositive = ctx.ADD() != null;

        String value = null;
        VarType varType = null;

        if (ctx.ID() != null) {
            String id = ctx.ID().getText();
            if (!dirFunc.variableExists(id)) {
                throw new RuntimeException("Error: Variable '" + id + "' usada sin ser declarada.");
            }

            varType = dirFunc.getVariableType(id);
            value = id;

        } else if (ctx.cte() != null) {
            if (ctx.cte().CTE_INT() != null) {
                value = ctx.cte().CTE_INT().getText();
                varType = VarType.INT;
            } else if (ctx.cte().CTE_FLOAT() != null) {
                value = ctx.cte().CTE_FLOAT().getText();
                varType = VarType.FLOAT;
            }

            if (value != null && !constTable.hasConstant(value)) {
                constTable.addConstant(value, varType);
            }

        } else if (ctx.f_call() != null) {
            return visit(ctx.f_call());
        } else if (ctx.expresion() != null) {
            return visit(ctx.expresion());
        }

        if (value == null || varType == null) {
            throw new RuntimeException("Error al procesar factor.");
        }

        if (isNegative) {
            String tempName = generateTemp(); // p.ej. t1, t2, etc.
            int tempAddress = memoria.assignTempAddress(varType); // asignar dirección temporal
            dirFunc.addVariable(tempName, varType, tempAddress);

            // Crear cuádruplo de resta unaria (0 - value)
            quadruples.add(new Quadruple("-", "0", value, tempName, dirFunc, constTable));

            stackContext.pushOperand(tempName, varType);
            return tempName;
        }

        // Si es positivo o no hay signo unario, simplemente push
        stackContext.pushOperand(value, varType);
        return value;
    }


    @Override
    public String visitF_call(WebbyParser.F_callContext ctx) {
        String funcName = ctx.ID().getText();

        // 1. Verificar que la función existe
        if (!dirFunc.hasFunction(funcName)) {
            throw new RuntimeException("Error: Función '" + funcName + "' no declarada.");
        }

        // 2. Generar cuádruplo de ERA
        quadruples.add(new Quadruple("ERA", "", "", funcName, dirFunc, constTable));

        // 3. Obtener parámetros esperados de la función
        List<DirFunc.Parameter> expectedParams = dirFunc.getFunctionParameters(funcName);

        // 4. Procesar los argumentos
        if (ctx.args() != null) {
            visit(ctx.args());
        }

        // 5. Validar número de argumentos
        if (argumentQueue.size() != expectedParams.size()) {
            throw new RuntimeException("Error: Número de argumentos no coincide para la función '" + funcName + "'. Se esperaban " + expectedParams.size() + ", se recibieron " + argumentQueue.size());
        }

        // 6. Generar cuádruplos de PARAM, validando tipos
        for (int i = 0; i < expectedParams.size(); i++) {
            String argAddress = argumentQueue.poll();
            VarType argType = argumentTypeQueue.poll();

            VarType expectedType = expectedParams.get(i).getType();

            if (!argType.equals(expectedType)) {
                throw new RuntimeException("Error de tipo en argumento " + (i + 1) + ": se esperaba " +
                        expectedType + ", pero se recibió " + argType);
            }

            quadruples.add(new Quadruple("PARAM", argAddress, "", "param" + i, dirFunc, constTable));
        }

        // 7. Generar cuádruplo de GOSUB
        int startQuad = dirFunc.getFunctionStartQuad(funcName);
        quadruples.add(new Quadruple("GOSUB", "", "", "#" + startQuad, dirFunc, constTable));

        // 8. Manejar el retorno de la función
        VarType returnType = dirFunc.getFunctionReturnType(funcName);
        if (returnType != VarType.VOID) {
            // 1. Generar un nombre único para el temporal
            String tempName = generateTemp(); // por ejemplo "t1", "t2", etc.

            // 2. Obtener dirección temporal desde la clase Memoria
            int tempAddress = memoria.assignTempAddress(returnType);  // entrega dirección disponible y la incrementa

            // 3. Registrar temporal en la función actual
            dirFunc.addVariable(tempName, returnType, tempAddress);

            // 4. Generar cuádruplo de asignación del retorno a este temporal
            quadruples.add(new Quadruple("=", funcName, "", tempName, dirFunc, constTable));

            // 5. Apilar el resultado en operandStack y typeStack para que sea usado en expresiones
            stackContext.pushOperand(tempName, returnType);

            return tempName;
        }
        return null;
    }

    @Override
    public String visitArgs(WebbyParser.ArgsContext ctx) {
        argumentQueue.clear();
        argumentTypeQueue.clear();

        for (WebbyParser.ExpresionContext exprCtx : ctx.expresion()) {
            String result = visit(exprCtx); // Esto empuja a operandStack

            String argAddr = stackContext.popOperand(); // Dirección del argumento
            VarType argType = stackContext.popType();   // Tipo del argumento

            argumentQueue.add(argAddr);
            argumentTypeQueue.add(argType);
        }

        return null;
    }
}
