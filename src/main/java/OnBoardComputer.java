public class OnBoardComputer implements BurnStream {

    @Override
    public int getNextBurn(DescentEvent status) {
        // Get current status from the DescentEvent
        int altitude = status.getAltitude();
        int velocity = status.getVelocity();
        int fuel = status.getFuel();
        

        int maxBurn = 200; // maximum burn allowed per tick
        int minBurn = 0;  // Minimum burn (no thrust)
        int burn = minBurn;

        // If we are close to the ground, we need to slow down a lot
        if (altitude < 100) {
            if (velocity > 10) {
                burn = maxBurn; // Burn as much as possible to slow down
            } else if (velocity > 500) {
                burn = 100; // Burn a medium amount to slow down gently
            } else {
                burn = minBurn; // Already slow, no need to burn
            }
        } else if (altitude < 300) {
            if (velocity > 200) {
                burn = maxBurn;
            } else if (velocity > 50) {
                burn = 100;
            } else {
                burn = minBurn;
            }
        } else {
            // If we are high up, save fuel but keep speed safe
            if (velocity > 40) {
                burn = maxBurn; // Too fast, burn a lot
            } else if (velocity > 20) {
                burn = 100; // Medium speed, burn a little
            } else {
                burn = minBurn; // Slow enough, don't burn
            }
        }

        

        // Never burn more fuel than you have left
        burn = Math.min(burn, fuel);

        // Print burn for debugging
        System.out.println("Burn rec: " + burn);

        return burn;
    }
}
