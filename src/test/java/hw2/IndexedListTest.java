package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7; // initial is the default value
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  // TODO Add more tests!

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexAboveRangeThrowsException() {
    try {
      indexedList.get(LENGTH + 1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-1, 10);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is above the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(LENGTH + 1, 10);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() changes the default value after IndexedList is instantiated.")
  void testPutChangesValueAfterConstruction() {
    indexedList.put(2, 7);
    assertEquals(7, indexedList.get(2));
  }

  @Test
  @DisplayName("put() overwrites the existing value at given index to provided value.")
  void testPutUpdatesValueAtGivenIndex() {
    indexedList.put(1, 8);
    assertEquals(8, indexedList.get(1));
    indexedList.put(1, -5);
    assertEquals(-5, indexedList.get(1));
  }

  @Test
  @DisplayName("put() used consecutively can add multiple values consecutively one at a time.")
  void testPutCanBeUsedMultipleTimes() {
    indexedList.put(1, 8);
    assertEquals(8, indexedList.get(1));
    indexedList.put(2, -5);
    assertEquals(-5, indexedList.get(2));
  }

  @Test
  @DisplayName("Put behaves as expected in a more complex case.")
  void testPutComplex1() {
    indexedList.put(1, 8);
    indexedList.put(2, 3);
    indexedList.put(5, 5);
    indexedList.put(1, INITIAL);
    indexedList.put(0, 3);
    indexedList.put(0, INITIAL);
    indexedList.put(9, 5);
    indexedList.put(0, 4);
    indexedList.put(9,INITIAL);
    indexedList.put(3,INITIAL);

    int[] comparisonArray = new int[10];
    for (int i = 0; i < 10; i++) {
      comparisonArray[i] = 7;
    }
    comparisonArray[0] = 4;
    comparisonArray[1] = INITIAL;
    comparisonArray[2] = 3;
    comparisonArray[5] = 5;
    comparisonArray[9] = INITIAL;

    for (int i = 0; i < LENGTH; i++) { // length == 10
      assertEquals(comparisonArray[i], indexedList.get(i));
    }
  }

  @Test
  @DisplayName("test iterator after IndexedList is instantiated.")
  void testEnhancedLoopAfterConstruction() {
    int counter = 0;
    for (int element : indexedList) {
      assertEquals(INITIAL, element);
      counter++;
    }
    assertEquals(LENGTH, counter);
  }

  @Test
  @DisplayName("iterator prints all default and non-default in the sparse list")
  void testEnhancedLoopAfterAddingNonDefaultNodes() {
    setup();
    indexedList.put(0, 3);
    indexedList.put(4,3);
    indexedList.put(9,14);
    int counter = 0;
    for(int i: indexedList) {
      assertEquals(i, indexedList.get(counter));
      counter++;
    }
  }

  @Test
  @DisplayName("test length after construction")
  void testLengthAfterConstruction() {
    assertEquals(LENGTH, indexedList.length());
  }

  @Test
  @DisplayName("constructor throws exception if length not in range.")
  void testConstructorThrowsLengthException() {
    try {
      IndexedList<Integer> intlist = new SparseIndexedList<>(-1,3);
      fail("Length Exception was not thrown for size < 0");
    } catch (LengthException ex) {
      return;
    }
  }

  @Test
  @DisplayName("iterator throws NoSuchElementException when hasNext() is false.")
  void testThrowNoSuchElementExceptionUponAccessingIndexPastList() {
    try {
      IndexedList<Integer> intlist = new SparseIndexedList<>(3, 1);
      Iterator<Integer> it = intlist.iterator();
      for(int i = 0; i < 5; i++) {
        it.next(); // this method inherently tests whether hasNext() is true.
      }
    } catch(Exception NoSuchElementException) {
      return;
    }
  }

  @Test
  @DisplayName("Iterator's next and hasNext functions yield correct values.")
  void testIteratorNextAndHasNextYieldingExpectedValues() {
    Iterator<Integer> it = indexedList.iterator();
    while(it.hasNext()) {
      int val = it.next();
      assertEquals(val, INITIAL);
    }
  }

  @Test
  @DisplayName("Can put a value into the first element of the list")
  void putIntoFirstElementOfList() {
    indexedList.put(0,1);
    assertEquals(1, indexedList.get(0));
  }


}
