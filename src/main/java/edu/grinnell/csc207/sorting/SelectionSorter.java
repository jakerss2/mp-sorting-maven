package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using selection sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Jake Bell
 * @author Samuel A. Rebelsky
 */

public class SelectionSorter<T> implements Sorter<T> {
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
  public SelectionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
  } // SelectionSorter(Comparator)

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Sort an array in place using selection sort.
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
    for (int i = 0; i < values.length - 1; i++) {
      select(values, i);
    } // for
  } // sort(T[])

  /**
   * Find the smallest value of the unsorted
   * portion of the array, and put it at the
   * correct index.
   *
   * @param values
   *  The values we are sorting.
   * @param index
   *  Where we will swap the smallest value remaining.
   */
  public void select(T[] values, int index) {
    int minIndex = indexOfSmall(values, index);

    T obj = values[minIndex];
    values[minIndex] = values[index];
    values[index] = obj;
  } // select(T[], int)

  /**
   * The method used to find the smallest value after start.
   *
   * @param values
   *  The values we are searching through.
   * @param start
   *  The start of the unsorted values.
   * @return
   *  The index of the smallest value remaining.
   */
  public int indexOfSmall(T[] values, int start) {
    int index = start;
    for (int j = start + 1; j < values.length; j++) {
      int comp = (order.compare(values[index], values[j]));
      if (comp >= 1) {
        index = j;
      } // if
    } // for
    return index;
  } // indexOfSmall(T[], int)
} // class SelectionSorter
