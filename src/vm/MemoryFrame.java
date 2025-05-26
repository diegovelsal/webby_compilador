package vm;

import java.util.ArrayList;

public class MemoryFrame {
    public final ArrayList<Integer> localInt = new ArrayList<>();
    public final ArrayList<Float> localFloat = new ArrayList<>();

    public final ArrayList<Integer> tempInt = new ArrayList<>();
    public final ArrayList<Float> tempFloat = new ArrayList<>();
    public final ArrayList<Boolean> tempBool = new ArrayList<>();

    public MemoryFrame(int localIntSize, int localFloatSize, int tempIntSize, int tempFloatSize, int tempBoolSize) {
        resize(localInt, localIntSize);
        resize(localFloat, localFloatSize);
        resize(tempInt, tempIntSize);
        resize(tempFloat, tempFloatSize);
        resize(tempBool, tempBoolSize);
    }

    public void setValue(int address, Object value) {
        if (address >= 5000 && address < 7000) {
            int index = address - 5000;
            ensureSize(localInt, index);
            localInt.set(index, (Integer) value);
        } else if (address >= 7000 && address < 9000) {
            int index = address - 7000;
            ensureSize(localFloat, index);
            localFloat.set(index, (Float) value);
        } else if (address >= 9000 && address < 11000) {
            int index = address - 9000;
            ensureSize(tempInt, index);
            tempInt.set(index, (Integer) value);
        } else if (address >= 11000 && address < 13000) {
            int index = address - 11000;
            ensureSize(tempFloat, index);
            tempFloat.set(index, (Float) value);
        } else if (address >= 13000 && address < 15000) {
            int index = address - 13000;
            ensureSize(tempBool, index);
            tempBool.set(index, (Boolean) value);
        } else {
            throw new IllegalArgumentException("Dirección inválida en setValue: " + address);
        }
    }

    // Helper privado para garantizar tamaño de lista
    private <T> void ensureSize(ArrayList<T> list, int index) {
        while (list.size() <= index) {
            list.add(null);
        }
    }

    private <T> void resize(ArrayList<T> list, int size) {
        for (int i = 0; i < size; i++) list.add(null);
    }
}
