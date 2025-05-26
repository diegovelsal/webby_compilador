package sem.funcs_vars;

import java.util.HashMap;
import java.util.Map;

import mem.MemoryManager;

public class ConstTable {
    private final Map<String, Integer> constantMap;
    private final MemoryManager memoryManager;

    public ConstTable(MemoryManager memoryManager) {
        this.constantMap = new HashMap<>();
        this.memoryManager = memoryManager;
    }

    public boolean hasConstant(String value) {
        return constantMap.containsKey(value);
    }

    public int getAddress(String value) {
        if (!hasConstant(value)) {
            throw new IllegalArgumentException("Constante no registrada: " + value);
        }
        return constantMap.get(value);
    }

    public int addConstant(String value, VarType type) {
        if (!hasConstant(value)) {
            int address = memoryManager.assignConstAddress(type);
            constantMap.put(value, address);
        }
        return constantMap.get(value); // Devuelve la dirección siempre
    }

    public Map<String, Integer> getConstantMap() {
        return constantMap;
    }
}
