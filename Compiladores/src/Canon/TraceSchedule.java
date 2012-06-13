package Canon;

public class TraceSchedule {

  public IRTree.StmList stms;
  BasicBlocks theBlocks;
  @SuppressWarnings("unchecked")
java.util.Dictionary table = new java.util.Hashtable();

  IRTree.StmList getLast(IRTree.StmList block) {
     IRTree.StmList l=block;
     while (l.tail.tail!=null)  l=l.tail;
     return l;
  }

  void trace(IRTree.StmList l) {
   for(;;) {
     IRTree.LABEL lab = (IRTree.LABEL)l.head;
     table.remove(lab.label);
     IRTree.StmList last = getLast(l);
     IRTree.Stm s = last.tail.head;
     if (s instanceof IRTree.JUMP) {
	IRTree.JUMP j = (IRTree.JUMP)s;
        IRTree.StmList target = (IRTree.StmList)table.get(j.targets.head);
	if (j.targets.tail==null && target!=null) {
               last.tail=target;
	       l=target;
        }
	else {
	  last.tail.tail=getNext();
	  return;
        }
     }
     else if (s instanceof IRTree.CJUMP) {
	IRTree.CJUMP j = (IRTree.CJUMP)s;
        IRTree.StmList t = (IRTree.StmList)table.get(j.iftrue);
        IRTree.StmList f = (IRTree.StmList)table.get(j.iffalse);
        if (f!=null) {
	  last.tail.tail=f; 
	  l=f;
	}
        else if (t!=null) {
	  last.tail.head=new IRTree.CJUMP(IRTree.CJUMP.notRel(j.relop),
					j.left,j.right,
					j.iffalse,j.iftrue);
	  last.tail.tail=t;
	  l=t;
        }
        else {
	  temp.Label ff = new temp.Label();
	  last.tail.head=new IRTree.CJUMP(j.relop,j.left,j.right,
					j.iftrue,ff);
	  last.tail.tail=new IRTree.StmList(new IRTree.LABEL(ff),
		           new IRTree.StmList(new IRTree.JUMP(j.iffalse),
					    getNext()));
	  return;
        }
     }
     else throw new Error("Bad basic block in TraceSchedule");
    }
  }

  IRTree.StmList getNext() {
      if (theBlocks.blocks==null) 
	return new IRTree.StmList(new IRTree.LABEL(theBlocks.done), null);
      else {
	 IRTree.StmList s = theBlocks.blocks.head;
	 IRTree.LABEL lab = (IRTree.LABEL)s.head;
	 if (table.get(lab.label) != null) {
          trace(s);
	  return s;
         }
         else {
	   theBlocks.blocks = theBlocks.blocks.tail;
           return getNext();
         }
      }
  }

  @SuppressWarnings("unchecked")
public TraceSchedule(BasicBlocks b) {
    theBlocks=b;
    for(StmListList l = b.blocks; l!=null; l=l.tail)
       table.put(((IRTree.LABEL)l.head.head).label, l.head);
    stms=getNext();
    table=null;
  }        
}

