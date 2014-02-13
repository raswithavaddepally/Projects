import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
//To generate the Midgame and Endgame positions using alpha-beta pruning
public class ABGame {
	static LinkList L = new LinkList();			// List representing the minimax tree
	static int depth;							// To store the value of the depth passed as command line argument
	public static int pos_evaluated=0;    // To store the number of positions evaluated by the static estimation function
	public static void main(String[] args)
	{
		// checking the arguments
		if(args.length!=3)						// if 3 arguments are not provided at the command line
		{
			System.out.println("Part 2:ABGame board3.txt board4.txt 2");
			System.exit(0);
		}
		else if(!args[0].equals("C:\\Users\\Rassu\\Desktop\\board5.txt"))  // if the name of the input file is not board3.txt
		{
			System.out.println("Part 2:ABGame board3.txt board4.txt 2");
			System.exit(0);
		}
		else if(!args[1].equals("C:\\Users\\Rassu\\Desktop\\board5AB5.txt"))  // if the name of the output file is not board4.txt
		{
			System.out.println("Part 2:ABGame board3.txt board5AB2.txt 2");
			System.exit(0);
		}
		depth=Integer.parseInt(args[2]);		
		ABGame abg = new ABGame();
		abg.generateMidgameEndgame(args[0],args[1]);
		Link parent=L.first;
		int minimax_estimate=abg.maxMin(-10000,parent,10000,0);	   // considering infinity to be 10000
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
		System.out.println("Position evaluated by static estimation: " + pos_evaluated);	// prints the number of positions evaluated
		System.out.println("MiniMax estimate: " + minimax_estimate);								// prints the minimax estimate value
		//to write the output board position into board4.txt
		try{
			Writer output = null;
			// Open the file that is the second 
			// command line parameter
			File file = new File(args[1]);
			output = new BufferedWriter(new FileWriter(file));
			output.write(final_pos + "\n");
			output.close();
			System.out.println("Check board2.txt for possible moves");
			}catch(Exception e){System.out.println("ERROR");}
	}
	
	private int maxMin(int alpha,Link temp,int beta, int d)
	{
		if(d==depth) return static_estimation(temp.b_pos);	// if the current node is the leaf node
		else
		{
			int num_WhitePieces=0,num_BlackPieces=0;
			for(int i=0;i<temp.b_pos.length();i++)
			{
				if(temp.b_pos.charAt(i)=='B')
					num_BlackPieces++;
				if(temp.b_pos.charAt(i)=='W')
					num_WhitePieces++;
			}
			if(num_WhitePieces==3)								
				temp.LL=generateHopping(temp.b_pos, temp.LL);	// if the number of white pieces is 3, then call generateHopping
			else
				temp.LL=generateMove(temp.b_pos, temp.LL);		// call to generateMove
			if(temp.LL.first==null || num_WhitePieces<=2 || num_BlackPieces<=2 )
			{
				return static_estimation(temp.b_pos);
			}
			// increase the current depth value			
			d++;
			int v=-10000;
			int temp_minMax=0;
			Link extra;
			extra=temp.LL.first;
			//for each child y of x:
			while(extra!=null)
			{
				temp_minMax=minMax(alpha,extra,beta,d);
				extra.pos_estimate=temp_minMax;
				if(temp_minMax>v)				//v=max(v,minMax(y));
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
    private int minMax(int alpha, Link temp, int beta, int d)
    {
    	if(d==depth) return static_estimation(temp.b_pos);		// if the current node is the leaf node
    	else
    	{
    		int num_BlackPieces=0,num_WhitePieces=0;
    		for(int i=0;i<temp.b_pos.length();i++)
			{
				if(temp.b_pos.charAt(i)=='W')
					num_WhitePieces++;
				if(temp.b_pos.charAt(i)=='B')
					num_BlackPieces++;
			}
			if(num_BlackPieces==3)								
				temp.LL=generateHopping_Black(temp.b_pos, temp.LL);
			else
				temp.LL=generateMove_Black(temp.b_pos, temp.LL);		
			if(temp.LL.first==null || num_WhitePieces<=2 || num_BlackPieces<=2)
			{
				return static_estimation(temp.b_pos);
			}
			d++;
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

	private void generateMidgameEndgame(String in_file, String out_file) {
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
			System.out.println("The input board position:");
			System.out.println (board);
			//Close the input stream
			in.close();
			fstream.close();			
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	// generate hopping movies of the white pieces
	public LinkList generateHopping(String board, LinkList LL1)
	{
		String b;
		LL1.first =null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i)=='W')
			{
				for(int j=0;j<board.length();j++)
				{
					if(board.charAt(j)=='x')
					{
						b=board;
						b=b.substring(0, i)+'x'+b.substring(i+1);
						b=b.substring(0, j)+'W'+b.substring(j+1);
						if(closeMill(j,b)) 
						{
							LL1=generateRemove(b, LL1);
						}
						else
						{
							LL1.insert(b);
						}
					}
				}
			}
		}
		return LL1;
	}
	
	// generate adjacent moves of the white pieces
	public LinkList generateMove(String board, LinkList LL1)
	{
		String b;
		int j;
		LL1.first =null;
		int[] n=null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i)=='W')
			{
				n=neighbors(i);
				for(int k=0;k<n.length;k++)
				{
					j=n[k];
					if(board.charAt(j)=='x')
					{
						b=board;
						b=b.substring(0, i)+'x'+b.substring(i+1);
						b=b.substring(0, j)+'W'+b.substring(j+1);
						if(closeMill(j,b))
						{
							LL1=generateRemove(b,LL1);
						}
						else
						{
							LL1.insert(b);
						}
					}
				}
			}
		}
		return LL1;
	}
	// to remove a black piece from the board
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

