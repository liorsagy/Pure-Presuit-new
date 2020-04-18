package treamcode;

import RobotUtilities.MovementVars;
import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Range;
import org.opencv.core.Point;

import java.util.ArrayList;

import static com.company.Robot.*;
import static java.lang.Math.cos;

public class RobotMonement {

    public static void followCurve(ArrayList<CurvePoint> allPoints, double followAngle){
        for (int i =0; i < allPoints.size() - 1; i++){
            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y), new FloatPoint(allPoints.get(i + 1).x,allPoints.get(i + 1).y));
        }
        CurvePoint followMe = getFollowPointPath(allPoints , new Point(worldXPosition, worldYPosition));

        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x, followMe.y));

        goToPosition(followMe.x,followMe.y,followMe.moveSpeed, followAngle,followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(ArrayList< CurvePoint> pathPoints, Point robotlocation){

        CurvePoint followMe = new CurvePoint(pathPoints.get(0));

        for(int i = 0 ; i < pathPoints.size()-1 ; i ++ ) {
            double followRadius = pathPoints.get(i).followDistance;
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get( i +1 );

            ArrayList<Point> intrectionx  = MathFunction.lineCircleintersaction(robotlocation , followRadius , startLine.toPoint(), endLine.toPoint());
            double closetAngle = 100000000;
            for (Point thisintractiox : intrectionx){
                double angle = Math.atan2(thisintractiox.y - worldYPosition , thisintractiox.x - worldXPosition );
                double deltaAngle = Math.abs(MathFunction.AngelWarp(angle - worldAngle_rad));
                if ( deltaAngle < closetAngle){
                    closetAngle = deltaAngle;
                    followMe.setPoint(thisintractiox);
                }
            }

        }
        return followMe;
    }






    public static void  goToPosition(double x, double y, double movementSpeed, double preferredAngle, double turnSpeed){

        double distanceToTarget = Math.hypot(x- worldXPosition, y- worldYPosition);
        double absoluteAngleToTarget = Math.atan2(y- worldYPosition, x- worldXPosition);

        double relativeAngleToPoint = MathFunction.AngelWarp(absoluteAngleToTarget - ( worldAngle_rad- Math.toRadians(90)));
        double relativeXtoPoint = cos(relativeAngleToPoint) * distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngleToPoint) * distanceToTarget;

        double movementXPower = relativeXtoPoint / (Math.abs(relativeXtoPoint) + Math.abs(relativeYtoPoint));
        double movementYPower = relativeYtoPoint / (Math.abs(relativeXtoPoint) + Math.abs(relativeYtoPoint));

        MovementVars.movement_x = movementXPower  * movementSpeed;
        MovementVars.movement_y = movementYPower  * movementSpeed;

         double relativeTurnAngle = relativeAngleToPoint - Math.toRadians(180) + preferredAngle;

         MovementVars.movement_turn = Range.clip(relativeTurnAngle / Math.toRadians(30),-1,1) * turnSpeed;

         if (distanceToTarget  < 10){
             MovementVars.movement_turn = 0;
            // MovementVars.movement_x = MovementVars.movement_x * 0.6;
             //MovementVars.movement_y = MovementVars.movement_y * 0.6;
         }
         if (distanceToTarget < 10){
            // MovementVars.movement_y = 0;
            // MovementVars.movement_x = 0;
         }



    }




}
