// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Optional;

import com.ctre.phoenix6.Utils;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.SetTargetAngle;
import frc.robot.commands.IntakeCommands.IntakeDirection;
import frc.robot.commands.IntakeCommands.Pivot;
import frc.robot.commands.IntakeCommands.PivotMid;
import frc.robot.commands.IntakeCommands.RunMotorManual;
import frc.robot.commands.IntakeCommands.RunRollerWheels;
import frc.robot.commands.Climb.ManualClimb;
import frc.robot.commands.Climb.RaiseClimb;
import frc.robot.commands.Climb.RetractClimb;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeMotor;
import frc.robot.commands.ShooterCommands.PrimeShooter;
import frc.robot.commands.ShooterCommands.Shoot;
import frc.robot.commands.ShooterCommands.VomitShooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.EstimatorSubsystem;
import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.HoodSubsystem;
import frc.robot.commands.AngleHood;
import frc.robot.commands.ChangeHoodAngleByLargeInterval;
import frc.robot.commands.AngleHood.OperatingMode;

import frc.robot.commands.PathPlanner.*;

// import frc.robot.commands.printValue;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.ClimbSubsystem;

public class RobotContainer {
    private double MaxSpeed = 0.2 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private double SlowMaxSpeed = 0.2 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double SlowMaxAngularRate = RotationsPerSecond.of(0.75 * 0.2).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private final HoodSubsystem m_hood = new HoodSubsystem();

    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
    private final CommandXboxController m_operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);

    //operator controls


    /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
    .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final SwerveRequest.FieldCentric slowDrive = new SwerveRequest.FieldCentric()
    .withDeadband(SlowMaxSpeed * 0.1).withRotationalDeadband(SlowMaxAngularRate * 0.1) // Add a 10% deadband
    .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

  private final Telemetry logger = new Telemetry(MaxSpeed);
  private final CommandXboxController joystick = new CommandXboxController(0);
  //Subsystems
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final ClimbSubsystem m_climbsubsystem = new ClimbSubsystem();
  private final ShooterSubsystem m_shooter = new ShooterSubsystem();



  // Replace with CommandPS4Controller or CommandJoystick if needed
  //operator controls
  private final Trigger intakeRollers = m_driverController.leftTrigger();
  //private final Trigger strongIntake = m_operatorController.povLeft();
  private final Trigger pivotIntakeDown = m_driverController.leftBumper();
  private final Trigger pivotIntakeUp = m_driverController.rightBumper();
  //private final Trigger pivotDebugDown = m_operatorController.povRight();
  //private final Trigger pivotIntakeMid = m_operatorController.povLeft();
