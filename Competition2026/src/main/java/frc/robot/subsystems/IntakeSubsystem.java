// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  SparkMax intakePivotMotor;
  SparkFlex intakeRollerMotor;

  PIDController pivotPID;
  PIDController upPivotPID;
  PIDController midPivotPID;

  SparkBaseConfig pivotConfig = new SparkFlexConfig().idleMode(IdleMode.kBrake);
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

    intakePivotMotor.configure(pivotConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

    pivotPID = new PIDController(
      Constants.intake.PIDValues.kP, 
      Constants.intake.PIDValues.kI, 
      Constants.intake.PIDValues.kD);

    upPivotPID = new PIDController(
      Constants.intake.UpPIDValues.kP, 
      Constants.intake.UpPIDValues.kI, 
      Constants.intake.UpPIDValues.kD);

    midPivotPID = new PIDController(
      Constants.intake.PIDValuesMidAngle.kP, 
      Constants.intake.PIDValuesMidAngle.kI, 
      Constants.intake.PIDValuesMidAngle.kD);

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
  public void intakeMid(){
    targetAngle = Constants.intake.intakePivotMidEncoderValue;
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
    intakePivotMotor.set(0);
    return; // This completely stops the intake from being active
    /* 
    if(manualOperation){
      // User is running motors manually, don't even try to work with PID
      return;
    }
    double speed = 0;
    // We're running in standard mode, set roller speed
    // System.out.println("Encoder: " + intakePivotMotor.getEncoder().getPosition()); TODO: Make work w/ ElasticDashboard
    intakeRollerMotor.set(rollerSpeed);
    if(targetAngle == Constants.intake.intakePivotMaxEncoderValue){
      // We are going down
      speed = pivotPID.calculate(intakePivotMotor.getEncoder().getPosition(), targetAngle);
    }
    else if(targetAngle == Constants.intake.intakePivotMinEncoderValue){
      // We are going up
      speed = upPivotPID.calculate(intakePivotMotor.getEncoder().getPosition(), targetAngle);
    } else if(targetAngle == Constants.intake.intakePivotMidEncoderValue){
      // You know what else is mid?
      speed = midPivotPID.calculate(intakePivotMotor.getEncoder().getPosition(), targetAngle);
    }
    
    // System.out.println("Speed: " + speed);
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
    if(Math.abs(intakePivotMotor.getEncoder().getPosition() - targetAngle) < 0.05){
      intakePivotMotor.set(0);
      //System.out.println("Deadband!"); // Change to elastic dashboard later
      return;
    }
    // All checks passed
    intakePivotMotor.set(speed);
    */
  }
}
