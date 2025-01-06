/* Copyright (c) 2025 StuyPulse Robotics. All rights reserved. */
/* This work is licensed under the terms of the MIT license */
/* found in the root directory of this project. */

package com.stuypulse.stuylib.input;

import com.stuypulse.stuylib.math.Vector2D;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * An class for using gamepads with different interfaces. You can implement this class in another
 * file, and then use it with a standard interface.
 *
 * <p>Any unimplemented buttons will never be triggered, so if a certain controller is missing one,
 * dont worry about it.
 *
 * <p>The button type that is used in OI.java is automatically generated by this class, as long as
 * you implement the {@code getRaw...()} function, the {@code getRaw...()} function will work.
 *
 * <p>This class does not come with a constructor because by itself, the class does not do anything.
 * You need to extend this class in order to use it.
 *
 * @author Sam (sam.belliveau@gmail.com)
 */
public class Gamepad implements Sendable {

    /*******************/
    /*** CONSTRUCTOR ***/
    /*******************/

    public Gamepad() {}

    /*******************************/
    /*** IMPLEMENTABLE FUNCTIONS ***/
    /*******************************/

    /** @return The name of the gamepad being used */
    public String getGamepadName() {
        return this.getClass().getSimpleName();
    }

    // Left Stick //
    /** @return The X position of the left analog stick */
    public double getLeftX() {
        return 0.0;
    }

    /** @return The Y position of the left analog stick */
    public double getLeftY() {
        return 0.0;
    }

    // Right Stick //
    /** @return The X position of the right analog stick */
    public double getRightX() {
        return 0.0;
    }

    /** @return The Y position of the right analog stick */
    public double getRightY() {
        return 0.0;
    }

    // D-Pad //
    /** @return If the up d-pad button is pressed */
    public boolean getRawDPadUp() {
        return false;
    }

    /** @return If the down d-pad button is pressed */
    public boolean getRawDPadDown() {
        return false;
    }

    /** @return If the left d-pad button is pressed */
    public boolean getRawDPadLeft() {
        return false;
    }

    /** @return If the right d-pad button is pressed */
    public boolean getRawDPadRight() {
        return false;
    }

    // Bumpers //
    /** @return If the left bumper is pressed */
    public boolean getRawLeftBumper() {
        return false;
    }

    /** @return If the right bumper is pressed */
    public boolean getRawRightBumper() {
        return false;
    }

    // Triggers //
    /** @return The amount that the left trigger is pressed */
    public double getLeftTrigger() {
        return 0.0;
    }

    /** @return The amount that the right trigger is pressed */
    public double getRightTrigger() {
        return 0.0;
    }

    // Face Buttons //
    /** @return If the face button on the top is pressed */
    public boolean getRawTopButton() {
        return false;
    }

    /** @return If the face button on the bottom is pressed */
    public boolean getRawBottomButton() {
        return false;
    }

    /** @return If the face button on the left is pressed */
    public boolean getRawLeftButton() {
        return false;
    }

    /** @return If the face button on the right is pressed */
    public boolean getRawRightButton() {
        return false;
    }

    // Left Menu / Right Menu //
    /** @return If the left menu button is pressed */
    public boolean getRawLeftMenuButton() {
        return false;
    }

    /** @return If the right menu button is pressed */
    public boolean getRawRightMenuButton() {
        return false;
    }

    // Analog Stick Buttons //
    /** @return If the left analog stick is pressed down */
    public boolean getRawLeftStickButton() {
        return false;
    }

    /** @return If the right analog stick is pressed down */
    public boolean getRawRightStickButton() {
        return false;
    }

    // Rumble //
    /** @param intensity amount to make the gamepad rumble */
    public void setRumble(double intensity) {
        return;
    }

    /*************************************************/
    /*** HELPER FUNCTIONS BASED ON IMPLEMENTATIONS ***/
    /*************************************************/

    // Left Stick //
    /** @return The position of the left analog stick in a {@link Vector2D} */
    public final Vector2D getLeftStick() {
        return new Vector2D(this.getLeftX(), this.getLeftY());
    }

