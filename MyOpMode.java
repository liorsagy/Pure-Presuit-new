package treamcode;

import java.util.ArrayList;
import static treamcode.RobotMovement.*;

public class MyOpMode extends OpMode{
    @Override
    public void init(){}

    @Override
    public void loop(){
         ArrayList<CurvePoint> allpoints = new ArrayList<>();
         allpoints.add(new CurvePoint(180,180,0.3,0.5,10));
         allpoints.add(new CurvePoint(220,180,0.3,0.5,40));
         //allpoints.add(new CurvePoint(280,100 ,0.3,0.5,5 ));
         allpoints.add(new CurvePoint(310,100 ,0.3,0.5,30 ));
         allpoints.add(new CurvePoint(280,20 ,0.3,0.7,30));
        // allpoints.add(new CurvePoint(100,0  ,0.3,0.7,50,Math.toRadians(50),1.0));
        followCurve(allpoints,Math.toRadians(90));

        //goToPosition(228,124,0.7,Math.toRadians(-45),0.3);
    }
}
