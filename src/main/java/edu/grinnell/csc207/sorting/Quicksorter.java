package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Samuel A. Rebelsky
 */

public class Quicksorter<T> implements Sorter<T> {
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
  public Quicksorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // Quicksorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using Quicksort.
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
    quick(values, 0, values.length - 1);
  } // sort(T[])

  public void quick(T[] values, int lb, int ub) {
    if (lb < ub) {
      Random rand = new Random();
      int pivot = lb + rand.nextInt(ub - lb + 1);
      T tmp = values[pivot];
      values[pivot] = values[ub];
      values[ub] = tmp;

      int[] bounds = quickHelper(values, lb, ub);

      quick(values, lb, bounds[0] - 1);
      quick(values, bounds[1] + 1, ub);
    }
  }

  public int[] quickHelper(T[] values, int lb, int ub) {
    int left = lb;
    int same = lb;
    int right = ub;
    T pivot = values[ub];
    

    while (same <= right) {
      int comp = order.compare(values[same], pivot);
      if (comp < 0) {
          T obj = values[same];
          values[same++] = values[left];
          values[left++] = obj;
      } else if (comp > 0) {
        T obj = values[same];
        values[same] = values[right];
        values[right--] = obj;
      } else {
        same++;
      }
    }
    return new int[] { left, right };
  }
} // class Quicksorter
