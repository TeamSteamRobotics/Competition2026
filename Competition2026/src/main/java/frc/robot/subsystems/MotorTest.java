// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MotorTest extends SubsystemBase {
  /** Creates a new MotorTest. */
  TalonFX falcon;

  SparkMax sparkMaxMotor;
  SparkFlex sparkFlexMotor;

  RelativeEncoder sparkEncoder;

  SparkBaseConfig config;

  PIDController controller;
  public MotorTest() {

    
    sparkFlexMotor = new SparkFlex(0, MotorType.kBrushless);
    sparkMaxMotor = new SparkMax(1, MotorType.kBrushless);

    sparkEncoder = sparkMaxMotor.getEncoder();

    config = new SparkFlexConfig();
    config
      .idleMode(IdleMode.kBrake)
      .follow(sparkMaxMotor);

    falcon = new TalonFX(2, "canivore");

    controller = new PIDController(0.1, 0.1, 0.1);

    sparkFlexMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

    sparkMaxMotor.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    double speed = controller.calculate(sparkEncoder.getPosition(), 0);
    sparkMaxMotor.set(speed);
    //sparkFlexMotor.set(0.5);

    falcon.set(0.5);
    // This method will be called once per scheduler run
  }
}
