package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.Servo;

public class CalibrateServo {
    public Servo servo;
    public double servoPos, increment;
    public CalibrateServo(Servo servo, double increment) {
        this.servo = servo;
        this.increment = increment;
    }

    public void calibrate(boolean up, boolean down) {
        servoPos = servo.getPosition();
        if (up) servo.setPosition(servoPos + increment);
        if (down) servo.setPosition(servoPos - increment);
    }
}
