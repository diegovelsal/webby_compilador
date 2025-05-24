package sem.exps;

import java.util.Map;

import sem.funcs_vars.ConstTable;
import sem.funcs_vars.DirFunc;

public class Quadruple {
    private String operator;
    private String leftOperand;
    private String rightOperand;
    private String result;
    private String leftAddress;
    private String rightAddress;
    private String resultAddress;

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

    public Quadruple(String operator, String leftOperand, String rightOperand, String result, DirFunc dirFunc, ConstTable constTable) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = result;
        this.leftAddress = resolveOperandAddress(leftOperand, dirFunc, constTable);
        this.rightAddress = resolveOperandAddress(rightOperand, dirFunc, constTable);
        this.resultAddress = resolveOperandAddress(result, dirFunc, constTable);
    }

    @Override
    public String toString() {
        return "(" + operator + ", " + leftOperand + ", " + rightOperand + ", " + result + ")";
    }

    public String toMemoryString() {
        return "(" + getOperatorCode(operator) + ", " + leftAddress + ", " + rightAddress + ", " + resultAddress + ")";
    }

    private String resolveOperandAddress(String operand, DirFunc dirFunc, ConstTable constTable) {
        if (operand == null || operand.isEmpty()) return "";

        if (operand.startsWith("#")) {
            return operand.substring(1);  // constante numérica literal
        }

        // Checar si es una constante string
        if (constTable.hasConstant(operand)) {
            Integer address = constTable.getAddress(operand);
            return address.toString();
        }

        // Buscar en el directorio de funciones
        Integer address = dirFunc.getVariableAddress(operand);
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
