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
