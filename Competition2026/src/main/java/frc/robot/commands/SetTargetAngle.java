package frc.robot.commands;

import static edu.wpi.first.units.Units.Value;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.HoodSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SetTargetAngle extends InstantCommand {

  HoodSubsystem m_HoodSubsystem;
  double m_value;
  CommandSwerveDrivetrain m_drive;

  public SetTargetAngle(double value, HoodSubsystem hoodSubsystem){

    m_value = value;
    m_HoodSubsystem = hoodSubsystem;

    addRequirements(m_HoodSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public SetTargetAngle(HoodSubsystem hoodSubsystem, CommandSwerveDrivetrain drive){
    m_HoodSubsystem = hoodSubsystem;
    addRequirements(m_HoodSubsystem);
    m_drive = drive;
    
    //m_value = m_HoodSubsystem.lookupHoodAngle(findDistanceToHub()); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_value = m_HoodSubsystem.lookupHoodAngle(findDistanceToHub());
    // System.out.println(SmartDashboard.getNumber("Sim Distance", 1));
    // System.out.println(m_value);
    m_HoodSubsystem.setTargetAngle(m_value);
    // System.out.println("Step 1 done");
  }
  
  public double findDistanceToHub(){
    Pose2d robotPose = m_drive.samplePoseAt(Utils.getSystemTimeSeconds()).get(); //TODO: Determing if correct timestamp method is used
    //Finds the x and y distances from the hub. Uses pythagorean theorem to find the distance to hub.
    double xDistanceToHub = Math.abs(Constants.Vision.FieldPositions.hubTranslation.getX() - robotPose.getX());
    double yDistanceToHub = Math.abs(Constants.Vision.FieldPositions.hubTranslation.getY() - robotPose.getY());
    return Math.sqrt(Math.pow(xDistanceToHub, 2) + Math.pow(yDistanceToHub, 2));
  }

}