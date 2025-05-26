package vm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

import sem.funcs_vars.ConstTable;
import sem.funcs_vars.VarTable.VarInfo;

public class VirtualMemory {
    // Segmentos globales
    private final ArrayList<Integer> globalInt = new ArrayList<>();
    private final ArrayList<Float> globalFloat = new ArrayList<>();

    // Constantes
    private final ArrayList<Integer> constInt = new ArrayList<>();
    private final ArrayList<Float> constFloat = new ArrayList<>();
    private final ArrayList<String> constString = new ArrayList<>();

    // Segmentos locales
    private final Deque<MemoryFrame> memoryFrames = new ArrayDeque<>();
    private MemoryFrame pendingFrame = null;

    public void pushFrame(MemoryFrame mf) {
        memoryFrames.push(mf);
    }

    public void popFrame() {
        memoryFrames.pop();
    }

    private MemoryFrame currentMemoryFrame() {
        if (memoryFrames.isEmpty()) throw new IllegalStateException("No hay contexto de ejecución activo.");
        return memoryFrames.peek();
    }

    public MemoryFrame getPendingFrame() {
        if (pendingFrame == null)
            throw new IllegalStateException("No hay frame pendiente para parámetros.");
        return pendingFrame;
    }

    public Object getValue(int address) {
        int offset;
        if (address >= 1000 && address < 3000) {
            offset = address - 1000;
            return offset < globalInt.size() ? globalInt.get(offset) : null;
        } else if (address >= 3000 && address < 5000) {
            offset = address - 3000;
            return offset < globalFloat.size() ? globalFloat.get(offset) : null;
        } else if (address >= 5000 && address < 7000) {
            offset = address - 5000;
            return currentMemoryFrame().localInt.get(offset);
        } else if (address >= 7000 && address < 9000) {
            offset = address - 7000;
            return currentMemoryFrame().localFloat.get(offset);
        } else if (address >= 9000 && address < 11000) {
            offset = address - 9000;
            return currentMemoryFrame().tempInt.get(offset);
        } else if (address >= 11000 && address < 13000) {
            offset = address - 11000;
            return currentMemoryFrame().tempFloat.get(offset);
        } else if (address >= 13000 && address < 15000) {
            offset = address - 13000;
            return currentMemoryFrame().tempBool.get(offset);
        } else if (address >= 15000 && address < 17000) {
            offset = address - 15000;
            return offset < constInt.size() ? constInt.get(offset) : null;
        } else if (address >= 17000 && address < 19000) {
            offset = address - 17000;
            return offset < constFloat.size() ? constFloat.get(offset) : null;
        } else if (address >= 19000 && address < 21000) {
            offset = address - 19000;
            return offset < constString.size() ? constString.get(offset) : null;
        }

        throw new IllegalArgumentException("Dirección inválida: " + address);
    }

    public void setValue(int address, Object value) {
        int offset;
         if (address >= 1000 && address < 3000) {
            offset = address - 1000;
            while (globalInt.size() <= offset) globalInt.add(null);
            globalInt.set(offset, (Integer) value);

        } else if (address >= 3000 && address < 5000) {
            offset = address - 3000;
            while (globalFloat.size() <= offset) globalFloat.add(null);
            globalFloat.set(offset, (Float) value);

        } else if (address >= 5000 && address < 7000) {
            offset = address - 5000;
            currentMemoryFrame().localInt.set(offset, (Integer) value);

        } else if (address >= 7000 && address < 9000) {
            offset = address - 7000;
            currentMemoryFrame().localFloat.set(offset, (Float) value);

        } else if (address >= 9000 && address < 11000) {
            offset = address - 9000;
            currentMemoryFrame().tempInt.set(offset, (Integer) value);

        } else if (address >= 11000 && address < 13000) {
            offset = address - 11000;
            currentMemoryFrame().tempFloat.set(offset, (Float) value);

        } else if (address >= 13000 && address < 15000) {
            offset = address - 13000;
            currentMemoryFrame().tempBool.set(offset, (Boolean) value);

        } else {
            throw new IllegalArgumentException("No se puede modificar memoria constante o dirección inválida: " + address);
        }
    }

