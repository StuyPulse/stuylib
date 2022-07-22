/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.streams.angles.filters;

import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.util.StopWatch;

/**
 * A filter, that when applied to the input of a motor, will profile it. Similar to the way in which
 * motion profiling can limit the amount of acceleration and jerk in an S-Curve, this can do that to
 * real time input. Because this will add a delay, it is recommended that the jerklimit is as high
 * as possible. Aside from the jerklimit, this is identicle to a SlewRateLimiter or TimedRateLimit.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class AJerkLimit implements AFilter {

    // Default number of times to apply filter (helps accuracy)
    private static final int kDefaultSteps = 64;

    // Stopwatch to Track dt
    private StopWatch mTimer;

    // Limits for each of the derivatives
    private Number mAccelLimit;
    private Number mJerkLimit;

    // The last output / acceleration
    private Angle mOutput;
    private double mAccel;

    // Number of times to apply filter (helps accuracy)
    private final int mSteps;

    /**
     * @param accelLimit maximum change in velocity per second (u/s)
     * @param jerkLimit maximum change in acceleration per second (u/s/s)
     * @param steps number of times to apply filter (improves accuracy)
     */
    public AJerkLimit(Number accelLimit, Number jerkLimit, int steps) {
        mTimer = new StopWatch();

        mAccelLimit = accelLimit;
        mJerkLimit = jerkLimit;

        mOutput = Angle.kZero;
        mAccel = 0;

        mSteps = steps;
    }

    /**
     * @param accelLimit maximum change in velocity per second (u/s)
     * @param jerkLimit maximum change in acceleration per second (u/s/s)
     */
    public AJerkLimit(Number accelLimit, Number jerkLimit) {
        this(accelLimit, jerkLimit, kDefaultSteps);
    }

    public Angle get(Angle target) {
        double dt = mTimer.reset() / mSteps;

        for (int i = 0; i < mSteps; ++i) {
            // if there is a jerk limit, limit the amount the acceleration can change
            if (0 < mJerkLimit.doubleValue()) {
                // amount of windup in system (how long it would take to slow down)
                double windup = Math.abs(mAccel) / mJerkLimit.doubleValue();

                // If the windup is too small, just use normal algorithm to limit acceleration
                if (windup < dt) {
                    // Calculate acceleration needed to reach target
                    double accel = target.velocityRadians(mOutput, dt) - mAccel;

                    // Try to reach it while abiding by jerklimit
                    mAccel += SLMath.clamp(accel, dt * mJerkLimit.doubleValue());
                } else {
                    // the position it would end up if it attempted to come to a full stop
                    Angle future =
                            mOutput.addRadians(
                                    0.5 * mAccel * (dt + windup)); // where the robot will end up

                    // Calculate acceleration needed to come to stop at target throughout windup
                    double accel = target.velocityRadians(future, windup);

                    // Try to reach it while abiding by jerklimit
                    mAccel += SLMath.clamp(accel, dt * mJerkLimit.doubleValue());
                }

            } else {
                // make the acceleration the difference between target and current
                mAccel = target.velocityRadians(mOutput, dt);
            }

            // if there is an acceleration limit, limit the acceleration
            if (0 < mAccelLimit.doubleValue()) {
                mAccel = SLMath.clamp(mAccel, mAccelLimit.doubleValue());
            }

            // adjust output by calculated acceleration
            mOutput = mOutput.addRadians(dt * mAccel);
        }

        return mOutput;
    }
}
