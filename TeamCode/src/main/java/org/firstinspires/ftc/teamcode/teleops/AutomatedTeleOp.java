package org.firstinspires.ftc.teamcode.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.Slide;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDriveCancelable;

import java.util.Vector;

/**
 * This opmode demonstrates how one can augment driver control by following Road Runner arbitrary
 * Road Runner trajectories at any time during teleop. This really isn't recommended at all. This is
 * not what Trajectories are meant for. A path follower is more suited for this scenario. This
 * sample primarily serves as a demo showcasing Road Runner's capabilities.
 * <p>
 * This bot starts in driver controlled mode by default. The player is able to drive the bot around
 * like any teleop opmode. However, if one of the select buttons are pressed, the bot will switch
 * to automatic control and run to specified location on its own.
 * <p>
 * If A is pressed, the bot will generate a splineTo() trajectory on the fly and follow it to
 * targetA (x: 45, y: 45, heading: 90deg).
 * <p>
 * If B is pressed, the bot will generate a lineTo() trajectory on the fly and follow it to
 * targetB (x: -15, y: 25, heading: whatever the heading is when you press B).
 * <p>
 * If Y is pressed, the bot will turn to face 45 degrees, no matter its position on the field.
 * <p>
 * Pressing X will cancel trajectory following and switch control to the driver. The bot will also
 * cede control to the driver once trajectory following is done.
 * <p>
 * The following may be a little off with this method as the trajectory follower and turn
 * function assume the bot starts at rest.
 * <p>
 * This sample utilizes the SampleMecanumDriveCancelable.java and TrajectorySequenceRunnerCancelable.java
 * classes. Please ensure that these files are copied into your own project.
 */
@TeleOp(name = "AutoTele", group = "advanced")
public class AutomatedTeleOp extends LinearOpMode {
    // Define 2 states, drive control or automatic control
    enum Mode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    Mode currentMode = Mode.DRIVER_CONTROL;

    // The coordinates we want the bot to automatically go to when we press the A button
    Vector2d targetAVector = new Vector2d(45, 45);
    // The heading we want the bot to end on for targetA
    double targetAHeading = Math.toRadians(90);

    // The location we want the bot to automatically go to when we press the B button
    Vector2d targetBVector = new Vector2d(-15, 25);

    // The angle we want to align to when we press Y
    double targetAngle = Math.toRadians(45);

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize custom cancelable SampleMecanumDrive class
        // Ensure that the contents are copied over from https://github.com/NoahBres/road-runner-quickstart/blob/advanced-examples/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/drive/advanced/SampleMecanumDriveCancelable.java
        // and https://github.com/NoahBres/road-runner-quickstart/blob/advanced-examples/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/drive/advanced/TrajectorySequenceRunnerCancelable.java

        SampleMecanumDriveCancelable drive = new SampleMecanumDriveCancelable(hardwareMap);
        boolean launch = false;
        ElapsedTime timer = new ElapsedTime();

        Slide slide = new Slide(hardwareMap);
        double speed = 0.5;


        // We want to turn off velocity control for teleop
        // Velocity control per wheel is not necessary outside of motion profiled auto
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Retrieve our pose from the PoseStorage.currentPose static field
        // See AutoTransferPose.java for further details
        drive.setPoseEstimate(new Pose2d());

        slide.launch(false);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            // Update the drive class
            drive.update();

            // Read pose
            Pose2d poseEstimate = drive.getPoseEstimate();

            // Print pose to telemetry
            telemetry.addData("mode", currentMode);
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();

            // We follow different logic based on whether we are in manual driver control or switch
            // control to the automatic mode
            switch (currentMode) {
                case DRIVER_CONTROL:
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    -gamepad2.left_stick_y * speed,
                                    -gamepad2.left_stick_x * speed,
                                    -gamepad2.right_stick_x * speed
                            )
                    );

//shawty            drive.update();

                    //speed = Math.max(0.5, Math.min(gamepad1.right_trigger + 0.5, 0.825));
                    speed = gamepad1.right_trigger*0.333+0.5;

                    if(gamepad1.right_trigger >= 0.5) slide.setArmPos(0.7875);
                    if(gamepad1.right_stick_button) slide.setArmPos(0.675);
                    if(gamepad1.left_stick_button) slide.setArmPos(0.7765);
                    if(gamepad1.right_bumper) slide.middleClaw();
                    if(gamepad1.left_bumper) slide.openClaw();
                    slide.setSlide(gamepad1.right_stick_y);
//penis in my mouth
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

                    if(gamepad1.dpad_left) slide.pullLeft(-1);
                    else if(gamepad1.share) slide.pullLeft(1);
                    else slide.pullLeft(0);

                    if(gamepad1.circle) slide.pullRight(-1);
                    else if(gamepad1.options) slide.pullRight(1);
                    else slide.pullRight(0);

                    if(gamepad1.touchpad)
                    {
                        slide.launch(true);
                    }


                   if (gamepad2.circle) {
                        // If the B button is pressed on gamepad1, we generate a lineTo()
                        // trajectory on the fly and follow it
                        // We switch the state to AUTOMATIC_CONTROL


                       Trajectory pickup = drive.trajectoryBuilder(new Pose2d())
                               .lineTo(new Vector2d(-10, 0))
                               .addDisplacementMarker(() -> drive.followTrajectoryAsync(
                                       drive.trajectoryBuilder(new Pose2d(-10 , 0, Math.toRadians(0)))
                                               .lineToLinearHeading(new Pose2d(-8, 18, Math.toRadians(-90.00)))
                                               .addDisplacementMarker(() -> drive.followTrajectoryAsync(
                                                       drive.trajectoryBuilder(new Pose2d(-8, 18, Math.toRadians(-90.00)))
                                                               .lineTo(new Vector2d(13, 17.35)).build())).build()
                               ))
                               .build();


                       drive.setPoseEstimate(new Pose2d());
                       slide.openClaw();
                       drive.followTrajectoryAsync(pickup);

                       currentMode = Mode.AUTOMATIC_CONTROL;
                    }
                    break;
                case AUTOMATIC_CONTROL:
                    // If x is pressed, we break out of the automatic following
                    if (gamepad1.y) {
                        drive.breakFollowing();
                        currentMode = Mode.DRIVER_CONTROL;
                    }
                    if (gamepad1.x) {
                        drive.breakFollowing();
                        currentMode = Mode.DRIVER_CONTROL;
                    }

                    // If drive finishes its task, cede control to the driver
                    if (!drive.isBusy()) {
                        currentMode = Mode.DRIVER_CONTROL;
                    }
                    break;
            }
        }
    }
}