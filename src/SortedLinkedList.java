import java.util.LinkedList;
import java.util.ListIterator;

public class SortedLinkedList<E extends Comparable<E>> extends LinkedList<E> {

    // New insertion method to ensure items are sorted in ascending order
    public void sortedInsert(E element) {
        if (isEmpty() || element.compareTo(getFirst()) <= 0) {
            // If the list is empty or the new element is smaller/equal to the first element
            addFirst(element);
        } else if (element.compareTo(getLast()) >= 0) {
            // If the new element is greater/equal to the last element
            addLast(element);
        } else {
            // Find the correct position to insert the element
            ListIterator<E> iterator = listIterator();
            while (iterator.hasNext()) {
                E current = iterator.next();
                if (element.compareTo(current) <= 0) {
                    iterator.previous(); // Move the iterator back
                    iterator.add(element); // Insert the element before the current element
                    return;
                }
            }
        }
    }
}
