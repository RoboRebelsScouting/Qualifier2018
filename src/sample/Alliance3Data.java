package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance3Data {
    int robot1A3;
    int robot2A3;
    int robot3A3;

    // these numbers are combined averages for the alliance
    double avgAutoFuel3;
    double avgAutoGear3;
    double avgTeleFuel3;
    double avgTeleGear3;
    double avgTeleClimb3;

    // strength of alliance
    double alliance3Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength3() {
        double strength = 0.0;

        double totalGears3 = avgAutoGear3 + avgTeleGear3;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear3 >= 1.0 && avgAutoGear3 < 3.0) {
            strength += 60;
            autoRotors = 1;
        } else if (avgAutoGear3 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears3 >= 12) {
            rotors = 4;
        } else if (totalGears3 >= 6) {
            rotors = 3;
        } else if (totalGears3 >= 2) {
            rotors = 2;
        } else if (totalGears3 >= 0) {
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
        strength += avgAutoFuel3;
        strength += avgTeleFuel3;
        if (avgAutoFuel3 + avgTeleFuel3 >= 40) {
            strength += 20;
        }
        int climbPoints3 = (int)(avgTeleClimb3) * 50;
        strength += climbPoints3;

        alliance3Strength = strength;
    }
}
