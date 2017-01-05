package org.firstinspires.ftc.teamcode.Components;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.MatrixConstants;
import com.qualcomm.robotcore.robocol.PeerAppDriverStation;

/**
 * Created by CJS on 12/8/16.
 */

@TeleOp
public class LockNLoad extends OpMode {

    //Super Secret Cheat Code
    String[] inputCode = new String[4];

    enum BowStates {ARMED, ARMING, LOCK, UNLOCK, UNREELING, DISARM, DISARMED}
    BowStates bowstate = BowStates.DISARMED;

    DcMotor rightDrive;
    DcMotor leftDrive;
    DcMotor drawBack;
    DcMotor aimRot;
    Servo lockPin;
    Servo grabArm;
    Servo beaconArm;

    boolean bowMoving = false;
    boolean mortarDown = false;
    boolean pinOut = false;

    double startTime;
    double startTime2;
    double startTime3;

    int targetDraw = 4000; // <--- Set draw target right here.
    double pinOutVal = 0.9;

    @Override
    public void init () {

        //Hardware Mapping
        rightDrive = hardwareMap.dcMotor.get("drive_right");
        leftDrive = hardwareMap.dcMotor.get("drive_left");
        
        drawBack = hardwareMap.dcMotor.get("motor_draw_back");
        aimRot = hardwareMap.dcMotor.get("motor_aim_rot");
        lockPin = hardwareMap.servo.get("servo_lock");
        grabArm = hardwareMap.servo.get("servo_grab_arm");
        beaconArm = hardwareMap.servo.get("servo_beacon");

        //Setting Defaults
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        drawBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drawBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        aimRot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        aimRot.setPower(0.3);

        lockPin.setPosition(0.0);

        grabArm.setDirection(Servo.Direction.REVERSE);
        grabArm.setPosition(1.0);

    }

    private void Aim(String pos) {

        if (pos == "UP") {
            aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            aimRot.setPower(0.9);
            aimRot.setTargetPosition(0);
        } else if (pos == "DOWN") {
            aimRot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            aimRot.setPower(0.8);
            aimRot.setTargetPosition(3000);
        }
    }

    float rangeMap(float inVar, float a, float b, float c, float d) {
        float rangeA = b - a;
        float rangeB = d - c;
        float percent = inVar / rangeA;
        float returnVar = (percent * rangeB) + c;
        return returnVar;
    }

