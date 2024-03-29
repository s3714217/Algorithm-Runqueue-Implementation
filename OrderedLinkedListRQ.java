import java.io.PrintWriter;
import java.lang.String;
import java.util.Arrays;

/**
 * Implementation of the run queue interface using an Ordered Link List.
 *
 * Your task is to complete the implementation of this class.
 * You may add methods and attributes, but ensure your modified class compiles and runs.
 *
 * @author Sajal Halder, Minyi Li, Jeffrey Chan.
 */
public class OrderedLinkedListRQ implements Runqueue {
	
	private int size;
	private Proc procArr[];
	private final int INI_SIZE = 1;
	
    
    public OrderedLinkedListRQ() {
    	size = 2;
        procArr = new Proc[size];
    	
    }  // end of OrderedLinkedList()


    @Override
    public void enqueue(String procLabel, int vt) {
        
    	//Assign the proc and its vt
    	this.procArr[this.size-this.INI_SIZE] = new Proc(procLabel,vt);
		/*
		 * assgin the new proc to the back of the array
		 * loop through the array to find suitable front and back node
		 * set the front link to the front node
		 * set the back link to the back node
		 * set the back link of the front node to the current node
		 * set the front link of the back node to the current node
		 * expand the size of the array
		 */
		
		int checkingPos = 1;
		while(checkingPos != 0)
		{
		 if(this.size > 2)
		 {
		  if(this.procArr[checkingPos] != null )
		  {
			if(this.procArr[checkingPos].vt <= vt)
			{
				
				
			  if(this.procArr[checkingPos].neighbor1 != 0)
			  {
				if(vt < this.procArr[this.procArr[checkingPos].neighbor1].vt )
				{
					this.procArr[this.size - this.INI_SIZE].neighbor2 = checkingPos;
					this.procArr[this.size - this.INI_SIZE].neighbor1 = this.procArr[checkingPos].neighbor1;
					this.procArr[this.procArr[checkingPos].neighbor1].neighbor2 = this.size - this.INI_SIZE;
					this.procArr[checkingPos].neighbor1 = this.size - this.INI_SIZE;break;
					
				}
				else 
				{
					checkingPos = this.procArr[checkingPos].neighbor1;
				}
			  }
			  else
			  {
				  this.procArr[this.size - this.INI_SIZE].neighbor2 = checkingPos;
				  this.procArr[this.size - this.INI_SIZE].neighbor1 = this.procArr[checkingPos].neighbor1;
				  this.procArr[checkingPos].neighbor1 = this.size - this.INI_SIZE;break;
			  }
			  
			  
			  
			}
			else if(this.procArr[checkingPos].vt > vt)
			{
				
				
				
			  if(this.procArr[checkingPos].neighbor2 != 0)
			  {
				if(vt >= this.procArr[this.procArr[checkingPos].neighbor2].vt)
				{
					this.procArr[this.size-this.INI_SIZE].neighbor2 = this.procArr[checkingPos].neighbor2;
					this.procArr[this.size - this.INI_SIZE].neighbor1 = checkingPos;
					this.procArr[this.procArr[checkingPos].neighbor2].neighbor1 = this.size - this.INI_SIZE;
					this.procArr[checkingPos].neighbor2 = this.size - this.INI_SIZE;break;
					
				}
				else
				{
					checkingPos = this.procArr[checkingPos].neighbor2;
				}
			  }
			  else 
			  {
				
				this.procArr[this.size - this.INI_SIZE].neighbor1 = checkingPos;
				this.procArr[this.size-this.INI_SIZE].neighbor2 = this.procArr[checkingPos].neighbor2;
				this.procArr[checkingPos].neighbor2 = this.size - this.INI_SIZE;break;
			  }
			  
			  
			  
			  
		  }
		 }
		  else {checkingPos++;}
		 }
		  else
		  {
			  checkingPos = 0;break;
		  }
		}
		
		this.size++;

		//Expanding the array    
		
	
		this.procArr = Arrays.copyOf(this.procArr, this.size);
		
		

    } // end of enqueue()


