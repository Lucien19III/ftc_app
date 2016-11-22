package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by CJS on 11/18/16.
 */

@Autonomous
public class ColorSensorBeaconDetect extends OpMode {

    enum BeaconColor {RED, BLUE, NONE}
    BeaconColor color;

    //Sensor Var
    ColorSensor clrSensor;

    //Other Var
    int RedValue;
    int BlueValue;

    @Override
    public void init() {

        clrSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "sensor_color");
        telemetry.addData("[HardwareMap]", "clrSensor Mapped.");
        telemetry.update();

    }

    @Override
    public void loop() {

        //Pull R and B values
        RedValue = clrSensor.red();
        BlueValue = clrSensor.blue();

        //What team is this side of the beacon?
        if (RedValue >= 4 && BlueValue <= 2) {
            //If red is predominate:
            color = color.RED;
        } else if (BlueValue >= 4 && RedValue <= 2) {
            //If blue is predominate:
            color = color.BLUE;
        } else {
            //If neither color is predominate:
            color = color.NONE;
        }

        //Telemetry
        telemetry.addData("[Red]", RedValue);
        telemetry.addData("[Blue]", BlueValue);
        telemetry.addData("[Team]", color);
        telemetry.update();

    }

}
