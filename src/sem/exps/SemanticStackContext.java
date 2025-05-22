package sem.exps;

import sem.funcs_vars.VarType;
import java.util.Stack;

public class SemanticStackContext {
    private Stack<String> operandStack;
    private Stack<String> operatorStack;
    private Stack<VarType> typeStack;

    public SemanticStackContext() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
        typeStack = new Stack<>();
    }

    // Métodos de acceso y utilidad
    public void pushOperand(String op, VarType type) {
        operandStack.push(op);
        typeStack.push(type);
    }

    public String popOperand() {
        return operandStack.pop();
    }

    public VarType popType() {
        return this.typeStack.pop();
    }

    public void pushOperator(String op) {
        operatorStack.push(op);
    }

    public String popOperator() {
        return operatorStack.pop();
    }

    public VarType peekType() {
        return this.typeStack.peek();
    }

    public String peekOperand() {
        return this.operandStack.peek();
    }

    public String peekOperator() {
        return operatorStack.peek();
    }

    public boolean operatorStackIsEmpty() {
        return operatorStack.isEmpty();
    }

    public boolean operandStackIsEmpty() {
        return operandStack.isEmpty();
    }
}

