package OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Components.Drive;
import Util.Vector2;

@TeleOp(name="Main", group="TeleOp")
public class Main extends LinearOpMode {
    Drive drive;
    DcMotorEx frontLeftMotor;
    DcMotorEx backLeftMotor;
    DcMotorEx frontRightMotor;
    DcMotorEx backRightMotor;
    IMU imu;

    @Override
    public void runOpMode() {
        imu = hardwareMap.get(IMU.class, "imu");
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");

        drive = new Drive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, imu);

        waitForStart();

        while (opModeIsActive()){
            if (!gamepad2.y) {
                Vector2 driveDirection = new Vector2(-gamepad1.left_stick_y, gamepad1.left_stick_x);
                float driveRotation = gamepad1.right_stick_x;
                drive.moveInDirection(driveDirection, driveRotation, 1.0f);
            }

            Vector2 preciseDirection = new Vector2(-gamepad2.left_stick_y, gamepad2.left_stick_x);
            float preciseRotation = gamepad2.right_stick_x * 0.6f;
            drive.moveInDirection(preciseDirection, preciseRotation, 0.35f);
        }
    }
}
