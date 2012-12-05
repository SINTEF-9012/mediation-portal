package org.remics.dome;

public class Pickup implements java.io.Serializable {

    private String _code;

    public Pickup(String p) {
        _code = p;
    }

    @Override
    public String toString() {
        return "Pickup(" + _code + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pickup)) {
            return false;
        }
        return _code.equals(((Pickup) o)._code);
    }
}
