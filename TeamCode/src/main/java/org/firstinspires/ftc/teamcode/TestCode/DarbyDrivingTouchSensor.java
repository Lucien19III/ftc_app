package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by wdarby on 11/3/2016.
 */
//@Autonomous
public class DarbyDrivingTouchSensor extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;
    TouchSensor touchSensor;

    @Override
    public void init() {
        //get references to the motors from the hardware map
        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");

        //reverse the right motor
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        //get a reference to the touch sensor
        touchSensor = hardwareMap.touchSensor.get("sensor_touch");
    }
        @Override

        public void loop(){

            if (touchSensor.isPressed()) {
                //Stop the motors if the touch sensor is pressed
                leftMotor.setPower(0);
                rightMotor.setPower(0);
            } else {
                //Keep driving if the touch sensor is not pressed
                leftMotor.setPower(0.5);
                rightMotor.setPower(0.5);
            }
            telemetry.addData("isPressed", String.valueOf(touchSensor.isPressed()));
            }
            }






