import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * @param <V>   {@inheritDoc}
 * @param <Key> {@inheritDoc}
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    /**
     * {@inheritDoc}
     */
    private HashMap<V, Integer> indexMap = new HashMap<V, Integer>();
    private ArrayList<Entry<Key, V>> minHeapList = new ArrayList<Entry<Key, V>>();
    int left(int index) {
        return 2 * index;
    }
    
    int right(int index) {
        return 2 * index + 1;
    }

    int parent(int index) {
        return (int) Math.floor(index / 2.0);
    }
    
    void minHeapify(int current) {
        int l = left(current);
        int r = right(current);
        int smallest = current;
        if (l <= this.size()) {
            if (minHeapList.get(l).key.compareTo(minHeapList.get(smallest).key) < 0) {
                smallest = l;
            }
        }

        if (r <= this.size()) {
            if (minHeapList.get(r).key.compareTo(minHeapList.get(smallest).key) < 0) {
                smallest = r;
            }
        }

        if (smallest != current) {
            swap(current, smallest);
            
            minHeapify(smallest);
            
        }
    }
    void swap(int current, int smallest) {
        Entry<Key, V> currentEntry = minHeapList.get(current);
        Entry<Key, V> smallestEntry = minHeapList.get(smallest);
        minHeapList.set(current, smallestEntry);
        indexMap.replace(smallestEntry.value, current);
        minHeapList.set(smallest, currentEntry);
        indexMap.replace(currentEntry.value, smallest);
    }
    
    @Override
    public int size() {
        if (minHeapList.size() == 0) {
            return 0;
        } else {
            return minHeapList.size() - 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        return indexMap.containsKey(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null || this.containsValue(value)) {
            throw new IllegalArgumentException();
        } else if (this.isEmpty()) {
    
            this.minHeapList.add(null);
        }
        Entry<Key, V> newEntry = new Entry<Key, V>(key, value);
        int current = this.size() + 1;
        
        
        indexMap.put(newEntry.value, current);

        minHeapList.add(newEntry);
        
        this.decreaseKey(value, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if (!indexMap.containsKey(value)) {
            throw new NoSuchElementException();
        }
        
        int valueIndex = indexMap.get(value);
        Entry<Key, V> previousEntry = minHeapList.get(valueIndex);
        
        if (newKey.compareTo((Key) previousEntry.key) > 0) {
            throw new IllegalArgumentException("New key is larger");
        }
        Entry<Key, V> newEntry = new Entry<Key, V>(newKey, value);
        minHeapList.set(valueIndex, newEntry);
        
        while (valueIndex > 1 && 
                minHeapList.get(valueIndex).key.compareTo(minHeapList.get(parent(valueIndex)).key) 
                < 0) {
            swap(valueIndex, parent(valueIndex));
            valueIndex = parent(valueIndex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return minHeapList.get(1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entry<Key, V> extractMin() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        swap(1, this.size());
        Entry<Key, V> output = minHeapList.remove(this.size());
        indexMap.remove(output.value);
        
        if (this.isEmpty()) {
            this.minHeapList.clear();
            this.indexMap.clear();
        } else {
            minHeapify(1);
        }
        
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        Set<V> valuesSet = new HashSet<V>();
        Iterator<Entry<Key, V>> listIterator = minHeapList.iterator();
        
        while (listIterator.hasNext()) {
            Entry<Key, V> current = listIterator.next();
            if (current != null) {
                valuesSet.add(current.value);
            }
        }
        
        return valuesSet;
    }
}