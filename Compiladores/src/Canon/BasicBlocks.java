package Canon;

public class BasicBlocks {
  public StmListList blocks;
  public temp.Label done;

  private StmListList lastBlock;
  private IRTree.StmList lastStm;

  private void addStm(IRTree.Stm s) {
	lastStm = lastStm.tail = new IRTree.StmList(s,null);
  }

  private void doStms(IRTree.StmList l) {
      if (l==null) 
	doStms(new IRTree.StmList(new IRTree.JUMP(done), null));
      else if (l.head instanceof IRTree.JUMP 
	      || l.head instanceof IRTree.CJUMP) {
	addStm(l.head);
	mkBlocks(l.tail);
      } 
      else if (l.head instanceof IRTree.LABEL)
           doStms(new IRTree.StmList(new IRTree.JUMP(((IRTree.LABEL)l.head).label), 
	  			   l));
      else {
	addStm(l.head);
	doStms(l.tail);
      }
  }

  void mkBlocks(IRTree.StmList l) {
     if (l==null) return;
     else if (l.head instanceof IRTree.LABEL) {
	lastStm = new IRTree.StmList(l.head,null);
        if (lastBlock==null)
  	   lastBlock= blocks= new StmListList(lastStm,null);
        else
  	   lastBlock = lastBlock.tail = new StmListList(lastStm,null);
	doStms(l.tail);
     }
     else mkBlocks(new IRTree.StmList(new IRTree.LABEL(new temp.Label()), l));
  }
   

  public BasicBlocks(IRTree.StmList stms) {
    done = new temp.Label();
    mkBlocks(stms);
  }
}
