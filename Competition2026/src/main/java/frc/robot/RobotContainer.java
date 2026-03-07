// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ShooterCommands.PrimeShooter;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.commands.ShooterCommands.VomitShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotContainer {
    

    private final ShooterSubsystem m_shooter = new ShooterSubsystem();

    private final CommandXboxController joystick = new CommandXboxController(0);

    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
    //private final CommandXboxController m_bluetoothController = new CommandXboxController(OperatorConstants.kBluetoothControllerPort);

    //operator controls
    private final Trigger primeShooter = m_operatorController.rightTrigger();
    private final Trigger Shoot = m_operatorController.b();
    private final Trigger VomitShooter = m_operatorController.x();

    private final SendableChooser<Command> autoChooser = AutoBuilder.buildAutoChooser();

    public RobotContainer() {
        configureBindings();
    }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    primeShooter.whileTrue(new PrimeShooter(m_shooter, Constants.shooter.defaultSpeed));
    Shoot.whileTrue(new Shoot(m_shooter, Constants.shooter.kickSpeed));
    VomitShooter.whileTrue(new VomitShooter(m_shooter, Constants.shooter.vomitSpeed, null));

        // Build an auto chooser. This will use Commands.none() as the default option.

        // Another option that allows you to specify the default auto by its name
        // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

        SmartDashboard.putData("Auto Choices", autoChooser);
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.




    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        return autoChooser.getSelected();
    }


    // private final Command m_complexAuto = new ComplexAuto(m_robotDirve, m_hatchSubsystem);

}
//shooter=work











