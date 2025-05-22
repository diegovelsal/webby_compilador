package sem.exps;

import java.util.HashMap;
import java.util.Map;
import sem.funcs_vars.VarType;

public class CuboSemantico {
    private final Map<String, Map<VarType, Map<VarType, VarType>>> cube;

    public CuboSemantico() {
        cube = new HashMap<>();

        // Operaciones aritméticas: + - * /
        for (String op : new String[]{"+", "-", "*", "/"}) {
            addEntry(op, VarType.INT, VarType.INT, VarType.INT);
            addEntry(op, VarType.INT, VarType.FLOAT, VarType.FLOAT);
            addEntry(op, VarType.FLOAT, VarType.INT, VarType.FLOAT);
            addEntry(op, VarType.FLOAT, VarType.FLOAT, VarType.FLOAT);
        }

        // Operaciones relacionales: < > !=
        for (String op : new String[]{"<", ">", "!="}) {
            addEntry(op, VarType.INT, VarType.INT, VarType.BOOL);
            addEntry(op, VarType.INT, VarType.FLOAT, VarType.BOOL);
            addEntry(op, VarType.FLOAT, VarType.INT, VarType.BOOL);
            addEntry(op, VarType.FLOAT, VarType.FLOAT, VarType.BOOL);
        }

        // Asignación =
        addEntry("=", VarType.INT, VarType.INT, VarType.INT);     // válida
        addEntry("=", VarType.FLOAT, VarType.FLOAT, VarType.FLOAT); // válida
        addEntry("=", VarType.INT, VarType.FLOAT, null);         // error
        addEntry("=", VarType.FLOAT, VarType.INT, null);         // depende de la semántica, pero aquí asumimos error también
    }

    private void addEntry(String op, VarType left, VarType right, VarType result) {
        cube
            .computeIfAbsent(op, k -> new HashMap<>())
            .computeIfAbsent(left, k -> new HashMap<>())
            .put(right, result);
    }

    public VarType getResultType(String op, VarType left, VarType right) {
        return cube.getOrDefault(op, new HashMap<>())
                   .getOrDefault(left, new HashMap<>())
                   .getOrDefault(right, null);
    }
}

