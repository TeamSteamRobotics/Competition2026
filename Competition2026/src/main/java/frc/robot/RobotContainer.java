// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import javax.print.attribute.standard.MediaSize.NA;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Climb.RaiseClimb;
import frc.robot.commands.Climb.RetractClimb;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.commands.ShooterCommands.PrimeShooter;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.commands.ShooterCommands.VomitShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.commands.PathPlanner.RunKickAuto;
import frc.robot.commands.PathPlanner.StartShooterAuto;
import frc.robot.commands.PathPlanner.StopKickAuto;
import frc.robot.commands.PathPlanner.StopShooterAuto;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
  //Subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ClimbSubsystem m_climbsubsystem;
  
  // Controllers
  private final CommandXboxController m_kOperatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  private final Trigger RaiseClimb = m_kOperatorController.leftBumper();
  private final Trigger RetractClimb = m_kOperatorController.rightBumper();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_climbsubsystem = new ClimbSubsystem();
    
    NamedCommands.registerCommand("StartShooterAuto", new StartShooterAuto(m_shooter, Constants.shooter.defaultSpeed));
    NamedCommands.registerCommand("RunKickAuto", new RunKickAuto(m_shooter, Constants.shooter.kickSpeed));
    NamedCommands.registerCommand("StopKickAuto", new StopKickAuto(m_shooter));
    NamedCommands.registerCommand("StopShooterAuto", new StopShooterAuto(m_shooter));

    // Configure the trigger bindings
    configureBindings();
  }
    
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private double SlowMaxSpeed = 0.2 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); //Can increase from 20% if need be
    private double SlowMaxAngularRate = RotationsPerSecond.of(0.75 * 0.2).in(RadiansPerSecond); // The multiplier of 0.2 is our percent there
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final SwerveRequest.FieldCentric slow drive = new SwerveRequest.FieldCentric()
            .withDeadband(SlowMaxSpeed * 0.1).withRotationalDeadband(SlowMaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); 

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final ShooterSubsystem m_shooter = new ShooterSubsystem();

    private final CommandXboxController joystick = new CommandXboxController(0);

    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);
    //private final CommandXboxController m_bluetoothController = new CommandXboxController(OperatorConstants.kBluetoothControllerPort);

    //operator controls
    private final Trigger primeShooter = m_operatorController.rightTrigger();
    private final Trigger Shoot = m_operatorController.b();
    private final Trigger VomitShooter = m_operatorController.x();

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    private final SendableChooser<Command> autoChooser = AutoBuilder.buildAutoChooser();

    

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
    //m_kOperatorController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    //Raise Climb
    RaiseClimb.whileTrue(new RaiseClimb(m_climbsubsystem));
    //Retract Climb
    RetractClimb.whileTrue(new RetractClimb(m_climbsubsystem));


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
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        joystick.rightTrigger().whileTrue(
          drivetrain.applyRequest(() ->
                slowDrive.withVelocityX(-joystick.getLeftY() * SlowMaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * SlowMaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * SlowMaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        return autoChooser.getSelected();
    }


    // private final Command m_complexAuto = new ComplexAuto(m_robotDirve, m_hatchSubsystem);

}
//shooter=work











