package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by alucien on 10/25/2016.
 */

public class FollowLine extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    OpticalDistanceSensor opticalDistanceSensor;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");

    }

    @Override
    public void loop() {
        double reflectance = opticalDistanceSensor.getLightDetected();

        if (reflectance >= 0.25) {
            rightMotor.setPower(-0.2);
            leftMotor.setPower(0);
        }
        else  {
            leftMotor.setPower(-0.2);
            rightMotor.setPower(0);
            telemetry.addData("Reflectance", reflectance);
        }

    }
}
