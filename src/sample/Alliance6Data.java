package sample;

/**
 * Created by 1153 on 4/2/2017.
 */
public class Alliance6Data {
    int robot1A6;
    int robot2A6;
    int robot3A6;

    // these numbers are combined averages for the alliance
    double avgAutoFuel6;
    double avgAutoGear6;
    double avgTeleFuel6;
    double avgTeleGear6;
    double avgTeleClimb6;

    // strength of alliance
    double alliance6Strength;

    // given the 5 factors, calculate a strength number
    public void calcStrength6() {
        double strength = 0.0;

        double totalGears6 = avgAutoGear6 + avgTeleGear6;
        int rotors = 0;
        int autoRotors = 0;

        if (avgAutoGear6 >= 1.0 && avgAutoGear6 < 3.0) {
            strength += 60;
            autoRotors = 1;
        }else if (avgAutoGear6 == 3.0) {
            strength += 120;
            autoRotors = 2;
        }

        // gears needed for rotors:
        // 1 gear = 1 rotor
        // 3 gears = 2 rotors
        // 7 gears = 3 rotors
        // 13 gears = 4 rotors
        //assume they get the free gear

        if (totalGears6 >= 12) {
            rotors = 4;
        } else if (totalGears6 >= 6) {
            rotors = 3;
        } else if (totalGears6 >= 2) {
            rotors = 2;
        } else if (totalGears6 >= 0) {
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
        strength += avgAutoFuel6;
        strength += avgTeleFuel6;
        if (avgAutoFuel6 + avgTeleFuel6 >= 40) {
            strength += 20;
        }
        int climbPoints6 = (int)(avgTeleClimb6) * 50;
        strength += climbPoints6;

        alliance6Strength = strength;
    }
}
