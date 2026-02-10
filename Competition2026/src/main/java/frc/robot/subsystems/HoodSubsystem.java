// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSubsystem extends SubsystemBase {
  private SparkMax elevateHoodMotor;
  private SparkMaxConfig config;
  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {
    elevateHoodMotor = new SparkMax(Constants.HoodConstants.hoodElevateMotorID, MotorType.kBrushless);
    
    //elevateHoodMotor.configure(new SparkMaxConfig().idleMode(IdleMode.kBrake), null, null);
  }
  @Override
  public void periodic() {
    if(elevateHoodMotor.getAbsoluteEncoder().getPosition() >= Constants.HoodConstants.hoodMaxEncoderValue){
      elevateHoodMotor.set(0);
      
    }
    // This method will be called once per scheduler run
  }
}
