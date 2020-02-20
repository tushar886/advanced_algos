package caching;

/*
@au
 */
import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    static class LRUEntry {
        /*
        This is one node of DLL which has both the next and prev pointers to it.
         */
        int value;
        int key;
        LRUEntry next;
        LRUEntry prev;

        LRUEntry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, LRUEntry> hashtable;
    private int totalItemsInCache;
    private int maxCapacity;

    //Maintain head and tail pointers to indicate what are the head and tail of the cache.
    private LRUEntry head;
    private LRUEntry tail;

    //define constructor which also takes in the size of cache in max capacity.
    public LRUCache(int maxCapacity) {
        hashtable = new HashMap<Integer, LRUEntry>();
        this.maxCapacity = maxCapacity;

        //create dummy head and tail pointers.
        head = new LRUEntry();
        tail = new LRUEntry();
        head.prev = null;
        head.next = tail;

        tail.prev = head;
        tail.next = null;
    }

    public Integer get(int key) {
        /**
         * Simple O(1) method get which simply returns the method from the hashtable.
         */
        LRUEntry entry = hashtable.get(key);

        if (entry == null) {
            return -1;
        }

        //since this key has been accesed move it to head as its LRU cache.
        moveToHead(key);
        return entry.value;
    }

    public void put(int key, int value) {

        LRUEntry entry = hashtable.get(key);

        if (entry == null) {
            // key is not present in hash table
        } else {
            // key is present , update its value and move it to head since that key has been accessed now.
            entry.value = value;
            moveToHead(entry);
        }


    }
}
