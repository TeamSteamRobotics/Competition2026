// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import java.io.ObjectInputFilter.Config;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.encoder.DetachedEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HoodSubsystem extends SubsystemBase {
  private double accumulatedAngle = 0;
  private SparkMax elevateHoodMotor;
  private SparkMaxConfig config = new SparkMaxConfig();
  private double targetAngle;
  private SparkClosedLoopController hoodPIDMotor;
  private double m_dist;
  private DutyCycleEncoder hoodAngleEncoder;

  

  private int counter = 0;

  PIDController hoodPID;

  //TODO: How to throughbore?

  TreeMap<Double, Double> angleLookupTable = new TreeMap<Double, Double>();

  /** Creates a new HoodSubsystem. */
  public HoodSubsystem() {

    SmartDashboard.putNumber("Sim Distance", 0);

  angleLookupTable.put(1.524, -0.4285);
  angleLookupTable.put(1.828, -0.4285);
  angleLookupTable.put(2.1336, -0.9047);
  angleLookupTable.put(2.4384, -1.2619);
  angleLookupTable.put(2.819, -1.666);
    
    config
      .idleMode(IdleMode.kBrake)
      .closedLoop
        .p(Constants.HoodConstants.PIDValues.kP)
        .i(Constants.HoodConstants.PIDValues.kI)
        .d(Constants.HoodConstants.PIDValues.kD);

    hoodPID = new PIDController(Constants.HoodConstants.PIDValues.kP, Constants.HoodConstants.PIDValues.kI, Constants.HoodConstants.PIDValues.kD);

    targetAngle = Constants.HoodConstants.hoodMaxEncoderValue;

    elevateHoodMotor = new SparkMax(Constants.HoodConstants.hoodElevateMotorID, MotorType.kBrushless);
    
    elevateHoodMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    hoodPIDMotor = elevateHoodMotor.getClosedLoopController();

    //hoodAngleEncoder = new DutyCycleEncoder(1); //We'll figure this out
    elevateHoodMotor.getEncoder().setPosition(0);
    

  }

  public double getTargetAngle(){
    return targetAngle;
  }

  public boolean hoodAtSetpoint(){
    return (Math.abs(elevateHoodMotor.getEncoder().getPosition() - targetAngle) <= 0.02);
  }
  /**
   * Sets targetAngle, clamping value if necessary.
   * @param value
   * The value that targetAngle is to be set to
   * @return
   * true if the value is within range, false if the value is out of range
   */

  public boolean setTargetAngle(double value){
    // if(value <= Constants.HoodConstants.hoodMinEncoderValue){
    //   targetAngle = Constants.HoodConstants.hoodMinEncoderValue;
    //   return false;
    // }
    // if(value >= Constants.HoodConstants.hoodMaxEncoderValue){
    //   targetAngle = Constants.HoodConstants.hoodMaxEncoderValue;
    //   return false;
    // }
    
    // We shouldn't need the above
    // System.out.println(value);
    // System.out.println(targetAngle);
    targetAngle = value;
    // System.out.println(targetAngle);
    // System.out.println("Step 2 done");
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
    
    if (sign > 0 && (targetAngle <= Constants.HoodConstants.hoodMaxEncoderValue + 0.01)) {
      targetAngle += Constants.HoodConstants.largeAngleInterval;
      return true;
    }
    else if (sign <= 0 && (targetAngle >= Constants.HoodConstants.hoodMinEncoderValue - 0.01)) {
      targetAngle -= Constants.HoodConstants.largeAngleInterval;
      return true;
    }
    return false;
  }

  public void moveUp(){
    targetAngle += Constants.HoodConstants.largeAngleInterval;
  }

    //Moves motor position to angle given in the function
    public void moveToTargetPos(double angle) {
      targetAngle = angle;
    }

  @Override
  public void periodic() {
    double speed;
    //accumulatedAngle += hoodAngleEncoder.get();
    if(elevateHoodMotor.getEncoder().getPosition() >= Constants.HoodConstants.hoodMaxEncoderValue + 0.01
    && targetAngle >= Constants.HoodConstants.hoodMaxEncoderValue){
      //speed = hoodPID.calculate(accumulatedAngle, Constants.HoodConstants.hoodMaxEncoderValue);
      //elevateHoodMotor.set(speed);
      elevateHoodMotor.set(0);
      //TODO: Figure out which works better later
      //hoodAngleEncoder;
      return;
    }
    if(targetAngle > 0){
      targetAngle = 0;
    }
    speed = hoodPID.calculate(elevateHoodMotor.getEncoder().getPosition(), targetAngle);
    // This method will be called once per scheduler run
    //System.out.println("We're doing stuff, at least");

    
    elevateHoodMotor.set(speed);
    SmartDashboard.putNumber("Hood Target Encoder", targetAngle);
    SmartDashboard.putNumber("Hood Actual Value", elevateHoodMotor.getEncoder().getPosition());
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

