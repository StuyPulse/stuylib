/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.control.angle;

import com.stuypulse.stuylib.math.Angle;

/**
 * Angle controllers can be grouped together in a "controller group" if they have the same setpoint
 * and measurement.
 *
 * <p>This allows different controller implementations that are controlling the same variable to be
 * concisely composed together.
 *
 * @author Myles Pasetsky (myles.pasetsky@gmail.com)
 */
public class AngleControllerGroup extends AngleController {

    /** Controller part of the group */
    private final AngleController mController;

    /** Controllers part of the group */
    private final AngleController[] mControllers;

    /** Create a controller group */
    public AngleControllerGroup(AngleController controller, AngleController... controllers) {
        mController = controller;
        mControllers = controllers;
    }

    /**
     * Updates the internal controllers with the setpoint and measurement and returns their combined
     * output.
     *
     * @param setpoint setpoint
     * @param measurement measurement
     * @return summed output of the interal controllers
     */
    @Override
    protected double calculate(Angle setpoint, Angle measurement) {
        double output = mController.update(setpoint, measurement);

        for (AngleController controller : mControllers) {
            output += controller.update(setpoint, measurement);
        }

        return output;
    }
}
