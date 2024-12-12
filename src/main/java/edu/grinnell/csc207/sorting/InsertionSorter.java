package edu.grinnell.csc207.sorting;

import java.util.Comparator;

/**
 * Something that sorts using insertion sort.
 *
 * @param <T>
 *   The types of values that are sorted.
 *
 * @author Jacob Bell
 * @author Samuel A. Rebelsky
 */

public class InsertionSorter<T> implements Sorter<T> {
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
  public InsertionSorter(Comparator<? super T> comparator) {
    this.order = comparator;
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
    for (int i = 0; i < values.length; i++) {
      insert(values, i);
    } // for
  } // sort(T[])

  /**
   * Place the certain index of an array in its
   * proper place in the sorted side of array.
   *
   * @param values
   *  the array we are sorting
   * @param index
   *  the place we are inserting in the sorted side of array
   */
  public void insert(T[] values, int index) {
    int j = (index - 1);
    T obj = values[index];

    while (j >= 0) {
      int comp = order.compare(values[j], obj);
      if (comp >= 1) {
        values[j + 1] = values[j];
        j--;
      } else {
        break;
      } // if/else
    } // while
    values[j + 1] = obj;
  } // insert(T[])
} // class InsertionSorter
