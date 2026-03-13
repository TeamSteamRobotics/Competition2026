// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package RetractClimb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.ClimbMotors;
import frc.robot.subsystems.ClimbSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RetractClimb extends InstantCommand {
  ClimbSubsystem m_climb;
  double m_speed;

  public RetractClimb (ClimbSubsystem climb, double speed) {
    m_climb = climb;
    m_speed = speed;

    addRequirements(climb);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(!m_climb.isRetracted()) {
      m_climb.retractClimb(m_speed);
    }
    else{
      m_climb.stopClimb();
    }
  }

}
