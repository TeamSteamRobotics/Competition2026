// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

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
  public static class HoodConstants {
    public static final int hoodElevateMotorID = 30;
    /**
     * SAFE maximum/minimum encoder value
     */
    public static final double hoodMaxEncoderValue = 0;  //TODO: Replace with actual value
    public static final double angleInterval = 0.1; //TODO: Replace with actual value
    public static final double largeAngleInterval = -0.2; //TODO: Replace with actual value
    //public static final double defaultAngleInterval = 3; //TODO: Replace with actual value
    public static final double hoodMinEncoderValue = -3.4; // TODO: Replace with actual value
    public static class PIDValues{
      public static final double kP = 0.05;
      public static final double kI = 0.0;
      public static final double kD = 0.0;
    }
    public static class HoodAngles {
      
    }
  }
    public class Vision{
    /**
     * Transformation from robot space to camera space, or from center of robot oriented forward to camera forward
     */
    public static final Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0, 0, 0));
    /**
     * List of standard deviations, 0 is x in meters, 1 is y in meters, 2 is heading in radians
     */
    public static final double[] robotPoseStdDev = {0.000508d, 0.002683d, 0.000763};
    /*
     * x Standard Deviation: 5.080060294706316E-4
     * y Standard Deviation: 0.0026839152279527845
     * theta Standard Deviation: 7.633268305608407E-4
     */

    public class FieldPositions {
      public static final double fieldLength = 0;
      public static final double fieldWidth = 0;

      public static final Translation2d hubPose = new Translation2d(0, 0);
      /**
       * TODO: Update for actual field positions
       */
      public static final ChainList<AprilTag> aprilTagList = new ChainList<AprilTag>()
        .chainAdd(new AprilTag(1, 
          new Pose3d(new Translation3d(16.697198, 0.65532, 1.4859), 
          new Rotation3d(new Quaternion(0.4539904997395468, 0, 0, 0.8910065241883678)))))
        .chainAdd(new AprilTag(2, 
          new Pose3d(new Translation3d(16.697198, 7.3964799999999995, 1.4859), 
          new Rotation3d(new Quaternion(-0.45399049973954675, 0, 0, 0.8910065241883679)))))
        .chainAdd(new AprilTag(3, 
          new Pose3d(new Translation3d(11.560809999999998, 8.05561, 1.30175), 
          new Rotation3d(new Quaternion(-0.7071067811865475, 0, 0, 0.7071067811865476)))))
        .chainAdd(new AprilTag(4, 
          new Pose3d(new Translation3d(9.276079999999999, 6.137656, 1.8679160000000001), 
          new Rotation3d(new Quaternion(0.9659258262890683, 0, 0.25881904510252074, 0)))))
        .chainAdd(new AprilTag(5, 
          new Pose3d(new Translation3d(9.276079999999999, 1.914906, 1.8679160000000001), 
          new Rotation3d(new Quaternion(0.9659258262890683, 0, 0.25881904510252074, 0)))))
        .chainAdd(new AprilTag(6, 
          new Pose3d(new Translation3d(13.474446, 3.3063179999999996, 0.308102), 
          new Rotation3d(new Quaternion(-0.8660254037844387, 0, 0, 0.49999999999999994)))))
        .chainAdd(new AprilTag(7, 
          new Pose3d(new Translation3d(13.890498, 4.0259, 0.308102), 
          new Rotation3d(new Quaternion(1, 0, 0, 0)))))
        .chainAdd(new AprilTag(8, 
          new Pose3d(new Translation3d(13.474446, 4.745482, 0.308102), 
          new Rotation3d(new Quaternion(0.8660254037844387, 0, 0, 0.49999999999999994)))))
        .chainAdd(new AprilTag(9, 
          new Pose3d(new Translation3d(12.643358, 4.745482, 0.308102), 
          new Rotation3d(new Quaternion(0.5000000000000001, 0, 0, 0.8660254037844386)))))
        .chainAdd(new AprilTag(10, 
          new Pose3d(new Translation3d(12.227305999999999, 4.0259, 0.308102), 
          new Rotation3d(new Quaternion(0, 0, 0, 1)))))
        .chainAdd(new AprilTag(11, 
          new Pose3d(new Translation3d(12.643358, 3.3063179999999996, 0.308102), 
          new Rotation3d(new Quaternion(-0.4999999999999998, 0, 0, 0.8660254037844387)))))
        .chainAdd(new AprilTag(12, 
          new Pose3d(new Translation3d(0.8511540, 0.65532, 1.4859), 
          new Rotation3d(new Quaternion(0.8910065241883679, 0, 0, 0.45399049973954675)))))
        .chainAdd(new AprilTag(13, 
          new Pose3d(new Translation3d(0.851154, 7.3964799999999995, 1.4859), 
          new Rotation3d(new Quaternion(-0.8910065241883678, 0, 0, 0.45399049973954686)))))
        .chainAdd(new AprilTag(14, 
          new Pose3d(new Translation3d(8.272272, 6.137656, 1.8679160000000001), 
          new Rotation3d(new Quaternion(0, -0.25881904510252074, 0, 0.9659258262890683)))))
        .chainAdd(new AprilTag(15, 
          new Pose3d(new Translation3d(8.272272, 1.914906, 1.8679160000000001), 
          new Rotation3d(new Quaternion(0, -0.25881904510252074, 0, 0.9659258262890683)))))
        .chainAdd(new AprilTag(16, 
          new Pose3d(new Translation3d(5.9875419999999995, -0.0038099999999999996, 1.30175), 
          new Rotation3d(new Quaternion(0.7071067811865476, 0, 0, 0.7071067811865476)))))
        .chainAdd(new AprilTag(17, 
          new Pose3d(new Translation3d(4.073905999999999, 3.3063179999999996, 0.308102), 
          new Rotation3d(new Quaternion(-0.4999999999999998, 0, 0, 0.8660254037844387)))))
        .chainAdd(new AprilTag(18, 
          new Pose3d(new Translation3d(3.6576, 4.0259, 0.308102), 
          new Rotation3d(new Quaternion(0, 0, 0, 1.0)))))
        .chainAdd(new AprilTag(19, 
          new Pose3d(new Translation3d(4.073905999999999, 4.745482, 0.308102), 
          new Rotation3d(new Quaternion(0.5000000000000001, 0, 0, 0.8660254037844386)))))
        .chainAdd(new AprilTag(20, 
          new Pose3d(new Translation3d(4.904739999999999, 4.745482, 0.308102), 
          new Rotation3d(new Quaternion(0.8660254037844387, 0, 0, 0.49999999999999994)))))
        .chainAdd(new AprilTag(21, 
          new Pose3d(new Translation3d(5.321046, 4.0259, 0.308102), 
          new Rotation3d(new Quaternion(1, 0, 0, 0)))))
        .chainAdd(new AprilTag(22, 
          new Pose3d(new Translation3d(4.904739999999999, 3.3063179999999996, 0.308102), 
          new Rotation3d(new Quaternion(-0.8660254037844387, 0, 0, 0.49999999999999994)))));
    }
  }
  public class FieldPositions {

    public static Translation2d hubPose;
  }
}


