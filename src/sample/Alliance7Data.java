package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance7Data {
    int robot1A7;
    int robot2A7;
    int robot3A7;

    // these numbers are combined averages for the alliance
    double avgAutoFuel7;
    double avgAutoGear7;
    double avgTeleFuel7;
    double avgTeleGear7;
    double avgTeleClimb7;

    // strength of alliance
    double alliance7Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength7() {
        double strength = 0.0;

        double totalGears7 = avgAutoGear7 + avgTeleGear7;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear7 >= 1.0 && avgAutoGear7 < 3.0) {
            strength += 60;
            autoRotors = 1;
        } else if (avgAutoGear7 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears7 >= 12) {
            rotors = 4;
        } else if (totalGears7 >= 6) {
            rotors = 3;
        } else if (totalGears7 >= 2) {
            rotors = 2;
        } else if (totalGears7 >= 0) {
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
        strength += avgAutoFuel7;
        strength += avgTeleFuel7;
        if (avgAutoFuel7 + avgTeleFuel7 >= 40) {
            strength += 20;
        }
        int climbPoints7 = (int)(avgTeleClimb7) * 50;
        strength += climbPoints7;

        alliance7Strength = strength;
    }
}
