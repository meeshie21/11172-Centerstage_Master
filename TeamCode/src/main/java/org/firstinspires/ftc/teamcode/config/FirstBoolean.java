package org.firstinspires.ftc.teamcode.config;

public class FirstBoolean {
    boolean recent = false;
    boolean returnBool = false;
    boolean y, z = false;
    public boolean betterboolean(boolean input) {
        if (!recent)  returnBool = input;
        else returnBool =  false;
        //returnBool = recent ? false : input;
        recent = input;
        return returnBool;
    }

}
