import static java.lang.System.*;

public enum Season {

    WINTER,SPRING,SUMMER,FALL;

    public static void main(String [] args) {
	for(Season s : Season.values()) {
	    out.println(s);
	}
    }

}

