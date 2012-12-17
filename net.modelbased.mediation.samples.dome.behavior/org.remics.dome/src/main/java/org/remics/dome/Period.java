package org.remics.dome;

public class Period implements java.io.Serializable {

    private String _code;
    private java.util.LinkedList<Pickup> _pickupList = new java.util.LinkedList<Pickup>();

    public Period(String p) {
        _code = p;
    }
    
    @Override
    public String toString() {
        return "Period(" + _code + ") with " + _pickupList.size() + " pickups";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Period)) {
            return false;
        }
        return _code.equals(((Period) o)._code);
    }

    public void add_pickup(Pickup p) {
        _pickupList.addLast(p);
    }

    public void remove_pickup(Pickup p) {
        _pickupList.removeLastOccurrence(p);
    }
}