    @Override
    public void loop () {

        //Untested Area-----------------------------------------------------------------------------

        //Little arm on top...
        grabArm.setPosition(rangeMap(gamepad1.left_trigger,0f,1f,0.1f,1f)); //<--- adjust arm on barrel with [left trigger]

        //PinLock Servo -- right bumper is added as a safety for firing
        if (gamepad1.right_bumper) { //<--- safety off when holding [right bumper]
            if (gamepad1.right_trigger > 0.3 && bowstate == BowStates.ARMED) { //<--- fire by pulling [right trigger]
                pinOut = true;
                bowstate = BowStates.DISARMED;
                if (lockPin.getPosition() > 0.5) {
                    pinOut = false;
                }
            }
        } else if (bowstate == BowStates.DISARMED) {
            pinOut = false;
        }

        //What happens when you pull the trigger:
        if (gamepad1.left_bumper && !bowMoving && bowstate == BowStates.DISARMED) { //<--- arm by pressing [left bumper]
            bowMoving = true;
            bowstate = BowStates.ARMING;
        } else if (gamepad1.back && !bowMoving && bowstate == BowStates.ARMED) { //<---  disarm by pressing [back]
            bowMoving = true;
            startTime2 = getRuntime();
            bowstate = BowStates.DISARM;
        }

        if (bowMoving) {
            switch (bowstate) {
                case ARMING:
                    //Pull pin out
                    pinOut = true;

                    //Crank tubing back
                    drawBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    drawBack.setPower(1.0);
                    drawBack.setTargetPosition(targetDraw);

                    //When tubing is all the way back, lock
                    if (!drawBack.isBusy()) {
                        startTime = getRuntime();
                        bowstate = BowStates.LOCK;
                    }
                    break;

                case LOCK:
                    //Push pin in
                    pinOut = false;

                    //When pin is all the way in, unreel
                    if (startTime + 0.2 <= getRuntime()) {
                        bowstate = BowStates.UNREELING;
                    }
                    break;

                case UNREELING:
                    //Make slack line for firing
                    drawBack.setTargetPosition(0);

                    //When done unwinding, it's armed
                    if (!drawBack.isBusy()) {
                        bowstate = BowStates.ARMED;
                    }
                    break;

                case DISARM:
                    //Pull slack out of line
                    drawBack.setTargetPosition(targetDraw);

                    //When done winding, pull pin
                    if (!drawBack.isBusy()) {
                        if (startTime2 + 0.2 <= getRuntime()) {
                            bowstate = BowStates.UNLOCK;
                            startTime3 = getRuntime();
                        }
                    }
                    break;

                case UNLOCK:
                    //Pull pin
                    pinOut = true;

                    //When pin pulled, unwind bow
                    drawBack.setTargetPosition(0);
                    //When unwound, drop pin
                    if (!drawBack.isBusy()) {
                        if (startTime3 + 0.2 <= getRuntime()) {
                            pinOut = false;
                        }
                    }
                    if (!drawBack.isBusy() && lockPin.getPosition() == 0) {
                        bowstate = BowStates.DISARMED;
                    }
                    break;

                case ARMED:
                    //Exit section of loop
                    bowMoving = false;
                    break;

                case DISARMED:
                    //Exit section of loop
                    bowMoving = false;
                    pinOut = false;
                    break;
            }
        }

        //End Untested Area-------------------------------------------------------------------------

        //AimRot Motor
        if (mortarDown) {
            if (gamepad1.dpad_up) {
                Aim("UP");
                mortarDown = false;
            }
        } else {
            if (gamepad1.dpad_down) {
                Aim("DOWN");
                mortarDown = true;
            }
        }

        if (!aimRot.isBusy()) {
            aimRot.setPower(0.0);
            aimRot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        //Pull Servo out when true
        if (pinOut) {
            lockPin.setPosition(pinOutVal);
        } else {
            lockPin.setPosition(0.0);
        }


        /*if (gamepad1.left_stick_y >= 0) {
            aimRot.setPower(Math.pow(gamepad1.left_stick_y, 2));
        } else {
            aimRot.setPower(-(Math.pow(gamepad1.left_stick_y, 2)));
        }*/

        telemetry.addData("DrawBack Power", drawBack.getPower());
        telemetry.addData("DrawBack Encoder", drawBack.getCurrentPosition());

        telemetry.addData("AimRot Power", aimRot.getPower());
        telemetry.addData("AimRot Encoder", aimRot.getCurrentPosition());

        telemetry.update();


        //Driver stuff...
        if (gamepad2.right_stick_y >= 0) {
            rightDrive.setPower(Math.pow(gamepad2.right_stick_y, 2));
        } else {
            rightDrive.setPower(-Math.pow(gamepad2.right_stick_y, 2));
        }

        if (gamepad2.left_stick_y >= 0) {
            leftDrive.setPower(Math.pow(gamepad2.left_stick_y, 2));
        } else {
            leftDrive.setPower(-Math.pow(gamepad2.left_stick_y, 2));
        }

        beaconArm.setPosition(gamepad2.right_trigger);


        //Cheat code to give pad1 driving control...
        /*boolean driveAccess = false;
        boolean depressed = false;
        String finalCode = "#";
        int activeIndex = 0;
        if (gamepad1.dpad_left && activeIndex < 5) {
            if (gamepad1.a && !depressed) { //<--- add "a" to code string when pressed
                inputCode[activeIndex] = "a";
                activeIndex += 1;
                depressed = true;
            } else if (gamepad1.b && !depressed) { //<--- add "b" to code string when pressed
                inputCode[activeIndex] = "b";
                activeIndex += 1;
                depressed = true;
            } else if (gamepad1.x && !depressed) { //<--- add "x" to code string when pressed
                inputCode[activeIndex] = "x";
                activeIndex += 1;
                depressed = true;
            } else if (gamepad1.y && !depressed) { //<--- add "y" to code string when pressed
                inputCode[activeIndex] = "y";
                activeIndex += 1;
                depressed = true;
            } else {
                depressed = false;
            }

            for (int i = 0; i <= inputCode.length; i += 1) {
                finalCode = finalCode + inputCode[i];
            }

            if (finalCode == "#xbya") {
                driveAccess = true;
            } else if (finalCode == "#abyx") {
                driveAccess = false;
            }
        } else {
            inputCode = new String[4];
            activeIndex = 0;
        }

        //If pad1 has driving privileges then let it drive and fine aim
        if (driveAccess) {
            double xVal;
            double yVal;

            //Setting individual axis for combination later
            if (gamepad1.left_stick_y >= 0) {
                yVal = Math.pow(gamepad1.left_stick_y, 2);
            } else {
                yVal = -Math.pow(gamepad1.left_stick_y, 2);
            }
            if (gamepad1.left_stick_x >= 0) {
                xVal = Math.pow(gamepad1.left_stick_x, 2);
            } else {
                xVal = -Math.pow(gamepad1.left_stick_x, 2);
            }

            //Adding a cap at 1 and -1
            if (yVal > 1) {
                yVal = 1;
            } else if (yVal < -1) {
                yVal = -1;
            } else if (xVal > 1) {
                xVal = 1;
            } else if (xVal < -1) {
                xVal = -1;
            }

            //Pad1 set power to motors
            if (yVal >= 0) {
                rightDrive.setPower(yVal - xVal);
                leftDrive.setPower(yVal + xVal);
            } else {
                rightDrive.setPower(yVal + xVal);
                leftDrive.setPower(yVal - xVal);
            }

            //Pad1 Aiming (finer than normal aiming)
            if (gamepad1.right_stick_y >= 0) {
                aimRot.setPower(Math.pow(gamepad1.right_stick_y, 2));
            } else {
                aimRot.setPower(-Math.pow(gamepad1.right_stick_y, 2));
            }
        }
        */
        //End Cheat Code

    }

    //Reset Everything
    @Override
    public void stop() {
        Aim("UP");
        drawBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (bowstate == BowStates.ARMED) {
            startTime2 = getRuntime();
        }

        while (aimRot.isBusy() || bowstate != BowStates.DISARMED) {
            if (bowstate == BowStates.ARMED) {

                //Pull slack out of line
                drawBack.setTargetPosition(targetDraw);

                //When done winding, pull pin
                if (!drawBack.isBusy()) {
                        bowstate = BowStates.UNLOCK;
                        startTime3 = getRuntime();
                }
            } else if (bowstate == BowStates.UNLOCK) {
                //Pull pin
                lockPin.setPosition(pinOutVal);

                //When pin pulled, unwind bow
                drawBack.setTargetPosition(0);
                //When unwound, drop pin
                if (!drawBack.isBusy()) {
                    if (startTime3 + 0.2 <= getRuntime()) {
                        lockPin.setPosition(0.0);
                    }
                }
                if (!drawBack.isBusy() && lockPin.getPosition() == 0) {
                    bowstate = BowStates.DISARMED;
                }
            }
        }
    }
}