package translate;

public class Nx extends Exp
{
  IRTree.Stm stm;
  public Nx(IRTree.Stm s)
  {
    stm = s;
  }
  public IRTree.Stm unNx()
  {
    return stm;
  }
  public IRTree.Exp unEx()
  {
    System.err.println("ERROR:  In well-typed MiniJava, (Nx n).unEx() should never be used.");
    return null;
  }
  public IRTree.Stm unCx(temp.Label t, temp.Label f)
  {
    System.err.println("ERROR:  In well-typed MiniJava, (Nx n).unCx() should never be used.");
    return null;
  }
}
