package edu.grinnell.csc207.sorting;

import org.junit.jupiter.api.BeforeAll;

/**
 * Tests of our FakeSorter.
 */
public class TestBellJakeSort extends TestSorter {
  /**
   * Set up the sorters.
   */
  @BeforeAll
  static void setup() {
    stringSorter = new BellJakeSort<String>((x,y) -> x.compareTo(y));
    intSorter = new BellJakeSort<Integer>((x,y) -> x.compareTo(y));
  } // setup()

} // class TestFakeSorter
