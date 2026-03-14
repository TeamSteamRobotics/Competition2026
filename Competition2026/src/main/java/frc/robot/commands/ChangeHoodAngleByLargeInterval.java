// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.HoodSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ChangeHoodAngleByLargeInterval extends InstantCommand {
  HoodSubsystem m_HoodSubsystem;
  int m_sign;
  public ChangeHoodAngleByLargeInterval(HoodSubsystem hoodSubsystem, int sign) {
    m_HoodSubsystem = hoodSubsystem;
    m_sign = sign;
    addRequirements(hoodSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_HoodSubsystem.moveByIntervalTrue(m_sign);
    System.out.println("Moved");
  }
}
