package treamcode;

import java.util.ArrayList;
import static treamcode.RobotMovement.*;

public class MyOpMode extends OpMode{
    @Override
    public void init(){}

    @Override
    public void loop(){
        ArrayList<CurvePoint> allpoints = new ArrayList<>();
        allpoints.add(new CurvePoint(180,180,0.3,0.6,30));
        allpoints.add(new CurvePoint(180,220,0.3,0.6,30));
        allpoints.add(new CurvePoint(100,310,0.3,0.6,30));
        allpoints.add(new CurvePoint(20 ,320,0.3,0.6,30));
        //allpoints.add(new CurvePoint(100,0  ,0.3,0.7,50,Math.toRadians(50),1.0));
        followCurve(allpoints, Math.toRadians(45));

        //goToPosition(228,124,1,Math.toRadians(90),0.3);
    }
}
