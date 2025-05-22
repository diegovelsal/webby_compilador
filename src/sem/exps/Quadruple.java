package sem.exps;

import java.util.Map;

import mem.MemoryManager;

public class Quadruple {
    private String operator;
    private String leftOperand;
    private String rightOperand;
    private String result;

    private static final Map<String, Integer> OPERATOR_CODES = Map.ofEntries(
        Map.entry("+", 1),
        Map.entry("-", 2),
        Map.entry("*", 3),
        Map.entry("/", 4),
        Map.entry("=", 5),
        Map.entry("!=", 6),
        Map.entry("<", 7),
        Map.entry(">", 8),
        Map.entry("PRINT", 9),
        Map.entry("GOTO", 10),
        Map.entry("GOTOF", 11)
        // Agrega más si es necesario
    );

    public Quadruple(String operator, String leftOperand, String rightOperand, String result) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = result;
    }

    @Override
    public String toString() {
        return "(" + operator + ", " + leftOperand + ", " + rightOperand + ", " + result + ")";
    }

    public String toMemoryString(MemoryManager memoryManager, String currentFunction) {
        String opCode = getOperatorCode(operator);
        String left = resolveOperandAddress(leftOperand, memoryManager, currentFunction);
        String right = resolveOperandAddress(rightOperand, memoryManager, currentFunction);
        String res = resolveOperandAddress(result, memoryManager, currentFunction);
        return "(" + opCode + ", " + left + ", " + right + ", " + res + ")";
    }

    private String resolveOperandAddress(String operand, MemoryManager memoryManager, String currentFunction) {
        if (operand == null || operand.isEmpty()) return "";

        if (operand.startsWith("#")) {
            return operand.substring(1);  // devuelve directamente el número como string
        }

        try {
            // Si es un número, revisa si está registrado como constante
            Double.parseDouble(operand);
            Integer address = memoryManager.getAddress(operand, currentFunction);
            if (address != null && address != -1) {
                return address.toString();
            } else {
                return operand; // fallback si no está registrado
            }
        } catch (NumberFormatException ignored) {
            // No es número literal, busca como variable/temporal
        }

        Integer address = memoryManager.getAddress(operand, currentFunction);
        return (address != null && address != -1) ? address.toString() : operand;
    }

    private String getOperatorCode(String op) {
        Integer code = OPERATOR_CODES.get(op);
        return (code != null) ? code.toString() : op;  // Si no se reconoce, regresa el símbolo original
    }

    // Getters si los necesitas
    public String getOperator() { return operator; }
    public String getLeftOperand() { return leftOperand; }
    public String getRightOperand() { return rightOperand; }
    public String getResult() { return result; }
}
