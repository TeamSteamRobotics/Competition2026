// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants;
import frc.robot.constants.shooter;


public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem. */
 GenericMotor shooterLeftMotor = new TalonFXMotor(constants.shooter.shooterLeftId);
 GenericMotor shooterRightMotor = new TalonFXMotor(constants.shooter.shooterRightId);
 Genericmotor kickMotor = new TalonFXMotor(constants.shooter.feedRollersId);

 AbsoluteEncoder shooterLeftEncoder;
 AbsoluteEncoder shooterRightEncoder;

 double m_targetSpeed;

 // insert Pid code

 public void StopMotor() {
    shooterLeftMotor.set(0);  //stop left shooter wheels
    shooterRightMotor.set(0); //stop right shooter wheels
    kickMotor.set(0);  //stop feed roller wheels 
 }

 public boolean beamBroken() {
    //insert beamBreak code
 }
 
 public void primeShooter(Double speed) {
    shooterLeftMotor.set(speed);
    shooterRightMotor.set(-speed);
 }
 
 public void runKick() {
    runKickMotor.set(0.20);
 }
 
 public void vomit(Double speed) {
    shooterLeftMotor.set(-speed);
    shooterRightMotor.set(speed);
    runKickMotor.set(-speed);
 }
 
 // need to add PIDControllers
  public boolean Shoot(double targetSpeed) {
    m_targetSpeed = targetSpeed;
    if (SmartDashboard.getBoolean("Use Test Shooter Speed", false)) {
      m_targetSpeed = SmartDashboard.getNumber("Shooter Speed",targetSpeed);
    }
    // Calculate how much to adjust the motor speed to reach the target.
    double pidOutputFront = topShooterPid.calculate(shooterLeftMotor.getVelocity(), m_targetSpeed);
    //FIXME: MAY BE THE OTHER WAY
    double pidOutputBack = bottomShooterPid.calculate(shooterRightMotor.getVelocity(), -m_targetSpeed);

    double pidGreenOutput = topShooterPid.calculate(runKickMotor.getVelocity(), m_targetSpeed);

    // Set the motors to the calculated speeds.
    shooterLeftMotor.set(-m_targetSpeed);
    shooterRightMotor.set(-m_targetSpeed);

    // Check if both motors have reached the desired speed.
    return (topShooterPid.atSetpoint() && bottomShooterPid.atSetpoint());
  }
 
 
 
 public ShooterSubsystem() {}

 @Override
 public void periodic() {
    // This method will be called once per scheduler run
 }
}
