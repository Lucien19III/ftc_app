package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by alucien on 10/25/2016.
 */

public class RobotDrive {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public RobotDrive(DcMotor left, DcMotor right) {
        leftMotor = left;
        rightMotor = right;

        rightMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void arcadeDrive(double forwardSpeed, double turnRate) {
        leftMotor.setPower(forwardSpeed + turnRate);
        rightMotor.setPower(forwardSpeed - turnRate);
    }
    public void arcadeDrive(Gamepad gamepad) {
        arcadeDrive(-gamepad.left_stick_y, gamepad.left_stick_x);
    }
     public void tankDrive(double leftValue, double rightValue){
        leftMotor.setPower(leftValue);
        rightMotor.setPower(rightValue);
    }
    public void tankDrive (double leftValue, double rightValue, boolean squareInputs) {
        if (squareInputs) {
            if(leftValue >= 0.0 ) {
                leftValue = (leftValue*leftValue);
            } else {
                leftValue = -(leftValue*leftValue);
            }
            if(rightValue >= 0.0 ) {
                rightValue = (rightValue*rightValue);
            } else {
                rightValue = -(rightValue*rightValue);
            }

        }
        tankDrive(leftValue, rightValue);
    }





}
