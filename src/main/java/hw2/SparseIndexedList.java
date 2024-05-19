package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {

  private Node<T> head;
  private int length;
  private T defaultVal;


  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */
  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    // TODO - upon construction, get() ensures that any valid index will return default, cuz no Nodes added yet
    if (size <= 0) {
      throw new LengthException();
    }
    length = size;
    head = null; // at first, head is null and it's only default values that we have. Is it right to initialize to null?
    defaultVal = defaultValue;


  }

  @Override
  public int length() {
    // TODO - should be done.
    return length; // was initially 0
  }



  private boolean isValid(int index) {
    return index >= 0 && index < length();
  } //mine

  @Override
  public T get(int index) throws IndexException {
    // TODO
    if (!isValid(index)) {
      throw new IndexException();
    }

    Node<T> node = head;
    while (node != null) {
      if (node.index == index) {
        return node.data;
      }
      node = node.next;
    }
    // if we've exited the while loop, there was no matching node. Must return defaultVal
    return defaultVal;
    //return null;
  }

  private void prepend(int index, T value) {
    if (value != defaultVal) { // else, do nothing. Nothing to prepend
      Node<T> n = new Node<T>(index, value);
      n.data = value;
      n.index = index;
      n.next = head;
      head = n;
    }

  }

  private void append(int index, T value) {
    Node<T> n = new Node<T>(index, value);
    Node<T> current = head;
    while (current != null) {
      if (n.index == current.index) { // i.e. we are simply replacing the Node at current index.
        current.data = n.data; // this should override current with n. Same index, and the next Node is the same.
        break;
      } else if (n.index > current.index && current.next == null) { // append n to the end of the list
        current.next = n;
        n.next = null; // otherwise it would still point to head, making a circular list
        break;
      } else if (n.index > current.index && n.index < current.next.index) { // n goes between current and the one after
        n.next = current.next;
        current.next = n; // situate n in between current and current.next
        break;
      } else { // we need to keep going over the list.
        current = current.next;
      }
    }
  }

  private void removeNode(int index) throws IndexException { // head being default is taken care of.
    checkIndex(index);

    if (head.index == index) { // to get here, value must have been defaultVal.
      // And if immediatelyNext is Null, then head is // the only element of the list.
      head = head.next;  // So, get rid of the current Head (but leave the rest intact)
      return;// don't set head to null - if there's a whole data structure after it,
      // just want to delete head, not the entire thing after it too
    }

    Node<T> prev = head; // Head might still have defaultVal -
    // that hasn't been checked yet by put(). This method will take care of that.
    Node<T> curr = head.next;

    while (curr != null) {
      if (curr.index == index) {
        prev.next = curr.next;
        return;
      }
      prev = curr;
      curr = curr.next;
    }
  }

  private void checkIndex(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    // TODO
    // there are two main parts to this method. Putting nodes in in the right order - that is, after
    // put() finishes, nodes should always be connected in ascending order with respect to their
    // indices - and secondly, removal. Removal shouldn't be too hard if nodes are already in order.

    // 2/14 7pm: What this program should do is initially add a node at the given index,
    // even if it has default val, and then if it has default val, delete it and reconnect
    // remaining nodes accordingly

    checkIndex(index);

    if (head == null && value == defaultVal) { // if this is true, the list is empty,
      // and we're just adding another defaulVal (i.e. nothing)
    } else if (head == null) { // make the new Node the Head.
      head = new Node<T>(index, value);
      //return;
    } else if (value == defaultVal) {
      //case 1 : non-default value node exists at index -> removeNode(index)
      //case 2:
      removeNode(index);
    } else if (index >= head.index) { // this accounts for all remaining cases where
      // we are overwriting an existing Node
      append(index, value); // with non-default value, because append() handles that.
      //handle insertion after the head
      //which would involve iterating through the linked list until the correct position
    } else { // head could store defaultVal. But prepend will only actually add a Node if
      prepend(index, value); // its value is not defaultVal. So prepend should account for that.
    } // prepend need not check whether we are overwriting Head. Append() already did.


  }

  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  private static class Node<T> {
    T data;
    Node<T> next;
    int index;

    Node(int ind, T value) {
      this.data = value;
      this.index = ind;
      this.next = null;
    }
  }

  private class SparseIndexedListIterator implements Iterator<T> {

    private Node<T> current;
    private int cursor;

    SparseIndexedListIterator() {
      //the nodes should all be ordered by index already, thanks to put()
      current = head;
      cursor = 0;
    }

    @Override
    public boolean hasNext() {
      // TODO
      return cursor < length; //MINE we want to go all the way to length-1 even if it's just default
      // returning current != null would mean if we start with an empty list,
      // it doesn't iterate at all - it means any time we come across a null,
      // it just discontinues the loop.
    }

    @Override
    public T next() throws NoSuchElementException {
      // TODO
      if (!hasNext()) { //mine
        throw new NoSuchElementException();
      }


      if ((current != null) && (cursor == current.index)) {
        // if you don't check that current isn't null,
        // it will crash because cannot access index of Null
        cursor++;
        T t = current.data;
        current = current.next;
        return t;
      } else {
        cursor++;
        return defaultVal;
      }


      // return null;
    }
  }
}