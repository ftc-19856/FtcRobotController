package Components;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ShootingPedro implements Runnable {

    private DcMotor shooterMotor;

    public ShootingPedro(DcMotor shooterMotor){
        this.shooterMotor = shooterMotor;
    }

    @Override
    public void run() {
        shooterMotor.setPower(1);
    }
}
