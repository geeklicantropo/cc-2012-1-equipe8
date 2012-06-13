package translate;

public class RelCx extends Cx
{

  private int relop;
  IRTree.Exp    left;
  IRTree.Exp    right;

  public RelCx(int o, IRTree.Exp l, IRTree.Exp r)
  {
    relop = o;
    left = l;
    right = r;
  }

  public IRTree.Stm unCx(temp.Label t, temp.Label f)
  {
    return new IRTree.CJUMP(relop, left, right, t, f);
  }
}
