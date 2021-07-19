package LinkedList.SinglyListList;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    // Internal node class to represent data
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    // Empty this linked list, O(n)
    public void clear() {
        Node<T> ptr = head;
        while (ptr != null) {
            Node<T> next = ptr.next;
            ptr.next = null;
            ptr.data = null;
            ptr = next;
        }
        head = tail = ptr = null;
        size = 0;
    }

    // Return the size of this linked list
    public int size() {
        return size;
    }

    // Is this linked list empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // Add an element to the tail of the linked list, O(1)
    public void add(T elem) {
        addLast(elem);
    }

    // Add a node to the tail of the linked list, O(1)
    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null);
        } else {
            tail.next = new Node<T>(elem, null);
            tail = tail.next;
        }
        size++;
    }

    // Add an element to the beginning of this linked list, O(1)
    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null);
        } else {

            Node<T> temp = new Node<T>(elem, head);
            head = temp;
        }
        size++;
    }

    // Add an element at a specified index
    public void addAt(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Illegal Index");
        }
        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            addLast(data);
            return;
        }

        Node<T> temp = head;
        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node<T> newNode = new Node<>(data, temp.next);
        temp.next = newNode;

        size++;
    }

    // Check the value of the first node if it exists, O(1)
    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException("Empty list");
        return head.data;
    }

    // Check the value of the last node if it exists, O(1)
    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException("Empty list");
        return tail.data;
    }

    // Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        // Can't remove data from an empty list
        if (isEmpty())
            throw new RuntimeException("Empty list");

        // Extract the data at the head and move
        // the head pointer forwards one node
        T data = head.data;
        head = head.next;
        --size;

        // If the list is empty set the tail to null
        if (isEmpty())
            tail = null;

        // Return the data that was at the first node we just removed
        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        // Can't remove data from an empty list
        if (isEmpty())
            throw new RuntimeException("Empty list");

        // Extract the data at the tail and move
        // the tail pointer backwards one node
        T data = tail.data;
        Node<T> ptr = head;
        Node<T> prev = head;
        while (ptr.next != null) {
            prev = ptr;
            ptr = ptr.next;
        }
        tail = prev;
        --size;

        // If the list is now empty set the head to null
        if (isEmpty())
            head = null;

        // Do a memory clean of the node that was just removed
        else
            tail.next = null;

        // Return the data that was in the last node we just removed
        return data;
    }

    // Remove an arbitrary node from the linked list, O(1)
    private T remove(Node<T> node) {
        // If the node to remove is somewhere either at the
        // head or the tail handle those independently
        if (node == head)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        // Make the pointers of adjacent nodes skip over 'node'
        // node.next.prev = node.prev;
        // node.prev.next = node.next;
        Node<T> ptr = head;
        Node<T> prev = head;
        while (ptr.next != null || ptr != node) {
            prev = ptr;
            ptr = ptr.next;
        }

        prev.next = node.next;

        // Temporarily store the data we want to return
        T data = node.data;

        // Memory cleanup
        node.data = null;
        node = node.next = null;

        --size;

        // Return the data in the node we just removed
        return data;
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        // Make sure the index provided is valid
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        int i;
        Node<T> ptr;

        // Search from the front of the list
        if (index < size / 2) {
            for (i = 0, ptr = head; i != index; i++) {
                ptr = ptr.next;
            }
            // Search from the back of the list
        } else
            for (i = size - 1, ptr = tail; i != index; i--) {
                // ptr = ptr.prev;
            }

        return remove(ptr);
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(Object obj) {
        Node<T> ptr;

        // Support searching for null
        if (obj == null) {
            for (ptr = head; ptr != null; ptr = ptr.next) {
                if (ptr.data == null) {
                    remove(ptr);
                    return true;
                }
            }
            // Search for non null object
        } else {
            for (ptr = head; ptr != null; ptr = ptr.next) {
                if (obj.equals(ptr.data)) {
                    remove(ptr);
                    return true;
                }
            }
        }
        return false;
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> ptr = head;

        // Support searching for null
        if (obj == null) {
            for (; ptr != null; ptr = ptr.next, index++) {
                if (ptr.data == null) {
                    return index;
                }
            }
            // Search for non null object
        } else
            for (; ptr != null; ptr = ptr.next, index++) {
                if (obj.equals(ptr.data)) {
                    return index;
                }
            }

        return -1;
    }

    // Check is a value is contained within the linked list
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> ptr = head;

            @Override
            public boolean hasNext() {
                return ptr != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = ptr.data;
                ptr = ptr.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> ptr = head;
        while (ptr != null) {
            sb.append(ptr.data);
            if (ptr.next != null) {
                sb.append(", ");
            }
            ptr = ptr.next;
        }
        sb.append(" ]");
        return sb.toString();
    }

}
