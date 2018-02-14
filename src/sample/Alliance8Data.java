package sample;

import static java.lang.Math.round;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance8Data {
    int robot1A8;
    int robot2A8;
    int robot3A8;

    // these numbers are combined averages for the alliance
    double avgAutoFuel8;
    double avgAutoGear8;
    double avgTeleFuel8;
    double avgTeleGear8;
    double avgTeleClimb8;

    // strength of alliance
    double alliance8Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength8() {
        double strength = 0.0;

        double totalGears8 = avgAutoGear8 + avgTeleGear8;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear8 >= 1.0 && avgAutoGear8 < 3.0) {
            strength += 60;
            autoRotors = 1;
        } else if (avgAutoGear8 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears8 >= 12) {
            rotors = 4;
        } else if (totalGears8 >= 6) {
            rotors = 3;
        } else if (totalGears8 >= 2) {
            rotors = 2;
        } else if (totalGears8 >= 0) {
            rotors = 1;
        } else {
            rotors = 0;
        }

        int teleRotors = rotors - autoRotors;
        // 40 points for each rotor in tele
        strength += (teleRotors * 40);
        if (rotors == 4) {
            strength += 100;
        }
        strength += avgAutoFuel8;
        strength += avgTeleFuel8;
        if (avgAutoFuel8 + avgTeleFuel8 >= 40) {
            strength += 20;
        }
        int climbPoints8 = (int) (round(avgTeleClimb8) * 50);
        strength += climbPoints8;

        alliance8Strength = strength;
    }
}
