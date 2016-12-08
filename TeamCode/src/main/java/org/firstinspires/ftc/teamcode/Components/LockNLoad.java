package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by CJS on 12/8/16.
 */

@TeleOp
public class LockNLoad extends OpMode {

    DcMotor drawBack;
    DcMotor aimRot;
    Servo lockPin;



    @Override
    public void init () {

        drawBack = hardwareMap.dcMotor.get("motor_drawback");
        aimRot = hardwareMap.dcMotor.get("motor_aimrot");
        lockPin = hardwareMap.servo.get("servo_lock");


        drawBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        aimRot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        aimRot.setDirection(DcMotorSimple.Direction.REVERSE);

        lockPin.setPosition(0.0);

    }

    @Override
    public void loop () {

        //PinLock Servo
        if (gamepad1.right_trigger > 0.8) {
            lockPin.setPosition(0.8);
        } else {
            lockPin.setPosition(0.0);
        }

        //DrawBack Motor
        if (gamepad1.right_stick_y >= 0) {
            drawBack.setPower(Math.pow(gamepad1.right_stick_y, 2));
        } else {
            drawBack.setPower(-(Math.pow(gamepad1.right_stick_y, 2)));
        }

        //AimRot Motor
        if (gamepad1.left_stick_y >= 0) {
            aimRot.setPower(Math.pow(gamepad1.left_stick_y, 2));
        } else {
            aimRot.setPower(-(Math.pow(gamepad1.left_stick_y, 2)));
        }

        telemetry.addData("DrawBack Power", drawBack.getPower());
        telemetry.addData("DrawBack Encoder", drawBack.getCurrentPosition());

        telemetry.addData("AimRot Power", aimRot.getPower());
        telemetry.addData("AimRot Encoder", aimRot.getCurrentPosition());

        telemetry.update();

    }
}
