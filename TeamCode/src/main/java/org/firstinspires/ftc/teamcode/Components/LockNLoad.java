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

    boolean mortarDown = false;

    @Override
    public void init () {

        drawBack = hardwareMap.dcMotor.get("motor_drawback");
        aimRot = hardwareMap.dcMotor.get("motor_aimrot");
        lockPin = hardwareMap.servo.get("servo_lock");


        drawBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drawBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        aimRot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        aimRot.setPower(0.3);

        lockPin.setPosition(0.0);

    }

    public void Aim(String pos) {

        if (pos == "UP") {
            aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            aimRot.setPower(0.5);
            aimRot.setTargetPosition(0);
        } else if (pos == "DOWN") {
            aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            aimRot.setPower(0.4);
            aimRot.setTargetPosition(4300);
        }
    }

    @Override
    public void loop () {

        //PinLock Servo
        if (gamepad1.right_trigger > 0.8) {
            lockPin.setPosition(0.7);
        } else {
            lockPin.setPosition(0.0);
        }

        //DrawBack Motor
        if (gamepad1.right_stick_y >= 0) {
            drawBack.setPower(Math.pow(gamepad1.right_stick_y, 2));
        } else {
            drawBack.setPower(-(Math.pow(gamepad1.right_stick_y, 2)));
        }

        if (gamepad1.left_bumper) {
            //Pull pin out
            //Draw bow
            //wait for bow to be drawn
            //Drop pin
            //Let bow winch out
        }

        //AimRot Motor
        if (mortarDown) {
            if (gamepad1.dpad_up) {
                Aim("UP");
                mortarDown = false;
            }
        } else {
            if (gamepad1.dpad_down) {
                Aim("DOWN");
                mortarDown = true;
            }
        }

        if (!aimRot.isBusy()) {
            aimRot.setPower(0.0);
            aimRot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        /*if (gamepad1.left_stick_y >= 0) {
            aimRot.setPower(Math.pow(gamepad1.left_stick_y, 2));
        } else {
            aimRot.setPower(-(Math.pow(gamepad1.left_stick_y, 2)));
        }*/

        telemetry.addData("DrawBack Power", drawBack.getPower());
        telemetry.addData("DrawBack Encoder", drawBack.getCurrentPosition());

        telemetry.addData("AimRot Power", aimRot.getPower());
        telemetry.addData("AimRot Encoder", aimRot.getCurrentPosition());

        telemetry.update();

    }

    @Override
    public void stop() {
        Aim("UP");
        while (aimRot.isBusy()) {
            telemetry.addData("Robot","Resetting");
            telemetry.update();
        }
        aimRot.setPower(0.0);
        aimRot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
