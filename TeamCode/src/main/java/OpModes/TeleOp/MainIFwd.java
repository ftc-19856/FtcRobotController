package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;


import Components.Toggle;
import Components.Drive;
import Components.Indexer;
import Util.Vector2;

@TeleOp(name="MainIFwd", group="TeleOp")
public class MainIFwd extends OpMode {
    Drive drive;
    DcMotorEx frontLeftMotor;
    DcMotorEx backLeftMotor;
    DcMotorEx frontRightMotor;
    DcMotorEx backRightMotor;
    DcMotorEx beltMotor;
    DcMotorEx shooterMotorOne;
    DcMotorEx shooterMotorTwo;
    IMU imu;
    Toggle shooterToggle;
    Toggle beltToggle;

    private boolean lastDpad_UpState;
    private boolean lastDpad_DownState;

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        beltMotor = hardwareMap.get(DcMotorEx.class, "beltMotor");
        shooterMotorOne = hardwareMap.get(DcMotorEx.class, "shooterMotorOne");
        shooterMotorTwo = hardwareMap.get(DcMotorEx.class, "shooterMotorTwo");

        drive = new Drive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, imu);

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
        beltToggle = new Toggle(false);


        telemetry.addData("Status:", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
            telemetry.addData("Status", "Running");


                Vector2 driveDirection = new Vector2(gamepad1.left_stick_x, gamepad1.left_stick_y);
                float driveRotation = gamepad1.right_stick_x;
                drive.moveInDirection(driveDirection, driveRotation, 1.0f, telemetry);



            shooterToggle.update(gamepad1.b);
            beltToggle.update(gamepad1.y);


            telemetry.addData("Shooting toggle", shooterToggle.getState());


            if(gamepad1.y){
                beltMotor.setPower(0.35);
            }
            else if(gamepad1.a){
                shooterMotorOne.setPower(-.7);
                shooterMotorTwo.setPower(-.7);
                beltMotor.setPower(-.3);
            }
            else {
                beltMotor.setPower(0);
            }

            if (shooterToggle.getState()){
                shooterMotorOne.setPower(0.25);
                shooterMotorTwo.setPower(0.25);
            }

            if(!shooterToggle.getState()){
                shooterMotorOne.setPower(0);
                shooterMotorTwo.setPower(0);
            }





            telemetry.update();



    }
}
