import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;

//To generate the opening positions of white pieces using the alpha-beta puning.
public class ABOpening {

	static LinkList L = new LinkList();			// List representing the minimax tree
	static int depth;							// Stores the value of the depth passed as command line argument
	public static int pos_evaluated=0;    // Stores the number of positions evaluated by the static estimation function
	public static void main(String[] args)
	{
		if(args.length!=3)						// if 3 arguments are not provided at the command line
		{
			System.out.println("Part 2: ABOPening board1.txt board2.txt 2");
			System.exit(0);
		}
		else if(!args[0].equals("C:\\Users\\Rassu\\Desktop\\board1.txt"))	// if the name of the input file is not board1.txt
		{
			System.out.println("Part 2 : ABOPening board1.txt board2.txt depth(number)");
			System.exit(0);
		}
		else if(!args[1].equals("C:\\Users\\Rassu\\Desktop\\board1ABO4.txt"))	// if the name of the output file is not board2.txt
		{
			System.out.println("ABOPening board1.txt board2.txt depth(number)");
			System.exit(0);
		}
		depth=Integer.parseInt(args[2]);
		ABOpening abo = new ABOpening();
		abo.generateMovesOpening(args[0],args[1]);
		Link parent=L.first;
		int minimax_estimate=abo.maxMin(-10000,parent,10000,0);	// to compute the minimax estimate
		System.out.println("Output board positions:");	
		String final_pos=null;
		Link extra;
		extra=parent.LL.first;
		while(extra!=null)
		{
			if(extra.pos_estimate==minimax_estimate)
			{
				final_pos=extra.b_pos;
				System.out.println(extra.b_pos);
				break;
			}
			extra=extra.nextLink;
		}
		System.out.println("Position evaluated by static estimation is: " + pos_evaluated);
		System.out.println("MiniMax estimate is: " + minimax_estimate);
		try{
		Writer output = null;
		// Open the file that is the second 
		// command line parameter
		File file = new File(args[1]);
		output = new BufferedWriter(new FileWriter(file));
		output.write(final_pos + "\n");
		output.close();
		System.out.println("Please Check board2.txt for possible moves");
		}catch(Exception e){System.out.println("ERROR");}
	}
	
