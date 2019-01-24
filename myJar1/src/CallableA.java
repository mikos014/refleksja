import jfk.*;

@Description(description = "factorial")
public final class CallableA implements ICallable
{
    @Override
    public int factorial(int i)
    {
        if (i==0 || i==1)
            return 1;
        else
            return i*factorial(i-1);
    }

    @Override
    public String concat(String a, String b)
    {
        return null;
    }

    @Override
    public String printMyGroup()
    {
        return null;
    }

    @Override
    public boolean compareString(String s, String s1) {
        return false;
    }

}