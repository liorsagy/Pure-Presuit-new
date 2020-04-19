package treamcode;

import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Range;
import org.opencv.core.Point;
import java.util.ArrayList;
import static com.company.Robot.*;
import static java.lang.Math.*;
import static treamcode.MathFunction.*;
import static RobotUtilities.MovementVars.*;

public class RobotMovement {

    public static void followCurve(ArrayList<CurvePoint> allPoints, double preferdAngel){
        for (int i =0; i < allPoints.size() - 1; i++){
            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y), new FloatPoint(allPoints.get(i + 1).x,allPoints.get(i + 1).y));
        }
        CurvePoint followMe = getFollowPointPath(allPoints , new Point(worldXPosition, worldYPosition));

        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x, followMe.y));

        goToPosition(followMe.x,followMe.y,followMe.moveSpeed, preferdAngel,followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(ArrayList< CurvePoint> pathPoints, Point robotlocation){

        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0 ; i < pathPoints.size()-1 ; i ++ ) {
            double followRadius = pathPoints.get(i).followDistance;
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get( i +1 );

            ArrayList<Point> Intersection  = lineCircleIntersection(robotlocation , followRadius , startLine.toPoint(), endLine.toPoint());
            double closetAngle = 100000000;
            for (Point thisintractiox : Intersection){
                double angle = atan2(thisintractiox.y - worldYPosition , thisintractiox.x - worldXPosition );
                double deltaAngle = abs(AngelWrap(angle - worldAngle_rad));
                if ( deltaAngle < closetAngle){
                    closetAngle = deltaAngle;
                    followMe.setPoint(thisintractiox);
                }
            }

        }
        return followMe;
    }

    public static void goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){
        double distanceToTarget = Math.hypot(x -worldXPosition, y - worldYPosition);

        double absoluteAngleToTarget = Math.atan2(y-worldYPosition,x-worldXPosition);

        double relativeAngleToPoint = AngelWrap(absoluteAngleToTarget - (worldAngle_rad - Math.toRadians(90)));

        double relativeXToPoint = Math.cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYToPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        double movementYPower = relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        movement_x = movementXPower * movementSpeed;
        movement_y = movementYPower * movementSpeed;

        double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;

        movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30),-1,1) * turnSpeed;

        if(distanceToTarget < 10){
            movement_turn = 0;
        }

    }
}
