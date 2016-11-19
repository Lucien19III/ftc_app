package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by CJS on 11/17/16.
 */


@Autonomous
public class ColeOpMode extends OpMode {

    enum  SensorMode {Ultrasonic, Optical}
    SensorMode sensorMode;


    //Sensors
    ModernRoboticsI2cRangeSensor rngSensor;
    int range;




    @Override
    public void init () {

        rngSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

    }


    @Override
    public void loop () {


        boolean toggle;

        if (rngSensor.rawOptical() > 0) {
            sensorMode = sensorMode.Optical;
            toggle = false;
        } else {
            sensorMode = sensorMode.Ultrasonic;
            toggle = true;
        }

        if (toggle) {
            range = rngSensor.rawUltrasonic();
        } else {
            range = rngSensor.rawOptical();
        }

        //Telemetry
        telemetry.addData("[Range Mode]", "Range Sensor is in " + sensorMode);
        telemetry.addData("[Range Value]", range);
        telemetry.update();

    }

}
