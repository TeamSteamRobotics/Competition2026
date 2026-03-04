// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//package frc.robot.subsystems;

import java.io.ObjectInputFilter.Config;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

    public void runIntake(double speed){
    m_intakeMotor.setI(speed);
    }
    
    public void stopIntake(){
      m_motor.set(0.8);

    }


    public IntakeSubsytem (){
 Config
      .idleMode(IdleMode.PIDValues.kP)
      .closedLoop
        .p(Constants.IntakeConstants.PIDValues.kP)
        .i(Constants.IntakeConstants.PIDValues.kI)
        .d(Constants.IntakeConstants.PIDValues.kD);

        targetAngle = ConstantsIntakeConstants.intakeEncoderValue;
        
        IntakePivotMotor= new Sparkmax(Constants.IntakeConstants.IntakePivotMotorID, MotorType.kBrushless);

        


        
  
        



    }
  
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
