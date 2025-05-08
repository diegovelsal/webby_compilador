package sem;

import java.util.HashMap;

public class VarTable {
    private static class VarInfo {
        VarType type;
        Object value;

        VarInfo(VarType type) {
            this.type = type;
            this.value = null;
        }
    }

    private HashMap<String, VarInfo> variables;

    public VarTable() {
        this.variables = new HashMap<>();
    }

    // Agregar una variable a la tabla
    public void addVariable(String name, VarType type) {
        if (!variables.containsKey(name)) {
            variables.put(name, new VarInfo(type));
        } else {
            throw new IllegalArgumentException("La variable '" + name + "' ya ha sido declarada.");
        }
    }

    // Obtener el tipo de una variable
    public VarType getVariableType(String name) {
        VarInfo info = variables.get(name);
        return (info != null) ? info.type : null;
    }

    // Obtener el valor de una variable
    public Object getVariableValue(String name) {
        VarInfo info = variables.get(name);
        return (info != null) ? info.value : null;
    }

    // Establecer el valor de una variable
    public void setVariableValue(String name, Object value) {
        VarInfo info = variables.get(name);
        if (info != null) {
            info.value = value;
        } else {
            throw new IllegalArgumentException("La variable '" + name + "' no ha sido declarada.");
        }
    }

    // Verificar existencia
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }
}
