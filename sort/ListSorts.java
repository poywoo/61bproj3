/* ListSorts.java */
package sort;

import graphalg.*;
import list.*;

import java.util.Random;

public class ListSorts {

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue newQueue = new LinkedQueue();
    try {
      while (q.isEmpty() == false) {
        LinkedQueue oneItem = new LinkedQueue();
        oneItem.enqueue((Comparable) q.dequeue());
        newQueue.enqueue(oneItem);

      }
    } catch (QueueEmptyException e) {
      System.out.println("Error: Queue empty in makeQueueOfQueues()");
    }
    return newQueue;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  // public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
  //   LinkedQueue newQueue = new LinkedQueue();
  //   try {
  //     while (q1.isEmpty() == false && q2.isEmpty() == false) {
  //       Comparable q1item = (Comparable) q1.front();
  //       Comparable q2item = (Comparable) q2.front();
  //       int num = q1item.compareTo(q2item);
  //       if (num < 0) {
  //         newQueue.enqueue(q1item);
  //         q1.dequeue();
  //       } else if (num > 0) {
  //         newQueue.enqueue(q2item);
  //         q2.dequeue();
  //       } else {
  //         newQueue.enqueue(q1item);
  //         newQueue.enqueue(q2item);
  //         q1.dequeue();
  //         q2.dequeue();
  //       }
  //     }
  //   } catch (QueueEmptyException e) {
  //     System.out.println("Error: Queue empty in mergeSortedQueues()");
  //   }
  //   if ((q1.isEmpty() == true) && (q2.isEmpty() == false)) {
  //     newQueue.append(q2);
  //   }
  //   if ((q1.isEmpty() == false) && (q2.isEmpty() == true)) {
  //     newQueue.append(q1);
  //   }
  //   return newQueue;
  // }

  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, int pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    try {
      while (qIn.isEmpty() == false) {
        int num = ((KEdge)qIn.front()).weight();
        if (num < pivot) {
          qSmall.enqueue(qIn.dequeue());
        } else if (num > pivot) {
          qLarge.enqueue(qIn.dequeue());
        } else {
          qEquals.enqueue(qIn.dequeue());
        }
      }
    } catch (QueueEmptyException e) {
      System.out.println("Error: Queue empty in partition()");
    }
  }

  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  // public static void mergeSort(LinkedQueue q) {
  //   if(q.size() < 2) {
  //     return;
  //   }
  //   LinkedQueue queueQ = makeQueueOfQueues(q);
  //   try {
  //     while (queueQ.size() > 1) {
  //       LinkedQueue q1 = (LinkedQueue) queueQ.dequeue();
  //       LinkedQueue q2 = (LinkedQueue) queueQ.dequeue();
  //       queueQ.enqueue(mergeSortedQueues(q1, q2));
  //     }
  //     q.append((LinkedQueue) queueQ.dequeue());
  //   } catch (QueueEmptyException e) {
  //     System.out.println("Error: Queue empty in mergeSort()");
  //   }
  // }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    if(q.size() < 2) {
      return;
    }
    Random r = new Random();
    int index = r.nextInt(q.size()) + 1;
    int pivot = ((KEdge)q.nth(index)).weight();
    LinkedQueue lessThan = new LinkedQueue();
    LinkedQueue equalTo = new LinkedQueue();
    LinkedQueue greaterThan = new LinkedQueue();
    partition(q, pivot, lessThan, equalTo, greaterThan);
    quickSort(lessThan);
    quickSort(greaterThan);
    q.append(lessThan);
    q.append(equalTo);
    q.append(greaterThan);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/

  // public static void main(String [] args) {

  //   LinkedQueue q = makeRandom(10);
  //   System.out.println(q.toString());
  //   mergeSort(q);
  //   System.out.println(q.toString());

  //   q = makeRandom(10);
  //   System.out.println(q.toString());
  //   quickSort(q);
  //   System.out.println(q.toString());

    
  //   Timer stopWatch = new Timer();
  //   q = makeRandom(SORTSIZE);
  //   stopWatch.start();
  //   mergeSort(q);
  //   stopWatch.stop();
  //   System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
  //                      stopWatch.elapsed() + " msec.");

  //   stopWatch.reset();
  //   q = makeRandom(SORTSIZE);
  //   stopWatch.start();
  //   quickSort(q);
  //   stopWatch.stop();
  //   System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
  //                      stopWatch.elapsed() + " msec.");
    
  // }

}
