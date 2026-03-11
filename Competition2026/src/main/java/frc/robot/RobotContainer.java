// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommands.IntakeDirection;
import frc.robot.commands.IntakeCommands.Pivot;
import frc.robot.commands.IntakeCommands.RunMotorManual;
import frc.robot.commands.IntakeCommands.RunRollerWheels;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeMotor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


//import frc.robot.commands.printValue;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kDriverOperatorPort);

  //operator controls
  private final Trigger intakeRollers = m_operatorController.x();
  private final Trigger pivotIntakeDown = m_operatorController.povDown();
  private final Trigger pivotIntakeUp = m_operatorController.povUp();
  private final Trigger pivotDebugDown = m_driverController.a();
  private final Trigger pivotDebugUp = m_driverController.b();
  private final Trigger rollerDebug = m_driverController.x();
  //private final Trigger toggleIntakePivotCommands = m_operatorController.y();
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
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
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(new printValue(m_intake));

    intakeRollers.whileTrue(new RunRollerWheels(m_intake, IntakeDirection.IN));
    pivotIntakeDown.onTrue(new Pivot(m_intake, IntakeDirection.OUT));
    pivotIntakeUp.onTrue(new Pivot(m_intake, IntakeDirection.IN));

    pivotDebugDown.whileTrue(new RunMotorManual(m_intake, 0.15, IntakeMotor.PIVOT));
    pivotDebugUp.whileTrue(new RunMotorManual(m_intake, -0.15, IntakeMotor.PIVOT));
    rollerDebug.whileTrue(new RunMotorManual(m_intake, 0.3, IntakeMotor.ROLLER));

    //toggleIntakePivotCommands.onTrue(new ToggleIntakePivotCommands(new IntakePivotIn(m_intake), new IntakePivotOut(m_intake)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
