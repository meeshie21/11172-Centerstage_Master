package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Slide;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name = "TeleOpMain", group = "drive")
public class SlideTest2 extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException {
        boolean launch = false;
        ElapsedTime timer = new ElapsedTime();

        Slide slide = new Slide(hardwareMap);
        double speed = 0.5;

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
//hottie
        while(opModeIsActive() && !isStopRequested())
        {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad2.left_stick_y * speed,
                            -gamepad2.left_stick_x * speed,
                            -gamepad2.right_stick_x * speed
                    )
            );

            if(gamepad2.circle)
            {
                 Trajectory pickup = drive.trajectoryBuilder(new Pose2d())
                    .lineTo(new Vector2d(0, -6))
                    .lineTo(new Vector2d(2, -6))
                    .build();


            }

//shawty            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

            if(gamepad2.x)
            {
                if(speed == 0.5) speed = 0.65;
                else speed = 0.5;
            }

            if(gamepad1.right_trigger >= 0.5) slide.setArmPos(0.8);
            if(gamepad1.left_trigger >= 0.5) slide.setArmPos(0.75);
            if(gamepad1.right_stick_button) slide.setArmPos(0.675);
            if(gamepad1.left_stick_button) slide.setArmPos(0.78);
            if(gamepad1.right_bumper) slide.middleClaw();
            if(gamepad1.left_bumper) slide.openClaw();
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

            if(gamepad1.touchpad && !launch)
            {
                timer.reset();
                launch = true;
            }

            if(gamepad2.right_bumper) slide.setLift(1);
            else slide.setLift(-0.25 * gamepad2.right_trigger);
            if(gamepad2.left_bumper) slide.setWinch(-1);
            else slide.setWinch(gamepad2.left_trigger);


            if(launch && timer.seconds() > 2) launch = false;

            //slide.launch(launch);
        }
    }
}
