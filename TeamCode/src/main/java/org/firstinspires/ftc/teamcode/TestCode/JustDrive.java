package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.DarbyRobotDrive;

/**
 * Created by wdarby on 11/29/2016.
 */
@TeleOp
public class JustDrive extends OpMode {
    final double HAND_LEFT_POSITION = 0.0;
    final double HAND_RIGHT_POSITION = 1.0;

    DarbyRobotDrive myRobotDrive;
    Servo hand;

    @Override
    public void init() {
        myRobotDrive = new DarbyRobotDrive(hardwareMap.dcMotor.get("motor_left"), hardwareMap.dcMotor.get("motor_right"));
        hand = hardwareMap.servo.get("hand");
    }

    @Override
    public void loop() {

        myRobotDrive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y, true);

        if (gamepad1.x){
            hand.setPosition(HAND_LEFT_POSITION);
        }
        if (gamepad1.a){
            hand.setPosition(HAND_RIGHT_POSITION);
        }


    }
}
