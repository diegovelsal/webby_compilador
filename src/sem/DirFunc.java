package sem;

import java.util.HashMap;

public class DirFunc {
    private HashMap<String, VarTable> functions;
    private String globalScopeName;
    private String currentFunction;

    public DirFunc(String programName) {
        this.globalScopeName = programName;
        this.currentFunction = programName;
        this.functions = new HashMap<>();
        this.functions.put(programName, new VarTable()); // Scope global
    }

    public boolean addFunction(String name) {
        if (!functions.containsKey(name)) {
            functions.put(name, new VarTable());
            return true; // Función agregada exitosamente
        } else return false; // La función ya existe
    }

    public boolean hasFunction(String name) {
        return functions.containsKey(name);
    }

    public VarTable getFunctionTable(String name) {
        return functions.get(name);
    }

    public void setCurrentFunction(String name) {
        if (functions.containsKey(name)) {
            this.currentFunction = name;
        } else {
            throw new IllegalArgumentException("La función '" + name + "' no existe.");
        }
    }

    public String getCurrentFunction() {
        return currentFunction;
    }

    public String getGlobalScopeName() {
        return globalScopeName;
    }

    // Agregar variable a la función actual
    public void addVariable(String varName, VarType type) {
        getFunctionTable(currentFunction).addVariable(varName, type);
    }

    // Verificación jerárquica: primero local, luego global
    public boolean variableLocalExists(String varName) {
        return functions.get(currentFunction).hasVariable(varName);
    }

    public boolean variableGlobalExists(String varName) {
        return functions.get(globalScopeName).hasVariable(varName);
    }

    public VarType getVariableType(String varName) {
        VarTable local = getFunctionTable(currentFunction);
        if (local.hasVariable(varName)) return local.getVariableType(varName);

        VarTable global = getFunctionTable(globalScopeName);
        if (global.hasVariable(varName)) return global.getVariableType(varName);

        throw new IllegalArgumentException("Variable '" + varName + "' no encontrada.");
    }
}
