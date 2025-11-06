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


    public double getServoPosition(){return servoPosition;}



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
