package vm;

import sem.funcs_vars.DirFunc;
import sem.funcs_vars.ConstTable;
import sem.exps.Quadruple;

import java.util.List;
import java.util.Stack;

public class VirtualMachine {
    private final List<Quadruple> quads;
    private final DirFunc dirFunc;
    private final ConstTable constTable;
    private final VirtualMemory memory;
    private final Stack<Integer> callStack = new Stack<>();

    private String currentFunctionName = "";
    private int instructionPointer = 0;
    private int paramCounter = 0;

    public VirtualMachine(List<Quadruple> quads, DirFunc dirFunc, ConstTable constTable) {
        this.quads = quads;
        this.dirFunc = dirFunc;
        this.constTable = constTable;
        this.memory = new VirtualMemory();
    }

    public void execute() {
        memory.loadConstants(constTable);
        memory.loadGlobalVariables(dirFunc.getGlobalVarTable().getVariables());

        while (instructionPointer < quads.size()) {
            Quadruple quad = quads.get(instructionPointer);
            int opCode = quad.getOperatorCode();
            int leftAddr = quad.getLeftAddress();
            int rightAddr = quad.getRightAddress();
            int resAddr = quad.getResultAddress();
            String left = quad.getLeftOperand();
            String res = quad.getResult();

            switch (opCode) {
                case 1: // +
                    executeArithmetic(leftAddr, rightAddr, resAddr, "+");
                    break;
                case 2: // -
                    executeArithmetic(leftAddr, rightAddr, resAddr, "-");
                    break;
                case 3: // *
                    executeArithmetic(leftAddr, rightAddr, resAddr, "*");
                    break;
                case 4: // /
                    executeArithmetic(leftAddr, rightAddr, resAddr, "/");
                    break;
                case 5: // =
                    if (leftAddr == 0) {
                        // Asignación del valor retornado por una función
                        int returnAddr = dirFunc.getReturnAddress(left);
                        Object returnValue = memory.getValue(returnAddr);
                        memory.popFrame();
                        memory.setValue(resAddr, returnValue);
                    } else {
                        memory.setValue(resAddr, memory.getValue(leftAddr));
                    }
                    break;

                case 6: // !=
                    memory.setValue(resAddr, !memory.getValue(leftAddr).equals(memory.getValue(rightAddr)));
                    break;
                case 7: // <
                    memory.setValue(resAddr, compareValues(memory.getValue(leftAddr), memory.getValue(rightAddr)) < 0);
                    break;
                case 8: // >
                    memory.setValue(resAddr, compareValues(memory.getValue(leftAddr), memory.getValue(rightAddr)) > 0);
                    break;
                case 9: // PRINT
                    System.out.print(memory.getValue(resAddr));
                    break;
                case 10: // PRINTLN
                    System.out.println(memory.getValue(resAddr));
                    break;
                case 11: // GOTO
                    instructionPointer = resAddr;
                    continue;
                case 12: // GOTOF
                    Boolean condition = (Boolean) memory.getValue(leftAddr);
                    if (!condition) {
                        instructionPointer = resAddr;
                        continue;
                    }
                    break;
                case 13: // ERA
                    currentFunctionName = res;  // nombre de la función objetivo
                    paramCounter = 0;
                    memory.loadLocalTempVariables(dirFunc.getVarTable(res).getVariables());
                    break;

                case 14: // PARAM
                    Object paramVal = memory.getValue(leftAddr);
                    int paramAddr = dirFunc.getParameterAddress(currentFunctionName, paramCounter);
                    memory.getPendingFrame().setValue(paramAddr, paramVal);
                    paramCounter++;
                    break;

                case 15: // GOSUB
                    callStack.push(instructionPointer + 1);
                    memory.commitPendingFrame(); // ahora sí lo hace activo
                    instructionPointer = resAddr;
                    continue;

                case 16: // RETURN
                    Object returnValue = memory.getValue(resAddr);
                    int returnAddr = dirFunc.getReturnAddress(currentFunctionName);
                    memory.setValue(returnAddr, returnValue);
                    instructionPointer = callStack.pop();
                    continue;
                    
                case 17: // ENDFUNC
                    memory.popFrame();
                    instructionPointer = callStack.pop();
                    continue;
                case 18: // ENDPROG
                    return;
                // Agrega otros casos según necesites
                default:
                    throw new RuntimeException("Opcode no implementado: " + opCode);
            }

            instructionPointer++;
        }
    }

    private void executeArithmetic(int leftAddr, int rightAddr, int resultAddr, String op) {
        Object leftVal = memory.getValue(leftAddr);
        Object rightVal = memory.getValue(rightAddr);

        Object result;
        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            int l = (Integer) leftVal;
            int r = (Integer) rightVal;
            result = switch (op) {
                case "+" -> l + r;
                case "-" -> l - r;
                case "*" -> l * r;
                case "/" -> l / r;
                default -> throw new RuntimeException("Operación aritmética inválida: " + op);
            };
        } else if (leftVal instanceof Float || rightVal instanceof Float) {
            float l = ((Number) leftVal).floatValue();
            float r = ((Number) rightVal).floatValue();
            result = switch (op) {
                case "+" -> l + r;
                case "-" -> l - r;
                case "*" -> l * r;
                case "/" -> l / r;
                default -> throw new RuntimeException("Operación aritmética inválida: " + op);
            };
        } else {
            throw new RuntimeException("Tipos incompatibles para operación aritmética");
        }

        memory.setValue(resultAddr, result);
    }

    private int compareValues(Object leftVal, Object rightVal) {
        if (leftVal instanceof Integer && rightVal instanceof Integer) {
            return Integer.compare((Integer) leftVal, (Integer) rightVal);
        } else if (leftVal instanceof Float || rightVal instanceof Float) {
            float l = ((Number) leftVal).floatValue();
            float r = ((Number) rightVal).floatValue();
            return Float.compare(l, r);
        } else {
            throw new RuntimeException("Tipos incompatibles para comparación");
        }
    }
}
