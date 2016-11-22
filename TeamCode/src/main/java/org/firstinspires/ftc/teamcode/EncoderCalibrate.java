package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by mgreg on 11/18/2016.
 */
@TeleOp
public class EncoderCalibrate extends LinearOpMode {
    DcMotor arm;
    DcMotor pullBack;

    private ElapsedTime runtime = new ElapsedTime();
    //neverest 60:1
    final static int ENCODER_CPR = 420;
    final static double GEAR_RATIO = 1;
    final static double REEL_DIAMETER = 2.1; //cm
    //final static double DRAW = 12;  // change this to change the drawback distance

    final static double CIRCUMFERENCE = Math.PI * REEL_DIAMETER;
    //final static double ROTATIONS = DRAW / CIRCUMFERENCE;
    final static double COUNTS_PER_CM = ENCODER_CPR * GEAR_RATIO / CIRCUMFERENCE;

    static final double PULL_SPEED = 0.6;
    static final double RELEASE_SPEED = 0.5;

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
            telemetry.addData("Path0", "Starting at %7d :%7d",
                    arm.getCurrentPosition(),
                    pullBack.getCurrentPosition());
            telemetry.update();

            // Wait for the game to start (driver presses PLAY)
            waitForStart();

            lockNload(PULL_SPEED, 20, 2.0);
            lockNload(RELEASE_SPEED, 20, 3.0);

            telemetry.addData("arm position", arm.getCurrentPosition());
            telemetry.addData("pull Back position", pullBack.getCurrentPosition());

            arm.setPower(gamepad1.left_stick_y);
            pullBack.setPower(gamepad1.right_stick_y);


            telemetry.update();
        }
    }

    public void lockNload(double speed,
                          double pullBackCM,
                          double timeoutS) {
        int newPullBackTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newPullBackTarget = pullBack.getCurrentPosition() + (int) (pullBackCM * COUNTS_PER_CM);
            pullBack.setTargetPosition(newPullBackTarget);


            // Turn On RUN_TO_POSITION
            pullBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            pullBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (pullBack.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newPullBackTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        pullBack.getCurrentPosition());
                telemetry.update();
            }
            // Stop all motion;
            pullBack.setPower(0);

            // Turn off RUN_TO_POSITION
            pullBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}