import java.io.PrintWriter;
import java.lang.String;
import java.util.Arrays;


/**
 * Implementation of the Runqueue interface using an Ordered Array.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan
 */
public class OrderedArrayRQ implements Runqueue {

    
	private int size;
	//private String procArray[];
	//private int procVT[];
	
	private Proc procArray[];
	
	private final int INI_SIZE = 1;
	private boolean sorted = false;
	
    public OrderedArrayRQ() {
        size = INI_SIZE;
        procArray = new Proc[size];
    //	procVT = new int[size];
    }  // end of OrderedArrayRQ()

    @Override
    public void enqueue(String procLabel, int vt) {
        // Implement me
    	 
   		//Assign the proc and its vt
    	Proc temp = new Proc(procLabel, vt);
    	this.procArray[this.size-this.INI_SIZE] = temp;
    	this.size++;

    	//Expanding the array    		
   		
    	this.procArray = Arrays.copyOf(this.procArray, this.size);
    	this.sorted = false;
    	
    	
    	
    
    } // end of enqueue()

    @Override
    public String dequeue() {
    	
        Proc temp = null;
//        if(!sorted)
//    	{this.Sorting();}
    	for(int x = 0; x < size; x++)
    	{
    		if(this.procArray[x] != null)
    		{
    			temp = procArray[x];
    			this.procArray[x] = null;
    			this.Sorting();break;
    		
    			
    		}
    	}
    	
    	
    	return temp.label;
    	// placeholder,modify this
    } // end of dequeue()

    @Override
    public boolean findProcess(String procLabel){
        
    	Proc Proc = null;
    	for(Proc x: this.procArray)
    	{
    		if(x!= null && x.label.equals(procLabel))
    		{
    			Proc = x;
    		}
    	}
    	
    	if(Proc != null)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
        
    } // end of findProcess()

    @Override
    public boolean removeProcess(String procLabel) {
    	
    	boolean removed = false;
    	for(int x= 0; x < this.size; x++)
    	{
    		if(this.procArray[x] != null && this.procArray[x].label.equals(procLabel))
    		{
    			this.procArray[x] = null;
    			removed = true;
    			this.Sorting();break;
    		}
    	}
    	
    	return removed;

        // placeholder, modify this
    } // end of removeProcess()

    @Override
    public int precedingProcessTime(String procLabel) {
        
    	 int totalPt = -1;
         
         if(!sorted)
         {this.Sorting();}
         
         if(this.findProcess(procLabel))
     	{
         for(int x = 0; x< this.size;x++)
         { 
        	 if(this.procArray[x] != null)
         	{	
        	
        		if(procLabel.equals(this.procArray[x].label))
        		{	
        			totalPt++;
        			break;
        		}
        		
        		totalPt += this.procArray[x].vt; 
        	}
         }
       	  
       	  }
    	
       	  
         
       	//System.out.println(totalPT);
           return totalPt;
    }// end of precedingProcessTime()

    @Override
    public int succeedingProcessTime(String procLabel) {
      int totalPt = -1;
      
      if(!sorted)
      {this.Sorting();}
      
      for(int x = 0; x< this.size;x++)
      {
    	  if(this.procArray[x] != null)
    	  {
    	  if(procLabel.equals(this.procArray[x].label)
    	   && x+1 < this.size)
    	  {		totalPt++;
    		  for (int y = x+1; y<this.size; y++)
    		  {
    			  if(this.procArray[y] != null)
    	    	  {
    				  totalPt += this.procArray[y].vt;
    	    	  }
    		  }
    		  break;
    	  }
    	  }
      }
      
      
    	//System.out.println(totalPT);
        return totalPt;
    	
    } // end of precedingProcessTime()

    @Override
    public void printAllProcesses(PrintWriter os) {
    	
    	String AllProc = "";
    	this.Sorting();
    	
    	for (int x = 0; x < this.size; x++)
    	{
    		if (this.procArray[x] != null)
    		{
    		 AllProc += this.procArray[x].label + " ";
    		}
    	}
    	
    	os.println(AllProc);
    	
    	
    }// end of printAllProcesses()
    
    private void Sorting()
    {//bubble sorting 
    	
    	int checkingPos = 0;
    	int sortedProc = 0;
    	
    	while(sortedProc <= this.size)
    	{
    		
    		if(checkingPos == this.size -2)
    		{
    			sortedProc++;
    			checkingPos = 0;
    		}
    	  if(this.procArray[checkingPos] != null && this.procArray[checkingPos+1] != null)
    	  {
    		if(this.procArray[checkingPos].vt > this.procArray[checkingPos+1].vt)
    		{
    			
    			Proc temp;
    			
    			temp = this.procArray[checkingPos];
    			
    			this.procArray[checkingPos] = this.procArray[checkingPos+1];
    			
    			this.procArray[checkingPos+1] = temp;
    			
    			
    		}
    	  }
    		
    		checkingPos++;
    			
    	}
    	this.sorted = true;
    }

} // end of class OrderedArrayRQ


