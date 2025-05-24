package mem;

import sem.funcs_vars.VarType;

public class MemoryManager {

    // Bases de cada segmento
    private final int GLOBAL_INT_BASE = 1000;
    private final int GLOBAL_FLOAT_BASE = 3000;

    private final int LOCAL_INT_BASE = 5000;
    private final int LOCAL_FLOAT_BASE = 7000;

    private final int TEMP_INT_BASE = 9000;
    private final int TEMP_FLOAT_BASE = 11000;
    private final int TEMP_BOOL_BASE = 13000;

    private final int CONST_INT_BASE = 15000;
    private final int CONST_FLOAT_BASE = 17000;
    private final int CONST_STRING_BASE = 19000;

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

    // Global
    public int assignGlobalAddress(String varName, VarType type) {
        return switch (type) {
            case INT -> GLOBAL_INT_BASE + globalIntCounter++;
            case FLOAT -> GLOBAL_FLOAT_BASE + globalFloatCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para global: " + type);
        };
    }

    // Local
    public int assignLocalAddress(VarType type) {
        return switch (type) {
            case INT -> LOCAL_INT_BASE + localIntCounter++;
            case FLOAT -> LOCAL_FLOAT_BASE + localFloatCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para variable local: " + type);
        };
    }

    // Temporal
    public int assignTempAddress(VarType type) {
        return switch (type) {
            case INT -> TEMP_INT_BASE + tempIntCounter++;
            case FLOAT -> TEMP_FLOAT_BASE + tempFloatCounter++;
            case BOOL -> TEMP_BOOL_BASE + tempBoolCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para temporal: " + type);
        };
    }

    // Constantes
    public int assignConstAddress(VarType type) {
        return switch (type) {
            case INT -> CONST_INT_BASE + constIntCounter++;
            case FLOAT -> CONST_FLOAT_BASE + constFloatCounter++;
            case STRING -> CONST_STRING_BASE + constStringCounter++;
            default -> throw new IllegalArgumentException("Tipo inválido para constante: " + type);
        };
    }

    // Reset de locales y temporales al entrar a una nueva función
    public void resetLocalAndTemp() {
        localIntCounter = 0;
        localFloatCounter = 0;

        tempIntCounter = 0;
        tempFloatCounter = 0;
        tempBoolCounter = 0;
    }
}
