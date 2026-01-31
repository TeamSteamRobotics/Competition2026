// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  private final Spark m_leftMotor = new
  Spark(Constants.IndexerConstants.leftIndexerId);
  private final Spark m_rightMotor = new
  Spark(Constants.IndexerConstants.rightIndexerId);
  
  public void runIndexer(double speed) {
    m_leftMotor.set(-Constants.IndexerConstants.speed);
    m_rightMotor.set(Constants.IndexerConstants.speed);
  } 
  
  //!The motor id's and speed values need to be set in the constants file!

  public void stopIndexer() {
    m_leftMotor.set(0.0);
    m_rightMotor.set(0.0);
  }
}
