// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new IndexerSubsystem. */
  public IndexerSubsystem() {
    m_indexerMotor = new SparkMax(Constants.IndexerConstants.indexerMotorId, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  private SparkMax m_indexerMotor;
  
  public void runIndexer() {
    m_indexerMotor.set(-Constants.IndexerConstants.speed);
  } 

  public void reverseIndexer() {
    m_indexerMotor.set(Constants.IndexerConstants.speed);
  } 
  
  //!The motor id's and speed values need to be set in the constants file!
  public void stopIndexer() {
    m_indexerMotor.set(0.0);
  }
}
