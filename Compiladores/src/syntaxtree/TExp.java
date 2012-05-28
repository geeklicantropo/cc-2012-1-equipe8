package syntaxtree;
import visitor.ExpVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class TExp {
  /**Mesmo que accept**/ 
  public abstract void accept(Visitor v);
  public abstract TType accept(TypeVisitor v);
//  public abstract translate.Exp accept(ExpVisitor v);
}
