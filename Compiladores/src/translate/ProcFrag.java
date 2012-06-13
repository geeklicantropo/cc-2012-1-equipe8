package translate;

public class ProcFrag extends Frag
{
  public IRTree.Stm    body;
  public Frame.Frame frame;
  public ProcFrag(IRTree.Stm b, Frame.Frame f)
  {
    body = b;
    frame = f;
  }
}
