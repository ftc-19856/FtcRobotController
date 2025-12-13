package OpModes.Autonomous; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Components.ShootingPedro;
import pedroPathing.Constants;

@Autonomous(name = "PedroAutoTest")
public class PedroAutoTest extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private DcMotor shooterMotorOne;
    private DcMotor shooterMotorTwo;
    private DcMotor beltMotor;

    private int pathState;

    private ShootingPedro shoot = new ShootingPedro(shooterMotorOne, shooterMotorTwo, beltMotor);

    private final Pose startPose = new Pose(33.3, 134.5, Math.toRadians(90));
    private final Pose shootPose = new Pose(23, 124, Math.toRadians(143));

    private PathChain scorePreload;

    public void buildPaths() {

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .addParametricCallback(1, shoot)
                .build();
    }

    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;

            case 1:
                if(!follower.isBusy()){
                    setPathState(-1);
                }
                break;
        }
    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop(){
        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        shooterMotorOne = hardwareMap.get(DcMotor.class, "shooterMotorOne");
        shooterMotorTwo = hardwareMap.get(DcMotor.class, "shooterMotorTwo");
        beltMotor = hardwareMap.get(DcMotor.class, "beltMotor");

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
}