//   private final Trigger rollerDebug = m_driverController.x();


  // private final Trigger raiseClimb = m_driverController.rightBumper();
  // private final Trigger retractClimb = m_driverController.leftBumper();

  private final Trigger setTargetAngle = m_driverController.leftTrigger();

  private final Trigger primeShooter = m_driverController.a();
  private final Trigger Shoot = m_driverController.rightTrigger();

  private final Trigger override = m_operatorController.a();

  //private final Trigger angleHoodUp = m_operatorController.povUp();
  //private final Trigger angleHoodDown = m_operatorController.povDown();
  //private final Trigger VomitShooter = m_operatorController.x();

  // private final Trigger manualBackUp = m_driverController.povUp();
  // private final Trigger manualFrontUp = m_driverController.povRight();
  // private final Trigger manualBackDown = m_driverController.povDown();
  // private final Trigger manualFrontDown = m_driverController.povLeft();

  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();


  private final String[] cameras = new String[]{"Arducam_OV9281_USB_Camera"};
  private final VisionSubsystem m_vision = new VisionSubsystem(cameras);
  private final EstimatorSubsystem m_estimator = new EstimatorSubsystem(drivetrain, m_vision);

  private final SendableChooser<Command> autoChooser;
  
    public RobotContainer() {
        
      NamedCommands.registerCommand("StartShooterAuto", new StartShooterAuto(m_shooter));
      NamedCommands.registerCommand("RunKickAuto", new RunKickAuto(m_shooter));
      NamedCommands.registerCommand("StopShooterAuto", new StopShooterAuto(m_shooter));
      NamedCommands.registerCommand("StopKickAuto", new StopKickAuto(m_shooter));
      NamedCommands.registerCommand("RunIntakeAuto", new RunIntakeAuto(m_intake, Constants.intake.rollerSpeed));
      NamedCommands.registerCommand("StopIntakeAuto", new StopIntakeAuto(m_intake));
      NamedCommands.registerCommand("DeployIntakeAuto", new DeployIntakeAuto(m_intake));
      NamedCommands.registerCommand("RetractIntakeAuto", new RetractIntakeAuto(m_intake));
      //NamedCommands.registerCommand("SetHoodAngleAuto", new SetHoodAngleAuto(m_hood));
      //NamedCommands.registerCommand("SetHoodAngleNil", new SetHoodAngleNil(m_hood));

      
      try{
        configureBindings();
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData("Auto Choices", autoChooser);
      }
      catch(Exception E){
        E.printStackTrace();
        throw E;
      }
      

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
    //m_driverController.b().whileTrue(new printValue(m_intake));

    intakeRollers.whileTrue(new RunRollerWheels(m_intake, IntakeDirection.IN, Constants.intake.rollerSpeed));
    //strongIntake.whileTrue(new RunRollerWheels(m_intake, IntakeDirection.IN, Constants.intake.fastRollerSpeed));
    pivotIntakeDown.onTrue(new Pivot(m_intake, IntakeDirection.OUT));
    pivotIntakeUp.onTrue(new Pivot(m_intake, IntakeDirection.IN));

    // pivotDebugDown.whileTrue(new RunMotorManual(m_intake, 1, IntakeMotor.PIVOT));
    //pivotIntakeMid.whileTrue(new PivotMid(m_intake));
    
    // rollerDebug.whileTrue(new RunMotorManual(m_intake, 0.3, IntakeMotor.ROLLER));

    
    // primeShooter.whileTrue(new PrimeShooter(m_shooter, Constants.shooter.defaultSpeed));
    // Shoot.whileTrue(new Shoot(m_shooter, Constants.shooter.kickSpeed));
    //VomitShooter.whileTrue(new VomitShooter(m_shooter, Constants.shooter.vomitSpeed, null));

    // toggleIntakePivotCommands.onTrue(new ToggleIntakePivotCommands(new IntakePivotIn(m_intake), new IntakePivotOut(m_intake)));
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
   
    //  raiseClimb.whileTrue(new RaiseClimb(m_climbsubsystem));
    //  retractClimb.whileTrue(new RetractClimb(m_climbsubsystem));

    setTargetAngle.onTrue(new SetTargetAngle(m_hood, drivetrain));


    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    primeShooter.whileTrue(new PrimeShooter(m_shooter, Constants.shooter.shootSpeed));
    Shoot.whileTrue(new Shoot(m_shooter, Constants.shooter.kickSpeed));
    //VomitShooter.whileTrue(new VomitShooter(m_shooter, Constants.shooter.vomitSpeed, null));

    //Debug commands for climb
    // manualBackDown.whileTrue(new ManualClimb(m_climbsubsystem, false, true));
    // manualFrontDown.whileTrue(new ManualClimb(m_climbsubsystem, false, false));
    // manualBackUp.whileTrue(new ManualClimb(m_climbsubsystem, true, true));
    // manualFrontUp.whileTrue(new ManualClimb(m_climbsubsystem, true, false));

        // Build an auto chooser. This will use Commands.none() as the default option.

        // Another option that allows you to specify the default auto by its name
        // autoChooser = AutoBuilder.buildAutoChooser("My Default Auto");

       //TODO
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

        joystick.x().whileTrue(drivetrain.applyRequest(() -> brake));
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
        joystick.a().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        // angleHoodUp.onTrue(new ChangeHoodAngleByLargeInterval(m_hood, 1));
        // angleHoodDown.onTrue(new ChangeHoodAngleByLargeInterval(m_hood, -1));
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        return autoChooser.getSelected();
    }


    // private final Command m_complexAuto = new ComplexAuto(m_robotDirve, m_hatchSubsystem);

}
//shooter=work