	private int maxMin(int alpha, Link temp, int beta, int d)
	{
		if(d==depth) return static_estimation(temp.b_pos);	// if the current node is the leaf node
		else
		{
			temp.LL=generateAdd(temp.b_pos, temp.LL);
			int num_WhitePieces=0,num_BlackPieces=0;
			for(int i=0;i<temp.b_pos.length();i++)
			{
				if(temp.b_pos.charAt(i)=='W')
					num_WhitePieces++;
				if(temp.b_pos.charAt(i)=='B')
					num_BlackPieces++;
			}
    		
    		if(temp.LL.first==null || num_WhitePieces==0 || num_BlackPieces==0)
			{
				return static_estimation(temp.b_pos);
			}
			d++;				 							// increase the current depth value	
			int v=-10000;
			int temp_minMax=0;
			Link extra;
			extra=temp.LL.first;
			//for each child y of x:
			while(extra!=null)
			{
				temp_minMax=minMax(alpha,extra,beta,d);		//v=max(v,minMax(y));
				extra.pos_estimate=temp_minMax;
				if(temp_minMax>v)
					v=temp_minMax;
				if(v>=beta) return v;
				else
				{
					if(v>alpha)
						alpha=v;
				}
				extra=extra.nextLink;		
			}
			return v;			
		}
	}
    private int minMax(int alpha, Link temp,int beta, int d)
    {
    	   	
    	if(d==depth) return static_estimation(temp.b_pos);
    	else
    	{
    		temp.LL=generateAdd_Black(temp.b_pos, temp.LL);
    		
    		int num_WhitePieces=0,num_BlackPieces=0;
			
			for(int i=0;i<temp.b_pos.length();i++)
			{
				if(temp.b_pos.charAt(i)=='W')
					num_WhitePieces++;
				if(temp.b_pos.charAt(i)=='B')
					num_BlackPieces++;
			}
    		
    		if(temp.LL.first==null || num_WhitePieces==0 || num_BlackPieces==0)
			{
				return static_estimation(temp.b_pos);
			}
    		
    		d++;											// increase the depth
    		int v=10000;
    		int temp_maxMin=0;
    		Link extra;
			extra=temp.LL.first;
    		while(extra!=null)
    		{
    			temp_maxMin=maxMin(alpha,extra,beta,d);		
    			if(temp_maxMin<v)
    				v=temp_maxMin;
    			if(v<=alpha) return v;
    			else
    			{
    				if(v<beta)
    					beta=v;
    			}
    			extra=extra.nextLink;
    		}
    		return v;
    	}
    }
	private void generateMovesOpening(String in_file, String out_file) {
		// code to read the file
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(in_file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String board;
			//Read the first Line
			board = br.readLine();
			L.insert(board);
			// Print the content on the console
			System.out.println("The input board position is:");
			System.out.println (board);
			//Close the input stream
			in.close();
			fstream.close();
			
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	// to generate the addition of white pieces to the board
	public LinkList generateAdd(String board, LinkList LL1)
	{
		String b;
		LL1.first =null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i) == 'x')
			{
				b=board;
				b=b.substring(0, i) + 'W' +b.substring(i+1);
				if(closeMill(i,b))
				{
					LL1=generateRemove(b,LL1);
				}
				else
				{
					LL1.insert(b);
				}
			}
		}
		return LL1;
	}
	// to generate the removal of black pieces from the board
	private LinkList generateRemove(String board,LinkList LL2) {
		String b;
		int count = 0;
		for (int k=0;k<board.length();k++)
		{
			if(board.charAt(k)=='B')
			{
				if(!closeMill(k,board))
				{
					count++;
					b=board;
					b=b.substring(0, k) + 'x' +b.substring(k+1);
					LL2.insert(b);
				}
			}
		}
		if(count==0) LL2.insert(board);
	return LL2;
	}

	// to generate the addition of black pieces to the board
	public LinkList generateAdd_Black(String board, LinkList LL1)
	{
		String b;
		LL1.first =null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i) == 'x')
			{
				b=board;
				b=b.substring(0, i) + 'B' +b.substring(i+1);
				if(closeMill(i,b))
				{
					LL1=generateRemove_Black(b,LL1);
				}
				else
				{
					LL1.insert(b);
				}
			}
		}
		return LL1;
	}

	// to generate the removal of white pieces from the board
	private LinkList generateRemove_Black(String board,LinkList LL2) {
		String b;
		int count = 0;
		for (int k=0;k<board.length();k++)
		{
			if(board.charAt(k)=='W')
			{
				if(!closeMill(k,board))
				{
					count++;
					b=board;
					b=b.substring(0, k) + 'x' +b.substring(k+1);
					LL2.insert(b);
				}
			}
		}
		if(count==0) LL2.insert(board);
	return LL2;
	}

	// the function returns true if adding the current position to the board will cause a closed mill, otherwise false
	private boolean closeMill(int j, String b) {
		char c = b.charAt(j);
		switch(j){
		case 0: if((b.charAt(2)==c && b.charAt(4)==c) || (b.charAt(6)==c && b.charAt(18)==c))
			return true;
		else return false;
case 1: if(b.charAt(11)==c && b.charAt(20)==c)
			return true;
		else return false;
case 2: if((b.charAt(0)==c && b.charAt(4)==c) || (b.charAt(15)==c && b.charAt(7)==c))
			return true;
		else return false;
case 3: if((b.charAt(10)==c && b.charAt(17)==c))
			return true;
		else return false;
case 4: if((b.charAt(8)==c && b.charAt(12)==c) || (b.charAt(0)==c && b.charAt(2)==c))
			return true;
		else return false;
case 5: if((b.charAt(9)==c && b.charAt(14)==c))
			return true;
		else return false;
case 6: if((b.charAt(0)==c && b.charAt(18)==c) || (b.charAt(7)==c && b.charAt(8)==c))
			return true;
		else return false;
case 7: if((b.charAt(6)==c && b.charAt(8)==c) || (b.charAt(2)==c && b.charAt(15)==c))
			return true;
		else return false;
case 8: if((b.charAt(7)==c && b.charAt(6)==c) || (b.charAt(4)==c && b.charAt(12)==c))
			return true;
		else return false;
case 9: if((b.charAt(5)==c && b.charAt(14)==c) || (b.charAt(10)==c && b.charAt(11)==c))
			return true;
		else return false;
case 10: if((b.charAt(3)==c && b.charAt(17)==c) || (b.charAt(9)==c && b.charAt(11)==c))
			return true;
		else return false;
case 11: if((b.charAt(1)==c && b.charAt(20)==c) || (b.charAt(9)==c && b.charAt(10)==c))
			return true;
		else return false;
case 12: if((b.charAt(8)==c && b.charAt(4)==c) || (b.charAt(14)==c && b.charAt(13)==c))
			return true;
		else return false;
case 13: if((b.charAt(14)==c && b.charAt(12)==c) || (b.charAt(16)==c && b.charAt(19)==c))
			return true;
		else return false;
case 14: if((b.charAt(9)==c && b.charAt(5)==c) || (b.charAt(12)==c && b.charAt(13)==c))
			return true;
		else return false;
case 15: if((b.charAt(17)==c && b.charAt(16)==c) || (b.charAt(7)==c && b.charAt(2)==c))
			return true;
		else return false;
case 16: if((b.charAt(19)==c && b.charAt(13)==c) || (b.charAt(17)==c && b.charAt(15)==c))
			return true;
		else return false;
case 17: if((b.charAt(10)==c && b.charAt(3)==c) || (b.charAt(15)==c && b.charAt(16)==c))
			return true;
		else return false;
case 18: if((b.charAt(6)==c && b.charAt(0)==c) || (b.charAt(20)==c && b.charAt(19)==c))
			return true;
		else return false;
case 19: if((b.charAt(16)==c && b.charAt(13)==c) || (b.charAt(18)==c && b.charAt(20)==c))
			return true;
		else return false;
case 20: if((b.charAt(18)==c && b.charAt(19)==c) || (b.charAt(11)==c && b.charAt(1)==c))
			return true;
		else return false;
		}
		return false;
	}
	
	// this function returns the position value of the corresponding board
	private int static_estimation(String b)
	{
		pos_evaluated++;
		int num_WhitePieces=0, num_BlackPieces=0;
		for(int i=0;i<b.length();i++)
		{
			if(b.charAt(i)=='W')
				num_WhitePieces++;
			else if(b.charAt(i)=='B')
				num_BlackPieces++;
		}
		return (num_WhitePieces-num_BlackPieces);
	}
	
	
}
