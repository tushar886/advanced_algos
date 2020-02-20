package advanced_ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ConstantTimeDS {

    /*
    Design a data structure that supports the following operations in Θ(1) time.
    insert(x): Inserts an item x to the data structure if not already present.

    remove(x): Removes an item x from the data structure if present.

    search(x): Searches an item x in the data structure.

    getRandom(): Returns a random element from current set of elements

    For the constant time we can use hashing to support the above three operations.
    The idea is to use a resizable array (ArrayList in Java, vector in C) together with hashing.
    Resizable arrays support insert in Θ(1) amortized time complexity.
    To implement getRandom(), we can simply pick a random number from 0 to size-1
    and return the element at that index. The hash map stores array values as keys and array indexes as values.

        insert(x)
        1) Check if x is already present by doing a hash map lookup.
        2) If not present, then insert it at the end of the array.
        3) Add in the hash table also, x is added as key and last array index as the index.

        remove(x)
        1) Check if x is present by doing a hash map lookup.
        2) If present, then find its index and remove it from a hash map.
        3) Swap the last element with this element in an array and remove the last element.
        Swapping is done because the last element can be removed in O(1) time.
        4) Update index of the last element in a hash map.

        getRandom()
        1) Generate a random number from 0 to last index.
        2) Return the array element at the randomly generated index.

        search(x)
        Do a lookup for x in hash map.
     */
    private List<Integer> arrList; // This is a resizable array
    private Map<Integer, Integer> indexMap; // Key will be array elements and value will be index in array.

    public ConstantTimeDS() {
        this.arrList = new ArrayList<Integer>();
        this.indexMap = new HashMap<Integer, Integer>();
    }

    /**
     * When we add element then we first add it in hash Map and then add it at end of array.
     * @param x
     */
    public void addElement(int x) {
        if (indexMap.get(x) != null) {
            return;
        }
        int s = arrList.size();
        arrList.add(s);
        indexMap.put(x, s);
    }

    public void remove(int x) {

    }
}
