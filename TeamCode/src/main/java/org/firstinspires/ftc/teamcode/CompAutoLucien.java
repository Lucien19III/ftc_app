package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by alucien on 11/9/2016.
 */

@Autonomous
//@Disabled   // comment out or remove this line to enable this opmode
public class CompAutoLucien extends OpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    Servo armLeft;
    Servo armRight;
    OpticalDistanceSensor opticalDistanceSensor;
    ModernRoboticsI2cRangeSensor rangeSensor;
    ColorSensor colorSensor;

    double distance;
    double reflectance;
    double refVar = 0.25;

    //static final double forwardTime = 1.0;


    int count = 0;

    //enum State {SENSE_COLOR, RED, BLUE, DONE};


    enum State {ONE, TWO};
    State state;


    @Override
    public void init() {

        // get a reference to our compass
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");
        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        armLeft = hardwareMap.servo.get("arm_left");
        armRight = hardwareMap.servo.get("arm_right");
        motorRight.setDirection(com.qualcomm.robotcore.hardware.DcMotor.Direction.REVERSE);
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");
        state = State.ONE;
    }

    @Override
    public void loop () {

        distance = rangeSensor.cmOptical();
        reflectance = opticalDistanceSensor.getLightDetected();

        switch (state) {
            case ONE:
                telemetry.addData("State", state);
                telemetry.update();
            while (reflectance < refVar) {
                motorRight.setPower(0.5);
                motorLeft.setPower(0.5);
                telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
                telemetry.addData("raw optical", rangeSensor.rawOptical());
                telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
            }
                state = State.TWO;
                break;

            case TWO:
                telemetry.addData("State", state);
                telemetry.update();

                while (distance > 20) {
                    if (reflectance <= 0.25) {
                        motorRight.setPower(-0.2);
                        motorLeft.setPower(0);
                    } else {
                        motorLeft.setPower(-0.2);
                        motorRight.setPower(0);
                    }
                    telemetry.addData("Reflectance", reflectance);
                    telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                }
                break;
        }
    }
}