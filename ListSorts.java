package cs61bhw8;


/* ListSorts.java */

import listhw8.*;

public class ListSorts {

  private final static int SORTSIZE = 1000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    LinkedQueue queues = new LinkedQueue();
    while (!q.isEmpty()){
    	LinkedQueue temp = new LinkedQueue();
    	try {
    		temp.enqueue(q.dequeue());
    	} catch(QueueEmptyException e){
    		System.out.println(e);
    	}
    	queues.enqueue(temp);
    }
    return queues;
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
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
	    // Replace the following line with your solution.
	    LinkedQueue newQueue = new LinkedQueue();
	    Comparable e1, e2;
	    try {
	      while(!q1.isEmpty() && !q2.isEmpty()) {
	        e1 = (Comparable)q1.front();
	        e2 = (Comparable)q2.front();
	        int sign = e1.compareTo(e2);
	        if (sign <= 0) {
	        	newQueue.enqueue(q1.dequeue());
	        } else {
	        	newQueue.enqueue(q2.dequeue());
	        }
	      }
	      if (q1.isEmpty()) {
	    	  newQueue.append(q2);
	      } 
	      if (q2.isEmpty()) {
	    	  newQueue.append(q1);
	      }
	    } catch(QueueEmptyException e) {
	      System.out.println(e);
	    }
	    return newQueue;
	  }

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
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
	  while(!qIn.isEmpty()) {
	      try {
	          int sign=pivot.compareTo((Comparable) qIn.front());
	          if (sign > 0) {
	            qSmall.enqueue(qIn.dequeue());
	          } else if (sign == 0) {
	            qEquals.enqueue(qIn.dequeue());
	          } else {
	            qLarge.enqueue(qIn.dequeue());
	          }
	        } catch(QueueEmptyException e) {
	          System.out.println(e);
	        }
	      }
	      
	    }


  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
	    // Your solution here.
	    LinkedQueue newqueues = makeQueueOfQueues(q);
	    LinkedQueue q1, q2, temp;
	    try {
	      while(newqueues.size() > 1){
	        q1 = (LinkedQueue)newqueues.dequeue();
	        q2 = (LinkedQueue)newqueues.dequeue();
	        temp = (LinkedQueue)mergeSortedQueues(q1, q2);
	        newqueues.enqueue(temp);
	      }
	      q.append((LinkedQueue) newqueues.front());
	    } catch(QueueEmptyException e) {
	        System.out.println(e);
	    }
	  }

  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
	    // Your solution here.
	    if(q.size() <= 1) {
	        return;
	    }
	    int num = (int) (q.size() * Math.random());
	    Comparable pivot = (Comparable) (q.nth(num));
	    LinkedQueue qSmall = new LinkedQueue();
	    LinkedQueue qEquals = new LinkedQueue();
	    LinkedQueue qLarge = new LinkedQueue();
	    partition(q, pivot, qSmall, qEquals, qLarge);
	    quickSort(qSmall); quickSort(qLarge);
	    q.append(qSmall);
	    q.append(qEquals);
	    q.append(qLarge);
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
  public static void main(String [] args) {

    LinkedQueue q = makeRandom(10);
    System.out.println(q.toString());
    mergeSort(q);
    System.out.println(q.toString());

    q = makeRandom(10);
    System.out.println(q.toString());
    quickSort(q);
    System.out.println(q.toString());

    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");
  }

}
