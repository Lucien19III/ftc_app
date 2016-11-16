package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by alucien on 10/25/2016.
 */
@Autonomous(name="stay in circle", group="lucien")
public class StayInsideCircle extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    OpticalDistanceSensor opticalDistanceSensor;
    ElapsedTime timer;

    double lightValue = 0.1;
    double darkValue = 0.3;
    double threshold = (darkValue+lightValue) / 2;

    enum State {Drive, Backup, Turn}
    State state;

    double BACKUP_TIME = 0.8;
    double TURN_TIME = 0.7;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("motor_left");
        rightMotor = hardwareMap.dcMotor.get("motor_right");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        opticalDistanceSensor = hardwareMap.opticalDistanceSensor.get("sensor_EOPD");

        state = State.Drive;

        timer = new ElapsedTime();
    }

    @Override
    public void loop() {

        double reflectance = opticalDistanceSensor.getLightDetected();

        switch(state) {
            case Drive:
          if (reflectance < threshold) {
              leftMotor.setPower(0);
              rightMotor.setPower(0);
              state = State.Backup;

              timer.reset();
          } else {
              leftMotor.setPower(0.15);
              rightMotor.setPower(0.15);
          }
       break;
            case Turn:
                leftMotor.setPower(-0.25);
                rightMotor.setPower(-0.25);

                if(timer.time() >= TURN_TIME) {
                    state =  State.Drive;
                }
                break;
        }
            telemetry.addData("Current State", state.name());
            telemetry.addData("Reflectance Value", reflectance);


    }
}
