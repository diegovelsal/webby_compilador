package sem.funcs_vars;

import java.util.HashMap;

public class VarTable {

    public static class VarInfo {
        private final VarType type;
        private final int address;

        public VarInfo(VarType type, int address) {
            this.type = type;
            this.address = address;
        }

        public VarType getType() {
            return type;
        }

        public int getAddress() {
            return address;
        }
    }

    private final HashMap<String, VarInfo> variables;

    public VarTable() {
        this.variables = new HashMap<>();
    }

    public void addVariable(String name, VarType type, int address) {
        if (variables.containsKey(name)) {
            throw new IllegalArgumentException("La variable '" + name + "' ya ha sido declarada.");
        }
        variables.put(name, new VarInfo(type, address));
    }

    public VarType getVariableType(String name) {
        VarInfo info = variables.get(name);
        return (info != null) ? info.getType() : null;
    }

    public int getVariableAddress(String name) {
        VarInfo info = variables.get(name);
        if (info == null) {
            throw new IllegalArgumentException("La variable '" + name + "' no ha sido declarada.");
        }
        return info.getAddress();
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public HashMap<String, VarInfo> getVariables() {
        return variables;
    }
}
