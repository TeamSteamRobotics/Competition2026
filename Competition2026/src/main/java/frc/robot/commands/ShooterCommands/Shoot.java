// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

/*
 * make sure to asign to correct motors
 */

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Shoot extends Command {
private ShooterSubsystem m_Shooter; //The subsystem this command controls.
private double speed; //The calculated speed for the shooter.
private double inputSpeed; //A fixed speed provided directly.
private Supplier<Double> distanceSupplier = () -> null; //A function that gives the distance to the target.
private boolean hasDistanceSupplier; //Indicates whether distance-based speed calculation is used.

  /** Creates a new RevFlyWheel. */
  public Shoot(ShooterSubsystem shooter, Supplier<Double> distancSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.

    //initialize shooter, distanceSupplier

  }

  public Shoot(ShooterSubsystem shooter, double speed) { //string type (needed?)
    m_Shooter = shooter;
    this.speed = speed;

    /*
     * if type is shoot
     * then inputSpeed = kickspeed from constants
     */

     hasDistanceSupplier = false;
  }

//   public RunKick(ShooterSubsystem m_shooter2) {
//     //TODO Auto-generated constructor stub
// }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Command the shooter subsystem to run at the calculated speed.
    //m_Shooter.runIndexer();
    m_Shooter.runKick(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Shooter.stopIndexer();
    m_Shooter.StopMotor();
  }

  // if interrupted
  // then stop motor

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  /*
   * calculates the motor required based on the distance to the target.
   * further distances might require higher speeds.
   */
}