    @Override
    public String dequeue() {
    	/*
    	 * Removing the proc that have no front node
    	 * Set the front link of the back node of the removed node as null (front of the queue)
		 * return the removed node
    	 */
    	Proc dequeue = null;
    	for(int x = 1; x < this.size; x++)
    	{
    		if (this.procArr[x] != null && this.procArr[x].neighbor2 == 0 )
    		{
    			dequeue = this.procArr[x];
    			this.procArr[this.procArr[x].neighbor1].neighbor2 = 0;
    			this.procArr[x] = null;
    			;break;
    			
    		}
    	}
    	
        return dequeue.label; 
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel) {
    	boolean existed = false;
    
    	/*
    	 * loop through the array to find the procLabel
    	 * return true if found
    	 */
    	
        for(Proc x : this.procArr)
        {
        	if(x != null && x.label.equals(procLabel))
        	{
        		existed = true;
        	}
        }
        return existed;
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) {
    	boolean removed = false;
        
    	/*
    	 * loop through the array to find the procLabel
    	 * set the back link of the front node of the removed node as the back link 
    	 * set the front link of the back node the removed node as the front link
    	 * return true if the node existed and removed
    	 */
    	
        for(int x = 1; x< this.size;x++)
        {
        	if(this.procArr[x] != null && this.procArr[x].label.equals(procLabel))
        	{
        		
        		if(this.procArr[this.procArr[x].neighbor1] != null)
        		{
        			this.procArr[this.procArr[x].neighbor1].neighbor2 = this.procArr[x].neighbor2;
        		}
        		if(this.procArr[this.procArr[x].neighbor2] != null)
        		{
        		this.procArr[this.procArr[x].neighbor2].neighbor1 = this.procArr[x].neighbor1;
        		}
        		this.procArr[x] = null;
        		removed = true;break;
        		
        	}
        }
       

        return removed;// placeholder modify this
    } // End of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) {
        
    	/*
    	 * loop through the array to find suitable proc
    	 * loop through the linked list to calculate the vt of all front node
    	 * return the total vt calculated
    	 */
    	
    	int TotalPT = -1;
    	for (int x = 1; x < this.size; x++)
    	{
    		if(this.procArr[x] != null && this.procArr[x].label.equals(procLabel))
    		{
    			TotalPT++;
    			int checkingPos = this.procArr[x].neighbor2;
    			while(checkingPos !=0)
    			{
    				TotalPT += this.procArr[checkingPos].vt;
    				checkingPos = this.procArr[checkingPos].neighbor2;
    			}
    			break;
    		}
    	}

        return TotalPT; // placeholder, modify this
    } // end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) {
    	/*
    	 * loop through the array to find suitable proc
    	 * loop through the linked list to calculate the vt of all back node
    	 * return the total vt calculated
    	 */
    	int TotalPT = -1;
    	
    	for (int x = 1; x < this.size; x++)
    	{
    		if(this.procArr[x] != null && this.procArr[x].label.equals(procLabel))
    		{
    			TotalPT++;
    			int checkingPos = this.procArr[x].neighbor1;
    		
    			while(checkingPos !=0)
    			{TotalPT += this.procArr[checkingPos].vt;
    				checkingPos = this.procArr[checkingPos].neighbor1;
    			}
    			break;
    		}
    	}

        return TotalPT;
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) {
    	/*
    	 * loop through the array to find the front node
    	 * loop through the linked list to append the back node to the String
    	 * print the String of the list
    	 */
    	String AllProc = "";
    	for (int x = 1; x < this.size; x++)
    	{
    		if(this.procArr[x] != null)
    		{
    			if(this.procArr[x].neighbor2 == 0)
    			{
    				int checkingPos = x;
        			while(checkingPos != 0)
        			{
        				AllProc += this.procArr[checkingPos].label + " ";
        				checkingPos = this.procArr[checkingPos].neighbor1;
        			}
        			break;
    			}
    		}
    	}
    	
    	
    	
   
    	os.println(AllProc);
    	
    	
    	
    } // end of printAllProcess()

} // end of class OrderedLinkedListRQ
