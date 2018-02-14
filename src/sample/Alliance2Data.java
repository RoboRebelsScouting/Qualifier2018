package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance2Data {
    int robot1A2;
    int robot2A2;
    int robot3A2;

    // these numbers are combined averages for the alliance
    double avgAutoScaleBlue;
    double avgAutoSwitchBlue;
    double avgTeleScaleBlue;
    double avgTeleSwitchBlue;
    double avgTeleClimbBlue;

    // strength of alliance
    double allianceBlueStrength;

    // given the 5 factors, calculate a strength number
    public void calcStrengthBlue() {
        double strength = 0.0;

        double totalSwitchBlue = avgAutoSwitchBlue + avgTeleSwitchBlue;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoSwitchBlue >= 1.0 && avgAutoSwitchBlue < 3.0) {
            strength += 60;
            autoRotors = 1;
        } else if (avgAutoSwitchBlue == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        /*
        if (totalGearsBlue >= 12) {
            rotors = 4;
        } else if (totalGearsBlue >= 6) {
            rotors = 3;
        } else if (totalGearsBlue >= 2) {
            rotors = 2;
        } else if (totalGearsBlue >= 0) {
            rotors = 1;
        } else {
            rotors = 0;
        } */

        int teleRotors = rotors - autoRotors;
        // 40 points for each rotor in tele
        strength += (teleRotors * 40);
        if (rotors == 4) {
            strength += 100;
        }
        strength += avgAutoScaleBlue;
        strength += avgTeleScaleBlue;
        if (avgAutoScaleBlue + avgTeleScaleBlue >= 40) {
            strength += 20;
        }
        int climbPointsBlue = (int)(avgTeleClimbBlue) * 50;
        strength += climbPointsBlue;

        allianceBlueStrength = strength;
    }
}

