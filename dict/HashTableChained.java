/* HashTableChained.java */

package dict;

import list.*;
/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  protected DList[] buckets;
  protected int numEntry;
  protected int numBuckets;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    numBuckets = sizeEstimate;
    while (!isPrime(numBuckets)) {
      numBuckets++;
    }
    numEntry = 0;
    buckets = new DList[numBuckets];
  }

  private static boolean isPrime(int i) {
    for (int div = 2; div < i; div++) {
      if (i % div == 0) {
        return false;
      }
    }
    return true;
  }

  /** 
   *  resize() modifies the hashtable whenever it reaches or exceeds 3/4 
   *  of the length of the hashtable. This ensures that the load factor remains constant as
   *  |V| changes.
   **/
  private void resize() {

    DList[] newBuckets = new DList[buckets.length*2];
    try{
      for(int i = 0; i < buckets.length; i++) {
        if( buckets[i] != null) {
          DListNode x = (DListNode)buckets[i].front();
          while(x.isValidNode()) {
            int hashKey = Math.abs((3*((Entry)x.item()).key().hashCode()+ 7) % 16908799) % newBuckets.length;
            x = (DListNode)x.next();
          }
          if(newBuckets[i] != null){
            newBuckets[i].insertBack(x.item());
          } else {
            newBuckets[i] = new DList();
            newBuckets[i].insertFront(x.item());
          }
        }
      }
      buckets = newBuckets;
    } catch (InvalidNodeException e) {

    }
  }


  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    buckets = new DList[3];
    numBuckets = 3;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int result;
    result = (((3*code + 7) % 16908799) % numBuckets);
    if (result < 0){
      result = result * -1;
    }
    return result;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return numEntry;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if (numEntry == 0){
      return true;
    } else {
      return false;
    }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    if(numEntry >= .75 * buckets.length) {
      resize();
    }
    Entry newEntry = new Entry();
    int keyIn = compFunction(key.hashCode());
    newEntry.key = key;
    newEntry.value = value;
    if (buckets[keyIn] != null){
      buckets[keyIn].insertBack(newEntry);
    } else {
      buckets[keyIn] = new DList();
      buckets[keyIn].insertFront(newEntry);
    }
    numEntry ++;
    return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    try{
      int keyIn = compFunction(key.hashCode());
      if (buckets[keyIn] == null){
        return null;
      } else {
        DListNode tracker = (DListNode) buckets[keyIn].front();
        while (tracker.isValidNode()) {
          if(((Entry)tracker.item()).key.equals(key)){
            return (Entry) tracker.item();
          }
          tracker = (DListNode) tracker.next();
        }
      }
    } catch(InvalidNodeException e){
      return null;
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    try{
      int keyIn = compFunction(key.hashCode());

      if (buckets[keyIn] == null){
        return null;
      } else {
        DListNode tracker = (DListNode) buckets[keyIn].front();
        while(tracker.isValidNode()){
          if(((Entry) tracker.item()).key.equals(key)){
            Entry found = (Entry) tracker.item();
            tracker.remove();
            numEntry --;
            return found;
          }
          tracker = (DListNode) tracker.next();
        }
      }
    } catch(InvalidNodeException e){
      System.out.println("InvalidNodeException in remove()");
    }
    return null;
  }
  

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for(int i = 0; i < numBuckets; i++){
      if (buckets[i] != null){
        buckets[i] = new DList();
      }
    }
    numEntry = 0;
  }

  public int collisions() {
    int counter = 0;
    for(int i = 0; i < numBuckets; i++){
      if(buckets[i] == null || buckets[i].length() == 1) {
        continue;
      } else{
        counter += buckets[i].length() - 1; 
      }
    }
    return counter;
  }

}