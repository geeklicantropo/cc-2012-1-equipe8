package translate;

public abstract class Cx extends Exp {

	public IRTree.Exp unEx() {
		temp.Temp r = new temp.Temp();
		temp.Label t = new temp.Label();
		temp.Label f = new temp.Label();

		return new IRTree.ESEQ(new IRTree.SEQ(new IRTree.MOVE(
				new IRTree.TEMP(r), new IRTree.CONST(1)), new IRTree.SEQ(unCx(
				t, f), new IRTree.SEQ(new IRTree.LABEL(f), new IRTree.SEQ(
				new IRTree.MOVE(new IRTree.TEMP(r), new IRTree.CONST(0)),
				new IRTree.LABEL(t))))), new IRTree.TEMP(r));
	}

	public abstract IRTree.Stm unCx(temp.Label t, temp.Label f);

	public IRTree.Stm unNx() {
		System.err.println("ERROR:  In well-typed MiniJava, (Cx c).unNx() should never be used.");
		return null;
	}
}