	// This function returns the neighbors of the current position in the board
	public int[] neighbors(int j)
	{
		int[] a=null;
		switch(j)
		{
		case 0:	a=new int[]{1,2,6};  
			return a;
		case 1: a=new int[]{11,0};
			return a;
		case 2: a=new int[]{0,3,7,4};
			return a;
		case 3: a=new int[]{10,2};
			return a;
		case 4: a=new int[]{2,8,5};
			return a;
		case 5: a=new int[]{4,9};
			return a;
		case 6: a=new int[]{0,7,18};
			return a;
		case 7: a=new int[]{2,6,8,15};
			return a;
		case 8: a=new int[]{4,7,12};
			return a;
		case 9: a=new int[]{5,10,14};
			return a;
		case 10:a=new int[]{3,9,11,17};
			return a;
		case 11:a=new int[]{1,10,20};
			return a;
		case 12:a=new int[]{8,13};
			return a;
		case 13:a=new int[]{12,14,16};
			return a;
		case 14:a=new int[]{9,13};
			return a;
		case 15:a=new int[]{7,16};
			return a;
		case 16:a=new int[]{13,15,17,19};
			return a;
		case 17:a=new int[]{16,10};
			return a;
		case 18:a=new int[]{6,19};
			return a;
		case 19:a=new int[]{16,18,20};
			return a;
		case 20:a=new int[]{11,19};
			return a;
		}
		return a;
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
	int static_estimation(String b)
	{
		int num_WhitePieces=0, num_BlackPieces=0, numBlackMoves=0;
		pos_evaluated++;
		LinkList SEL=new LinkList();
		for(int i=0;i<b.length();i++)
		{
			if(b.charAt(i)=='W')
				num_WhitePieces++;
			else if(b.charAt(i)=='B')
				num_BlackPieces++;
		}
		if(num_BlackPieces==3) SEL=generateHopping_Black(b,SEL);
		else SEL=generateMove_Black(b,SEL);
		Link temp=SEL.first;
		while(temp!=null)
		{
			numBlackMoves++;
			temp=temp.nextLink;
		}
		if(num_BlackPieces<=2) return(10000);
		else if(num_WhitePieces<=2) return(-10000);
		else if(numBlackMoves==0) return(10000);
		else return(1000*(num_WhitePieces-num_BlackPieces)-numBlackMoves);
	}
	
	// to generate hopping moves for black pieces
	public LinkList generateHopping_Black(String board, LinkList LL1)
	{
		String b;
		LL1.first =null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i)=='B')
			{
				for(int j=0;j<board.length();j++)
				{
					if(board.charAt(j)=='x')
					{
						b=board;
						b=b.substring(0, i)+'x'+b.substring(i+1);
						b=b.substring(0, j)+'B'+b.substring(j+1);
						if(closeMill(j,b)) 
						{
							LL1=generateRemove_Black(b, LL1);
						}
						else
						{
							LL1.insert(b);
						}
					}
				}
			}
		}
		return LL1;
	}

	// to generate adjacent moves of the black pieces
	public LinkList generateMove_Black(String board, LinkList LL1)
	{
		String b;
		int j;
		LL1.first =null;
		int[] n=null;
		for(int i=0;i<board.length();i++)
		{
			if(board.charAt(i)=='B')
			{
				n=neighbors(i);
				for(int k=0;k<n.length;k++)
				{
					j=n[k];
					if(board.charAt(j)=='x')
					{
						b=board;
						b=b.substring(0, i)+'x'+b.substring(i+1);
						b=b.substring(0, j)+'B'+b.substring(j+1);
						if(closeMill(j,b))
						{
							LL1=generateRemove_Black(b,LL1);
						}
						else
						{
							LL1.insert(b);
						}
					}
				}
			}
		}
		return LL1;
	}
	// to remove a white piece from the board
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
}

