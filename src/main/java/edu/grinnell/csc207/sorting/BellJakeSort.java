package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * My Unique sorting algorithm which utilizes insertion and merge
 * sorting. It also uses forkJoinPool which splits the arrays
 * and allows them to be sorted at the same time(parallelization).
 * Acknowledgement: ChatGPT was used to help make the sorting faster.
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

  /** Typical runsize for tim sort. */
  private static final int RUN_SIZE = 32;

  /** Hold the same fjp. */
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
    this.forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
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
    } // for
    parallelMergeSort(values);
  } // sort(T[])


/**
 * Insertion sort has the front of the array
 * as the sorted, we then get following elements
 * and place them where they go in the sorted
 * section, and shift.
 * @param values
 *  The values we are sorting.
 * @param left
 *  The left bound of the values we will sort.
 * @param right
 *  The right bound of the values we will sort.
 */
  private void insertionSort(T[] values, int left, int right) {
    for (int i = left + 1; i <= right; i++) {
      T temp = values[i];
      int low = left;
      int high = i - 1;

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
   *  The values we are sorting.
   */
  private void mergeSort(T[] values) {
    int n = values.length;
    T[] temp = (T[]) new Object[n];

    // Iteratively merge subarrays of increasing size
    for (int size = 1; size < n; size *= 2) {
      for (int left = 0; left < n - size; left += 2 * size) {
        int mid = left + size - 1;
        int right = Math.min(left + 2 * size - 1, n - 1);
        merge(values, temp, left, mid, right);
      } // for
    } // for
  } // mergeSort(T[])

  /**
   * Given an array, we move along to sides of an array
   * and "merge" them by comparing each index of the
   * sides of the array, and increasing the
   * respective index.
   * @param values
   *  The values we are sorting.
   * @param temp
   *  The temporary array we are storing in.
   * @param left
   *  The left bound of what we will sort.
   * @param mid
   *  The middle of values we are sorting.
   * @param right
   *  The right bound of what we will sort.
   */
  private void merge(T[] values, T[] temp, int left, int mid, int right) {
    System.arraycopy(values, left, temp, left, right - left + 1);

    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
      if (order.compare(temp[i], temp[j]) <= 0) {
        values[k++] = temp[i++];
      } else {
        values[k++] = temp[j++];
      } // if
    } // while

    while (i <= mid) {
      values[k++] = temp[i++];
    } // while

    while (j <= right) {
      values[k++] = temp[j++];
    } // while
  } // merge(T[], T[], int, int, int)

  /**
   * Merge sort that utilizes forkJoinPool
   * which allows multiple sortings to happen
   * at the same time.
   * @param values
   *  The values we are searching.
   */
  private void parallelMergeSort(T[] values) {
    int n = values.length;

    if (n <= 1000) {
      mergeSort(values);
    } else {
      forkJoinPool.invoke(new MergeTask(values, 1, n));
    } // if/else
  } // parallelMergeSort(T[])

  /**
   * MergeTask used to begin the merging.
   */
  private class MergeTask extends RecursiveTask<Void> {
    /** The values we are sorting. */
    private final T[] values;

    /** Size of how many parallelizations we are doing. */
    private final int size;

    /** The size of values. */
    private final int n;

    /**
     * Initialize our MergeTask.
     * @param values
     *  The values we are sorting.
     * @param size
     *  Size of how many parallelizations we are doing.
     * @param n
     *  The size of values.
     */
    public MergeTask(T[] values, int size, int n) {
      this.values = values;
      this.size = size;
      this.n = n;
    } // MergeTask(T[], int, int)

    @Override
    protected Void compute() {
      if (size >= n) {
        return null; // All merging is complete
      } // if

      // Create parallel tasks for merging subarrays
      invokeAll(new MergeTask(values, size * 2, n));

      for (int left = 0; left < n - size; left += 2 * size) {
        int mid = left + size - 1;
        int right = Math.min(left + 2 * size - 1, n - 1);
        merge(values, (T[]) new Object[n], left, mid, right);
      } // for

      return null;
    } // compute()
  } // MergeTask
} // class BellJakeSort
