package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Shooter", group="Shooter")
public class Shooter  extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor feeder = null;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "motor1");
        rightDrive = hardwareMap.get(DcMotor.class, "motor2");
        rightDrive = hardwareMap.get(DcMotor.class, "motor3");


        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        feeder.setDirection(DcMotorSimple.Direction.FORWARD);


        waitForStart();
        runtime.reset();

        double power = 0;
        double fPower = 0;
        int lastIt = 0;
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry

            double stickY = gamepad1.left_stick_y;
            boolean b = gamepad1.b;
            if(stickY == 1 && lastIt == 1) {
                if (stickY > 0) {
                    power += 0.01;
                } else {
                    power = 0;

                }
            }

            if(b) { fPower = 0.15; }
            else  { fPower = 0.00; }


            double powerf    = Range.clip(power, -0.75, 0.75) ;
            double fPowerf    = Range.clip(fPower, -0.75, 0.75) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            leftDrive .setPower(powerf);
            rightDrive.setPower(powerf);
            feeder.setPower(fPowerf);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Power (%.2f), Feeder Power (%.2f)", powerf, fPowerf);
            telemetry.update();
        }
    }
}
