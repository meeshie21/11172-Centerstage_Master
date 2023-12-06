package org.firstinspires.ftc.teamcode.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.config.Slide;
import org.firstinspires.ftc.teamcode.config.VisionCamera;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous (name = "poop", group = "autos")
public class RedAutoBackdropFormatted extends LinearOpMode {
    public VisionCamera camera;
    public double middlePos = 0.75;
    public double boardPos = 0.675;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        String path = "middle";
        camera = new VisionCamera(hardwareMap, telemetry);

        TrajectorySequence middle = drive.trajectorySequenceBuilder(new Pose2d(-37.97, -61.48, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(-40.07, -31, Math.toRadians(90.00)))
                .lineTo(new Vector2d(-39.55, -40.53))
                .lineToLinearHeading(new Pose2d(6, -43.32, Math.toRadians(0.00)))
                .build();

        TrajectorySequence left = drive.trajectorySequenceBuilder(new Pose2d(-37.97, -61.48, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(-37.97, -48.41, Math.toRadians(180-78.019108272)))
                .lineToLinearHeading(new Pose2d(-43.62, -37.6256, Math.toRadians(180-52.790445864)))
                .lineToLinearHeading(new Pose2d(-37.97, -48.41, Math.toRadians(180-78.019108272)))
                .build();

        TrajectorySequence left2 = drive.trajectorySequenceBuilder(left.end())
                .lineToLinearHeading(new Pose2d(6.6, -35.5, Math.toRadians(0.00)))
                .build();

        TrajectorySequence right = drive.trajectorySequenceBuilder(new Pose2d(-37.97, -61.48, Math.toRadians(90.00)))
                .lineToLinearHeading(new Pose2d(-19.14, -36.78, Math.toRadians(90.00)))
                .lineTo(new Vector2d(-19.14, -51.53))
                .lineToLinearHeading(new Pose2d(9, -44.75, Math.toRadians(0.00)))
                .build();

        TrajectorySequence park = drive.trajectorySequenceBuilder(new Pose2d(9.17, -36.39, Math.toRadians(0.00)))
                .lineTo(new Vector2d(-5.28, -36.05))
                .lineTo(new Vector2d(5, -60.62))
                .build();

        while (!isStarted()) {
            camera.telemetryTfod();
            slide.launch(false);
            slide.middleClaw();
            path = camera.getSide();
        }

        waitForStart();
        sleep(500);
        slide.bottomAuto();
        drive.setPoseEstimate(middle.start());

        switch(path) {
            case "left":
                drive.followTrajectorySequence(left);
                drive.followTrajectorySequence(left2);
                sleep(1000);
                slide.setArmPos(middlePos);
                sleep(1000);
                slide.openClaw();
                sleep(2000);
                drive.setPoseEstimate(park.start());
                slide.setArmPos(boardPos);
                drive.followTrajectorySequence(park);
                break;
            case "right":
                drive.followTrajectorySequence(right);
                sleep(1000);
                slide.setArmPos(middlePos);
                sleep(2000);
                slide.openClaw();
                sleep(2000);
                drive.setPoseEstimate(park.start());
                slide.setArmPos(boardPos);
                drive.followTrajectorySequence(park);
                break;
            case "middle":
                drive.followTrajectorySequence(middle);
                sleep(1000);
                slide.setArmPos(middlePos);
                sleep(2000);
                slide.openClaw();
                sleep(1000);
                drive.setPoseEstimate(park.start());
                slide.setArmPos(boardPos);
                drive.followTrajectorySequence(park);
                break;
        }
    }
}