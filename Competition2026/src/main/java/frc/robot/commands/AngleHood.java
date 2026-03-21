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
  private boolean operatingOnDistance;
  private boolean didOneIntervalMove;
  public enum OperatingMode {
    DISTANCE,
    VARIABLE,
    INTERVAL
  }
  private OperatingMode mode;
  //private Supplier<Double> m_distance;

  /** Creates a new HoodManualAngle. 
     * @throws Exception
     * when using wrong enum */
  public AngleHood(HoodSubsystem hood, int sign, OperatingMode fmode){
    if(fmode == OperatingMode.DISTANCE){
      System.out.println("Ah, you used the wrong enum, it ain't gonna work");
    }
    m_hood = hood;
    m_sign = sign;
    addRequirements(m_hood);
    operatingOnDistance = false;
    mode = fmode;
    didOneIntervalMove = false;
    // Use addRequirements() here to declare subsystem dependencies.
  }
  public AngleHood(HoodSubsystem hood, CommandSwerveDrivetrain drive){
    m_hood = hood;
    m_drive = drive;
    //m_distance = distance;
    addRequirements(m_hood);
    operatingOnDistance = true;
    mode = OperatingMode.DISTANCE;
    didOneIntervalMove = false;
  }

  public double findDistanceToHub(){
    Pose2d robotPose = m_drive.samplePoseAt(Utils.getSystemTimeSeconds()).get(); //TODO: Determing if correct timestamp method is used
    //Finds the x and y distances from the hub. Uses pythagorean theorem to find the distance to hub.
    double xDistanceToHub = Math.abs(Constants.Vision.FieldPositions.hubTranslation.getX() - robotPose.getX());
    double yDistanceToHub = Math.abs(Constants.Vision.FieldPositions.hubTranslation.getY() - robotPose.getY());
    return Math.sqrt(Math.pow(xDistanceToHub, 2) + Math.pow(yDistanceToHub, 2));
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(mode == OperatingMode.VARIABLE){
      m_hood.moveByIntervalVariable(m_sign);
      return;
    }
    if(mode == OperatingMode.INTERVAL){
      if(didOneIntervalMove){
        return;
      }
      //System.out.println("upgoing");
      if(m_sign == 1){
        m_hood.moveUp();
        return;
      } 
      m_hood.moveByIntervalTrue(m_sign);
      didOneIntervalMove = true;
      return;
    }
    
    m_hood.setTargetAngle(m_hood.lookupHoodAngle(findDistanceToHub()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //m_hood.setTargetAngle(Constants.HoodConstants.hoodMinEncoderValue);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(didOneIntervalMove){
      return m_hood.hoodAtSetpoint();
    }
    return false;
  }
}
