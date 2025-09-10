package OpModes.Autonomous.pedroPathing;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizers;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class FConstants {

    static {

        FollowerConstants.localizers = Localizers.TWO_WHEEL;
        //FollowerConstants.mass = [MASS];

        FollowerConstants.leftFrontMotorName = "leftFront";
        FollowerConstants.leftRearMotorName = "leftRear";
        FollowerConstants.rightFrontMotorName = "rightFront";
        FollowerConstants.rightRearMotorName = "rightRear";

        FollowerConstants.leftFrontMotorDirection = DcMotorEx.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorEx.Direction.REVERSE;
        FollowerConstants.rightFrontMotorDirection = DcMotorEx.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorEx.Direction.FORWARD;
    }
}
