package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by mgreg on 10/20/2016.
 */

public class TouchSensBackUp extends LinearOpMode {
        DcMotor leftMotor;
        DcMotor rightMotor;
        TouchSensor touchSensor;

        int BACKUP_TIME = 1000;
        int TURN_TIME = 2000;

    @Override
    public void runOpMode() throws InterruptedException {

        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        touchSensor = hardwareMap.touchSensor.get("sensor_touch");

        waitForStart();

        while (true) {
            if (touchSensor.isPressed()) {
                leftMotor.setPower(-0.25);
                rightMotor.setPower(-0.25);
                telemetry.addData("state", "backing_up");
                sleep(BACKUP_TIME);

            leftMotor.setPower(0.30);
            rightMotor.setPower(-0.30);
            telemetry.addData("state", "turning");
                sleep(TURN_TIME);
            } else {
                leftMotor.setPower(0.5);
                rightMotor.setPower(0.5);
                telemetry.addData("state", "driving");
            }


            waitOneFullHardwareCycle();
        }
    }
}


