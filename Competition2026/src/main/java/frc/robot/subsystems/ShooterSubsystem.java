// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.shooter;
import frc.robot.Constants.shooter.ShooterPid;
import frc.robot.subsystems.Motors.TalonFXMotor;
import frc.robot.subsystems.Motors.GenericMotor;
import java.util.Map.Entry;
import java.util.TreeMap;


public class ShooterSubsystem extends SubsystemBase {
    /** Creates a new ShooterSubsystem. */
   TalonFX shooterLeftMotor = new TalonFX(Constants.shooter.shooterLeftId, "rio");
   TalonFX shooterRightMotor = new TalonFX(Constants.shooter.shooterRightId, "rio");
   SparkFlex kickMotor = new SparkFlex(Constants.shooter.feedRollersId, MotorType.kBrushless); //TODO: CanID might be wrong?
   TalonFXConfiguration config = new TalonFXConfiguration();
   SparkFlexConfig sconfig = new SparkFlexConfig();
   //
   SparkClosedLoopController kickLoop;
  
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
    m_defaultSpeed = Constants.shooter.defaultSpeed;
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
 

 public void primeShooter() {
    shooterLeftMotor.set(-m_defaultSpeed);
    shooterRightMotor.set(m_defaultSpeed);
    //System.out.println("We got here :3");
 }
 public void primeShooter(double inputSpeed) {
  shooterLeftMotor.set(-inputSpeed);
  shooterRightMotor.set(inputSpeed);
  //System.out.println("We got here :3" + inputSpeed);
}
 
 public void runKick(double m_kickSpeed) {
  System.out.println("yahoo2");
    kickMotor.set(m_kickSpeed);
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
 
 
 public ShooterSubsystem() {
  shooterLeftMotor.getConfigurator().apply(config);
  shooterRightMotor.getConfigurator().apply(config);
  kickMotor.configure(sconfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  //kickLoop = kickMotor.getClosedLoopController();
  
 }

 @Override
 public void periodic() {
    // This method will be called once per scheduler run
 }
}
