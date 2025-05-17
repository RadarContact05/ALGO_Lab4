import java.util.*;

public class LinearProbingHashMap<T1, T2> {
    private static final int INIT_CAPACITY = 4;
    private int N;
    private int M;
    private T1[] keys;
    private T2[] vals;

    public LinearProbingHashMap() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashMap (int M) {
        this.M = M;
        this.N = 0;
        this.keys = (T1[]) new Object[M];
        this.vals = (T2[]) new Object[M];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public boolean contains(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        return get(key) != null;
    }

    public T2 get(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if(keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public void put(T1 key, T2 val) {
        if (key == null) throw new IllegalArgumentException ("argument is null");
        if (val == null) {
            delete(key);
            return;
        }

        if (N >= M/2) resize(2 * M);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public void delete(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        if (!contains(key)) return;

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }

        keys[i] = null;
        vals[i] = null;
        N--;

        i = (i + 1) % M;
        while (keys[i] != null) {
            T1 k = keys[i];
            T2 v = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(k, v);  
            i = (i + 1) % M;
        }
        if (N > 0 && N <= M/8) resize(M/2);
    }

    public Iterable<T1> keys() {
        List<T1> list = new ArrayList<>();
        for (T1 k : keys) {
            if (k != null) list.add(k);
        }
        return list;
    }

    private int hash(T1 key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int capacity) {
        LinearProbingHashMap<T1, T2> temp = new LinearProbingHashMap<>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        this.keys = temp.keys;
        this.vals = temp.vals;
        this.M = temp.M;
    }

    public Iterable<T1> keys(Comparator<T2> cmp) {
        throw new UnsupportedOperationException();
    }
}