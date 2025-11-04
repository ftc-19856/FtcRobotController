package Components;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Indexer {

    Servo rotationServo;
    float pos1 = 0.0f;
    float pos2 = 1f/30;
    float pos3 = 2f/30;
    float pos4 = 3f/30;
    float pos5 = 4f/30;
    float pos6 = 5f/30;
    private Telemetry telemetry;
    private double servoPosition = 0;
    private float servoStartConstant = 12.0f;

    public Indexer(Servo rotation){
        this.rotationServo = rotation;
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

    public double getServoPosition(){return servoPosition;}


 /*   public void moveToPos1(){
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
    } */


    public void moveUp(){
        servoPosition += 1;
        servoPosition %= 6;
        float offset = 0;
        if(servoPosition == 1 || servoPosition == 3 || servoPosition == 5){
            offset -= 0.3f;
        }
        rotationServo.setPosition((servoPosition+servoStartConstant+offset)/30);
    }

    public void moveDown(){
        servoPosition += 5;
        servoPosition %= 6;
        float offset = 0;
        if(servoPosition == 1 || servoPosition == 3 || servoPosition == 5){
            offset -= 0.3f;
        }
        rotationServo.setPosition((servoPosition+servoStartConstant+offset)/30);

    }

    public void init(){rotationServo.setPosition((servoPosition+servoStartConstant)/30);}


}
