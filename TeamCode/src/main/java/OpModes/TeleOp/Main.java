package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


import Components.Toggle;
import Components.Drive;
import Components.Indexer;
import Util.Vector2;

@TeleOp(name="Main", group="TeleOp")
public class Main extends OpMode {
    Drive drive;
    Indexer indexer;
    DcMotorEx frontLeftMotor;
    DcMotorEx backLeftMotor;
    DcMotorEx frontRightMotor;
    DcMotorEx backRightMotor;
    DcMotorEx intakeMotor;
    DcMotorEx shooterMotor;
    Servo indexerRot;
    Servo kicker;
    IMU imu;
    //Toggle intakeToggle;
    Toggle shooterToggle;

    private boolean lastDpad_UpState;
    private boolean lastDpad_DownState;

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        indexerRot = hardwareMap.get(Servo.class, "indexerRot");
        kicker = hardwareMap.get(Servo.class, "kicker");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");

        drive = new Drive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, imu);
        indexer = new Indexer(indexerRot);

        backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //intakeToggle = new Toggle(false);
        shooterToggle = new Toggle(false);

        indexer.init();


        telemetry.addData("Status:", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
            telemetry.addData("Status", "Running");

            telemetry.addData("Indexer position", indexer.getServoPosition());

                Vector2 driveDirection = new Vector2(-gamepad2.left_stick_y, gamepad2.left_stick_x);
                float driveRotation = gamepad2.right_stick_x;
                drive.moveInDirection(driveDirection, driveRotation, 1.0f, telemetry);


            if (indexer.getServoPosition() == 0 || indexer.getServoPosition() == 2 || indexer.getServoPosition() == 4){
                intakeMotor.setPower(-1);
            }
            else {
                intakeMotor.setPower(0);
            }

            if (gamepad2.right_bumper){
                kicker.setPosition(1);
            }
            else {
                kicker.setPosition(0);
            }

            shooterToggle.update(gamepad2.b);


            telemetry.addData("Shooter toggle", shooterToggle.getState());

            if(gamepad2.dpad_up && !lastDpad_UpState){
                indexer.moveUp();
            }

            lastDpad_UpState = gamepad2.dpad_up;

            telemetry.addData("lastDpadUp", lastDpad_UpState);


            if(gamepad2.dpad_down && !lastDpad_DownState){
                indexer.moveDown();
            }

            lastDpad_DownState = gamepad2.dpad_down;
            telemetry.addData("lastDpadDown", lastDpad_DownState);

            if (indexer.getServoPosition() == 1 || indexer.getServoPosition() == 3 || indexer.getServoPosition() == 5){

                if (shooterToggle.getState()){
                    shooterMotor.setPower(1);
                }
            }

            if(!shooterToggle.getState()){
                shooterMotor.setPower(0);
            }

            telemetry.addData("Indexer servo pos", indexer.getServoPosition());

            telemetry.update();



    }
}
