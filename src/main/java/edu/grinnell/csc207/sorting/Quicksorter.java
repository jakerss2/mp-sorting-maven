package edu.grinnell.csc207.sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * Something that sorts using Quicksort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Jake Bell
 * @author Samuel A. Rebelsky
 */

public class QuickSorter<T> implements Sorter<T> {
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
  public QuickSorter(Comparator<? super T> comparator) {
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
    quickSort(values, 0, values.length);
  } // sort(T[])

  /**
   * The main quicksort algorithm using partition.
   *
   * @param values
   *  The values we want to sort.
   * @param lb
   *  The lower bound of values we look at.
   * @param ub
   *  The upper bound of values we look at.
   */
  public void quickSort(T[] values, int lb, int ub) {
    if (ub - lb <= 1) {
      return;
    } // if

    Random rand = new Random();
    int pivot = lb + rand.nextInt(ub - lb);
    T tmp = values[pivot];
    values[pivot] = values[ub - 1];
    values[ub - 1] = tmp;

    int[] bounds = partition(values, lb, ub - 1);

    quickSort(values, lb, bounds[0]);
    quickSort(values, bounds[1], ub);
  } // quickSort(T[], int, int)

  /**
   * Using the DNF method, we will rearrange an array
   * with values on the left will be less than
   * initial ub value. Anything equal to the value at our
   * original ub will be place after the less than values.
   * Unprocessed values follow, then greater than.
   *
   * @param values
   *  What we are rearraging.
   * @param lb
   *  The lower bound of the values.
   * @param ub
   *  The upper bound of values.
   * @return
   *  The index of where the less values end and index of
   *  where greater values start.
   */
  public int[] partition(T[] values, int lb, int ub) {
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
      } // if/else
    } // while
    return new int[] {left, right + 1};
  } // partition(T[], int, int)
} // class Quicksorter
