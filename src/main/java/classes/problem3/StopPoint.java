package classes.problem3;

/**
 * @see Landmark
 * This is helper class that wraps around information if we need rest in current landmark after the previous one
 * @param landmark stop position
 * @param restRequired if current stop point is brighter than previous one
 */
public record StopPoint(Landmark landmark, boolean restRequired) {
}
