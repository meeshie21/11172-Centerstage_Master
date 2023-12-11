package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.BetterBoolGamepad;
import org.firstinspires.ftc.teamcode.config.CalibrateServo;
import org.firstinspires.ftc.teamcode.config.Slide;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOpMainCali", group = "drive")
public class TeleOpMainCali extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException {
        boolean launch = false;
        ElapsedTime timer = new ElapsedTime();


        Slide slide = new Slide(hardwareMap);
        CalibrateServo calArm = new CalibrateServo(slide.arm, 0.02);
        BetterBoolGamepad bGamepad1 = new BetterBoolGamepad(gamepad1);
        BetterBoolGamepad bGamepad2 = new BetterBoolGamepad(gamepad2);
        slide.arm.setPosition(0.6);
        double speed = 0.5;
        double deadzone = 0.05;
        double forward, strafe, turn;


        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();
//hottie
        while(opModeIsActive() && !isStopRequested())
        {
            forward = (Math.abs(gamepad2.left_stick_y)>deadzone) ? -gamepad2.left_stick_y : 0;
            drive.setWeightedDrivePower(
                    new Pose2d(
                            forward,
                            -gamepad2.left_stick_x,
                            -gamepad2.right_stick_x
                    )
            );

            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.addData("motor pos", slide.slide.getCurrentPosition());
            //telemetry.update();


            speed = 0.5;



            if(gamepad1.right_trigger >= 0.5) slide.setArmPos(0.8);
            if(gamepad1.left_trigger >= 0.5) slide.setArmPos(0.75);
            if(gamepad1.right_stick_button) slide.setArmPos(0.675);
            if(gamepad1.left_stick_button) slide.setArmPos(0.785);
            if(gamepad1.right_bumper) slide.middleClaw();
            if(gamepad1.left_bumper) slide.openClaw();
            calArm.calibrate(bGamepad2.dpad_up(), bGamepad2.dpad_down());
            telemetry.addData("Arm Pos", calArm.servoPos);
            telemetry.update();


            slide.setSlide(gamepad1.right_stick_y);
//penis
            if(gamepad1.dpad_down)
            {
                slide.bottom();
            }

            else if(gamepad1.dpad_up)
            {
                slide.top();
            }


            else if(gamepad1.dpad_right)
            {
                slide.middle();
            }

            if(gamepad2.square && !launch)
            {
                timer.reset();
                launch = true;
            }

            if(gamepad2.right_bumper) slide.setLift(1);
            else slide.setLift(-0.25 * gamepad2.right_trigger);
            if(gamepad2.left_bumper) slide.setWinch(-1);
            else slide.setWinch(gamepad2.left_trigger);


            if(launch && timer.seconds() > 2) launch = false;

            slide.launch(launch);
        }
    }
}
