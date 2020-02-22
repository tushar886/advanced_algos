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

        //since this key has been accessed move it to head as its LRU cache.
        moveToHead(entry);
        return entry.value;
    }

    public void put(int key, int value) {

        LRUEntry entry = hashtable.get(key);

        if (entry == null) {
            // key is not present in hash table
            // Create new entry
            LRUEntry newEntry = new LRUEntry();
            newEntry.key = key;
            newEntry.value = value;

            //after new entry is created add it to hash table and actual list that represents cache
            hashtable.put(key, newEntry);
            addToFront(newEntry);
            totalItemsInCache++;

            //If over capacity has occured removed the LRU item
            if (totalItemsInCache > maxCapacity) {
                removeLRUEntry();
            }
        } else {
            // key is present , update its value and move it to head since that key has been accessed now.
            entry.value = value;
            moveToHead(entry);
        }
    }

    private void removeLRUEntry() {
        LRUEntry tail = popTail();

        hashtable.remove(tail.key);
        --totalItemsInCache;
    }

    private LRUEntry popTail() {
        LRUEntry tailItem = tail.prev;
        removeFromList(tailItem);

        return tailItem;
    }

    private void addToFront(LRUEntry node) {
        // Wire up the new node being to be inserted
        node.prev = head;
        node.next = head.next;

      /*
        Re-wire the node after the head. Our node is still sitting "in the middle of nowhere".
        We got the new node pointing to the right things, but we need to fix up the original
        head & head's next.
        head <-> head.next <-> head.next.next <-> head.next.next.next <-> ...
        ^            ^
        |- new node -|
        That's where we are before these next 2 lines.
      */
        head.next.prev = node;
        head.next = node;
    }

    private void removeFromList(LRUEntry node) {
        LRUEntry savedPrev = node.prev;
        LRUEntry savedNext = node.next;

        savedPrev.next = savedNext;
        savedNext.prev = savedPrev;
    }

    private void moveToHead(LRUEntry node) {
        removeFromList(node);
        addToFront(node);
    }
}
