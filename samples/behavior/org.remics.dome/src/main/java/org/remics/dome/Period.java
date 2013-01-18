/**
 * This file is part of Mediation Portal [ http://mosser.github.com/mediation-portal ]
 *
 * Copyright (C) 2012-  SINTEF ICT
 * Contact: Franck Chauvel <franck.chauvel@sintef.no>
 *
 * Module: root
 *
 * Mediation Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Mediation Portal is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Mediation Portal. If not, see
 * <http://www.gnu.org/licenses/>.
 */
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
