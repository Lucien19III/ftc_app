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
    final double LEFT_CLOSED_POSITION = 1.0;
    final double RIGHT_OPEN_POSITION = 1.0;
    final double RIGHT_CLOSED_POSITION = 0.0;

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

        //hand button presser codes
        //changed servo values to full on and x and a to activate
        if (gamepad2.x) {
            leftGripper.setPosition(LEFT_OPEN_POSITION);
        } else {
            leftGripper.setPosition(LEFT_CLOSED_POSITION);
        }
        if (gamepad2.a) {
            rightGripper.setPosition(RIGHT_OPEN_POSITION);
        } else {
            rightGripper.setPosition(RIGHT_CLOSED_POSITION);
        }

        //cocking mech
        //gamepad2.a should run a cocking sequence
        //needs encoder values for specifc distance...

        //trigger
        //maybe gamepad2.right_trigger (float);

        //arm code
        // This code will control the up and down movement of
        // the arm using the y and b gamepad buttons
        //could set encoder values so it doesn't go to far?
        if (gamepad2.y) {
            arm.setPower(0.5);
        } else if (gamepad2.b) {
            arm.setPower(-0.5);
        } else {
            arm.setPower(0);

        }
    }
}

