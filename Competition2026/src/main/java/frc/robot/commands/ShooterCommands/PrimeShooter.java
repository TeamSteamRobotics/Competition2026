// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ShooterCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Motors.*;
import frc.robot.subsystems.ShooterSubsystem;


/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class PrimeShooter extends Command {
private ShooterSubsystem m_Shooter; //The subsystem this command controls.
private double speed; //The calculated speed for the shooter.
private double inputSpeed; //A fixed speed provided directly.
private Supplier<Double> distanceSupplier = () -> null; //A function that gives the distance to the target.
private boolean hasDistanceSupplier; //Indicates whether distance-based speed calculation is used.

  // /** Creates a new RevFlyWheel. */
  // public PrimeShooter(ShooterSubsystem shooter, double defaultspeed) {
  //   // Use addRequirements() here to declare subsystem dependencies.

  //   //initialize shooter, distanceSupplier

  //   /*
  //   TODO: Tues Feb 3rd: 

  //   Write psuedo code for the PrimeShooter Command

  //   look at: 
  //     Cheif Delfi, and last year's PrimeShooter code
  //     you will need to use a speed variable to start the shooter wheel motors to rev up the wheels in order to shoot fuel
  //     What has been written in THIS years ShooterSubsystem

  //     All in 2025 develop-but-autos:
  //       lines 82-94 on PrimeShooter code
  //       StopMotors code, explain why this code is inportant
  //       RollGreen code, explain what each line does
  //       Intake's: Pivot and Roll codes, explain what each line does
      
  //     Reserch what PID values are, h what they do, cheif Delphi, ask if questions
  //   */
  // }

  public PrimeShooter(ShooterSubsystem shooter, double speed) {
    m_Shooter = shooter;

    /*
     * if type is shoot
     * then inputSpeed = Shooter Speed from smartdashboard
     * else inputSpeed = speed
     */

     hasDistanceSupplier = false;
  }

  public PrimeShooter(ShooterSubsystem m_shooter) {
    //TODO Auto-generated constructor stub
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Shooter.primeShooter(inputSpeed);
    // Determine the speed: calculate it from distance or use the fixed input speed.
    //speed = (hasDistanceSupplier ? speedFromDistance(distanceSupplier.get()) : inputSpeed);

    //Command the shooter subsystem to run at the calculated speed.
    
   //m_Shooter.Shoot(speed); //add Shoot function to ShooterSubsystem
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    // if interrupted
    // then stop motor
    m_Shooter.StopMotor();
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    // If a distance supplier is being used (indicated by hasDistanceSupplier being true),
    // The command also ends if the distance supplier provides a null value.
    // -This handles casese where the distance sensor might fail or is unavailable,
    // ensuring the command terminates safely.
    m_Shooter.StopMotor();
    return (hasDistanceSupplier && (distanceSupplier.get() == null || distanceSupplier == null));
    //return false;
  }

  /*
   * calculates the motor required based on the distance to the target.
   * further distances might require higher speeds.
   */
  private double speedFromDistance(double distance) {
    double f_speed = m_Shooter.lookupShotSpeed(distance); //Add lookupShotSpeed function to ShooterSubsystem
    // calculate the speed from distance
    return f_speed;
  }
}
