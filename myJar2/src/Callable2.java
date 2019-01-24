import jfk.*;

@Description(description = "compareString")
public class Callable2 implements ICallable
{
    @Override
    public int factorial(int i) {
        return 0;
    }

    @Override
    public String concat(String s, String s1) {
        return null;
    }

    @Override
    public String printMyGroup() {
        return null;
    }

    @Override
    public boolean compareString(String s, String s1) {
        return s.equals(s1);
    }
}
