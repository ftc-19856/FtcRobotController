package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


import Components.ButtonHandler;
import Components.Drive;
import Util.Vector2;

@TeleOp(name="Main", group="TeleOp")
public class Main extends OpMode {
    Drive drive;
    DcMotorEx frontLeftMotor;
    DcMotorEx backLeftMotor;
    DcMotorEx frontRightMotor;
    DcMotorEx backRightMotor;
    IMU imu;

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");

        drive = new Drive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, imu);

        backRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ButtonHandler turnOffGamepadOne = new ButtonHandler(false);


        telemetry.addData("Status:", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
            telemetry.addData("Status", "Running");
            telemetry.update();

            if (!gamepad2.y) {
                telemetry.addData("Main drive enabled", (!gamepad2.y));
                telemetry.update();
                Vector2 driveDirection = new Vector2(-gamepad1.left_stick_y, gamepad1.left_stick_x);
                float driveRotation = gamepad1.right_stick_x;
                drive.moveInDirection(driveDirection, driveRotation, 1.0f);
            }

            Vector2 preciseDirection = new Vector2(-gamepad2.left_stick_y, gamepad2.left_stick_x);
            float preciseRotation = gamepad2.right_stick_x * 0.6f;
            drive.moveInDirection(preciseDirection, preciseRotation, 1.0f);

    }
}
