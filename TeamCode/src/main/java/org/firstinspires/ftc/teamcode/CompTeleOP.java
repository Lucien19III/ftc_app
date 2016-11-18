package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
/**
 * Created by wdarby on 11/8/2016.
 */
@TeleOp
public class CompTeleOP extends OpMode {
    DarbyRobotDrive myRobotDrive;
    DcMotor arm;

    final double LEFT_OPEN_POSITION = 0.0;
    final double LEFT_CLOSED_POSITION = 0.5;
    final double RIGHT_OPEN_POSITION = 1.0;
    final double RIGHT_CLOSED_POSITION = 0.5;

    Servo leftGripper;
    Servo rightGripper;

    @Override
    public void init() {
        myRobotDrive = new DarbyRobotDrive(hardwareMap.dcMotor.get("motor_left"), hardwareMap.dcMotor.get("motor_right"));
        //get references to the arm motor from the hardware map
        arm = hardwareMap.dcMotor.get("arm");
        leftGripper = hardwareMap.servo.get("arm_left");
        rightGripper = hardwareMap.servo.get("arm_right");
    }

    @Override
    public void loop() {
        // Call the tankDrive method of the RobotDrive class
        myRobotDrive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y, true);

        //arm code
        // This code will control the up and down movement of
        // the arm using the y and b gamepad buttons

        if (gamepad2.y) {
            arm.setPower(1);
        } else if (gamepad2.b) {
            arm.setPower(-1);
        } else {
            arm.setPower(0);

            if (gamepad1.x) {
                leftGripper.setPosition(LEFT_OPEN_POSITION);
                rightGripper.setPosition(RIGHT_OPEN_POSITION);

            }
            if (gamepad1.a) {
                leftGripper.setPosition(LEFT_CLOSED_POSITION);
                rightGripper.setPosition(RIGHT_CLOSED_POSITION);

            }

        }
    }
}

