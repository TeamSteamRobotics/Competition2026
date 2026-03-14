// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ClimbSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ManualClimb extends Command {
  /** Creates a new ManualClimb. */
  ClimbSubsystem m_climb;
  boolean movingUp;
  boolean whichMotor;
  public ManualClimb(ClimbSubsystem climb, boolean up, boolean motor) {
    m_climb = climb;
    addRequirements(m_climb);
    whichMotor = motor;
    movingUp = up;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println("We are executing");
    if(movingUp){
      m_climb.manualMotor(Constants.ClimbMotors.climbSpeed, whichMotor);
    } else {
      m_climb.manualMotor(-Constants.ClimbMotors.climbSpeed, whichMotor);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stopClimb();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
