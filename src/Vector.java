public interface Vector {
    int size();
    void add(Object obj);
    void add(int index, Object obj);
    void remove(int index);
    void set(int index, Object obj);
    Object get(int index);
}
