package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static java.lang.System.arraycopy;

public class ArrayList<T> implements List<T> {
    private static final double SIZE_MULTIPLIER = 1.5;
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int size = 0;

    public ArrayList(int capacity) {
        if (capacity > 0) {
            elements = (T[]) new Object[capacity];
        } else if (capacity == 0) {
            elements = (T[]) new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Invalid capacity");
        }
    }

    public ArrayList() {
        elements = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T value) {
        growIfFull();
        elements[size] = value;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            rangeCheck(index);
            growIfFull();
            arraycopy(elements, index, elements, index + 1, elements.length - index - 1);
            elements[index] = value;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public void set(T value, int index) {
        rangeCheck(index);
        elements[index] = value;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        T oldValue = elements[index];
        arraycopy(elements, index + 1, elements, index, elements.length - index - 1);
        size--;
        return oldValue;
    }

    @Override
    public T remove(T element) {
        for (int i = 0; i < size; i++) {
            if (element == elements[i] || element != null && element.equals(elements[i])) {
                return remove(i);
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

    private void growIfFull() {
        if (size == elements.length) {
            elements = Arrays.copyOf(this.elements, (int) (elements.length * SIZE_MULTIPLIER));
        }
    }
}
