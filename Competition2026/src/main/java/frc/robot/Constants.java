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
    public static final int kDriverOperatorPort = 1;
  }

  public class intake {
    public static final int intakePivotID = 19;
    public static final int rollerId = 18;

    public static final double rollerSpeed = 0.3; //TODO: Replace with actual value
    public static final double pivotSpeed = 0.05;

    public static final double intakePivotMinEncoderValue = 0; //TODO: Replace with actual value
    public static final double intakePivotMaxEncoderValue = 2.0; //TODO: Replace with actual value
    public static class PIDValues {
      public static final double kP = 0.1; //TODO: Replace with actual value
      public static final double kI = 0.1; //TODO: Replace with actual value
      public static final double kD = 0.1; //TODO: Replace with actual value
    }
  }
}
