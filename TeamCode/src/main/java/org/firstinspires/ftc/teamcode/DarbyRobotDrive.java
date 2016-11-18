package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by wdarby on 11/8/2016.
 */


public class DarbyRobotDrive {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public DarbyRobotDrive(DcMotor left, DcMotor right) {
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

    public void tankDrive(double leftValue, double rightValue) {
        leftMotor.setPower(leftValue);
        rightMotor.setPower(rightValue);

        }
    public void tankDrive(double leftValue, double rightValue, boolean squareInputs) {
        // If squareInputs is true, square each of the inputs, preserving the sign
        if (squareInputs) {
            if (leftValue >= 0.0) {
                leftValue = (leftValue * leftValue);
            } else {
                leftValue = -(leftValue * leftValue);
            }
            if (rightValue >= 0.0) {
                rightValue = (rightValue * rightValue);
            } else {
                rightValue = -(rightValue * rightValue);
            }
        }
        //Apply the values using the other tankDrive method
        tankDrive(leftValue, rightValue);

    }
}
