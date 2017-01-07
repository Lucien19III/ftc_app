package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by CJS on 1/6/17.
 */


@Autonomous
public class LineFollow extends OpMode {

    enum States {FOLLOW_LINE, STOP}
    States state = States.FOLLOW_LINE;

    OpticalDistanceSensor lightS;
    ModernRoboticsI2cRangeSensor rangeS;
    DcMotor mLeft;
    DcMotor mRight;

    double whiteLevel;
    double distance;
    double distanceIn;
    double expire;
    double firstTick = 0;
    double lastTick = 0;

    @Override
    public void init() {
        lightS = hardwareMap.opticalDistanceSensor.get("sensor_light");
        rangeS = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        mLeft = hardwareMap.dcMotor.get("drive_left");
        mRight = hardwareMap.dcMotor.get("drive_right");
        mLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        whiteLevel = lightS.getLightDetected();
        distanceIn = rangeS.rawUltrasonic();

        //Smooth out the spamminess that is the ultrasonic data
        if (distanceIn != lastTick) {
            if (distanceIn != firstTick) {
                distance = distanceIn;
            }
        }
        firstTick = lastTick;
        lastTick = distanceIn;

        //Report sensor data
        telemetry.addData("Distance", distance);
        telemetry.addData("WhiteLevel", whiteLevel);

        switch (state) {
            case FOLLOW_LINE:
                //Turn back and forth to follow the line
                if (whiteLevel < 0.3) {
                    mLeft.setPower(0.3);
                    mRight.setPower(0.05);
                    telemetry.addData("Direction","Right");
                } else {
                    mLeft.setPower(0.05);
                    mRight.setPower(0.3);
                    telemetry.addData("Direction","Left");
                }
                //If there is something blocking the way, stop
                if (distance <= 12) {
                    state = States.STOP;
                    expire = getRuntime() + 10;
                    telemetry.clear();
                    telemetry.addData("Robit", "I am being blocked.");
                }
                break;

            case STOP:
                //Stop motors
                mLeft.setPower(0.0);
                mRight.setPower(0.0);
                //See if obstacle moves
                if (distance > 12) {
                    state = States.FOLLOW_LINE;
                    telemetry.clear();
                    telemetry.addData("Robit", "The obstacle cleared.");
                }
                //Make the program stop after 10 seconds of sitting still
                if (expire <= getRuntime()) {
                    telemetry.clear();
                    telemetry.addData("Robit", "I'm bored.");
                    telemetry.update();
                }
                break;
        }

        telemetry.update();
    }

}
