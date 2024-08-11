package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final double SIZE_MULTIPLIER = 1.5;
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements = {};
    private int size = 0;

    public ArrayList(int capacity) {
        if (capacity > 0) {
            elements = new Object[capacity];
        } else if (capacity == 0) {
            elements = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Invalid capacity");
        }
    }

    public ArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T value) {
        if (size == elements.length) {
            elements = grow();
        }
        elements[size] = value;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayListIndexOutOfBoundsException("Index out of bounds");
        }
        if (size == this.elements.length) {
            elements = grow();
        }
        if (index == elements.length) {
            add(value);
        } else {
            Object[] newData = elements.clone();
            System.arraycopy(newData, 0, elements, 0, index);
            elements[index] = value;
            System.arraycopy(newData, index, elements, index + 1, newData.length - index - 1);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] newData = new Object[list.size()];
        for (int i = 0; i < newData.length; i++) {
            newData[i] = list.get(i);
        }
        elements = Arrays.copyOf(elements, (size + newData.length));
        System.arraycopy(newData, 0, elements, size, newData.length);
        size += newData.length;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return (T) elements[index];
    }

    @Override
    public void set(T value, int index) {
        rangeCheck(index);
        elements[index] = value;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        Object[] newData = elements.clone();
        T oldValue = (T) elements[index];
        System.arraycopy(newData, index + 1, elements, index, newData.length - index - 1);
        size--;
        return oldValue;
    }

    @Override
    public T remove(T element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if ((elements[i]) == null) {
                    remove(i);
                    return null;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(elements[i])) {
                remove(i);
                return element;
            }
        }
        throw new NoSuchElementException("Element " + element + " does not exist");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException("Index out of bounds");
        }
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, (int) (elements.length * SIZE_MULTIPLIER));
    }
}