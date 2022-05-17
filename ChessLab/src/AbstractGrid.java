import java.util.ArrayList;

/**
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * AbstractGrid contains the methods that are common to grid implementations.
 *
 * @param <E> the Object stored in the AbstractGrid
 * @author Cay Horstmann
 * @author Nelson Gou
 * @version 3/28/22
 */
public abstract class AbstractGrid<E> implements Grid<E>
{
    /**
     * Returns a list of all neighbors (not their Locations) in the 8 cardinal directions.
     * @param loc a location in this grid
     * @return a list of all neighbors
     */
    public ArrayList<E> getNeighbors(Location loc)
    {
        ArrayList<E> neighbors = new ArrayList<E>();
        for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
            neighbors.add(get(neighborLoc));
        return neighbors;
    }

    /**
     * Generates a list of all valid adjacent locations (in 8 cardinal directions).
     * @param loc a location in this grid
     * @return an ArrayList of all valid adjacent locations
     */
    public ArrayList<Location> getValidAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();

        int d = Location.NORTH;
        for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            if (isValid(neighborLoc))
                locs.add(neighborLoc);
            d = d + Location.HALF_RIGHT;
        }
        return locs;
    }

    /**
     * Returns all empty adjacent locations (in 8 cardinal directions).
     * @param loc a location in this grid
     * @return all empty adjacent locations
     */
    public ArrayList<Location> getEmptyAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Location neighborLoc : getValidAdjacentLocations(loc))
        {
            if (get(neighborLoc) == null)
                locs.add(neighborLoc);
        }
        return locs;
    }

    /**
     * Returns all occupied adjacent locations (in 8 cardinal directions).
     * @param loc a location in this grid
     * @return all occupied adjacent locations
     */
    public ArrayList<Location> getOccupiedAdjacentLocations(Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Location neighborLoc : getValidAdjacentLocations(loc))
        {
            if (get(neighborLoc) != null)
                locs.add(neighborLoc);
        }
        return locs;
    }

    /**
     * Creates a String that describes this grid.
     * @return a String with descriptions of all objects in this grid (not
     *         necessarily in any particular order), in the format {loc=obj, loc=obj, ...}
     */
    public String toString()
    {
        String s = "{";
        for (Location loc : getOccupiedLocations())
        {
            if (s.length() > 1)
                s += ", ";
            s += loc + "=" + get(loc);
        }
        return s + "}";
    }
}
