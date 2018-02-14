package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance5Data {
    int robot1A5;
    int robot2A5;
    int robot3A5;

    // these numbers are combined averages for the alliance
    double avgAutoFuel5;
    double avgAutoGear5;
    double avgTeleFuel5;
    double avgTeleGear5;
    double avgTeleClimb5;

    // strength of alliance
    double alliance5Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength5() {
        double strength = 0.0;

        double totalGears5 = avgAutoGear5 + avgTeleGear5;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear5 >= 1.0 && avgAutoGear5 < 3.0) {
            strength += 60;
            autoRotors = 1;
        }else if (avgAutoGear5 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears5 >= 12) {
            rotors = 4;
        } else if (totalGears5 >= 6) {
            rotors = 3;
        } else if (totalGears5 >= 2) {
            rotors = 2;
        } else if (totalGears5 >= 0) {
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
        strength += avgAutoFuel5;
        strength += avgTeleFuel5;
        if (avgAutoFuel5 + avgTeleFuel5 >= 40) {
            strength += 20;
        }
        int climbPoints5 = (int)(avgTeleClimb5) * 50;
        strength += climbPoints5;

        alliance5Strength = strength;
    }
}
