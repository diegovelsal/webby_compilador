package mem;

import sem.funcs_vars.VarType;

import java.util.HashMap;
import java.util.Map;

public class MemoryManager {

    // Bases de cada segmento
    private final int GLOBAL_INT_BASE = 1000;
    private final int GLOBAL_FLOAT_BASE = 2000;

    private final int LOCAL_INT_BASE = 3000;
    private final int LOCAL_FLOAT_BASE = 4000;

    private final int TEMP_INT_BASE = 5000;
    private final int TEMP_FLOAT_BASE = 6000;
    private final int TEMP_BOOL_BASE = 7000;

    private final int CONST_INT_BASE = 8000;
    private final int CONST_FLOAT_BASE = 9000;
    private final int CONST_STRING_BASE = 10000;

    // Contadores (inician en 0)
    private int globalIntCounter = 0;
    private int globalFloatCounter = 0;

    private int localIntCounter = 0;
    private int localFloatCounter = 0;

    private int tempIntCounter = 0;
    private int tempFloatCounter = 0;
    private int tempBoolCounter = 0;

    private int constIntCounter = 0;
    private int constFloatCounter = 0;
    private int constStringCounter = 0;

    // Estructuras de memoria
    private final HashMap<String, Integer> globalMemory = new HashMap<>();
    private final HashMap<String, HashMap<String, Integer>> localMemory = new HashMap<>();
    private final HashMap<String, Integer> tempMemory = new HashMap<>();
    private final HashMap<String, Integer> constMemory = new HashMap<>();

    // Global
    public int assignGlobal(String varName, VarType type) {
        int address;
        switch (type) {
            case INT -> address = GLOBAL_INT_BASE + globalIntCounter++;
            case FLOAT -> address = GLOBAL_FLOAT_BASE + globalFloatCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para global: " + type);
        }
        globalMemory.put(varName, address);
        return address;
    }

    // Local
    public int assignLocal(String functionName, String varName, VarType type) {
        localMemory.putIfAbsent(functionName, new HashMap<>());
        int address;
        switch (type) {
            case INT -> address = LOCAL_INT_BASE + localIntCounter++;
            case FLOAT -> address = LOCAL_FLOAT_BASE + localFloatCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para local: " + type);
        }
        localMemory.get(functionName).put(varName, address);
        return address;
    }

    // Temporal
    public int assignTemp(String tempName, VarType type) {
        int address;
        switch (type) {
            case INT -> address = TEMP_INT_BASE + tempIntCounter++;
            case FLOAT -> address = TEMP_FLOAT_BASE + tempFloatCounter++;
            case BOOL -> address = TEMP_BOOL_BASE + tempBoolCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para temporal: " + type);
        }
        tempMemory.put(tempName, address);
        return address;
    }

    // Constantes
    public int assignConst(String value, VarType type) {
        if (constMemory.containsKey(value)) {
            return constMemory.get(value);
        }

        int address;
        switch (type) {
            case INT -> address = CONST_INT_BASE + constIntCounter++;
            case FLOAT -> address = CONST_FLOAT_BASE + constFloatCounter++;
            case STRING -> address = CONST_STRING_BASE + constStringCounter++;
            default -> throw new IllegalArgumentException("Tipo de constante inválido: " + type);
        }

        constMemory.put(value, address);
        return address;
    }

    // Getters para acceder a las memorias si lo necesitas
    public HashMap<String, Integer> getGlobalMemory() {
        return globalMemory;
    }

    public HashMap<String, HashMap<String, Integer>> getLocalMemory() {
        return localMemory;
    }

    public HashMap<String, Integer> getTempMemory() {
        return tempMemory;
    }

    public HashMap<String, Integer> getConstMemory() {
        return constMemory;
    }

    public Integer getAddress(String name, String currentFunction) {
        // Primero temporales
        if (tempMemory.containsKey(name)) {
            return tempMemory.get(name);
        }
        // Luego locales de la función actual
        if (currentFunction != null && localMemory.containsKey(currentFunction)) {
            Map<String, Integer> funcLocals = localMemory.get(currentFunction);
            if (funcLocals.containsKey(name)) {
                return funcLocals.get(name);
            }
        }
        // Luego globales
        if (globalMemory.containsKey(name)) {
            return globalMemory.get(name);
        }
        // Finalmente constantes
        if (constMemory.containsKey(name)) {
            return constMemory.get(name);
        }
        return -1; // No se encontró
    }

    public boolean isConstantRegistered(String value) {
        if (constMemory.containsKey(value)) return true;
        return false;
    }


    // Reset de locales y temporales al entrar a una nueva función
    public void resetLocalAndTemp() {
        localIntCounter = 0;
        localFloatCounter = 0;

        tempIntCounter = 0;
        tempFloatCounter = 0;
        tempBoolCounter = 0;

        tempMemory.clear();
    }
}
