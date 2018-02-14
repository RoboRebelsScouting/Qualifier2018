package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1153 on 3/28/2017.
 */
public class RobotData {
    public int robotNumber;

    public List<Integer> matchList;

    public int matches;

    public DataGroup gears;

    public DataGroup teleGears;
    public DataGroup autoGears;

    public DataGroup autoCross;

    public DataGroup lowShots;

    public DataGroup highShots;

    public DataGroup climb;

    public DataGroup autoLowShots;
    public DataGroup autoHighShots;

    public RobotData() {
        robotNumber = 0;
        matches = 0;
        gears = new DataGroup();
        teleGears = new DataGroup();
        autoGears = new DataGroup();
        autoCross = new DataGroup();
        lowShots = new DataGroup();
        highShots = new DataGroup();
        climb = new DataGroup();
        autoLowShots = new DataGroup();
        autoHighShots = new DataGroup();

        matchList = new ArrayList<Integer>();
    }
}

