package com.otto15.common.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represent location by x, y, z arguments
 * @author Rakhmatullin R.
 */
public class Location implements Serializable, Comparable<Location> {
    private double x;
    private long y;
    private float z;

    public Location(double x, long y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return x + "; " + y + "; " + z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 && y == location.y && Float.compare(location.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public int compareTo(Location otherLocation) {
        return Comparator.comparing(Location::getX)
                .thenComparing(Location::getY)
                .thenComparing(Location::getZ)
                .compare(this, otherLocation);
    }
}
