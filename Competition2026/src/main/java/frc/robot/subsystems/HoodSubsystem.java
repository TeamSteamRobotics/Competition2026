// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.io.ObjectInputFilter.Config;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSubsystem extends SubsystemBase {
  private SparkMax elevateHoodMotor;
  private SparkMaxConfig config = new SparkMaxConfig();
  private double targetAngle;
  private SparkClosedLoopController hoodPIDMotor;


  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {
    config
      .idleMode(IdleMode.kBrake)
      .closedLoop
        .p(Constants.HoodConstants.PIDValues.kP)
        .i(Constants.HoodConstants.PIDValues.kI)
        .d(Constants.HoodConstants.PIDValues.kD);

    targetAngle = Constants.HoodConstants.defaultAngleIntervalue;

    elevateHoodMotor = new SparkMax(Constants.HoodConstants.hoodElevateMotorID, MotorType.kBrushless);
    
    elevateHoodMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    hoodPIDMotor = elevateHoodMotor.getClosedLoopController();
    

  }

  //Increases or decreases targetAngle value by angleInterval (in constants)
  public void moveByInterval(int sign) {
      if (sign >= 0) {
        targetAngle += Constants.HoodConstants.angleInterval;
      }
      else if (sign <= 0) {
        targetAngle -= Constants.HoodConstants.angleInterval;
      }
    }

    //Moves motor position to angle given in the function
    public void moveToTargetPos(double angle) {
      targetAngle = angle;
    }

  @Override
  public void periodic() {
    if(elevateHoodMotor.getAbsoluteEncoder().getPosition() >= Constants.HoodConstants.hoodMaxEncoderValue){
      hoodPIDMotor.setSetpoint(Constants.HoodConstants.hoodMaxEncoderValue, ControlType.kPosition);
      //elevateHoodMotor.set(0);
      //TODO: Figure out which works better later
      

      
    }
    // This method will be called once per scheduler run

    hoodPIDMotor.setSetpoint(targetAngle, ControlType.kPosition);
  }
}
