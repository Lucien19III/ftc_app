package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by alucien on 11/9/2016.
 */

@Autonomous
//@Disabled   // comment out or remove this line to enable this opmode
public class FindLine extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    Servo armLeft;
    Servo armRight;
    OpticalDistanceSensor opticalDistanceSensor;
    ModernRoboticsI2cRangeSensor rangeSensor;
    ElapsedTime time;
    ColorSensor colorSensor;

    //static final double forwardTime = 1.0;


    int count = 0;

    //enum State {SENSE_COLOR, RED, BLUE, DONE};


    enum State {ONE, TWO, SENSE_COLOR, RED, BLUE, EXIT};
    State state;


    @Override
    public void runOpMode() {

        // get a reference to our compass
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");
        double distance = rangeSensor.cmOptical();
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");
        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        armLeft = hardwareMap.servo.get("arm_left");
        armRight = hardwareMap.servo.get("arm_right");
        motorRight.setDirection(com.qualcomm.robotcore.hardware.DcMotor.Direction.REVERSE);

        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");
        double reflectance = opticalDistanceSensor.getLightDetected();

        double refVar = 0.25;
        time = new ElapsedTime();
        // wait for the start button to be pressed
        waitForStart();

        switch (state) {
            case ONE:
                telemetry.addData("State", state);
            while (reflectance < refVar) {
                motorRight.setPower(0.5);
                motorLeft.setPower(0.5);
//look at the telemetry
                telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
                telemetry.addData("raw optical", rangeSensor.rawOptical());
                telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
                telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
            }
                state = state.TWO;
                break;

            case TWO:
                telemetry.addData("State", state);
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
                state = state.SENSE_COLOR;
                break;

                case SENSE_COLOR:
                    telemetry.addData("State", state);
                    if (colorSensor.blue() > 5) {
                    armLeft.setPosition(0);
                    motorRight.setPower(0.5);
                    motorLeft.setPower(0.5);
                    sleep(1500);
                    } else {
                        armRight.setPosition(0);
                        motorLeft.setPower(0.5);
                        motorRight.setPower(0.5);
                        sleep(1500);
                    }
                    state = state.EXIT;
                    break;

            case RED:
                telemetry.addLine("Switched to state.RED");
                break;

            case BLUE:
                telemetry.addLine("Switched to state.BLUE");
                break;

            case EXIT:
                telemetry.addLine("Switched to state.EXIT");
                break;

           /* case EXIT:
                telemetry.addData("State", state);
                telemetry.addData("Reflectance", reflectance);
                    motorRight.setPower(-0.5);
                    motorLeft.setPower(-0.5);
                    sleep(500);
                    motorLeft.setPower(0.5);
                    motorRight.setPower(-0.5);
                    sleep(750);

                  state = state.TWO
                  break;

                  case TWO:
                telemetry.addData("State", state);
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
                state = state.SENSE_COLOR;
                break;


                    */
        }
    }
}