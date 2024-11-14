package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using merge sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 */

public class MergeSorter<T> implements Sorter<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The way in which elements are ordered.
   */
  Comparator<? super T> order;

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
  public MergeSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // MergeSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using merge sort.
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
    merge(values, 0, values.length - 1);
  } // sort(T[])

  public void merge(T[] values, int lb, int ub) {
    if (lb < ub) {
      int midpoint = lb + (ub - lb) / 2;

      merge(values, lb, midpoint);
      merge(values, midpoint + 1, ub);

      mergeHelper(values, lb, midpoint, ub);
    }
  }

  public void mergeHelper(T[] values, int lb, int m, int ub) {
    int sizeLeft = m - lb + 1;
    int sizeRight = ub - m;
    T[] tmpLeft = (T[]) new Object[sizeLeft];
    T[] tmpRight = (T[]) new Object[sizeRight];

    for (int i = 0; i < sizeLeft; i++) { 
      tmpLeft[i] = values[i + lb];
    }
    for (int i = 0; i < sizeRight; i++) {
      tmpRight[i] = values[m + 1 + i];
    }

    int i = 0;
    int j = 0;
    int k = lb;

    while (i < sizeLeft && j < sizeRight) {
      int comp = order.compare(tmpLeft[i], tmpRight[j]);
      if (comp <= 0) {
        values[k++] = tmpLeft[i++];
      } else {
        values[k++] = tmpRight[j++];
      }
    }

    while (i < sizeLeft) {
      values[k++] = tmpLeft[i++];
    }

    while (j < sizeRight) {
      values[k++] = tmpRight[j++];
    }
  }
} // class MergeSorter
