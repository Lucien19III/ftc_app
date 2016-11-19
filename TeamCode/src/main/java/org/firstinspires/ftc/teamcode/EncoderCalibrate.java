package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by mgreg on 11/18/2016.
 */
@TeleOp
public class EncoderCalibrate extends LinearOpMode {
    DcMotor arm;
    DcMotor pullBack;

    //neverest 60:1
    final static int ENCODER_CPR = 420;
    final static double GEAR_RATIO = 1;
    final static double REEL_DIAMETER = 2.1; //cm
    final static double DRAW = 12;  // change this to change the drawback distance

    final static double CIRCUMFERENCE = Math.PI * REEL_DIAMETER;
    final static double ROTATIONS = DRAW / CIRCUMFERENCE;
    final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

    @Override
    public void runOpMode() throws InterruptedException {

   {
        arm = hardwareMap.dcMotor.get("arm");
        pullBack = hardwareMap.dcMotor.get("pullBack");

        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                arm.getCurrentPosition(),
                pullBack.getCurrentPosition());
        telemetry.update();

       // Wait for the game to start (driver presses PLAY)
       waitForStart();

       encoder

        telemetry.addData("arm position", arm.getCurrentPosition());
        telemetry.addData("pull Back position", pullBack.getCurrentPosition());

        arm.setPower(gamepad1.left_stick_y);
        pullBack.setPower(gamepad1.right_stick_y);


        telemetry.update();
    }
}
