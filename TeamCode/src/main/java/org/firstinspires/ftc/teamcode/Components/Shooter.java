package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DarbyRobotDrive;

import java.sql.Time;

/**
 * Created by CJS on 11/19/16.
 */

@Autonomous

public class Shooter extends OpMode {

    DcMotor winchMotor;
    DcMotor angleMotor;
    Servo pinLock;
    Servo ballSweep;

    enum SweepState {OPEN, CLOSE}
    SweepState sweepState;

    boolean released = true;


    @Override
    public void init() {

        telemetry.addData("[Init]", "Beginning");
        telemetry.update();

        //winchMotor = hardwareMap.dcMotor.get("motor_winch");
        //angleMotor = hardwareMap.dcMotor.get("motor_angle");
        //pinLock = hardwareMap.servo.get("servo_pin");
        ballSweep = hardwareMap.servo.get("servo_sweep");

        telemetry.addData("[Init]", "HardwareMap Finished");
        telemetry.update();

        ballSweep.setPosition(0.7);
        sweepState = sweepState.OPEN;
        //pinLock.setDirection(Servo.Direction.REVERSE);
        //pinLock.setPosition(0.0);

        telemetry.addData("[Init]", "Finished");
        telemetry.update();
    }

    @Override
    public void loop() {

        //winchMotor.setPower(gamepad1.left_stick_y);
        if (gamepad1.right_bumper) {
            if (sweepState == sweepState.CLOSE && released) {
                ballSweep.setPosition(0.7);
                sweepState = sweepState.OPEN;
                released = false;
            } else if (sweepState == sweepState.OPEN && released) {
                ballSweep.setPosition(0.0);
                sweepState = sweepState.CLOSE;
                released = false;
            }
        } else {
            released = true;
        }
        /*if (gamepad1.x) {
            pinLock.setPosition(0.4);
        } else {
            pinLock.setPosition(0.0);
        }*/

    }


}