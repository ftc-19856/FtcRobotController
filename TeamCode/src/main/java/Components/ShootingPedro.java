package Components;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ShootingPedro implements Runnable {

    private DcMotor shooterMotorOne;
    private DcMotor shooterMotorTwo;
    private DcMotor beltMotor;

    public ShootingPedro(DcMotor shooterMotorOne, DcMotor shooterMotorTwo, DcMotor beltMotor){
        this.shooterMotorOne = shooterMotorOne;
        this.shooterMotorTwo = shooterMotorTwo;
        this.beltMotor = beltMotor;

    }

    @Override
    public void run() {
        shooterMotorOne.setPower(1);
        shooterMotorTwo.setPower(1);
        beltMotor.setPower(1);
    }
}
