package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants;
import frc.robot.Constants.ClimbMotors;
import frc.robot.subsystems.Motors.GenericMotor;
import frc.robot.subsystems.Motors.SparkMaxMotor;

public class ClimbSubsystem extends SubsystemBase {
  SparkFlex climbMotorLeft = new SparkFlex(ClimbMotors.climbLeft, MotorType.kBrushless);
  SparkFlex climbMotorRight = new SparkFlex(ClimbMotors.climbRight, MotorType.kBrushless);
  
  double retractClimbSpeed = ClimbMotors.climbSpeed;

  public void raiseClimb() {
    climbMotorLeft.set(ClimbMotors.climbSpeed); //make sure is SLOW, don't bend metal
    climbMotorRight.set(ClimbMotors.climbSpeed); //Move with left motor
  }

  public void retractClimb() {
    climbMotorLeft.set(-retractClimbSpeed); //Slowly increase speed
    climbMotorRight.set(-retractClimbSpeed); //Move with left motor
    
    if (retractClimbSpeed <= 1) {
      retractClimbSpeed = Math.min(retractClimbSpeed + 0.004, 1); // placeholder
    }
    
    SmartDashboard.putNumber("Climb Speed", retractClimbSpeed);
  }

  public void stopClimb() {
    climbMotorLeft.set(0);
    climbMotorRight.set(0);
    retractClimbSpeed = ClimbMotors.climbSpeed;
  }

  public boolean isRetracted() {
    // returns true if the left motor turned past the minimum turns
    return climbMotorLeft.getAbsoluteEncoder().getPosition() <= ClimbMotors.minClimbRotations;
  }

  public boolean isRaised() {
    // returns true if the left motor turned past the maximum turns
    return climbMotorLeft.getAbsoluteEncoder().getPosition() >= ClimbMotors.maxClimbRotations;
  }
}