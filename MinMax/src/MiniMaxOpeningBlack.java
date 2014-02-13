import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;

//To generate the opening positions of black pieces using the minimax algorithm.
public class MiniMaxOpeningBlack {

	static LinkList L = new LinkList();			// List representing the minimax tree
	static int depth;							// to store the value of the depth passed as command line argument
	public static int pos_evaluated=0;	// to store the number of positions evaluated by the static estimation function
	public static void main(String[] args)
	{
		if(args.length!=3)						// if 3 arguments are not provided at the command line
		{
			System.out.println("Part 3 :MiniMaxOPeningBlack board1.txt board2.txt 2");
			System.exit(0);
		}
		else if(!args[0].equals("C:\\Users\\Rassu\\Desktop\\board6.txt"))	// if the name of the input file is not board1.txt
		{
			System.out.println("Part 3 :MiniMaxOPeningBlack board1.txt board2.txt depth(number)");
			System.exit(0);
		}
		else if(!args[1].equals("C:\\Users\\Rassu\\Desktop\\board6MMB2.txt"))	// if the name of the output file is not board2.txt
		{
			System.out.println("USAGE: java MiniMaxOPeningBlack board1.txt board2.txt depth(number)");
			System.exit(0);
		}
		depth=Integer.parseInt(args[2]);
		MiniMaxOpeningBlack mmob = new MiniMaxOpeningBlack();
		mmob.generateMovesOpening(args[0],args[1]);
		Link parent=L.first;
		int minimax_estimate=mmob.maxMin(parent,0);				// to compute the minimax estimate
		System.out.println("The output board positions are:");
		String final_pos=null;
		Link extra;
		extra=parent.LL.first;
		while(extra!=null)
		{
			if(extra.pos_estimate==minimax_estimate)
			{
				final_pos=extra.b_pos;
				break;
			}
			extra=extra.nextLink;
		}
		
		for(int i=0;i<final_pos.length();i++)
		{
			if(final_pos.charAt(i)=='W')
				final_pos=final_pos.substring(0, i)+'B'+final_pos.substring(i+1);
			else if(final_pos.charAt(i)=='B')
				final_pos=final_pos.substring(0, i)+'W'+final_pos.substring(i+1);	
		}
		System.out.println(final_pos);
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
		System.out.println("Please Check the board2.txt for the possible moves");
		}catch(Exception e){System.out.println("ERROR");}
		
	}
	
	private int maxMin(Link temp, int d)
	{
		if(d==depth) return static_estimation(temp.b_pos);	// if the current node is the leaf node
		else
		{
			temp.LL=generateAdd(temp.b_pos, temp.LL);
			
			int num_WhitePieces=0,num_BlackPieces=0;
			if(temp.LL.first==null)								// if the present node is leaf node, return it
			{
				return static_estimation(temp.b_pos);
			}
			
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
			}if(temp.LL.first==null)							// if the present node is leaf node, return it
			{
				return static_estimation(temp.b_pos);
			}
			
			d++;											// increase the current depth value	
			int v=-10000;
			int temp_minMax=0;
			Link extra;
			extra=temp.LL.first;
			//for each child y of x:
			while(extra!=null)
			{							
				temp_minMax=minMax(extra,d);				//v=max(v,minMax(y));
				extra.pos_estimate=temp_minMax;
				if(temp_minMax>v)
					v=temp_minMax;
				extra=extra.nextLink;		
			}
			return v;			
		}
	}
    private int minMax(Link temp, int d)
    {
    	if(d==depth) return static_estimation(temp.b_pos);
    	else
    	{
    		temp.LL=generateAdd_Black(temp.b_pos, temp.LL);
    		
    		int num_WhitePieces=0,num_BlackPieces=0;
			if(temp.LL.first==null)								// if the present node is leaf node, return it
			{
				return static_estimation(temp.b_pos);
			}
			
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
    		
    		d++;										// increase the depth
    		int v=10000;
    		int temp_maxMin=0;
    		Link extra;
			extra=temp.LL.first;
    		while(extra!=null)
    		{
    			temp_maxMin=maxMin(extra,d);
    			if(temp_maxMin<v)
    				v=temp_maxMin;
    			extra=extra.nextLink;
    		}
    		return v;
    	}
    }
	private void generateMovesOpening(String in_file, String out_file) {
		// TODO Auto-generated method stub
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
			System.out.println("The input board position is:");
			System.out.println (board);
			for(int i=0;i<board.length();i++)
			{
				if(board.charAt(i)=='W')
					board=board.substring(0, i)+'B'+board.substring(i+1);
				else if(board.charAt(i)=='B')
					board=board.substring(0, i)+'W'+board.substring(i+1);
			}
			L.insert(board);
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
