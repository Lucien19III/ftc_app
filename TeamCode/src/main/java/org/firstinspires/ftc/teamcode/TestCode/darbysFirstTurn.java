package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by wdarby on 10/13/2016.
 */

//@TeleOp
public class darbysFirstTurn extends OpMode {

    DcMotor leftMotor;
    DcMotor rightMotor;


    @Override
    public void init() {
        //get references to the motors from the hardware map
        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");

        //reverse the left motor
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    @Override
    public void loop () {
        //get the values from the gamepads
        //note: pushing the stick all the way up returns -1, so we need to reverse the values
        float leftY = -gamepad1.left_stick_y;
        float rightY = -gamepad1.right_stick_y;

        //set the power of the motors with the gamepad values
        leftMotor.setPower(leftY);
        rightMotor.setPower(rightY);
/*
        @Override
        public void loop () {
         float throttle =-gamepad1.left_stick_y;
         rightMotor.setPower(throttle);
          leftMotor.setPower(throttle);
        }

       @Override
        public void loop(){
        */
            //get the values from the gamepads
            //note: pushing the stick all the way up returns -1,
            //so we need to reverse the y values
            float xValue = gamepad1.left_stick_x;
            float yValue = -gamepad1.left_stick_y;

            //calculate the power needed for each motor
            float leftPower = yValue + xValue;
            float rightPower = yValue + xValue;

            //clip the power values so that it only goes from -1 to 1
            leftPower = Range.clip(leftPower, -1, 1);
            rightPower = Range.clip(rightPower, -1, 1);

            //set the power of the motors with the gamepad values
            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);

        }
    }


