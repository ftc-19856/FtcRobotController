package OpModes.Autonomous; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import Components.ShootingPedro;
import pedroPathing.Constants;

@Autonomous(name = "AutoBlueGoal")
public class AutoBlueGoal extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private DcMotorEx shooterMotorOne;
    private DcMotorEx shooterMotorTwo;
    private DcMotorEx beltMotor;

    private int pathState;

    private ShootingPedro shoot = new ShootingPedro(shooterMotorOne, shooterMotorTwo, beltMotor);

    private final Pose startPose = new Pose(23.8, 126, Math.toRadians(143));
    private final Pose shootPose = new Pose(28.9, 122, Math.toRadians(143));
    private final Pose leavePose = new Pose(36.2, 129.3, Math.toRadians(143));

    private PathChain scorePreload;
    private PathChain leave;

    public void buildPaths() {

        scorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        leave = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, leavePose))
                .setConstantHeadingInterpolation(leavePose.getHeading())
                .build();
    }

    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;

            case 1: // Wait for path to finish
                if (!follower.isBusy()) {

                    // START SHOOTERS
                    shooterMotorOne.setVelocity(700);
                    shooterMotorTwo.setVelocity(700);

                    if (shooterMotorOne.getVelocity() >= 680 && shooterMotorOne.getVelocity() <= 720 && shooterMotorTwo.getVelocity() >= 680 && shooterMotorTwo.getVelocity() <= 720){
                        beltMotor.setPower(.35);
                    }
                    else beltMotor.setPower(0);

                    actionTimer.resetTimer();
                    setPathState(2);
                }
                break;

            case 2: // Wait 20 seconds
                if (actionTimer.getElapsedTimeSeconds() >= 20) {
                    follower.followPath(leave);
                    setPathState(3);
                }
                break;

            case 3: // Finish second path
                if (!follower.isBusy()) {
                    setPathState(-1); // done
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
        telemetry.addData("Path State", pathState);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading", follower.getPose().getHeading());
        telemetry.update();

    }

    @Override
    public void init() {
        actionTimer = new Timer();
        pathTimer = new Timer();
        opmodeTimer = new Timer();

        shooterMotorOne = hardwareMap.get(DcMotorEx.class, "shooterMotorOne");
        shooterMotorTwo = hardwareMap.get(DcMotorEx.class, "shooterMotorTwo");
        beltMotor = hardwareMap.get(DcMotorEx.class, "beltMotor");

        shooterMotorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        shooterMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        buildPaths();
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
}