    // Right Stick //
    /** @return The position of the right analog stick in a {@link Vector2D} */
    public final Vector2D getRightStick() {
        return new Vector2D(this.getRightX(), this.getRightY());
    }

    // D-Pad //
    /** @return The x position of the d-pad as if it were a stick */
    public final double getDPadX() {
        return (getRawDPadRight() ? 1.0 : 0.0) - (getRawDPadLeft() ? 1.0 : 0.0);
    }

    /** @return The y position of the d-pad as if it were a stick */
    public final double getDPadY() {
        return (getRawDPadUp() ? 1.0 : 0.0) - (getRawDPadDown() ? 1.0 : 0.0);
    }

    /** @return The position of the d-pad as if it were a stick in a Vector2D */
    public final Vector2D getDPad() {
        return new Vector2D(this.getDPadX(), this.getDPadY());
    }

    // Triggers //
    public static final double ANALOG_THRESHOLD = 1.0 / 4.0;

    /** @return If the left trigger is pressed down more than {@link #ANALOG_THRESHOLD} */
    public final boolean getLeftTriggerPressed() {
        return getLeftTrigger() > ANALOG_THRESHOLD;
    }

    /** @return If the left trigger is pressed down more than {@link #ANALOG_THRESHOLD} */
    public final boolean getRightTriggerPressed() {
        return getRightTrigger() > ANALOG_THRESHOLD;
    }

    /***************************************************/
    /*** TRIGGERS BASED OFF OF IMPLEMENTED FUNCTIONS ***/
    /***************************************************/

    // Sticks //
    /**
     * @return Trigger that activates when {@link #getLeftY()} is greater than +{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getLeftStickUp() {
        return new Trigger(() -> getLeftY() > +ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getLeftY()} is less than -{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getLeftStickDown() {
        return new Trigger(() -> getLeftY() < -ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getLeftX()} is less than -{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getLeftStickLeft() {
        return new Trigger(() -> getLeftX() < -ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getLeftX()} is greater than +{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getLeftStickRight() {
        return new Trigger(() -> getLeftX() > +ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getRightY()} is greater than +{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getRightStickUp() {
        return new Trigger(() -> getRightY() > +ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getRightY()} is less than -{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getRightStickDown() {
        return new Trigger(() -> getRightY() < -ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getRightX()} is less than -{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getRightStickLeft() {
        return new Trigger(() -> getRightX() < -ANALOG_THRESHOLD);
    }

    /**
     * @return Trigger that activates when {@link #getRightX()} is greater than +{@link
     *     #ANALOG_THRESHOLD}
     */
    public final Trigger getRightStickRight() {
        return new Trigger(() -> getRightX() > +ANALOG_THRESHOLD);
    }

    // D-Pad //
    /** @return Trigger that activates with {@link #getRawDPadUp()} */
    public final Trigger getDPadUp() {
        return new Trigger(this::getRawDPadUp);
    }

    /** @return Trigger that activates with {@link #getRawDPadDown()} */
    public final Trigger getDPadDown() {
        return new Trigger(this::getRawDPadDown);
    }

    /** @return Trigger that activates with {@link #getRawDPadLeft()} */
    public final Trigger getDPadLeft() {
        return new Trigger(this::getRawDPadLeft);
    }

    /** @return Trigger that activates with {@link #getRawDPadRight()} */
    public final Trigger getDPadRight() {
        return new Trigger(this::getRawDPadRight);
    }

    // Bumpers //
    /** @return Trigger that activates with {@link #getRawLeftBumper()} */
    public final Trigger getLeftBumper() {
        return new Trigger(this::getRawLeftBumper);
    }

    /** @return Trigger that activates with {@link #getRawRightBumper()} */
    public final Trigger getRightBumper() {
        return new Trigger(this::getRawRightBumper);
    }

    // Triggers //
    /** @return Trigger that activates with {@link #getLeftTriggerPressed()} */
    public final Trigger getLeftTriggerButton() {
        return new Trigger(this::getLeftTriggerPressed);
    }

