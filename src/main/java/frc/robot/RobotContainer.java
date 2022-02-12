// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Button;

import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ZeroIntake;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.PowerDistributionSubsystem;


public class RobotContainer {
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  private final GyroSubsystem m_GyroSubsystem = new GyroSubsystem();
  public final static XboxController m_controller = new XboxController(0);
  public final PowerDistributionSubsystem m_PowerDistributionSubsystem = new PowerDistributionSubsystem();
  public static IndexerSubsystem m_iIndexerSubsystem = new IndexerSubsystem();

  public static IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();
  

  public RobotContainer() {
    // Set up the default command for the drivetrain.
    // The controls are for field-oriented driving:
    // Left stick Y axis -> forward and backwards movement
    // Left stick X axis -> left and right movement
    // Right stick X axis -> rotation
    m_drivetrainSubsystem.setDefaultCommand(new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -modifyAxis(-m_controller.getLeftY()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_controller.getLeftX()) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
            () -> -modifyAxis(-m_controller.getRightX()) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND
    ));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Back button zeros the gyroscope
    new Button(m_controller::getBackButton)
            // No requirements because we don't need to interrupt anything
            .whenPressed(m_GyroSubsystem::zeroBestGyro)
            .whenReleased(m_GyroSubsystem::setIsZeroingFalse);

    new Button(m_controller::getRightBumper)
            .whileHeld(new IntakeCommand(mIntakeSubsystem));

    new Button(m_controller::getStartButton)
            .whenPressed(new ZeroIntake(mIntakeSubsystem));

    //new Button(m_controller::getStartButton)
    //        .whenPressed(new ZeroIntake());
            
    //new Button(m_controller::getAButton).whenPressed(new climberActuatorIn(servoSubsystem));
    
    //new Button(m_controller::getBButton).whenPressed(new climberActuatorOut(servoSubsystem));
  
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new InstantCommand();
  }

  private static double deadband(double value, double deadband) {

    if (Math.abs(value) > deadband) {

      if (value > 0.0) {

        return (value - deadband) / (1.0 - deadband);

      } else {

        return (value + deadband) / (1.0 - deadband);

      }

    } else {

      return 0.0;
      
    }
  }

  public static double modifyAxis(double value) {
    // Deadband
    value = deadband(value, 0.15);

    // Square the axis
    value = Math.copySign(value * value, value);

    return value;
  }


}
