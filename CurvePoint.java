package treamcode;

import org.opencv.core.Point;

public class CurvePoint {
    public double x;
    public double y;
    public double heading;
    public double moveSpeed;
    public double turnSpeed;
    public double followDistance;
    public double pointLenght;


    public CurvePoint( double y, double  x, double moveSpeed, double turnSpeed,
                       double followDistance){
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
    }

    public CurvePoint ( double y, double  x, double heading, double moveSpeed, double turnSpeed,
                        double followDistance){
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
    }

    public CurvePoint( CurvePoint thispoint){
        x = thispoint.x;
        y = thispoint.y;
        moveSpeed = thispoint.moveSpeed;
        turnSpeed = thispoint.turnSpeed;
        followDistance = thispoint.followDistance;
        pointLenght = thispoint.pointLenght;
    }

    public Point toPoint(){
        return new Point(x,y);
    }

    public void setPoint(Point point){
        x = point.x;
        y = point.y;
    }
}
