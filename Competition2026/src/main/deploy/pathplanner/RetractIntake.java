// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package RetractIntake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RetractIntake extends InstantCommand {
  IntakeSubsystem m_intake;
  double m_speed;
  sparkMax m_intakePivotMotor;

  public RetractIntake(IntakeSubsystem intake, double speed, SparkMax intakePivotMotor) {
    m_intake = intake;
    m_speed = speed;
    m_intakePivotMotor = intakePivotMotor;
    
    addRequirements(intake);
      // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_intake.targetAngle == Constants.intake.intakePivotMaxEncoderValue) {
      m_intake.intakeIn();
    }
    else {
      m_intakePivotMotor.stopMotor();
    }
    //m_intake.roller(m_speed);
  }
}
