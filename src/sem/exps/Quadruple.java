package sem.exps;

public class Quadruple {
    private String operator;
    private String leftOperand;
    private String rightOperand;
    private String result;

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

    // Getters (si los necesitas)
    public String getOperator() { return operator; }
    public String getLeftOperand() { return leftOperand; }
    public String getRightOperand() { return rightOperand; }
    public String getResult() { return result; }
}
