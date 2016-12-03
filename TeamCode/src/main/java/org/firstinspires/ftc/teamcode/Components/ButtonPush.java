package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.TelemetryMessage;

/**
 * Created by CJS on 12/2/16.
 */

@Autonomous
public class ButtonPush extends OpMode {

    //Enumerations
    enum Sides {LEFT, RIGHT}
    Sides side = Sides.LEFT;

    enum FlowItems {FIND_LINE, LINE_FOLLOW, COLOR_SWITCH, PUSH_BUTTON, COMPLETE}
    FlowItems flow = FlowItems.FIND_LINE;


    //Hardware
    private Servo forcePlateSwing;
    private ColorSensor colorSensor;



    //Pulled Values
    double redVal = colorSensor.red();
    double blueVal = colorSensor.blue();


    //Other Declared Variables
    String team = "Unselected";



    //Initialization
    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("sensor_color");


        forcePlateSwing = hardwareMap.servo.get("servo_forcePlate");
        forcePlateSwing.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Team Color", team);
        telemetry.addData("Init", "Complete.  Waiting for Team Color input.");
        telemetry.update();


        while (team == "Unselected") {
            if (gamepad1.x) {
                team = "Blue";
                break;
            } else if (gamepad1.b) {
                team = "Red";
                break;
            }
        }

        telemetry.addData("Team Color", team);
        telemetry.update();
    }






    //Main Loop
    @Override
    public void loop() {

        switch (flow) {

            case FIND_LINE:
                //Do Some Stuff...  Then:
                telemetry.addData("FIND_LINE", "Done");
                telemetry.update();
                flow = flow.LINE_FOLLOW;
                break;

            case LINE_FOLLOW:
                //Do Some More Stuff... Then:
                telemetry.addData("LINE_FOLLOW", "Done");
                telemetry.update();
                flow = flow.COLOR_SWITCH;
                break;

            case COLOR_SWITCH:

                //Recenter Servo
                forcePlateSwing.setPosition(0.5);
                if (team == "Red" && redVal <= 1 && blueVal >= 4) {
                    forcePlateSwing.setPosition(0.8);
                } else if (team == "Blue" && redVal >= 4 && blueVal <= 1) {
                    forcePlateSwing.setPosition(0.8);
                }

                flow = flow.PUSH_BUTTON;
                break;

            case PUSH_BUTTON:
                //Do Even More Stuff... Then:
                telemetry.addData("PUSH_BUTTON", "Done");
                telemetry.update();
                forcePlateSwing.setPosition(0.5);
                flow = flow.COMPLETE;
                break;

            case COMPLETE:
                //Move On From The Beacon
                telemetry.addData("COMPLETE", "Done");
                telemetry.update();
                break;
        }
    }

}
