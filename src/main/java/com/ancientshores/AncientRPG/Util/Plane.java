package com.ancientshores.AncientRPG.Util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 23.02.13
 * Time: 21:51
 */
public class Plane {
    Vector nv;
    Location p;

    public Plane(Vector v1, Vector v2, Location p) {
        this.nv = v1.crossProduct(v2);
        this.p = p;
    }

    public Plane(Vector nv, Location p) {
        this.nv = nv;
        this.p = p;
    }


    public double distance(Location p) {
        double val = -(this.p.toVector().dot(nv)) / nv.length();
        Vector normalized = nv.normalize();
        if (val > 0) {
            val *= -1;
            normalized.multiply(-1);

        }
        return normalized.getX() * p.getX() + normalized.getY() * p.getY() + normalized.getZ() * p.getZ() + val;
    }
}
