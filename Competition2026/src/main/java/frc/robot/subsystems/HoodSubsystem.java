// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import java.io.ObjectInputFilter.Config;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSubsystem extends SubsystemBase {
  private SparkMax elevateHoodMotor;
  private SparkMaxConfig config = new SparkMaxConfig();
  private double targetAngle;
  private SparkClosedLoopController hoodPIDMotor;
  private double m_dist;

  TreeMap<Double, Double> angleLookupTable = new TreeMap<Double, Double>();

  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {
    config
      .idleMode(IdleMode.kBrake)
      .closedLoop
        .p(Constants.HoodConstants.PIDValues.kP)
        .i(Constants.HoodConstants.PIDValues.kI)
        .d(Constants.HoodConstants.PIDValues.kD);

    targetAngle = Constants.HoodConstants.hoodMinEncoderValue;

    elevateHoodMotor = new SparkMax(Constants.HoodConstants.hoodElevateMotorID, MotorType.kBrushless);
    
    elevateHoodMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    hoodPIDMotor = elevateHoodMotor.getClosedLoopController();
    

  }

  public double getTargetAngle(){
    return targetAngle;
  }
  /**
   * Sets targetAngle, clamping value if necessary.
   * @param value
   * The value that targetAngle is to be set to
   * @return
   * true if the value is within range, false if the value is out of range
   */
  public boolean setTargetAngle(double value){
    if(value <= Constants.HoodConstants.hoodMinEncoderValue){
      targetAngle = Constants.HoodConstants.hoodMinEncoderValue;
      return false;
    }
    if(value >= Constants.HoodConstants.hoodMaxEncoderValue){
      targetAngle = Constants.HoodConstants.hoodMaxEncoderValue;
      return false;
    }
    targetAngle = value;
    return true;
  }

  /**
   * Moves the hood by a constant angle interval
   * @param sign
   * The direction to move the hood. Up if positive, down if negative
   * @return
   * If targetAngle was changed
   */
  public boolean moveByIntervalVariable(int sign) {
    if(targetAngle >= Constants.HoodConstants.hoodMaxEncoderValue || targetAngle <= Constants.HoodConstants.hoodMinEncoderValue){
      return false;
    }
    if (sign > 0) {
      targetAngle += Constants.HoodConstants.angleInterval;
    }
    else if (sign <= 0) {
      targetAngle -= Constants.HoodConstants.angleInterval;
    }
    return true;
  }
  /**
   * Moves by a large interval
   * @param sign
   * @return
   */
  public boolean moveByIntervalTrue(int sign) {
    if(targetAngle >= Constants.HoodConstants.hoodMaxEncoderValue || targetAngle <= Constants.HoodConstants.hoodMinEncoderValue){
      return false;
    }
    if (sign > 0) {
      targetAngle += Constants.HoodConstants.largeAngleInterval;
    }
    else if (sign <= 0) {
      targetAngle -= Constants.HoodConstants.largeAngleInterval;
    }
    return true;
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

    // This will make the motor move to whatever our target angle is continuously.
    hoodPIDMotor.setSetpoint(targetAngle, ControlType.kPosition);
  }

  public double lookupHoodAngle(double dist){
    if(SmartDashboard.getBoolean("Use Test Distance", false)){
      m_dist = SmartDashboard.getNumber("Test Distance", dist);
    } else {
      m_dist = dist;
    }
    //TODO: Add bounds code
    Entry<Double, Double> lower = angleLookupTable.floorEntry(m_dist); // just copy-pasted from Zach's code
    Entry<Double, Double> upper = angleLookupTable.ceilingEntry(m_dist);
    if(lower == null)
      return upper.getValue();
    if(upper == null)
      return lower.getValue();
    double slope = (upper.getValue() - lower.getValue()) / (upper.getKey() - lower.getKey());
    return (slope * (m_dist - lower.getKey()) + lower.getValue());
  }
}
