package translate;

public abstract class Exp {
    
	abstract IRTree.Exp unEx();
    abstract IRTree.Stm unNx();
    abstract IRTree.Stm unCx(temp.Label t, temp.Label f);
    
}