    /** @return Trigger that activates with {@link #getRightTriggerPressed()} */
    public final Trigger getRightTriggerButton() {
        return new Trigger(this::getRightTriggerPressed);
    }

    // Face Triggers //
    /** @return Trigger that activates with {@link #getRawTopButton()} */
    public final Trigger getTopButton() {
        return new Trigger(this::getRawTopButton);
    }

    /** @return Trigger that activates with {@link #getRawBottomButton()} */
    public final Trigger getBottomButton() {
        return new Trigger(this::getRawBottomButton);
    }

    /** @return Trigger that activates with {@link #getRawLeftButton()} */
    public final Trigger getLeftButton() {
        return new Trigger(this::getRawLeftButton);
    }

    /** @return Trigger that activates with {@link #getRawRightButton()} */
    public final Trigger getRightButton() {
        return new Trigger(this::getRawRightButton);
    }

    // Left Menu / Right Menu //
    /** @return Trigger that activates with {@link #getRawLeftMenuButton()} */
    public final Trigger getLeftMenuButton() {
        return new Trigger(this::getRawLeftMenuButton);
    }

    /** @return Trigger that activates with {@link #getRawRightMenuButton()} */
    public final Trigger getRightMenuButton() {
        return new Trigger(this::getRawRightMenuButton);
    }

    // Analog Stick Triggers //
    /** @return Trigger that activates with {@link #getRawLeftStickButton()} */
    public final Trigger getLeftStickButton() {
        return new Trigger(this::getRawLeftStickButton);
    }

    /** @return Trigger that activates with {@link #getRawRightStickButton()} */
    public final Trigger getRightStickButton() {
        return new Trigger(this::getRawRightStickButton);
    }

    /*******************************/
    /*** SENDABLE INITIALIZATION ***/
    /*******************************/

    @Override
    public final void initSendable(SendableBuilder builder) {
        // Name
        builder.addStringProperty("Gamepad Name", this::getGamepadName, x -> {});

        // Left Stick
        builder.addDoubleProperty("Left Stick X", this::getLeftX, x -> {});
        builder.addDoubleProperty("Left Stick Y", this::getLeftY, x -> {});

        // Right Stick
        builder.addDoubleProperty("Right Stick X", this::getRightX, x -> {});
        builder.addDoubleProperty("Right Stick Y", this::getRightY, x -> {});

        // D-Pad
        builder.addBooleanProperty("D-Pad Up", this::getRawDPadUp, x -> {});
        builder.addBooleanProperty("D-Pad Down", this::getRawDPadDown, x -> {});
        builder.addBooleanProperty("D-Pad Left", this::getRawDPadLeft, x -> {});
        builder.addBooleanProperty("D-Pad Right", this::getRawDPadRight, x -> {});

        // Bumpers
        builder.addBooleanProperty("Bumper Left", this::getRawLeftBumper, x -> {});
        builder.addBooleanProperty("Bumper Right", this::getRawRightBumper, x -> {});

        // Triggers
        builder.addDoubleProperty("Trigger Left", this::getLeftTrigger, x -> {});
        builder.addDoubleProperty("Trigger Right", this::getRightTrigger, x -> {});

        // Face Buttons
        builder.addBooleanProperty("Face Button Top", this::getRawTopButton, x -> {});
        builder.addBooleanProperty("Face Button Bottom", this::getRawBottomButton, x -> {});
        builder.addBooleanProperty("Face Button Left", this::getRawLeftButton, x -> {});
        builder.addBooleanProperty("Face Button Right", this::getRawRightButton, x -> {});

        // Left Menu / Right Menu //
        builder.addBooleanProperty("Button Left Menu", this::getRawLeftMenuButton, x -> {});
        builder.addBooleanProperty("Button Right Menu", this::getRawRightMenuButton, x -> {});

        // Analog Stick Buttons
        builder.addBooleanProperty("Left Stick Button", this::getRawLeftStickButton, x -> {});
        builder.addBooleanProperty("Right Stick Button", this::getRawRightStickButton, x -> {});
    }
}
