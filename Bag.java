import java.util.Iterator;
// A multiset using linked list
// no delete.

public class Bag<T> implements Iterable<T> {
    private Node  first;
    private int size;

    private class Node {
        private T     value;
        private Node  next;
    }

    public Bag() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(T elem) {
      Node elemNode = new Node();
      elemNode.value = elem;
      elemNode.next = first;
      first = elemNode;
      size++;
    }

    public Iterator<T> iterator()  {
        return new Iterator(){
          Node current = first;
          public boolean hasNext()  {
              return current != null;
          }
          public T next() {
              T val = current.value;
              current = current.next;
              return val;
          }
        };
    }

    public static void main(String[] args) {

      Bag<String> bag = new Bag<String>();
      for (String w : args){
        bag.add(w);
      }
      
      for(String w : bag){
        System.out.println(w);
      }
    }
  }
