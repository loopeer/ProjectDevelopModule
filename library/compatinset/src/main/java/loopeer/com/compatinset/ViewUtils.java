package loopeer.com.compatinset;

public class ViewUtils {

    public static boolean objectEquals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

}