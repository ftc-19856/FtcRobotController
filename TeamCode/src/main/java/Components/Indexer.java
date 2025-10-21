package Components;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Indexer {

    Servo servo;
    float pos1 = 0.0f;
    float pos2 = 0.0f;
    float pos3 = 0.0f;
    float pos4 = 0.0f;
    float pos5 = 0.0f;
    float pos6 = 0.0f;
    private Telemetry telemetry;

    public Indexer(Servo servo){
        this.servo = servo;
    }

    public enum POS_STATE {
        POS1,
        POS2,
        POS3,
        POS4,
        POS5,
        POS6
    }

    public POS_STATE posState = POS_STATE.POS1;

    public double getServoPosition(){return servo.getPosition();}

    public void moveToPos1(){
        servo.setPosition(pos1);
        posState = POS_STATE.POS1;
        telemetry.addData("Position", posState);
        telemetry.update();
    }

    public void moveToPos2(){
        servo.setPosition(pos2);
        posState = POS_STATE.POS2;
        telemetry.addData("Position", posState);
        telemetry.update();
    }

    public void moveToPos3(){
        servo.setPosition(pos3);
        posState = POS_STATE.POS3;
        telemetry.addData("Position", posState);
        telemetry.update();
    }
    public void moveToPos4(){
        servo.setPosition(pos4);
        posState = POS_STATE.POS4;
        telemetry.addData("Position", posState);
        telemetry.update();
    }

    public void moveToPos5(){
        servo.setPosition(pos5);
        posState = POS_STATE.POS5;
        telemetry.addData("Position", posState);
        telemetry.update();
    }

    public void moveToPos6(){
        servo.setPosition(pos6);
        posState = POS_STATE.POS6;
        telemetry.addData("Position", posState);
        telemetry.update();
    }
}
