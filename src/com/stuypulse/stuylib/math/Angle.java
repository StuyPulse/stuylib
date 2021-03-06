/* Copyright (c) 2021 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.math;

import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * This angle class is made to remove the ambiguity of units when passing or returning angles. It
 * stores it in radians, but returns it in any unit depending on what the user requests. All of the
 * functions and math normalize the angle to help issues.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public final class Angle {

    // Configuration for toString() function
    private static final boolean STRING_RADIANS = false;
    private static final int STRING_SIGFIGS = 5;

    /******************************************************/
    /*** CONSTANT ANGLE VALUES, SET AT BENCHMARK VALUES ***/
    /******************************************************/

    public static final Angle kRadiant = Angle.fromRadians(1.0);

    public static final Angle kDegree = Angle.fromDegrees(1.0);

    public static final Angle kArcMinute = Angle.fromArcMinutes(1.0);
    public static final Angle kArcSecond = Angle.fromArcSeconds(1.0);

    public static final Angle k0deg = Angle.fromDegrees(0);
    public static final Angle k30deg = Angle.fromDegrees(30);
    public static final Angle k45deg = Angle.fromDegrees(45);
    public static final Angle k60deg = Angle.fromDegrees(60);
    public static final Angle k90deg = Angle.fromDegrees(90);
    public static final Angle k120deg = Angle.fromDegrees(120);
    public static final Angle k135deg = Angle.fromDegrees(135);
    public static final Angle k150deg = Angle.fromDegrees(150);
    public static final Angle k180deg = Angle.fromDegrees(180);
    public static final Angle k210deg = Angle.fromDegrees(210);
    public static final Angle k225deg = Angle.fromDegrees(225);
    public static final Angle k240deg = Angle.fromDegrees(240);
    public static final Angle k270deg = Angle.fromDegrees(270);
    public static final Angle k300deg = Angle.fromDegrees(300);
    public static final Angle k315deg = Angle.fromDegrees(315);
    public static final Angle k330deg = Angle.fromDegrees(330);

    /********************************/
    /*** PRIVATE HELPER FUNCTIONS ***/
    /********************************/

    private static final double TAU = Math.PI * 2.0;

    /**
     * @param radians the angle to be normalized
     * @param center the center of the normalized range +/- pi
     * @return the normalized angle
     */
    private static double normalizeRadians(double radians, double center) {
        return radians - TAU * Math.round((radians - center) / TAU);
    }

    /**
     * @param degrees the angle to be normalized
     * @param center the center of the normalized range +/- 180
     * @return the normalized angle
     */
    private static double normalizeDegrees(double degrees, double center) {
        return degrees - 360.0 * Math.round((degrees - center) / 360.0);
    }

    /********************************/
    /*** ANGLE CASHE OPTIMIZATION ***/
    /********************************/

    private static final Angle ANGLE_CASHE[] = new Angle[360];

    static {
        for (int degrees = 0; degrees < ANGLE_CASHE.length; ++degrees)
            ANGLE_CASHE[degrees] = new Angle(Math.toRadians(degrees));
    }

    /**
     * Get angle from cashed angle lookup table
     *
     * @param degrees degrees of the resulting angle
     * @return the cashed angle
     */
    private static final Angle cashedAngle(int degrees) {
        return ANGLE_CASHE[degrees - 360 * (int) (degrees / 360)];
    }

    /*************************/
    /*** FACTORY FUNCTIONS ***/
    /*************************/

    /**
     * Construct a new Angle class with a double in radians
     *
     * @param radians the angle for the new angle class in radians
     * @return an angle class with the specified angle
     */
    public static Angle fromRadians(double radians) {
        return new Angle(radians);
    }

    /**
     * Construct a new Angle class with a double in degrees
     *
     * @param degrees the angle for the new angle class in degrees
     * @return an angle class with the specified angle
     */
    public static Angle fromDegrees(double degrees) {
        return fromRadians(Math.toRadians(degrees));
    }

    /**
     * Return a new Angle class with an integer in degrees.
     *
     * <p>Because the value that the angle is created from is an integer, every possible angle has
     * been precalculated. You can use this method if you are worried about memory usage or
     * computation time.
     *
     * @param degrees the angle for the new angle class in degrees
     * @return an angle class with the specified angle
     */
    public static Angle fromDegrees(int degrees) {
        return cashedAngle(degrees);
    }

    /**
     * Construct a new Angle class with a double in arcminute
     *
     * @param arcminutes the angle for the new angle class in arcminutes
     * @return an angle class with the specified angle
     */
    public static Angle fromArcMinutes(double arcminutes) {
        return fromDegrees(arcminutes / 60.0);
    }

    /**
     * Construct a new Angle class with a double in arcseconds
     *
     * @param arcseconds the angle for the new angle class in arcseconds
     * @return an angle class with the specified angle
     */
    public static Angle fromArcSeconds(double arcseconds) {
        return fromArcMinutes(arcseconds / 60.0);
    }

    /**
     * Contstruct a new Angle class from x and y positions.
     *
     * <p>You can think of this as getting the angle from rise [y] over run [x].
     *
     * @param x the run of the vector
     * @param y the rise of the vector
     * @return the new angle class
     */
    public static Angle fromVector(double x, double y) {
        return fromRadians(Math.atan2(y, x));
    }

    /**
     * Construct a new Angle class from a vector.
     *
     * <p>This is identicle to calling fromVector(x, y) but by passing in the vectors x and y
     * values.
     *
     * @param vector the vector from which to get the angle from
     * @return the angle of the vector
     */
    public static Angle fromVector(Vector2D vector) {
        return fromVector(vector.x, vector.y);
    }

    /**
     * Construct a new Angle from a slope.
     *
     * <p>This will get you the angle of any slope in the form of rise / run.
     *
     * @param slope the slope to get the angle from
     * @return the angle of the slope
     */
    public static Angle fromSlope(double slope) {
        return fromRadians(Math.atan(slope));
    }

    /****************************/
    /*** CLASS IMPLEMENTATION ***/
    /****************************/

    /** The value of the angle stored in radians */
    private final double mRadians;

    // Precalculated Trig Values
    private final double mSin;
    private final double mCos;

    /** @param radians the value of the new angle */
    private Angle(double radians) {
        mRadians = normalizeRadians(radians, 0.0);
        mSin = Math.sin(mRadians);
        mCos = Math.cos(mRadians);
    }

    /** @return the value of the angle in radians centered around 0.0 (+/- pi) */
    public double toRadians() {
        return mRadians;
    }

    /**
     * @param center the angle in radians to be centered around (+/- pi)
     * @return the angle normalized around the center in radians
     */
    public double toRadians(double center) {
        return normalizeRadians(this.toRadians(), center);
    }

    /** @return the value of the angle in degrees centered around 0.0 (+/- 180) */
    public double toDegrees() {
        return Math.toDegrees(this.toRadians());
    }

    /**
     * @param center the angle in degrees to be centered around (+/- 180)
     * @return the angle normalized around the center in degrees
     */
    public double toDegrees(double center) {
        return normalizeDegrees(this.toDegrees(), center);
    }

    /**
     * Return the StuyLib Angle class in the form of the WPILib Rotation2d.
     *
     * <p>This function is here in order to make interoperability with WPILib easier so that manual
     * conversion isn't needed as much.
     *
     * @return Rotation2d with the same value as this angle
     */
    public Rotation2d getRotation2d() {
        return new Rotation2d(toRadians());
    }

    /**
     * Add two angles together
     *
     * @param other the other angle in the sum
     * @return the sum of the two angles [normalized from (-pi, pi)]
     */
    public Angle add(Angle other) {
        return fromRadians(this.toRadians() + other.toRadians());
    }

    /**
     * Subtract two angles from each other
     *
     * @param other the other angle to subtract
     * @return the first angle subtracted from the other [normalized from (-pi, pi)]
     */
    public Angle sub(Angle other) {
        return fromRadians(this.toRadians() - other.toRadians());
    }

    /**
     * Multiply the angle by a scalar value
     *
     * @param scale the scaler value to multiply the angle by
     * @return the multiplied angle [normalized from (-pi, pi)]
     */
    public Angle mul(double scale) {
        return fromRadians(this.toRadians() * scale);
    }

    /**
     * Divide the angle by a scalar value
     *
     * @param scale the scaler value to Divide the angle by
     * @return the divided angle [normalized from (-pi, pi)]
     */
    public Angle div(double scale) {
        return fromRadians(this.toRadians() / scale);
    }

    /** @return an angle rotated by 180 degrees or Pi Radians */
    public Angle negative() {
        return fromRadians(0.0 - this.toRadians());
    }

    /** @return the sine value of this angle */
    public double sin() {
        return mSin;
    }

    /** @return the cosine value of this angle */
    public double cos() {
        return mCos;
    }

    /** @return the tangent value of this angle */
    public double tan() {
        return mSin / mCos;
    }

    /** @return the angle as a point on the unit circle */
    public Vector2D getVector() {
        return new Vector2D(this.cos(), this.sin());
    }

    /**
     * Compare Angle to another object
     *
     * @param other object to compare to
     * @return both objects are Angle and they equal eachother
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Angle) {
            return this.toRadians() == ((Angle) other).toRadians();
        }

        return false;
    }

    /** @return the hashCode of the double for the value in radians */
    @Override
    public int hashCode() {
        return Double.hashCode(this.toRadians());
    }

    /** @return the string representation of the angle */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Angle(");

        if (STRING_RADIANS) {
            out.append(SLMath.round(this.toRadians(), STRING_SIGFIGS)).append("pi, ");
        }

        out.append(SLMath.round(this.toDegrees(), STRING_SIGFIGS)).append("deg)");

        return out.toString();
    }
}
