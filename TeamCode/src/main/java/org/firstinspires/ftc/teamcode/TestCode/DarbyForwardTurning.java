package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by wdarby on 10/27/2016.
 */
//@Autonomous
public class DarbyForwardTurning extends LinearOpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
          @Override
    public void runOpMode() throws InterruptedException {
              //set up the left and right motors from the configuration file
              leftMotor = hardwareMap.dcMotor.get("motor_left");
              rightMotor = hardwareMap.dcMotor.get("motor_right");
              //reverse the right side motor
              rightMotor.setDirection(DcMotor.Direction.REVERSE);

              // Wait for the start button to be pressed
              waitForStart();
              //Set the motors to drive the robot forward
              leftMotor.setPower(0.5);
              rightMotor.setPower(0.5);

              //Wait for 2 seconds
              sleep(2000);

              //Stop the robot
              //leftMotor.setPower(0);
              //rightMotor.setPower(0);

              //Set the motors to turn the robot right
              leftMotor.setPower(0.5);
              rightMotor.setPower(-0.5);

              //Wait for 1.1 seconds
              sleep(1100);

              //Stop the robot
              leftMotor.setPower(0);
              rightMotor.setPower(0);
          }

}
