package Components;

import org.junit.Test;

import Util.Vector2;

import static org.junit.Assert.assertEquals;

public class DriveTest {

    @Test
    public void directionToMotorPower_fullForwardInput_isLimitedToSqrtHalf() {
        double[] powers = Drive.directionToMotorPower(new Vector2(0, 1), 0f);

        double expectedMagnitude = 1.0;
        assertEquals(expectedMagnitude, Math.abs(powers[0]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[1]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[2]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[3]), 1e-6);
    }

    @Test
    public void directionToMotorPower_halfForwardInput_isLimitedToSqrtHalf() {
        double[] powers = Drive.directionToMotorPower(new Vector2(0, 0.5), 0f);

        double expectedMagnitude = 0.5;
        assertEquals(expectedMagnitude, Math.abs(powers[0]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[1]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[2]), 1e-6);
        assertEquals(expectedMagnitude, Math.abs(powers[3]), 1e-6);
    }

    @Test
    public void directionToMotorPower_diagonalInput_reachesFullPowerAfterNormalization() {
        double[] powers = Drive.directionToMotorPower(new Vector2(1, 1), 0f);

        assertEquals(1.0, powers[0], 1e-6);
        assertEquals(0.0, powers[1], 1e-6);
        assertEquals(0.0, powers[2], 1e-6);
        assertEquals(1.0, powers[3], 1e-6);
    }
}
