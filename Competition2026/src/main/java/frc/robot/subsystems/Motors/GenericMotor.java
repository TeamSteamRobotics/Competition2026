// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Motors;

public interface GenericMotor { 
    void set(double output); // Open-loop control
    void setVoltage(double voltage); // Voltage control
    void setPosition(double rotations); // Closed-loop position control
    void overridePosition(double rotations);

    double getPosition(); // Relative position (internal encoder)
    double getVelocity(); // Velocity in RPM
    double getAbsolutePosition(); // Absolute position from external encoder
}
