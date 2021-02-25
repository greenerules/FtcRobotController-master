package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum OpMode", group="Mecanum Opmode")
public class MecanumOpMode extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftBackDrive  = hardwareMap.get(DcMotor.class, "motor1");
        rightBackDrive = hardwareMap.get(DcMotor.class, "motor2");
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "motor3");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "motor4");

        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftFrontPower;
            double rightFrontPower;
            double leftBackPower;
            double rightBackPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.left_stick_x;
            leftFrontPower    = Range.clip(drive + turn, -0.75, 0.75) ;
            rightFrontPower   = Range.clip(drive - turn, -0.75, 0.75) ;
            leftBackPower    = Range.clip(drive - turn, -0.75, 0.75) ;
            rightBackPower   = Range.clip(drive + turn, -0.75, 0.75) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            leftBackDrive .setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left back (%.2f), right back (%.2f)," +
                    "left front (%.2f), right front (%.2f)", leftBackPower, rightBackPower,leftFrontPower,rightFrontPower);
            telemetry.update();
        }
    }
}
