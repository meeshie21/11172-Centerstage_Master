package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Slide {
    public DcMotor slide, tape, pullRight, pullLeft, winch, lift;
    public Servo arm, claw, right, left, launcher;

    public double liftLPos, liftRPos, armPos;

    public double liftIncrement = 0.01;

    public double armIncrement = 0.05;

    public Slide(HardwareMap map) {
        slide = map.dcMotor.get("slide");
        winch = map.dcMotor.get("winch");
        lift = map.dcMotor.get("lift");
        launcher = map.servo.get("launcher");
       // tape = map.dcMotor.get("tape");
        //pullRight = map.dcMotor.get("pullRight");
        //pullLeft = map.dcMotor.get("pullLeft");

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

    public void setLift(double power) {
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setPower(power);
    }
    public void setWinch(double power) {
        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        winch.setPower(-1 * power);
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
        if(launch) launcher.setPosition(1);
        else launcher.setPosition(0);
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
        setArmPos(0.765);
        right.setPosition(0.5);
        left.setPosition(0.5);
    }

    public void calibrateLift(boolean armUp, boolean armDown, boolean liftUp, boolean liftDown) {
        armPos = arm.getPosition();
        liftLPos = left.getPosition();
        liftRPos = right.getPosition();

        if (armUp) {
            arm.setPosition(armPos + armIncrement);
        }
        if (armDown) {
            arm.setPosition(armPos - armIncrement);
        }

        if (liftUp) {
            left.setPosition(liftLPos + liftIncrement);
            right.setPosition(liftRPos - liftIncrement);
        }

        if (liftDown) {
            left.setPosition(liftLPos - liftIncrement);
            right.setPosition(liftRPos - liftIncrement);
        }
    }

    public void top()
    {
        setArmPos(0.77);
        right.setPosition(0.9);
        left.setPosition(0.1);
    }

    public void bottom()
    {
        setArmPos(0.74);
        right.setPosition(0);
        left.setPosition(1);
    }

    public void bottomAuto()
    {
        right.setPosition(0);
        left.setPosition(1);
    }

    public void openClaw() {claw.setPosition(0.65);}
    public void middleClaw() {claw.setPosition(0.375);}
    public void closeClaw() {claw.setPosition(0.5);}
    public void clawPos1() {claw.setPosition(0.65);}
    public void clawPos2() {claw.setPosition(0.35);}


}

