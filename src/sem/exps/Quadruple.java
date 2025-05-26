package sem.exps;

import java.util.Map;

import sem.funcs_vars.ConstTable;
import sem.funcs_vars.DirFunc;

public class Quadruple {
    private String operator;
    private String leftOperand;
    private String rightOperand;
    private String result;
    private int operatorCode;
    private int leftAddress;
    private int rightAddress;
    private int resultAddress;

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
        Map.entry("GOTOF", 11),
        Map.entry("ERA", 12),
        Map.entry("PARAM", 13),
        Map.entry("GOSUB", 14),
        Map.entry("ENDFUNC", 15),
        Map.entry("ENDPROG", 16)
    );

    public Quadruple(String operator, String leftOperand, String rightOperand, String result, DirFunc dirFunc, ConstTable constTable) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.result = result;
        this.operatorCode = OPERATOR_CODES.get(operator);
        this.leftAddress = resolveOperandAddress(leftOperand, dirFunc, constTable);
        this.rightAddress = resolveOperandAddress(rightOperand, dirFunc, constTable);
        this.resultAddress = resolveOperandAddress(result, dirFunc, constTable);
    }

    @Override
    public String toString() {
        return "(" + operator + ", " + leftOperand + ", " + rightOperand + ", " + result + ")";
    }

    public String toMemoryString() {
        String arg1 = (leftAddress == 0) ? leftOperand : String.valueOf(leftAddress);
        String arg2 = (rightAddress == 0) ? rightOperand : String.valueOf(rightAddress);
        String arg3 = (resultAddress == 0) ? result : String.valueOf(resultAddress);
        
        return "(" + operatorCode + ", " + arg1 + ", " + arg2 + ", " + arg3 + ")";
    }

    private int resolveOperandAddress(String operand, DirFunc dirFunc, ConstTable constTable) {
        if (operand == null || operand.isEmpty()) return 0;

        if (operand.startsWith("#")) {
            return Integer.parseInt(operand.substring(1));  // constante numérica literal
        }

        // Checar si es una constante string
        if (constTable.hasConstant(operand)) {
            return constTable.getAddress(operand);
        }

        // Buscar en el directorio de funciones
        if (dirFunc.variableExists(operand)) {
            return dirFunc.getVariableAddress(operand);
        }

        return 0;  // Si no se encuentra, regresa el símbolo original
    }

    // Getters si los necesitas
    public String getOperator() { return operator; }
    public String getLeftOperand() { return leftOperand; }
    public String getRightOperand() { return rightOperand; }
    public String getResult() { return result; }
    public int getOperatorCode() { return operatorCode; }
    public int getLeftAddress() { return leftAddress; }
    public int getRightAddress() { return rightAddress; }
    public int getResultAddress() { return resultAddress; }

}
