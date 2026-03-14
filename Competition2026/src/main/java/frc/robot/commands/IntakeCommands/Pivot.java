// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Pivot extends InstantCommand {
  IntakeSubsystem m_intake;
  IntakeDirection m_dir;
  public Pivot(IntakeSubsystem intake, IntakeDirection dir) {
    m_intake = intake;
    m_dir = dir;
    addRequirements(m_intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_dir == IntakeDirection.OUT){
      m_intake.intakeOut();
      return;
    }
    m_intake.intakeIn();
  }
}
