// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import com.ctre.phoenix6.HootAutoReplay;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private static final String kDefaultAuto = "Default";
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser();

    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;

    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {
        m_robotContainer = new RobotContainer();

        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        SmartDashboard.putData("Auto choices", m_chooser);



    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 
        
        SmartDashboard.putBoolean("Hub Active", isHubActive());
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }

        m_autoSelected = m_chooser.getSelected();
    }

    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
            case kDefaultAuto:
            default:
                break;
        }
    }

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}

    public boolean isHubActive() {
  Optional<Alliance> alliance = DriverStation.getAlliance();
  // If we have no alliance, we cannot be enabled, therefore no hub.
  if (alliance.isEmpty()) {
    return false;
  }
  // Hub is always enabled in autonomous.
  if (DriverStation.isAutonomousEnabled()) {
    return true;
  }
  // At this point, if we're not teleop enabled, there is no hub.
  if (!DriverStation.isTeleopEnabled()) {
    return false;
  }

  // We're teleop enabled, compute.
  double matchTime = DriverStation.getMatchTime();
  String gameData = DriverStation.getGameSpecificMessage();
  // If we have no game data, we cannot compute, assume hub is active, as its likely early in teleop.
  if (gameData.isEmpty()) {
    return true;
  }
  boolean redInactiveFirst = false;
  switch (gameData.charAt(0)) {
    case 'R' -> redInactiveFirst = true;
    case 'B' -> redInactiveFirst = false;
    default -> {
      // If we have invalid game data, assume hub is active.
      return true;
    }
  }

  // Shift was is active for blue if red won auto, or red if blue won auto.
  boolean shift1Active = switch (alliance.get()) {
    case Red -> !redInactiveFirst;
    case Blue -> redInactiveFirst;
  };

  if (matchTime > 130) {
    // Transition shift, hub is active.
    return true;
  } else if (matchTime > 105) {
    // Shift 1
    return shift1Active;
  } else if (matchTime > 80) {
    // Shift 2
    return !shift1Active;
  } else if (matchTime > 55) {
    // Shift 3
    return shift1Active;
  } else if (matchTime > 30) {
    // Shift 4
    return !shift1Active;
  } else {
    // End game, hub always active.
    return true;
  }
}
}
