// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  SparkMax intakePivotMotor;
  SparkFlex intakeRollerMotor;

  PIDController pivotPID;
  /** Are the motors being run manually, or by PID? */
  boolean manualOperation;
  double targetAngle;
  double rollerSpeed;
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    intakePivotMotor = new SparkMax(Constants.intake.intakePivotID, MotorType.kBrushless);
    intakeRollerMotor = new SparkFlex(Constants.intake.rollerId, MotorType.kBrushless);

    targetAngle = Constants.intake.intakePivotMinEncoderValue;

    intakePivotMotor.getEncoder().setPosition(0);

    pivotPID = new PIDController(
      Constants.intake.PIDValues.kP, 
      Constants.intake.PIDValues.kI, 
      Constants.intake.PIDValues.kD);

    manualOperation = false;
  }

  /**
   * Moves intake out of the robot
   */
  public void intakeOut(){
    targetAngle = Constants.intake.intakePivotMaxEncoderValue;
  }

  public void intakeIn(){
    targetAngle = Constants.intake.intakePivotMinEncoderValue;
  }
  public void runMotorManual(double speed, IntakeMotor motor){
    manualOperation = true;
    if(motor == IntakeMotor.ROLLER){
      intakeRollerMotor.set(speed);
      return;
    }
    intakePivotMotor.set(speed);
  }

  public void releaseManualControl(){
    intakeRollerMotor.stopMotor();
    intakePivotMotor.stopMotor();
    manualOperation = false;
  }

  public void setRollerSpeed(double rollerSpeed){
    this.rollerSpeed = rollerSpeed;
  }

  @Override
  public void periodic() {
    if(manualOperation){
      // User is running motors manually, don't even try to work with PID
      return;
    }
    // We're running in standard mode, set roller speed
    System.out.println("Encoder: " + intakePivotMotor.getEncoder().getPosition());
    intakeRollerMotor.set(rollerSpeed);
    double speed = pivotPID.calculate(intakePivotMotor.getEncoder().getPosition(), targetAngle); //Tried to run wrong direction, might this fix it?
    System.out.println("Speed: " + speed);
    if(speed > 1){
      // Out of bounds
      intakePivotMotor.set(1);
      return;
    }
    if(speed < -1){
      // Out of bounds
      intakePivotMotor.set(-1);
      return;
    }
    // All checks passed
    intakePivotMotor.set(speed);
    
  }
}
