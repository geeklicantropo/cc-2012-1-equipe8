package translate;

import visitor.ExpVisitor;

public class AndExp extends Exp
{

  temp.Label label = new temp.Label();
  Exp        left;
  Exp        right;

  public AndExp(Exp l, Exp r)
  {
    left = l;
    right = r;
  }

  public IRTree.Exp unEx()
  {
    temp.Temp r = new temp.Temp();
    temp.Label t = new temp.Label();
    temp.Label f = new temp.Label();

    return new IRTree.ESEQ(new IRTree.SEQ(new IRTree.MOVE(new IRTree.TEMP(r), new IRTree.CONST(1)), new IRTree.SEQ(left.unCx(label,
        f), new IRTree.SEQ(new IRTree.LABEL(label), new IRTree.SEQ(right.unCx(t, f), new IRTree.SEQ(new IRTree.LABEL(f),
        new IRTree.SEQ(new IRTree.MOVE(new IRTree.TEMP(r), new IRTree.CONST(0)), new IRTree.LABEL(t))))))), new IRTree.TEMP(r));
  }

  public IRTree.Stm unCx(temp.Label t, temp.Label f)
  {
    return new IRTree.SEQ(left.unCx(label, f), new IRTree.SEQ(new IRTree.LABEL(label), right.unCx(t, f)));
  }

  public IRTree.Stm unNx()
  {
    System.err.println("ERROR:  In well-typed MiniJava, (AndExp a).unNx() should never be used.");
    return null;
  }
  
}
