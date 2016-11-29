package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by alucien on 11/29/2016.
 */
@TeleOp
public class lightsensetest extends OpMode {
    OpticalDistanceSensor opticalDistanceSensor;
    double reflectance;

    @Override
    public void init() {
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");
    }

    @Override
    public void loop() {
        reflectance = opticalDistanceSensor.getLightDetected();
        telemetry.addData("Reflectance", reflectance);
        telemetry.update();
    }
}
