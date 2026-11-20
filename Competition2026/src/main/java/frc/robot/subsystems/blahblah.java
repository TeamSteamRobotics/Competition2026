// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Motors.SparkMaxMotor;

public class blahblah extends SubsystemBase {
  double m_speed;

  /** Creates a new blahblah. */
  public blahblah() {
    m_speed = speed;
    blah_Motor = SparkMaxMotor(constants.intake.IntakeMotor)
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
