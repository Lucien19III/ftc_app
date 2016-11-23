package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by alucien on 11/23/2016.
 */
@TeleOp
public class TankDrive extends OpMode {
   DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");

        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.left_stick_y > 0) {
            motorLeft.setPower(gamepad1.left_stick_y * gamepad1.left_stick_y);
        } else if (gamepad1.left_stick_y < 0) {
            motorLeft.setPower(-(gamepad1.left_stick_y * gamepad1.left_stick_y));
        } else {
            motorLeft.setPower(0.0);
        }
        if (gamepad1.right_stick_y > 0) {

            motorRight.setPower(gamepad1.right_stick_y * gamepad1.right_stick_y);
        } else  if (gamepad1.right_stick_y < 0){
            motorRight.setPower(-(gamepad1.right_stick_y * gamepad1.right_stick_y));
        } else {
            motorRight.setPower(0.0);

        }
    }
}
