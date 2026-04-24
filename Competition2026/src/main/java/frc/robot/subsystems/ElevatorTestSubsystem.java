// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.*;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
      .withMechanismCircumference(Meters.of(Inches.of(0.25).in(Meters)))
      .withClosedLoopController(4, 0, 0)
      .withSimClosedLoopController(4, 0, 0)
      .withTrapezoidalProfile(MetersPerSecond.of(0.5), MetersPerSecondPerSecond.of(0.5))
      .withFeedforward(new ElevatorFeedforward(0, 0, 0))
      .withSimFeedforward(new ElevatorFeedforward(0, 0, 0))
      .withTelemetry("ElevatorMotor", TelemetryVerbosity.HIGH)
      .withGearing(12)
      .withMotorInverted(false)
      .withIdleMode(MotorMode.BRAKE)
      .withStatorCurrentLimit(Amps.of(40))
      .withClosedLoopRampRate(Seconds.of(0.25))
      .withOpenLoopRampRate(Seconds.of(0.25));

    
  private SparkMax spark = new SparkMax(1, MotorType.kBrushless);
  private SmartMotorController sparkSmartMotorController = new SparkWrapper(spark, DCMotor.getNEO(1), elevatorMotorConfig);

  private ElevatorConfig elevatorConfig = new ElevatorConfig(sparkSmartMotorController)
    .withStartingHeight(Meters.of(0.5))
    .withHardLimits(Meters.of(0), Meters.of(3))
    .withTelemetry("Elevator", TelemetryVerbosity.HIGH)
    .withMass(Kilograms.of(10));
  
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
