// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.Supplier;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.HoodSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AngleHood extends Command {
  public HoodSubsystem m_hood;
  private int m_sign;
  public CommandSwerveDrivetrain m_drive;
  //private Supplier<Double> m_distance;

  /** Creates a new HoodManualAngle. */
  public AngleHood(HoodSubsystem hood, int sign) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_hood = hood;
    m_sign = sign;
    addRequirements(m_hood);
  }
  public AngleHood(HoodSubsystem hood, CommandSwerveDrivetrain drive){
    m_hood = hood;
    m_drive = drive;
    //m_distance = distance;
    addRequirements(m_hood);
  }

  public double findDistanceToHub(){
    Pose2d robotPose = m_drive.samplePoseAt(Utils.getSystemTimeSeconds()).get(); //TODO: Determing if correct timestamp method is used
    //Finds the x and y distances from the hub. Uses pythagorean theorem to find the distance to hub.
    double xDistanceToHub = Math.abs(Constants.FieldPositions.hubPose.getX() - robotPose.getX());
    double yDistanceToHub = Math.abs(Constants.FieldPositions.hubPose.getY() - robotPose.getY());
    return Math.sqrt(Math.pow(xDistanceToHub, 2) + Math.pow(yDistanceToHub, 2));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_hood.moveByInterval(m_sign);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
