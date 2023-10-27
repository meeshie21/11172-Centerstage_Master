package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {
    public DcMotor slide, launcher, tape, pullRight, pullLeft;
    public Servo arm, claw, right, left;

    public Slide(HardwareMap map) {
        slide = map.dcMotor.get("slide");
        //launcher = map.dcMotor.get("launcher");
       // tape = map.dcMotor.get("tape");
        pullRight = map.dcMotor.get("pullRight");
        pullLeft = map.dcMotor.get("pullLeft");

        claw = map.servo.get("claw");
        arm = map.servo.get("arm");
        right = map.servo.get("right");
        left = map.servo.get("left");

    }

    public void moveSlide() {

    }

    public void setSlide(double power) {
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slide.setPower(power);
    }

    public void pull(double power) {
        pullRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pullRight.setPower(power);
        pullLeft.setPower(-1 * power);
    }

    public void pullLeft(double power)
    {
        pullRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullLeft.setPower(power * -1);
    }
    public void pullRight(double power)
    {
        pullRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullRight.setPower(power);
    }


    public void launch(boolean launch) {
        launcher.setPower(launch ? 1 : 0);
    }

    public void slideByTime(double milliseconds, double power)
    {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while(timer.milliseconds() <= milliseconds)
        {
            slide.setPower(power);
        }
    }

    public void setArmPos(double position)
    {
        arm.setPosition(position);
    }

    public void middle()
    {
        right.setPosition(0.5);
        left.setPosition(0.5);
    }

    public void top()
    {
        right.setPosition(0.9);
        left.setPosition(0.1);
    }

    public void bottom()
    {
        right.setPosition(0);
        left.setPosition(1);
    }

    public void openClaw() {claw.setPosition(1);}
    public void middleClaw() {claw.setPosition(0.2);}
    public void closeClaw() {claw.setPosition(0.5);}

    }

