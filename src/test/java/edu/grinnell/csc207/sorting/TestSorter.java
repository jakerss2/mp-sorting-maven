package edu.grinnell.csc207.sorting;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.grinnell.csc207.util.ArrayUtils;

/**
 * Tests of Sorter objects. Please do not use this class directly.
 * Rather, you should subclass it and initialize stringSorter and
 * intSorter in a static @BeforeAll method.
 *
 * @author Your Name
 * @uathor Samuel A. Rebelsky
 */
public class TestSorter {

  // +---------+-----------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * The sorter we use to sort arrays of strings.
   */
  static Sorter<String> stringSorter = null;

  /**
   * The sorter we use to sort arrays of integers.
   */
  static Sorter<Integer> intSorter = null;

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Given a sorted array and a permutation of the array, sort the
   * permutation and assert that it equals the original.
   *
   * @param <T>
   *   The type of values in the array.
   * @param sorted
   *   The sorted array.
   * @param perm
   *   The permuted sorted array.
   * @param sorter
   *   The thing to use to sort.
   */
  public <T> void assertSorts(T[] sorted, T[] perm, Sorter<? super T> sorter) {
    T[] tmp = perm.clone();
    sorter.sort(perm);
    assertArrayEquals(sorted, perm,
      () -> String.format("sort(%s) yields %s rather than %s",
          Arrays.toString(tmp), 
          Arrays.toString(perm), 
          Arrays.toString(sorted)));
  } // assertSorts

  // +-------+-------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * A fake test. I've forgotten why I've included this here. Probably
   * just to make sure that some test succeeds.
   */
  @Test
  public void fakeTest() {
    assertTrue(true);
  } // fakeTest()

  /**
   * Ensure that an array that is already in order gets sorted correctly.
   */
  @Test
  public void orderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    String[] expected = original.clone();
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that an array that is ordered backwards gets sorted correctly.
   */
  @Test
  public void reverseOrderedStringTest() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "foxtrot", "delta", "charlie", "bravo", "alpha" };
    String[] expected = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    assertSorts(expected, original, stringSorter);
  } // orderedStringTest

  /**
   * Ensure that a randomly permuted version of a moderate-sized
   * array sorts correctly.
   */
  @Test 
  public void permutedIntegersTest() { 
    int SIZE = 100; 
    if (null == intSorter) { 
      return; 
    } // if
    Integer[] original = new Integer[SIZE];
    for (int i = 0; i < SIZE; i++) {
      original[i] = i;
    } // for
    Integer[] expected = original.clone();
    ArrayUtils.permute(original);
    assertSorts(expected, original, intSorter);
  } // permutedIntegers

  /**
   * Check when only the first and last element
   * are needed to be switched.
   */
  @Test
  public void firstLastSwitchedInt() {
    if (null == intSorter) {
      return;
    } // if
    Integer[] original = { 5, 2, 3, 4, 1 };
    Integer[] expected = { 1, 2, 3, 4, 5 };
    assertSorts(expected, original, intSorter);
  } // firstLastSwitchedInt

  /**
   * Ensure it works when there is only one element
   */
  @Test
  public void singletonInt() {
    if (null == intSorter) {
      return;
    } // if
    Integer[] original = { 1 };
    Integer[] expected = { 1 };
    assertSorts(expected, original, intSorter);
  } // singletonInt

  /**
   * Ensure it works if first two elements are switched
   */
  @Test
  public void firstTwoString() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "bravo", "alpha", "charlie", "delta", "foxtrot" };
    String[] expected = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    assertSorts(expected, original, stringSorter);
  } // firstTwoString

  /**
   * Ensure it works if last two elements are switched
   */
  @Test
  public void lastTwoString() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "alpha", "bravo", "charlie", "foxtrot", "delta" };
    String[] expected = { "alpha", "bravo", "charlie", "delta", "foxtrot" };
    assertSorts(expected, original, stringSorter);
  } // firstTwoString

  /**
   * Ensure it stays the same if all are the same
   */
  @Test
  public void allSameString() {
    if (null == stringSorter) {
      return;
    } // if
    String[] original = { "echo", "echo", "echo", "echo", "echo"};
    String[] expected = { "echo", "echo", "echo", "echo", "echo"};
    assertSorts(expected, original, stringSorter);
  } // allSameString

  @Test
  public void someDuplicateInt() {
    if (null == intSorter) {
      return;
    } // if
    Integer[] original = { 1, 5, 3, 5, 5, 4, 2, 2 };
    Integer[] expected = { 1, 2, 2, 3, 4, 5, 5, 5 };
    assertSorts(expected, original, intSorter);
  } // firstLastSwitchedInt

  @Test
  public void manyDuplicateInt() {
    if (null == intSorter) {
      return;
    } // if
    Integer[] original = { 1, 2, 1, 2, 2, 1, 2, 1 };
    Integer[] expected = { 1, 1, 1, 1, 2, 2, 2, 2 };
    assertSorts(expected, original, intSorter);
  } // manyDuplicateInt
} // class TestSorter
