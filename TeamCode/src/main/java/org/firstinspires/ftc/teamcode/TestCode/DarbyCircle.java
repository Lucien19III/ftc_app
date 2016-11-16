package org.firstinspires.ftc.teamcode.TestCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by wdarby on 10/27/2016.
 */
@Autonomous
public class DarbyCircle extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;

  @Override
  public void init(){
      leftMotor = hardwareMap.dcMotor.get("motor_left");
      rightMotor = hardwareMap.dcMotor.get("motor_right");

      rightMotor.setDirection(DcMotor.Direction.REVERSE);

  }
    @Override
    public void loop(){
        leftMotor.setPower(0.75);
        rightMotor.setPower(0.15);
        
    }
}
