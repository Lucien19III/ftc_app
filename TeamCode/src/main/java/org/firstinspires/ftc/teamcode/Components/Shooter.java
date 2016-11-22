package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DarbyRobotDrive;

import java.sql.Time;

/**
 * Created by CJS on 11/19/16.
 */

@TeleOp
public class Shooter extends OpMode {

    DarbyRobotDrive myRobotDrive;

    private ElapsedTime runtime = new ElapsedTime();
    double driveTime = 4.0;


    @Override
    public void init() {

        myRobotDrive = new DarbyRobotDrive(hardwareMap.dcMotor.get("motor_left"), hardwareMap.dcMotor.get("motor_right"));

    }

    @Override
    public void loop() {

       // if (driveTime < runtime) {

        }

    }


