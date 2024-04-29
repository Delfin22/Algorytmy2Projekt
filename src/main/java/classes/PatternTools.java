package classes;

public class PatternTools {

    public static boolean searchPattern(String s1, String pattern) throws IllegalArgumentException{
        if(s1.isEmpty())
            throw new IllegalArgumentException();
        if(s1.contains(pattern))
            return true;
        return false;
    }
}
