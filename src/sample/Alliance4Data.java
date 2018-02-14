package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance4Data {
    int robot1A4;
    int robot2A4;
    int robot3A4;

    // these numbers are combined averages for the alliance
    double avgAutoFuel4;
    double avgAutoGear4;
    double avgTeleFuel4;
    double avgTeleGear4;
    double avgTeleClimb4;

    // strength of alliance
    double alliance4Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength4() {
        double strength = 0.0;

        double totalGears4 = avgAutoGear4 + avgTeleGear4;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear4 >= 1.0 && avgAutoGear4 < 3.0) {
            strength += 60;
            autoRotors = 1;
        } else if (avgAutoGear4 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears4 >= 12) {
            rotors = 4;
        } else if (totalGears4 >= 6) {
            rotors = 3;
        } else if (totalGears4 >= 2) {
            rotors = 2;
        } else if (totalGears4 >= 0) {
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
        strength += avgAutoFuel4;
        strength += avgTeleFuel4;
        if (avgAutoFuel4 + avgTeleFuel4 >= 40) {
            strength += 20;
        }
        int climbPoints4 = (int)(avgTeleClimb4) * 50;
        strength += climbPoints4;

        alliance4Strength = strength;
    }
}
