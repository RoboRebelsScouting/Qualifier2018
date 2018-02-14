package sample;

import static java.lang.Math.round;

/**
 * Created by 1153 on 3/28/2017.
 */
public class AllianceData {
    int robot1;
    int robot2;
    int robot3;
    int allianceNumber;

    // these numbers are combined averages for the alliance
    double avgAutoScale;
    double avgAutoSwitch;
    double avgTeleScale;
    double avgTeleSwitch;
    double avgTeleClimb;

    // strength of alliance
    double allianceLowStrength;
    double allianceRawStrength;
    double allianceHighStrength;

    // given the 5 factors, calculate a strength number
    public void calcStrength() {
        double lowStrength = 0.0;
        double highStrength = 0.0;
        double rawStrength = 0.0;

        double totalSwitchs = avgAutoSwitch + avgTeleSwitch;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoSwitch >= 1.0 && avgAutoSwitch < 3.0) {
            rawStrength += 60;
            autoRotors = 1;
        }else if (avgAutoSwitch == 3.0) {
            rawStrength += 120;
            autoRotors = 2;
        }
        if (round(avgAutoSwitch) >= 1.0 && round(avgAutoSwitch) < 3.0) {
            highStrength += 60;
            autoRotors = 1;
        }else if (round(avgAutoSwitch) == 3.0) {
            highStrength += 120;
            autoRotors = 2;
        }
        if ((int) avgAutoSwitch >= 1.0 && (int) avgAutoSwitch < 3.0) {
            lowStrength += 60;
            autoRotors = 1;
        }else if ((int)avgAutoSwitch == 3.0) {
            lowStrength += 120;
            autoRotors = 2;
        }

        /* if (totalSwitchs >= 12) {
            rotors = 4;
        } else if (totalSwitchs >= 6) {
            rotors = 3;
        } else if (totalSwitchs >= 2) {
            rotors = 2;
        } else if (totalSwitchs >= 0) {
            rotors = 1;
        } else {
            rotors = 0;
        } */

        int teleRotors = rotors - autoRotors;
        // 40 points for each rotor in tele
        lowStrength += (teleRotors * 40);
        highStrength += (teleRotors * 40);
        rawStrength += (teleRotors * 40);
        if (rotors == 4) {
            lowStrength += 100;
            rawStrength += 100;
            highStrength += 100;
        }
        lowStrength += avgAutoScale;
        lowStrength += avgTeleScale;
        if (avgAutoScale + avgTeleScale >= 40) {
            lowStrength += 20;
            highStrength += 20;
            rawStrength += 20;
        }
        int climbPoints = (int)(avgTeleClimb) * 50;
        lowStrength += climbPoints;

        climbPoints = (int) (round (avgTeleClimb)) *50;
        highStrength += climbPoints;

        climbPoints = (int) (avgTeleClimb*50);
        rawStrength += climbPoints;

        allianceLowStrength = lowStrength;
        allianceHighStrength = highStrength;
        allianceRawStrength = rawStrength;
    }
}
