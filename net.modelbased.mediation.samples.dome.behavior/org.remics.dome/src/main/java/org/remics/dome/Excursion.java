package org.remics.dome;

public class Excursion implements java.io.Serializable {

    private java.util.LinkedList<Period> _periodList = new java.util.LinkedList<Period>();

    public void add_period(Period p) {
        _periodList.addLast(p);
        System.out.println("PERIOD ADDED: " + p);
    }

    public void remove_period(Period p) {
        _periodList.removeLastOccurrence(p);
        System.out.println("PERIOD REMOVED: " + p);
    }

    public void add_pickup(Pickup p) {
        _periodList.getLast().add_pickup(p);
        System.out.println("PICKUP ADDED: " + p);
    }

    public void remove_pickup(Pickup p) {
        _periodList.getLast().remove_pickup(p);
        System.out.println("PICKUP REMOVED: " + p);
    }
}
