package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by wdarby on 11/8/2016.
 */
@TeleOp
public class CompTeleOP extends OpMode {
    DarbyRobotDrive myRobotDrive;
    DcMotor arm;
    DcMotor pullBack;
    Servo trigger;
    Servo loader;

    private ElapsedTime runtime = new ElapsedTime();
    //neverest 60:1
    final static int ENCODER_CPR = 420;
    final static double GEAR_RATIO = 1;
    //final static double REEL_DIAMETER = 2.1; //cm
    //final static double DRAW = 12;  // change this to change the drawback distance

    final static double CIRCUMFERENCE = 6.2;   //cm         //Math.PI * REEL_DIAMETER;
    //final static double ROTATIONS = DRAW / CIRCUMFERENCE;
    final static double COUNTS_PER_CM = ENCODER_CPR * GEAR_RATIO / CIRCUMFERENCE;

    static final double PULL_SPEED = 0.6;
    static final double RELEASE_SPEED = 0.5;


    @Override
    public void init() {
        myRobotDrive = new DarbyRobotDrive(hardwareMap.dcMotor.get("motor_left"), hardwareMap.dcMotor.get("motor_right"));
        //get references to the arm motor from the hardware map
        arm = hardwareMap.dcMotor.get("arm");
        pullBack = hardwareMap.dcMotor.get("pullBack");
        trigger = hardwareMap.servo.get("trigger");
        loader = hardwareMap.servo.get("loader");

    }

    @Override
    public void loop() {
        // Call the tankDrive method of the RobotDrive class // works!
        myRobotDrive.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y, true);

        //hand button presser codes
        //changed servo values to full on and x and a to activate
        /*
        if (gamepad2.x) {
            leftGripper.setPosition(LEFT_OPEN_POSITION);
        } else {
            leftGripper.setPosition(LEFT_CLOSED_POSITION);
        }
        if (gamepad2.a) {
            rightGripper.setPosition(RIGHT_OPEN_POSITION);
        } else {
            rightGripper.setPosition(RIGHT_CLOSED_POSITION);
        }
        */
        //cocking mech
        //gamepad2.a should run a cocking sequence
        //needs encoder values for specifc distance...

        //trigger
        //maybe gamepad2.a ();
        if (gamepad2.a) {
            trigger.setPosition(1.0);
        } else {
            trigger.setPosition(0.0);
        }

        /*if (gamepad2.b) {
            // some how run lockNload
            trigger.setPosition(0.0);
            lockNload(PULL_SPEED, 15, 4.0);
            lockNload(RELEASE_SPEED, -15, 4.0);
            trigger.setPosition(1.0);
        }
        */
        //arm code
        // This code will control the up and down movement of
        // the arm using the y and b gamepad buttons
        //could set encoder values so it doesn't go to far? or touch button?
        arm.setPower(gamepad2.left_stick_y /5);

        if (gamepad2.y) {
            arm.setPower(0.4);
        } else if (gamepad2.b) {
            arm.setPower(-0.4);
        } else {
            arm.setPower(0);
        }

        //loader code
        if (gamepad2.left_bumper) {
            loader.setPosition(0.0);
        } else {
            loader.setPosition(1.0);
        }
    }

    public void lockNload(double speed, double pullBackCM, double timeoutS) {
        int newPullBackTarget;

        // Ensure that the opmode is still active
        if (true) {

            // Determine new target position, and pass to motor controller
            newPullBackTarget = pullBack.getCurrentPosition() + (int) (pullBackCM * COUNTS_PER_CM);
            pullBack.setTargetPosition(newPullBackTarget);


            // Turn On RUN_TO_POSITION
            pullBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            pullBack.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (runtime.seconds() < timeoutS && pullBack.isBusy()) {
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



   //appendix!

    /**
     * Created by alucien on 11/1/2016.
     */
    //@Autonomous
   /*
    public static class EncoderCode extends OpMode {
        DcMotor rightMotor;
        DcMotor leftMotor;

        final static int ENCODER_CPR = 1440;
        final static double GEAR_RATIO = 2;
        final static int WHEEL_DIAMETER = 4;
        final static int DISTANCE = 24;

        final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        final static double ROTATIONS = DISTANCE / CIRCUMFERENCE;
        final static double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        @Override
        public void init() {
            leftMotor = hardwareMap.dcMotor.get("motor_left");
            rightMotor = hardwareMap.dcMotor.get("motor_right");

            rightMotor.setDirection(DcMotor.Direction.REVERSE);

            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //leftMotor.setChannelMode(DcMotorController.RunMode.RESET_ENCODERS);
            //rightMotor.setCha
        }

        @Override
        public void start() {
            leftMotor.setTargetPosition((int) COUNTS);
            rightMotor.setTargetPosition((int) COUNTS);

            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode (DcMotor.RunMode.RUN_TO_POSITION);

            leftMotor.setPower(0.5);
            rightMotor.setPower(0.5);
        }
        @Override
        public void loop() {
            telemetry.addData("Motor Target", COUNTS);
            telemetry.addData("Left Position", leftMotor.getCurrentPosition());
            telemetry.addData("Right Position", rightMotor.getCurrentPosition());
        }



    }
}
*/
