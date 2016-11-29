package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.DarbyRobotDrive;

/**
 * Created by wdarby on 11/29/2016.
 */
@TeleOp
public class JustDrive extends OpMode {
    DarbyRobotDrive myRobotDrive;

    @Override
    public void init() {
        myRobotDrive = new DarbyRobotDrive(hardwareMap.dcMotor.get("motor_left"), hardwareMap.dcMotor.get("motor_right"));
    }

    @Override
    public void loop() {
        myRobotDrive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y, true);
    }
}