    public void loadConstants(ConstTable constTable) {
        Map<String, Integer> constMap = constTable.getConstantMap();
        for (String key : constMap.keySet()) {
            int address = constMap.get(key);

            if (address >= 15000 && address < 17000) {
                int offset = address - 15000;
                while (constInt.size() <= offset) constInt.add(null);
                constInt.set(offset, Integer.parseInt(key));

            } else if (address >= 17000 && address < 19000) {
                int offset = address - 17000;
                while (constFloat.size() <= offset) constFloat.add(null);
                constFloat.set(offset, Float.parseFloat(key));

            } else if (address >= 19000 && address < 21000) {
                int offset = address - 19000;
                while (constString.size() <= offset) constString.add(null);
                constString.set(offset, key);
            }
        } 
    }

    public void loadGlobalVariables(Map<String, VarInfo> variables) {
        int tempIntSize = 0;
        int tempFloatSize = 0;
        int tempBoolSize = 0;
        for (Map.Entry<String, VarInfo> entry : variables.entrySet()) {
            int address = entry.getValue().getAddress();
            if (address >= 1000 && address < 3000) {
                int index = address - 1000;
                while (globalInt.size() <= index) {
                    globalInt.add(0);
                }
            } else if (address >= 3000 && address < 5000) {
                int index = address - 3000;
                while (globalFloat.size() <= index) {
                    globalFloat.add(0.0f);
                }
            } else if (address >= 9000 && address < 11000) {
                tempIntSize = Math.max(tempIntSize, address - 9000 + 1);
            } else if (address >= 11000 && address < 13000) {
                tempFloatSize = Math.max(tempFloatSize, address - 11000 + 1);
            } else if (address >= 13000 && address < 15000) {
                tempBoolSize = Math.max(tempBoolSize, address - 13000 + 1);
            } else {
                throw new IllegalArgumentException("Dirección de variable global inválida: " + address);
            }
        }

        // Crear un MemoryFrame con el tamaño máximo de temporales
        MemoryFrame mf = new MemoryFrame(0, 0, tempIntSize, tempFloatSize, tempBoolSize);
        pushFrame(mf);
    }

    public void loadLocalTempVariables(Map<String, VarInfo> variables) {
        int localIntSize = 0;
        int localFloatSize = 0;
        int tempIntSize = 0;
        int tempFloatSize = 0;
        int tempBoolSize = 0;

        for (Map.Entry<String, VarInfo> entry : variables.entrySet()) {
            int address = entry.getValue().getAddress();

            if (address >= 5000 && address < 7000) {
                localIntSize = Math.max(localIntSize, address - 5000 + 1);
            } else if (address >= 7000 && address < 9000) {
                localFloatSize = Math.max(localFloatSize, address - 7000 + 1);
            } else if (address >= 9000 && address < 11000) {
                tempIntSize = Math.max(tempIntSize, address - 9000 + 1);
            } else if (address >= 11000 && address < 13000) {
                tempFloatSize = Math.max(tempFloatSize, address - 11000 + 1);
            } else if (address >= 13000 && address < 15000) {
                tempBoolSize = Math.max(tempBoolSize, address - 13000 + 1);
            } else {
                throw new IllegalArgumentException("Dirección de variable local/temporal inválida: " + address);
            }
        }

        pendingFrame = new MemoryFrame(localIntSize, localFloatSize, tempIntSize, tempFloatSize, tempBoolSize);
    }

    public void commitPendingFrame() {
        if (pendingFrame == null)
            throw new IllegalStateException("No hay frame pendiente para activar.");
        pushFrame(pendingFrame);
        pendingFrame = null;
    }

}

