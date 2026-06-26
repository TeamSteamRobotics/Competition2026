// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.*;

import java.util.ResourceBundle.Control;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import yams.mechanisms.config.ElevatorConfig;
import yams.mechanisms.positional.Elevator;
import yams.motorcontrollers.SmartMotorController;
import yams.motorcontrollers.SmartMotorControllerConfig;
import yams.motorcontrollers.SmartMotorControllerConfig.ControlMode;
import yams.motorcontrollers.SmartMotorControllerConfig.MotorMode;
import yams.motorcontrollers.SmartMotorControllerConfig.TelemetryVerbosity;
import yams.motorcontrollers.local.SparkWrapper; 

public class ElevatorTestSubsystem extends SubsystemBase {

  public static enum Mode{
    PID,
    SET_HEIGHT,
    IDLE
  }
  /** Creates a new ElevatorTestSubsystem. */
  private SmartMotorControllerConfig elevatorMotorConfig = new SmartMotorControllerConfig()
      .withControlMode(ControlMode.CLOSED_LOOP)
      .withMechanismCircumference(Meters.of(Inches.of(Constants.ElevatorConstants.inchesPerRotation).in(Meters)))
      .withClosedLoopController(Constants.ElevatorConstants.PIDConstants.kP,
        Constants.ElevatorConstants.PIDConstants.kI,
        Constants.ElevatorConstants.PIDConstants.kD)
      .withSimClosedLoopController(Constants.ElevatorConstants.PIDConstants.kP,
        Constants.ElevatorConstants.PIDConstants.kI,
        Constants.ElevatorConstants.PIDConstants.kD)
      .withTrapezoidalProfile(MetersPerSecond.of(Constants.ElevatorConstants.TrapezoidalProfile.maxSpeed), 
        MetersPerSecondPerSecond.of(Constants.ElevatorConstants.TrapezoidalProfile.maxAccel))
      .withFeedforward(new ElevatorFeedforward(0, 0, 0))
      .withSimFeedforward(new ElevatorFeedforward(0, 0, 0))
      .withTelemetry("ElevatorMotor", TelemetryVerbosity.HIGH)
      .withGearing(Constants.ElevatorConstants.gearReduction)
      .withMotorInverted(false)
      .withIdleMode(MotorMode.BRAKE)
      .withStatorCurrentLimit(Amps.of(Constants.ElevatorConstants.statorCurrentLimit))
      .withClosedLoopRampRate(Seconds.of(Constants.ElevatorConstants.PIDConstants.closedLoopRampRate))
      .withOpenLoopRampRate(Seconds.of(0.25));

    
  private SparkMax spark = new SparkMax(Constants.ElevatorConstants.elevatorMotorID, MotorType.kBrushless);
  private SmartMotorController sparkSmartMotorController = new SparkWrapper(spark, DCMotor.getNEO(1), elevatorMotorConfig);

  private ElevatorConfig elevatorConfig = new ElevatorConfig(sparkSmartMotorController)
    .withStartingHeight(Meters.of(Constants.ElevatorConstants.Heights.startingHeight))
    .withHardLimits(Meters.of(Constants.ElevatorConstants.Heights.minHeight), 
      Meters.of(Constants.ElevatorConstants.Heights.minHeight))
    .withTelemetry("Elevator", TelemetryVerbosity.HIGH)
    .withMass(Kilograms.of(Constants.ElevatorConstants.mass));
  
  private Elevator elevator = new Elevator(elevatorConfig);

  Distance targetHeight;
  Mode mode;

  public ElevatorTestSubsystem() {
    mode = Mode.IDLE;
  }

  private void setTargetHeight(Distance height, Mode setMode){
    targetHeight = height;
    mode = setMode;
  }
  public Command setHeight(Distance height, Mode setMode) {
    return runOnce(
      () -> setTargetHeight(height, setMode)
    );
  }

  @Override
  public void periodic() {
    switch(mode){
      case PID:
        elevator.setMeasurementPositionSetpoint(targetHeight);
        break;
      case SET_HEIGHT:
        elevator.run(targetHeight);
        break;
      case IDLE:
        elevator.run(Meters.of(0));
        break;
    }
    // This method will be called once per scheduler run
  }
}
