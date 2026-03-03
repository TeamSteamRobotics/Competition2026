// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
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
      public static final double fieldLength = 16.541;
      public static final double fieldWidth = 8.069;
      public static final ChainList<AprilTag> aprilTagList = new ChainList<AprilTag>()
        .chainAdd(new AprilTag(1, 
          new Pose3d(new Translation3d(11.8779798, 7.4247756, 0.889), 
          new Rotation3d(new Quaternion(6.123233995736766e-17, 0, 0, 1)))))
        .chainAdd(new AprilTag(2, 
          new Pose3d(new Translation3d(11.9154194, 4.638039999999999, 1.12395), 
          new Rotation3d(new Quaternion(0.7071067811865476, 0, 0, 0.7071067811865476)))))
        .chainAdd(new AprilTag(3, 
          new Pose3d(new Translation3d(11.3118646, 4.3902376, 1.12395), 
          new Rotation3d(new Quaternion(6.123233995736766e-17, 0, 0, 1)))))
        .chainAdd(new AprilTag(4, 
          new Pose3d(new Translation3d(11.3118646, 4.0346376, 1.12395), 
          new Rotation3d(new Quaternion(6.123233995736766e-17, 0, 0, 1)))))
        .chainAdd(new AprilTag(5, 
          new Pose3d(new Translation3d(11.8779798, 3.4312351999999997, 1.12395), 
          new Rotation3d(new Quaternion(-0.7071067811865476, -0, 0, 0.7071067811865476)))))
        .chainAdd(new AprilTag(6, 
          new Pose3d(new Translation3d(13.474446, 0.6444996, 0.889), 
          new Rotation3d(new Quaternion(6.123233995736766e-17, 0, 0, 1)))))
        .chainAdd(new AprilTag(7, 
          new Pose3d(new Translation3d(11.9528844, 0.6444996, 0.889), 
          new Rotation3d(new Quaternion(1, 0, 0, 0)))))
        .chainAdd(new AprilTag(8, 
          new Pose3d(new Translation3d(12.2710194, 3.4312351999999997, 1.12395), 
          new Rotation3d(new Quaternion(-0.7071067811865476, -0, 0, 0.7071067811865476)))))
        .chainAdd(new AprilTag(9, 
          new Pose3d(new Translation3d(12.519177399999998, 3.6790375999999996, 1.12395), 
          new Rotation3d(new Quaternion(1, 0, 0, 0)))))
        .chainAdd(new AprilTag(10, 
          new Pose3d(new Translation3d(12.519177399999998, 4.0346376, 1.12395), 
          new Rotation3d(new Quaternion(1, 0, 0, 0)))))
        .chainAdd(new AprilTag(11, 
          new Pose3d(new Translation3d(12.2710194, 4.638039999999999, 1.12395), 
          new Rotation3d(new Quaternion(0.7071067811865476, 0, 0, 0.7071067811865476)))))
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
          new Rotation3d(new Quaternion(-0.8660254037844387, 0, 0, 0.49999999999999994)))))
        .chainAdd(new AprilTag(23,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(24,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(25,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(26,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(27,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(28,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(29,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(30,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(31,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))))
        .chainAdd(new AprilTag(32,
          new Pose3d(new Translation3d(0, 0, 0),
          new Rotation3d(new Quaternion(0, 0, 0, 0)))));
    }
  }
}
