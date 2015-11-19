package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	private boolean joined = false;
	private boolean empty = false;
	private int cntTuple = -1;
	private ArrayList<Attribute> tempAttributeList = new ArrayList<Attribute>(); 
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}


	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		
		if(joinPredicate.isEmpty())
			return leftChild.next();
		
		if(!joined){
		int leftCAIndex=0;
		int rightCAIndex=0;
		Tuple leftTuple;
		Tuple rightTuple;
		ArrayList<Tuple> leftTupleList= new ArrayList<Tuple>();
		ArrayList<Tuple> rightTupleList= new ArrayList<Tuple>();
		

		while((leftTuple=leftChild.next())!=null){
			leftTupleList.add(leftTuple);
		
			}
		
		while((rightTuple=rightChild.next())!=null){
			rightTupleList.add(rightTuple);
			}
				
		if(leftTupleList.size()==0){
			joined=true;
			empty = true;
		}
		else if (rightTupleList.size()==0){
			joined=true;
			empty = true;
		}
		else if (leftTupleList.size()==0 && rightTupleList.size()==0){
			joined = true;
			empty = true;

		}
		else {
		for(int cntLeftTuple =0;cntLeftTuple<leftTupleList.get(0).getAttributeList().size();cntLeftTuple++)
			for(int cntRightTuple =0;cntRightTuple<rightTupleList.get(0).getAttributeList().size();cntRightTuple++)
				if(leftTupleList.get(0).getAttributeName(cntLeftTuple).equals(rightTupleList.get(0).getAttributeName(cntRightTuple))){
					leftCAIndex=cntLeftTuple;
					rightCAIndex=cntRightTuple;
					break;
				}
		
		for(int indexRightTuple =0;indexRightTuple<rightTupleList.size();indexRightTuple++)
			for(int indexLeftTuple =0;indexLeftTuple<leftTupleList.size();indexLeftTuple++)
				if(rightTupleList.get(indexRightTuple).getAttributeValue(rightCAIndex).equals(leftTupleList.get(indexLeftTuple).getAttributeValue(leftCAIndex))){
					tempAttributeList.clear();
					tempAttributeList.addAll(leftTupleList.get(indexLeftTuple).getAttributeList());
					tempAttributeList.addAll(rightTupleList.get(indexRightTuple).getAttributeList());
					tempAttributeList.remove((leftTupleList.get(indexLeftTuple).getAttributeList().size()+rightCAIndex));
					tuples1.add(new Tuple(new ArrayList<Attribute> (tempAttributeList)));	
				}				

			
		joined = true;	
		}
		}
		
	
		if(empty){
			return null;
		}
		
		if(cntTuple!=tuples1.size()-1){
			cntTuple++;
			newAttributeList.clear();
			newAttributeList.addAll(tuples1.get(cntTuple).getAttributeList());
			return tuples1.get(cntTuple);
		}
		
		return null;
	}
	
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
		{
			return(newAttributeList);
		}
	}

}