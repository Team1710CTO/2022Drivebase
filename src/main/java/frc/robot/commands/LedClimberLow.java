// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LedSubsystem;

public class LedClimberLow extends CommandBase {
    /** Creates a new Climb. */

    private LedSubsystem ledsubsystem;

    public LedClimberLow(LedSubsystem ledsubsystem) {

        this.ledsubsystem = ledsubsystem;

        addRequirements(ledsubsystem);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
        ledsubsystem.setLength();
        ledsubsystem.fillPercent(0, 200, 0, 10, false);

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
