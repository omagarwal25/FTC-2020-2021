package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.concurrent.TimeUnit;


@TeleOp(name = "main", group = "")
public class main extends LinearOpMode {

  private DcMotor leftDrive;
  private DcMotor rightDrive;
  private DcMotor strafeDrive;
  private DistanceSensor frontDistance;
  private DistanceSensor backDistance;
  //private Servo clawLeft;
  //private Servo clawRight;
  private Double shootingDeg;
  private Double shooterTurnConstant;
  private Double distanceConstant;
  private CRServo shooter;
  private CRServo conveyor;
  private Double distance;
  private CRServo intake;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

  	//INIT
    leftDrive = hardwareMap.get(DcMotor.class, "leftDrive");
    rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
    strafeDrive = hardwareMap.get(DcMotor.class, "strafeDrive");
    //clawLeft = hardwareMap.get(Servo.class, "clawLeft");
    //clawRight = hardwareMap.get(Servo.class, "clawRight");
    frontDistance = hardwareMap.get(Servo.class, "frontDistance");
    backDistance = hardwareMap.get(Servo.class, "backDistance");


    shooter = hardwareMap.get(CRServo.class, "shooter");
    conveyor = hardwareMap.get(CRServo.class, "conveyor");
    intake = hardwareMap.get(CRServo.class, "intake");

    shooterTurnConstant = 0.01;
    distanceConstant = 0.10;


    waitForStart();
    if (opModeIsActive()) {
    	rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    	strafeDrive.setDirection(DcMotorSimple.Direction.REVERSE);
    	shooter.setDirection(DcMotorSimple.Direction.REVERSE);

      leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      strafeDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

      leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      strafeDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
      // Put run blocks here.
      while (opModeIsActive()) {
        if (gamepad1.right_bumper == True) {
        	if (frontDistance.getDistance(DistanceUnit.CM) < backDistance.getDistance(DistanceUnit.CM)) {
        		while (frontDistance.getDistance(DistanceUnit.CM) < backDistance.getDistance(DistanceUnit.CM)) {
        			leftDrive.setPower(0.2);
        		}
        		leftDrive.setPower(0);
        	} 
        	else if (frontDistance.getDistance(DistanceUnit.CM) > backDistance.getDistance(DistanceUnit.CM)) {
        		while (frontDistance.getDistance(DistanceUnit.CM) > backDistance.getDistance(DistanceUnit.CM)) {
        			rightDrive.setPower(0.2);
        		}
        		rightDrive.setPower(0);
        	} 
        	//USES TRIG TO CALCULATE SHOOTING ANGLE
        	shootingDeg = Math.atan(143.51/frontDistance.getDistance(DistanceUnit.CM)) / Math.PI * 180;
        	//TURNS TO ANGLE
        	rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        	rightDrive.setTargetPosition(shootingDeg*-shooterTurnConstant);
       	 	rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       	 	//CALCULATES DISTANCE THAT ROBOT IS SHOOTING
       	 	distance = Math.sqrt(Math.pow(143.51, 2) + Math.pow( 236.22 - frontDistance, 2));
       	 	//SHOOTS RINGS
       	 	shooter.setPower(distance*distanceConstant);
       	 	conveyor.setPower(1);
       	 	sleep(5000);
       	 	shooter.setPower(0);
       	 	conveyor.setPower(0);
        } 
      	// CHECKS WHAT DRIVE MODE & EXECUTES, SHOOTER FOR GOALS IS DONEZO
   	    else if (gamepad1.left_stick_y != 0) {
          if (gamepad1.right_stick_x > 0) {
            rightDrive.setPower(gamepad1.right_stick_y);
            leftDrive.setPower(gamepad1.right_stick_y * abs(1 - gamepad1.right_stick_x));
          }
          else if (gamepad1.right_stick_x > 0) {
            leftDrive.setPower(gamepad1.right_stick_y);
            rightDrive.setPower(gamepad1.right_stick_y * abs(1 - gamepad1.right_stick_x));
          }
          else {
            leftDrive.setPower(gamepad1.right_stick_y);
            rightDrive.setPower(gamepad1.right_stick_y);
          }
        }

        if (gamepad1.cross == True) {
        	conveyor.setPower(1);
        	intake.setPower(1);
        }
        else {
        	conveyor.setPower(0);
          intake.setPower(0);
          shooter.setPower(0);
        }
        
        /*if (gamepad1.triangle == True) {
        	if (clawRight.getPosition() == 0) {
        		clawRight.setPosition(1);
        		clasLeft.setPosition(1);
        	}
        	else {
        		clawRight.setPosition(0);
        		clawLeft.setPosition(0);
        	}
        }*/
      
        telemetry.addData("LEncoder", leftDrive.getCurrentPosition());
        telemetry.addData("REncoder", rightDrive.getCurrentPosition());
        telemetry.addData("Left Pow", leftDrive.getPower());
        telemetry.addData("Right Pow", rightDrive.getPower());
        
        
        
        telemetry.update();
      }
    }
  }
}
