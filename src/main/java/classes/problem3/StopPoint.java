package classes.problem3;

/**
 * @param landmark     stop position
 * @param restRequired if current stop point is brighter than previous one
 * @see Landmark
 * This is helper class that wraps around information if we need rest in current landmark after the previous one
 */
public record StopPoint(Landmark landmark, boolean restRequired) {
    @Override
    public String toString() {
        return String.format("{Stop %s %s}", landmark, restRequired ? "Rest required" : "");
    }
}
