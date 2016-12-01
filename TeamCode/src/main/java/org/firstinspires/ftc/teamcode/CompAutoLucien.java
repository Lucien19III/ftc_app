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
    double refVar = 0.6;

    //static final double forwardTime = 1.0;


    int count = 0;

    //enum State {SENSE_COLOR, RED, BLUE, DONE};


    enum State {ONE, TWO, THREE,SENSE_COLOR};
    State state;


    @Override
    public void init() {

        // get a reference to our compass
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");
        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        //armLeft = hardwareMap.servo.get("arm_left");
        //armRight = hardwareMap.servo.get("arm_right");
        motorRight.setDirection(com.qualcomm.robotcore.hardware.DcMotor.Direction.REVERSE);
        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");
        state = State.ONE;
    }

    @Override
    public void loop () {

        distance = rangeSensor.getDistance(DistanceUnit.CM);
        reflectance = opticalDistanceSensor.getLightDetected();

        switch (state) {
            case ONE:
                telemetry.addData("State", state);
                telemetry.update();

                motorRight.setPower(0.2);
                motorLeft.setPower(0.2);

                telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
                telemetry.addData("raw optical", rangeSensor.rawOptical());
                telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.addData("Reflectance", reflectance);
                telemetry.update();
                if (reflectance >= refVar) {
                    state = State.TWO;
                }
                    break;

            case TWO:
                telemetry.addData("State", state);

                    if (reflectance <= refVar) {
                        motorRight.setPower(0.2);
                        motorLeft.setPower(-0.1);
                    } else {
                        motorLeft.setPower(0.2);
                        motorRight.setPower(-0.1);
                    }
                    telemetry.addData("Reflectance", reflectance);
                    telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                    telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
                    telemetry.update();

                if (distance < 15) {
                    state = State.THREE;
                }
                    break;

            case THREE:
                motorRight.setPower(0.0);
                motorLeft.setPower(0.0);


         break;
            case SENSE_COLOR:
                telemetry.addData("State", state);
                telemetry.update();

                if (colorSensor.blue() > 5) {
                    armLeft.setPosition(0);
                    motorRight.setPower(0.2);
                    motorLeft.setPower(0.2);
                    //Need to sleep for 1500 nanoseconds
                } else {
                    armRight.setPosition(1);
                    motorLeft.setPower(0.2);
                    motorRight.setPower(0.2);
                    //Need to sleep for 1500 nanoseconds
                }
                break;
        }
    }
}

