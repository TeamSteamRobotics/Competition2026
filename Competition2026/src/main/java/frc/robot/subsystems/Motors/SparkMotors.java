// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Motors;

import java.time.format.ResolverStyle;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SparkMotors extends SubsystemBase implements GenericMotor{
  private SparkMax motor;
  private RelativeEncoder relativeEncoder;
  private AbsoluteEncoder absoluteEncoder;
  SparkClosedLoopController sparkPid;

  boolean isPid = false;

  int m_canId;

  SparkMaxConfig pidConfig;

  /** Creates a new SparkMaxMotor. */
  public SparkMotors(int canID) {
    isPid = false;
    m_canId = canID;
    motor = new SparkMax(canID, MotorType.kBrushless);
    relativeEncoder = motor.getEncoder();
    absoluteEncoder = motor.getAbsoluteEncoder();
  }

  public SparkMotors(int canID, double kP, double kI, double kD, double minPower, double maxPower) {
    isPid = true;
    motor = new SparkMax(canID, MotorType.kBrushless);
    pidConfig = new SparkMaxConfig();
    pidConfig.closedLoop
      .p(kP)
      .i(kI)
      .d(kD)
      .outputRange(minPower, maxPower);

    sparkPid = motor.getClosedLoopController();

    motor.configure(pidConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    relativeEncoder = motor.getEncoder();
    absoluteEncoder = motor.getAbsoluteEncoder();
  }

  public SparkMotors(int canID, double kP, double kI, double kD, double minPower, double maxPower, boolean inverted) {
    isPid = true;
    motor = new SparkMax(canID, MotorType.kBrushless);
    pidConfig = new SparkMaxConfig();
    pidConfig.closedLoop
      .p(kP)
      .i(kI)
      .d(kD)
      .outputRange(minPower, maxPower);

    pidConfig.inverted(inverted);

    sparkPid = motor.getClosedLoopController();

    motor.configure(pidConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    relativeEncoder = motor.getEncoder();
    absoluteEncoder = motor.getAbsoluteEncoder();
  }

    public void set(double output) {
      if(isPid) {
        sparkPid.setSetpoint(0, ControlType.kDutyCycle);
      }
      motor.set(output);
    }

    @Override
    public void setVoltage(double voltage) {
       sparkPid.setSetpoint(0, ControlType.kVoltage);
       motor.setVoltage(voltage);
    }

    @Override
    public void setPosition(double rotations) {
        sparkPid.setSetpoint(rotations, ControlType.kPosition);
         //Use the internal PID to move to position
    }

    @Override
    public double getPosition() {
        return relativeEncoder.getPosition();  // Returns relative encoder position (resets at power cycle)
    }

    @Override
    public double getVelocity() {
        return relativeEncoder.getVelocity();  // Returns velocity in RPM
    }

    @Override
    public double getAbsolutePosition() {
        return absoluteEncoder.getPosition();  // Returns absolute encoder position (persists across power cycles)
   }

    @Override
    public void overridePosition(double rotations) {
        relativeEncoder.setPosition(rotations);
    }

    @Override
    public void periodic() {
      Logger.recordOutput("MotorOutputs/MOTOR_" + m_canId + "_Output", motor.get());
    }
}
