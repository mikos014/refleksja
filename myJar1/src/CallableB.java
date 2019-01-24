import jfk.*;

@Description(description = "concat")
public class CallableB implements ICallable{

    @Override
    public int factorial(int i) {
        return 0;
    }

    @Override
    public String concat(String s, String s1) {
        s = s.concat(s1);
        return s;
    }

    @Override
    public String printMyGroup() {
        return null;
    }

    @Override
    public boolean compareString(String s, String s1) {
        return false;
    }
}
