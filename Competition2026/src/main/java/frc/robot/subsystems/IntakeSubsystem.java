// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.ObjectInputFilter.Config;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.subsystems.Motors.GenericMotor;
import frc.robot.subsystems.Motors.SparkMotors;

public class IntakeSubsystem extends SubsystemBase {
  /** Creates a new IntakeSubsystem. */
  SparkMax intakePivotMotor = new SparkMax(Constants.intake.intakePivotID, MotorType.kBrushless);
  SparkFlex rollerMotor = new SparkFlex(Constants.intake.rollerId, SparkFlex.MotorType.kBrushless);
  // SparkFlexConfig rollerConfig = new SparkFlexConfig();
  // SparkMaxConfig pivotIntakeConfig = new SparkMaxConfig();

  //double m_intakePivotSpeed;

  // double m_targetAngle = Constants.intake.intakePivotMinEncoderValue;
  // SparkClosedLoopController intakePivotPIDMotor;

  // PIDController simPID;

  // SlewRateLimiter filter;

  public void StopMotor() {
    rollerMotor.set(0);
    intakePivotMotor.set(0);
  }

  //If sign if positive, intake goes out. IF sign is negative or zero, intake goes in.
  // public void toggleIntakePivot(int sign) {
  //   if (sign > 0) {
  //     m_targetAngle = Constants.intake.intakePivotMaxEncoderValue;
  //   }
  //   else if (sign <= 0) {
  //     m_targetAngle = Constants.intake.intakePivotMinEncoderValue;
  //   }
  // }

  public void runPivot(double speed) {
    intakePivotMotor.set(speed);
    System.out.println(speed);
  }

  public void runIntakeRollers(double rollerSpeed) {
    rollerMotor.set(rollerSpeed);
  }

  public void stopIntakeRollers() {
    rollerMotor.set(0.0);
  }

  public IntakeSubsystem() {
    // pivotIntakeConfig
    //   .idleMode(IdleMode.kBrake)
    //   .closedLoop
    //     .p(Constants.intake.PIDValues.kP)
    //     .i(Constants.intake.PIDValues.kI)
    //     .d(Constants.intake.PIDValues.kD);
    // simPID = new PIDController(
    //   Constants.intake.PIDValues.kP, 
    //   Constants.intake.PIDValues.kI, 
    //   Constants.intake.PIDValues.kD);

    // filter = new SlewRateLimiter(0.1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //This will make the motor move to whatever our target angle is continuously
    //intakePivotPIDMotor.setSetpoint(m_targetAngle, ControlType.kPosition);
    //simPID.setSetpoint(m_targetAngle);

    // Code to set speed based on PID and SlewRateLimiter
    // intakePivotMotor.set(
    //   filter.calculate(
    //     simPID.calculate(
    //       intakePivotMotor.getPosition(), m_targetAngle)));
  }
}
