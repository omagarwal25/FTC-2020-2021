import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MainShooter2", group = "")
public class MainShooter2 extends LinearOpMode {

  private DcMotor leftDrive;
  private DcMotor rightDrive;
  private DcMotor strafeDrive;
  private DcMotor shooter;
  private double forwardBackwardPower;
  private double strafePower;
  private double forwardPowerMod;
  private double strafePowerMod;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    leftDrive = hardwareMap.dcMotor.get("leftDrive");
    rightDrive = hardwareMap.dcMotor.get("rightDrive");
    strafeDrive = hardwareMap.dcMotor.get("strafeDrive");
    shooter = hardwareMap.dcMotor.get("shooter");

    forwardPowerMod = 1;
    strafePowerMod = 1;
    forwardBackwardPower = 0*forwardPowerMod;
    strafePower = 0*strafePowerMod;

    telemetry.addData("forwardBackwardPower", forwardBackwardPower);
    telemetry.addData("strafePower", strafePower);
    telemetry.addData("fowardBackwardEncoding", leftDrive.getCurrentPosition());
    telemetry.addData("strafeEncoding", strafeDrive.getCurrentPosition());

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
    }
    while (opModeIsActive()) {
      // Put loop blocks here.

      if (gamepad1.a) {
        shooter.setPower(-0.6);
      }
      else {
        shooter.setPower(0);
      }

      forwardBackwardPower = gamepad1.left_stick_y*forwardPowerMod;
      strafePower = gamepad1.left_stick_x*strafePowerMod;

      leftDrive.setPower(forwardBackwardPower);
      rightDrive.setPower(forwardBackwardPower);

      strafeDrive.setPower(strafePower);

      telemetry.update();

    }
  }
}