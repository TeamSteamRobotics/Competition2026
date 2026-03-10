// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.IntakePivotIn;
import frc.robot.commands.IntakePivotOut;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ToggleIntakePivotCommands extends Command {
  Command IntakePivotIn;
  Command IntakePivotOut;

  boolean isIntakePivotOut = false;
  /** Creates a new ToggleIntakePivotCommands. */
  public ToggleIntakePivotCommands(Command pivIn, Command pivOut) {
    IntakePivotIn = pivIn;
    IntakePivotOut = pivOut;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (isIntakePivotOut == true) {
      IntakePivotIn.execute();
      isIntakePivotOut = false;
    }
    else {
      IntakePivotOut.execute();
      isIntakePivotOut = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
