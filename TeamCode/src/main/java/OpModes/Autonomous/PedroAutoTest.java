package OpModes.Autonomous; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.Constants;

@Autonomous(name = "PedroAutoTest")
public class PedroAutoTest extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(38.6, 33.2, Math.toRadians(0));
    private final Pose endPose = new Pose(105, 33.2, Math.toRadians(180));

    private Path initialMove;

    public void buildPaths() {

        initialMove = new Path(new BezierLine(startPose, endPose));
        initialMove.setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading());
    }

    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(initialMove);
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



