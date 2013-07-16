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
package org.ota.specs;

public class Trip {

    private java.util.LinkedList<Period> _periodList = new java.util.LinkedList<Period>();

    public void add_trip(String period, String pickup) {
		final Period p = new Period(period);
        _periodList.addLast(p);
        p.add_pickup(new Pickup(pickup));
        System.out.println("TRIP ADDED: " + period + ", " + pickup);
    }

    public void remove_trip(String period, String pickup) {
		final Period p = new Period(period);
        _periodList.get(_periodList.lastIndexOf(p)).remove_pickup(new Pickup(pickup));
        _periodList.removeLastOccurrence(p);
        System.out.println("TRIP REMOVED: " + period + ", " + pickup);
    }
    
}
