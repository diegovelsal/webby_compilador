package sem.funcs_vars;

import java.util.HashMap;
import java.util.List;

public class DirFunc {
    public static class Parameter {
        private final String name;
        private final VarType type;
        private final int address;

        public Parameter(String name, VarType type, int address) {
            this.name = name;
            this.type = type;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public VarType getType() {
            return type;
        }

        public int getAddress() {
            return address;
        }
    }
    public class FunctionInfo {
        private final VarTable varTable;
        private final java.util.List<Parameter> parameters;
        private int startQuad;

        public FunctionInfo() {
            this.varTable = new VarTable();
            this.parameters = new java.util.ArrayList<>();
        }

        public VarTable getVarTable() {
            return varTable;
        }

        public void addParameter(String name, VarType type, int address) {
            parameters.add(new Parameter(name, type, address));
            varTable.addVariable(name, type, address); 
        }

        public List<Parameter> getParameters() {
            return parameters;
        }

        public void setStartQuad(int quad) {
            this.startQuad = quad;
        }

        public int getStartQuad() {
            return startQuad;
        }
    }

    private final HashMap<String, FunctionInfo> functions;
    private final String globalScopeName;
    private String currentFunction;

    public DirFunc(String programName) {
        this.globalScopeName = programName;
        this.currentFunction = programName;
        this.functions = new HashMap<>();
        this.functions.put(programName, new FunctionInfo()); // Scope global
    }

    public boolean addFunction(String name) {
        if (!functions.containsKey(name)) {
            functions.put(name, new FunctionInfo());
            return true;
        }
        return false;
    }

    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    public void setCurrentFunction(String name) {
        if (!functions.containsKey(name)) {
            throw new IllegalArgumentException("Función '" + name + "' no existe.");
        }
        this.currentFunction = name;
    }

    public String getCurrentFunction() {
        return currentFunction;
    }

    public VarTable getCurrentVarTable() {
        return functions.get(currentFunction).getVarTable();
    }

    public void addVariable(String name, VarType type, int address) {
        getCurrentVarTable().addVariable(name, type, address);
    }

    public void addParameter(String name, VarType type, int address) {
        functions.get(currentFunction).addParameter(name, type, address);
    }

    public List<Parameter> getFunctionParameters(String funcName) {
        return functions.get(funcName).getParameters();
    }

    public void setFunctionStartQuad(String funcName, int quad) {
        functions.get(funcName).setStartQuad(quad);
    }

    public int getFunctionStartQuad(String funcName) {
        return functions.get(funcName).getStartQuad();
    }

    public boolean variableExists(String name) {
        return variableExistsInCurrentScope(name) || variableExistsInGlobal(name);
    }

    public boolean variableExistsInCurrentScope(String name) {
        return getCurrentVarTable().hasVariable(name);
    }

    public boolean variableExistsInGlobal(String name) {
        return functions.get(globalScopeName).getVarTable().hasVariable(name);
    }

    public boolean globalIsCurrent() {
        return currentFunction.equals(globalScopeName);
    }

    public VarType getVariableType(String name) {
        if (variableExistsInCurrentScope(name)) {
            return getCurrentVarTable().getVariableType(name);
        } else if (variableExistsInGlobal(name)) {
            return functions.get(globalScopeName).getVarTable().getVariableType(name);
        }
        throw new IllegalArgumentException("Variable '" + name + "' no encontrada.");
    }

    public int getVariableAddress(String name) {
        if (variableExistsInCurrentScope(name)) {
            return getCurrentVarTable().getVariableAddress(name);
        } else if (variableExistsInGlobal(name)) {
            return functions.get(globalScopeName).getVarTable().getVariableAddress(name);
        }
        throw new IllegalArgumentException("Variable '" + name + "' no encontrada.");
    }
}
