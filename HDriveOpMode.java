package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "BasicOpMode (Blocks to Java)", group = "")
public class BasicOpMode extends LinearOpMode {

  private DcMotor leftDrive;
  private DcMotor rightDrive;
  private DcMotor strafeDrive;
  private DcMotor shooter;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    strafeDrive = hardwareMap.get(DcMotor.class, "strafeDrive");
    shooter = hardwareMap.dcMotor.get("shooter");

    // Reverse one of the drive motors.
    waitForStart();
    if (opModeIsActive()) {
      leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      strafeDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        // Use left stick to drive and right stick to turn
        // The Y axis of a joystick ranges from -1 in its topmost position
        // to +1 in its bottommost position. We negate this value so that
        // the topmost position corresponds to maximum forward power.
        leftDrive.setPower(-gamepad1.left_trigger * gamepad1.left_stick_y);
        rightDrive.setPower(gamepad1.left_trigger * gamepad1.left_stick_y);
        strafeDrive.setPower(-gamepad1.left_trigger * gamepad1.left_stick_x);
        telemetry.addData("LEncoder", leftDrive.getCurrentPosition());
        telemetry.addData("REncoder", rightDrive.getCurrentPosition());
        telemetry.addData("Left Pow", leftDrive.getPower());
        telemetry.addData("Right Pow", rightDrive.getPower());
        
         if (gamepad1.a) {
          shooter.setPower(-0.6);
        }
        else {
          shooter.setPower(0);
        }
        
        
        
        telemetry.update();
      }
    }
  }
}
