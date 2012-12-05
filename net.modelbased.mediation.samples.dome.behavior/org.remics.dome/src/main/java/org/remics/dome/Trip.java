/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.remics.dome;

/**
 *
 * @author bmori
 */
public class Trip {

    private java.util.LinkedList<Period> _periodList = new java.util.LinkedList<Period>();

    public void add_trip(Period period, Pickup pickup) {
        _periodList.addLast(period);
        period.add_pickup(pickup);
        System.out.println("TRIP ADDED: " + period + ", " + pickup);
    }

    public void remove_trip(Period period, Pickup pickup) {
        _periodList.get(_periodList.lastIndexOf(period)).remove_pickup(pickup);
        _periodList.removeLastOccurrence(period);
        System.out.println("TRIP REMOVED: " + period + ", " + pickup);
    }
    
}
