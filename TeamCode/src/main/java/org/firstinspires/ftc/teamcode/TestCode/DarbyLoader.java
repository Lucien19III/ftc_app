package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wdarby on 11/29/2016.
 */
@TeleOp
public class DarbyLoader extends OpMode {

    final double ARM_OPEN_POSITION = -0.5;
    final double ARM_CLOSED_POSITION = 0.5;

    Servo loader;


    @Override
    public void init() {
        loader = hardwareMap.servo.get("loader");

    }

    @Override
    public void loop() {

        if (gamepad1.x){
            loader.setPosition(ARM_OPEN_POSITION);
        }
        if (gamepad1.a){
            loader.setPosition(ARM_CLOSED_POSITION);
        }
    }




}
