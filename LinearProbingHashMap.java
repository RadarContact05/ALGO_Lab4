import java.util.*;

public class LinearProbingHashMap<T1, T2> {
    private static int initCap = 4;     // inital capacity of the HasMap 
    private int N;              // number of key-value pairs
    private int M;              // size of linear-probing table
    private T1[] keys;          // the keys
    private T2[] vals;          // the values

    public LinearProbingHashMap() {
        this(initCap);          // construct a empty Hashmap table with initCap capacity
    }

    // constructs an empty Hash table with given initial capacity
    public LinearProbingHashMap(int capacity) {
        this.M = capacity;
        this.N = 0;
        keys = (T1[]) new Object[capacity];
        vals = (T2[]) new Object[capacity];
    }

    // returns the size of key-value pairs
    public int size() {
        return N;
    }

    // check if table is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // checks if key exist in table
    public boolean contains(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        return get(key) != null;
    }

    // gets value associated with the given key. Use hash to find the array position of the key-value pair
    public T2 get(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key))
                return vals[i];
        }
        return null;
    }

    // insert key-value pair into the table and resizing if necessary. We also remove the value if key is null
    public void put(T1 key, T2 val) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        if (val == null) {
            delete(key);
            return;
        }

        // double the table size if half full
        if (N >= M/2) resize(2 * M);

        int i;
        // find next available slot or existing key
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            // if key found, update value and return
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        // insert the new key-value pair
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    // removes the key and its value from the table
    public void delete(T1 key) {
        if (key == null) throw new IllegalArgumentException("argument is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }

        // delete key and value
        keys[i] = null;
        vals[i] = null;
        N--;

        // rehash all keys in same cluster
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

        // halves size of array if it's 12.5% full or less
        if (N > 0 && N <= M/8) resize(M/2);
    }

    public Iterable<T1> keys() {
        List<T1> list = new ArrayList<>();
        for (T1 key : keys) {
            if (key != null) list.add(key);
        }
        return list;
    }

    // returns all keys sorted by their associated values (largest first)
    public Iterable<T1> keys(Comparator<T2> cmp) {
        List<T1> list = new ArrayList<>();
        for (T1 key : keys) {
            if (key != null) {
                list.add(key);
            }
        }
        Collections.sort(list, new Comparator<T1>() {
            public int compare(T1 k1, T1 k2) {
                T2 v1 = get(k1);
                T2 v2 = get(k2);
                return cmp.compare(v2, v1);
            }
        });
        return list;
    }

    // hash function: non-negative, within [0..M-1]
    private int hash(T1 key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    // resize the hash table to the given capacity
    private void resize(int capacity) {
        LinearProbingHashMap<T1, T2> temp = new LinearProbingHashMap<>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        M = temp.M;
    }
}