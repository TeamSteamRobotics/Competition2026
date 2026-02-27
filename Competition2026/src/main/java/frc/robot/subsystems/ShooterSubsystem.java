// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.shooter;
import frc.robot.Constants.shooter.ShooterPid;
import frc.robot.subsystems.Motors.TalonFXMotor;
import frc.robot.subsystems.Motors.GenericMotor;
import frc.robot.subsystems.Motors.SparkMaxMotor;

import java.util.Map.Entry;
import java.util.TreeMap;


public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ShooterSubsystem. */
   GenericMotor shooterLeftMotor = new TalonFXMotor(Constants.shooter.shooterLeftId, "rio");
   GenericMotor shooterRightMotor = new TalonFXMotor(Constants.shooter.shooterRightId, "rio");
   GenericMotor kickMotor = new TalonFXMotor(Constants.shooter.feedRollersId, "rio");
   GenericMotor indexerMotor = new SparkMaxMotor(Constants.shooter.indexerMotorId);
   AbsoluteEncoder shooterLeftEncoder;
   AbsoluteEncoder shooterRightEncoder;
  
   TreeMap<Double, Double> speedLookupTable = new TreeMap<Double, Double>(); //make speed table
  
   double m_targetSpeed;
   double m_dist;
   double m_defaultSpeed;
   double m_kickSpeed; 

  public void StopMotor() {
    shooterLeftMotor.set(0);  //stop left shooter wheels
    shooterRightMotor.set(0); //stop right shooter wheels
    kickMotor.set(0);  //stop feed roller wheels 
    indexerMotor.set(0); //stop indexer motor
  }

  DigitalInput BeamBreak = new DigitalInput(0);//To Do find channel for beambreak 

  int BeamCounter = 0;
public boolean overrideDefault; 
    public boolean Breambroken(){
    if (BeamBreak.get()){
      BeamCounter ++;
      return true;
    }
    return false;
 //if (  > BeamCounter)
}
 

 public void primeShooter(Double m_defaultSpeed) {
    shooterLeftMotor.set(m_defaultSpeed);
    shooterRightMotor.set(-m_defaultSpeed);
 }
 
 public void runKick(double m_kickSpeed) {
    kickMotor.set(m_kickSpeed);
 }
  
   public void runIndexer() {
    indexerMotor.set(-Constants.shooter.speed);
  } 

  public void reverseIndexer() {
    indexerMotor.set(Constants.shooter.speed);
  } 
  
  //!The motor id's and speed values need to be set in the constants file!
  public void stopIndexer() {
    indexerMotor.set(0.0);
  }

  // public boolean Shoot(double targetSpeed) {
  //   m_targetSpeed = targetSpeed;
  //   if (SmartDashboard.getBoolean("Use Test Shooter Speed", false)) {
  //     m_targetSpeed = SmartDashboard.getNumber("Shooter Speed",targetSpeed);
  //   }
  //   // Calculate how much to adjust the motor speed to reach the target.
  //   double pidOutputFront = topShooterPid.calculate(shooterLeftMotor.getVelocity(), m_targetSpeed);
  //   //FIX ME: MAY BE THE OTHER WAY
  //   double pidOutputBack = bottomShooterPid.calculate(shooterRightMotor.getVelocity(), -m_targetSpeed);

  //   double pidGreenOutput = topShooterPid.calculate(kickMotor.getVelocity(), m_targetSpeed);

  //   // Set the motors to the calculated speeds.
  //   shooterLeftMotor.set(-m_targetSpeed);
  //   shooterRightMotor.set(-m_targetSpeed);

  //   // Check if both motors have reached the desired speed.
  //   return (topShooterPid.atSetpoint() && bottomShooterPid.atSetpoint());
  // }
 
  public double lookupShotSpeed(double dist){
    if(SmartDashboard.getBoolean("Use Test Distance", false)){
      m_dist = SmartDashboard.getNumber("Test Distance", dist);
    } else {
      m_dist = dist;
    }
    if(m_dist < 0.3048 || m_dist > 1.778){
      // System.out.println("Distance exceeds bounds. Returning default speed");
      // System.out.println(dist);
      return Constants.shooter.defaultSpeed;
    }
    Entry<Double, Double> lower = speedLookupTable.floorEntry(m_dist); // just copy-pasted from Zach's code
    Entry<Double, Double> upper = speedLookupTable.ceilingEntry(m_dist);
    if(lower == null)
      return upper.getValue();
    if(upper == null)
      return lower.getValue();
    double slope = (upper.getValue() - lower.getValue()) / (upper.getKey() - lower.getKey());
    return (slope * (m_dist - lower.getKey()) + lower.getValue());
  }
 
 
 public ShooterSubsystem() {}

 @Override
 public void periodic() {
    // This method will be called once per scheduler run
 }
}
