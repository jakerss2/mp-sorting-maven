package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * My Unique sorting algorithm which utilizes insertion and merge
 * sorting. It also uses forkJoinPool which splits the arrays
 * and allows them to be sorted at the same time(parallelization).
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Jacob Bell
 * @author Samuel A. Rebelsky
 */

public class BellJakeSort<T> implements Sorter<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

  /** Typical runsize for tim sort */
  private static final int RUN_SIZE = 32;

  /** Hold the same fjp */
  private final ForkJoinPool forkJoinPool;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a sorter using a particular comparator.
   *
   * @param comparator
   *   The order in which elements in the array should be ordered
   *   after sorting.
   */
  public BellJakeSort(Comparator<? super T> comparator) {
    this.order = comparator;
    this.forkJoinPool = new ForkJoinPool();
  } // InsertionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using insertion sort.
   *
   * @param values
   *   an array to sort.
   *
   * @post
   *   The array has been sorted according to some order (often
   *   one given to the constructor).
   * @post
   *   For all i, 0 &lt; i &lt; values.length,
   *     order.compare(values[i-1], values[i]) &lt;= 0
   */
  @Override
  public void sort(T[] values) {
    int n = values.length;

    // Use insertion sort on small "runs" within the array
    for (int i = 0; i < n; i += RUN_SIZE) {
      int end = Math.min(i + RUN_SIZE - 1, n - 1);
      insertionSort(values, i, end);
    }

    parallelMergeSort(values, 0, n - 1);
} // sort([])


/**
 * Insertion sort has the front of the array
 * as the sorted, we then get following elements
 * and place them where they go in the sorted
 * section, and shift.
 * @param values
 * @param left
 * @param right
 */
  private void insertionSort(T[] values, int left, int right) {
    for (int i = left + 1; i <= right; i++) {
      T temp = values[i];
      int low = left, high = i - 1;

      while (low <= high) {
        int mid = low + (high - low) / 2;
        if (order.compare(values[mid], temp) < 0) {
          low = mid + 1;
        } else {
          high = mid - 1;
        } // if/else
      } // while

    System.arraycopy(values, low, values, low + 1, i - low);
    values[low] = temp;
    } // for
  } // insertionSort(T[], int, int)

  /**
   * Main mergesort method that
   * recursively calls the helper
   * to give proper bounds.
   * @param values
   * @param left
   * @param right
   */
  private void mergeSort(T[] values, int left, int right) {
    if (left < right) {
      int mid = left + (right - left) / 2;

      mergeSort(values, left, mid);
      mergeSort(values, mid + 1, right);

      merge(values, left, mid, right);
    } // if
  } // mergeSort(T[], int, int)

  /**
   * Given an array, we move along to sides of an array
   * and "merge" them by comparing each index of the 
   * sides of the array, and increasing the
   * respective index.
   * @param values
   * @param left
   * @param mid
   * @param right
   */
  private void merge(T[] values, int left, int mid, int right) {
    int sizeLeft = mid - left + 1;
    int sizeRight = right - mid;

    T[] temp = (T[]) new Object[values.length];

    System.arraycopy(values, left, temp, left, sizeLeft);
    System.arraycopy(values, mid + 1, temp, mid + 1, sizeRight);

    int i = left, j = mid + 1, k = left;

    while (i <= mid && j <= right) {
      if (order.compare(temp[i], temp[j]) <= 0) {
          values[k++] = temp[i++];
      } else {
          values[k++] = temp[j++];
      }
    }

    while (i <= mid) {
      values[k++] = temp[i++];
    }

    while (j <= right) {
      values[k++] = temp[j++];
    }
  }


  /**
   * Merge sort that utilizes forkJoinPool
   * which allows multiple sortings to happen
   * at the same time
   * @param values
   * @param left
   * @param right
   */
  private void parallelMergeSort(T[] values, int left, int right) {
    if (right - left <= 1000) {
        mergeSort(values, left, right);
    } else {
        forkJoinPool.invoke(new MergeTask(values, left, right));
    } // if/else
  } // parallelMergeSort(T[], int int)

  private class MergeTask extends RecursiveTask<Void> {
    private final T[] values;
    private final int left;
    private final int right;

    public MergeTask(T[] values, int left, int right) {
      this.values = values;
      this.left = left;
      this.right = right;
    } // MergeTask(T[], int, int)

    @Override
    protected Void compute() {
      int mid = left + (right - left) / 2;
      MergeTask leftTask = new MergeTask(values, left, mid);
      MergeTask rightTask = new MergeTask(values, mid + 1, right);

      // Fork the tasks
      leftTask.fork();
      rightTask.fork();

      // Wait for both sides to finish
      leftTask.join();
      rightTask.join();

      // Merge results
      merge(values, left, mid, right);
      return null;
    } // compute() 
  } // MergeTask
} // class BellJakeSort
