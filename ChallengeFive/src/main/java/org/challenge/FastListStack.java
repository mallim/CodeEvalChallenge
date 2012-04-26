package org.challenge;

import javolution.util.FastList;

import java.util.Collection;
import java.util.Iterator;

/**
 */
public class FastListStack<E> implements Iterable<E>{

    private FastList<E> inner;

    public FastListStack(){
        inner = new FastList<E>();
    }

    public FastListStack( Collection<E> collection )
    {
        inner = new FastList<E>();
        for( E element: collection ){
            push( element );
        }
    }

    public boolean isEmpty() {
        return inner.isEmpty();
    }

    public int size() {
        return inner.size();
    }

    public void push(E item){
        inner.addFirst(item);
    }

    public E pop( ){
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        return inner.removeFirst();
    }

    public Iterator<E> iterator() {
        return inner.iterator();
    }

    @Override public String toString() {
        return inner.toString();
    }
}
