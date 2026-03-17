package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants;
import frc.robot.Constants.ClimbMotors;
import frc.robot.subsystems.Motors.GenericMotor;
import frc.robot.subsystems.Motors.SparkMaxMotor;

public class ClimbSubsystem extends SubsystemBase {
  SparkMax climbMotorBack = new SparkMax(ClimbMotors.climbBack, MotorType.kBrushless);
  SparkMax climbMotorFront = new SparkMax(ClimbMotors.climbFront, MotorType.kBrushless);
  
  double retractClimbSpeed = ClimbMotors.climbSpeed;

  int counter;

  public ClimbSubsystem(){
    climbMotorBack.getEncoder().setPosition(0);
    climbMotorFront.getEncoder().setPosition(0);

    
  }

  public void manualMotor(double speed, boolean whichMotor){
    //System.out.println("We should be moving");
    if(whichMotor){
      climbMotorBack.set(speed);
    } else {
      climbMotorFront.set(speed);
    }
  }

  public void raiseClimb() {
    climbMotorBack.set(ClimbMotors.climbSpeed); //make sure is SLOW, don't bend metal
    climbMotorFront.set(ClimbMotors.climbSpeed); //Move with left motor

  }

  public void retractClimb() {
    climbMotorBack.set(-retractClimbSpeed); //Slowly increase speed
    climbMotorFront.set(-retractClimbSpeed); //Move with left motor
    
    if (retractClimbSpeed <= 1) {
      retractClimbSpeed = Math.min(retractClimbSpeed + 0.004, 1); // placeholder
    }
    
    SmartDashboard.putNumber("Climb Speed", retractClimbSpeed);
  }

  public void stopClimb() {
    climbMotorBack.set(0);
    climbMotorFront.set(0);
    retractClimbSpeed = ClimbMotors.climbSpeed;
  }

  public boolean isRetracted() {
    // returns true if the left motor turned past the minimum turns
    //System.out.println(climbMotorBack.getAbsoluteEncoder().getPosition());
    return climbMotorBack.getEncoder().getPosition() <= ClimbMotors.minClimbRotations;
  }

  public boolean isRaised() {
    // returns true if the left motor turned past the maximum turns
    //return climbMotorBack.getEncoder().getPosition() >= ClimbMotors.maxClimbRotations;
    
    return false;
  }

  @Override
  public void periodic(){
    if(counter == 20){
      System.out.println(climbMotorBack.getEncoder().getPosition());
    }
    counter++;
  }
}