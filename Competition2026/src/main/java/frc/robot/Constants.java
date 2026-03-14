// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    public static final int kBluetoothControllerPort = 2;
  }
  public class ClimbMotors {
    public static final int climbBack = 24;
    public static final int climbFront = 23;
    public static final double maxClimbRotations = 3; //fix later
    public static double minClimbRotations = -1; //placeholder
    public static double climbSpeed = 0.05; //placeholder
  }
  public class drive {
    public class FrontLeft{
      public static final int driveId = 1;
      public static final int steerId = 2;
      public static final int encoderId = 3;
    }
      
    public class FrontRight{
      public static final int driveId = 4;
      public static final int steerId = 5;
      public static final int encoderId = 6;
    }

    public class BackLeft {
      public static final int driveId = 7;
      public static final int steerId = 8;
      public static final int encoderId = 9;
    }
      
    public class BackRight {
      public static final int driveId = 10;
      public static final int steerId = 11;
      public static final int encoderId = 12;
    }

  } 

  public class shooter {
    public static final int shooterLeftId = 21;
    public static final int shooterRightId = 20;
    public static final int feedRollersId = 22;
    public static final double defaultSpeed = 0.75;
    public static final double kickSpeed = 0.3; //0.1 filler speed fix to be accurate
    public static final double vomitSpeed = 0.60;
    public static final int indexerMotorId = 25;
    public static final double speed = 0.25;

    public class ShooterPid{
      public static final double kP = 0.08;
      public static final double kI = 0.001;
      public static final double kD = 3.0;
      public static final double tolerance = 0.1;   
    }
  }

  public class intake {
    public static final int intakePivotID = 19;
    public static final int rollerId = 18;

    public static final double rollerSpeed = 0.25; //TODO: Replace with actual value
    public static final double pivotSpeed = 0.05;

    public static final double intakePivotMinEncoderValue = 0;
    public static final double intakePivotMaxEncoderValue = 1.1; //should be 2.0
    public static class PIDValues {
      public static final double kP = 0.3; //TODO: Replace with actual value
      public static final double kI = 0.00; //TODO: Replace with actual value
      public static final double kD = 0.0; //TODO: Replace with actual value
    }
    public static class UpPIDValues {
      public static final double kP = 0.45; //TODO: Replace with actual value
      public static final double kI = 0.0; //TODO: Replace with actual value
      public static final double kD = 0.0; //TODO: Replace with actual value
    }
  }
}



/**
 *   public class DriveMotors {
    public class FrontLeft {
      public static final int driveId = 1;
      public static final int steerId = 2;
      public static final int encoderId = 3;

    }
    public class FrontRight {
      public static final int driveId = 4;
      public static final int steerId = 5;
      public static final int encoderId = 6;
    }
    public class BackLeft {
      public static final int driveId = 7;
      public static final int steerId = 8;
      public static final int encoderId = 9;
    }
    public class BackRight {
      public static final int driveId = 10;
      public static final int steerId = 11;
      public static final int encoderId = 12;
      }
  }




  public class Shooter{ 
    public static final int greenRollerId = 19;
    public static final int frontRollerId = 20;
    public static final int backRollerId = 21;
    public static final double defaultSpeed = 0.40;
    public static final double vomitSpeed = -0.1;
    public static final double rollerSpeed = 0.40;
    public static final double speedIncrement = 0.0;
 */