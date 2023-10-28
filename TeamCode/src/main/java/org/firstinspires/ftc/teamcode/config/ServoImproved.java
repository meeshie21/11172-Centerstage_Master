package org.firstinspires.ftc.teamcode.config;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ServoImproved {
    public Telemetry tele;
    public Servo servo;
    public double currentPos;

    public ServoImproved(Servo servoHardware, Telemetry teleParam) {
        tele = teleParam;
        servo = servoHardware;
    }
    public void calibrateServo(boolean up, boolean down) {
        if (up) {
            currentPos += 0.01;
        }
        else if (down) {
            currentPos -= 0.01;
        }
        tele.addData("Current Servo Position: ", currentPos);
    }
}
