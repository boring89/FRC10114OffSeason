// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  private Timer time = new Timer();
  private double nowTime;

  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private WPI_VictorSPX LF, LB, RF, RB;
  private Joystick m_Joystick = new Joystick(0);
  private MotorControllerGroup LGroup, RGroup;
  private DifferentialDrive m_Drive;


  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    LF = new WPI_VictorSPX(0);
    LB = new WPI_VictorSPX(1);
    RF = new WPI_VictorSPX(2);
    RB = new WPI_VictorSPX(3);
    LGroup = new MotorControllerGroup(LF, LB);
    RGroup = new MotorControllerGroup(RF, RB);
    m_Drive = new DifferentialDrive(LGroup, RGroup);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    LGroup.setInverted(true);
    m_autoSelected = m_chooser.getSelected();
    System.out.println("AutoSelected" + m_autoSelected);

    time.reset();
    time.start();
  }


  @Override
  public void autonomousPeriodic() {
    nowTime = time.get();
    if (nowTime < 3) {
      LGroup.set(1.0);
      RGroup.set(1.0);
    }else {
      LGroup.set(0.0);
      RGroup.set(0.0);
    }
  }

  @Override
  public void teleopInit() {
    LGroup.setInverted(true);
  }

  @Override
  public void teleopPeriodic() {
    double RTrigger = m_Joystick.getRawAxis(3);
    double LTrigger = m_Joystick.getRawAxis(2);
    double LStickX = m_Joystick.getRawAxis(0);
    double Speed = RTrigger - LTrigger;
    double Turn = LStickX;
    m_Drive.arcadeDrive(Speed, Turn);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
