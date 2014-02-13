import java.io.*;
import java.util.*;
class id3{
	
	class result{
		public double weightedgain;
		public int coltosplit;
		public ArrayList<ArrayList<Integer>> splits;
		 
		}
	
	public static result entropyofasplit(int[] split, int[][] orgtable, int numofcols ){
		
		id3 m = new id3();
		result res = m.new result();
		double mainentropy =0 ;
		int numofsplitvals=0;
		double gain=0, maxgain=0;
		Map<Integer,Integer> map= new HashMap<Integer, Integer>();
		int i = 0;
		
		while(split[i] !=0){
			int key= orgtable[split[i]-1][numofcols-1];
		  if (map.containsKey(key)){
			 
			 map.put(key, map.get(key)+1);
		  }
		  else{
			  map.put(key,1);
		  }
		  i++;
		}
		numofsplitvals = i;
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			double prob = (double)((Integer)pairs.getValue()/(float)i);
			mainentropy =  (mainentropy + prob *((Math.log(1/prob))/(Math.log(2))));
		}
		
		for (int j=0 ; j< numofcols-1 ; j++){
			ArrayList<ArrayList<Integer>> splits = new ArrayList<ArrayList<Integer>>();
			int k = 0;
			Map<Integer, ArrayList<Integer>> columnmap = new HashMap<Integer, ArrayList<Integer>>();
			while(split[k] !=0 ){
				int key = orgtable[split[k]-1][j];
				
				if (columnmap.containsKey(key)){
					 
					columnmap.get(key).add(split[k]);
				  }
				  else{
					  columnmap.put(key,new ArrayList<Integer>());
					  columnmap.get(key).add(split[k]);
				  }
				k++;
			}
			
			Iterator columnitr = columnmap.entrySet().iterator();
			double entropyforacolumn=0;
			
			while(columnitr.hasNext()){
				Map.Entry<Integer, List<Integer>> pairs = (Map.Entry<Integer, List<Integer>>)columnitr.next();
				ArrayList<Integer> al = (ArrayList<Integer>) pairs.getValue();
			   int []columnentropy = new int[al.size()];
			   splits.add(al);
			   columnentropy = convertIntegers(al);
			   double colentropy =  columnentropy(columnentropy, orgtable, numofcols);
			   entropyforacolumn = entropyforacolumn + (al.size()/(i*1.0)) * colentropy; 
			}
			
			gain = mainentropy - entropyforacolumn;
			if (gain > maxgain){
				maxgain = gain;
				res.coltosplit = j;
				res.splits = splits;
			}
		}
		
		res.weightedgain = (numofsplitvals/(orgtable.length/1.0)) * maxgain ;
		return res;
		
	}
	
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	public static double columnentropy(int[] split, int[][] orgtable, int numofcols){
		
		double mainentropy =0 ;;
		Map<Integer,Integer> map= new HashMap<Integer,Integer>();
		int i = 0;
		while(split.length > i){
			int key= orgtable[split[i]-1][numofcols-1];
			
		  if (map.containsKey(key)){
			 
			 map.put(key, map.get(key)+1);
		  }
		  else{
			  map.put(key,1);
		  }
		  i++;
		}
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pairs = (Map.Entry)it.next();
			double prob = (double)((Integer)pairs.getValue()/(float)i);
			mainentropy =  (mainentropy + prob *((Math.log(1/prob))/(Math.log(2))));
		}
		return mainentropy;
		
	}
	
	
	
	public static void main (String argv[]){
		
		System.out.println("Enter names of the files dataset input-partition output-partition:");
		Scanner inputcommands = new Scanner(System.in);
		String datasetfile = inputcommands.next();
		String inputpartition = inputcommands.next();
		String outputpartition = inputcommands.next();
		//System.out.println(datasetfile);
	  File file = new File(datasetfile);
	  int numoflines=0;
	  int numofcols =0;
	  int coltosplit=0;
	  int partitionselected = 0;
	  ArrayList<ArrayList<Integer>> splits = new ArrayList<ArrayList<Integer>>() ;
	  double maxweightedgain=0;
	  id3 m = new id3();
	  result res = m.new result();
	  int input[][] = null ;
	  try {
		Scanner s = new Scanner(file);
		
		while (s.hasNextLine()){
			 numoflines = s.nextInt();
			 numofcols = s.nextInt();
			 input =new int[numoflines][numofcols];
			s.nextLine();
			for (int i=0; i< numoflines;i++){
				for (int j=0; j< numofcols;j++){
					input[i][j] = s.nextInt();
					
				}
				
			}
					
			}
		s.close();
		
	  }
	  catch(FileNotFoundException e) {
          e.printStackTrace();
      }
	  try{
		  File p = new File(inputpartition);
		  Scanner t = new Scanner(p);
		  String token[] = new String[numoflines];
		  String lines[]= new String[numoflines];
		  int i =0;
		  while(t.hasNextLine()){
			  int partition[] = new int [numoflines+1];
			  String line = t.nextLine();
			  lines[i]=line;
			
			  Scanner lineScanner = new Scanner(line);
			  token[i] = lineScanner.next();
			  int j = 0;
			  while (lineScanner.hasNext()) {
			   partition[j] = lineScanner.nextInt();
			   j++;
			  }
			  res  = entropyofasplit(partition, input,numofcols);
			  if (res.weightedgain > maxweightedgain){
				  maxweightedgain = res.weightedgain;
				  coltosplit = res.coltosplit;
				  splits = res.splits;
				  partitionselected = i;
			  }
			  lineScanner.close();
			  i++;
			  
		  }
		  int k = 0;
		  File writefile = new File(outputpartition);

		try {
			FileWriter fileWriter = new FileWriter(writefile);
			 BufferedWriter bw = new BufferedWriter(fileWriter);
			 
			 
			  while (lines[k] != null){
				  if (k==partitionselected){
					  int l=1;
					  System.out.print("Partition " + token[k] +" was replaced with partitions ");
					  for(ArrayList<Integer> s : splits){
						  bw.write(token[k]+l );
						  System.out.print( token[k]+l + " ");
						  for(Integer num: s){
							  bw.write(" " + num );
						  }
						  bw.write("\n");
						  l++;
					  }
					  System.out.print( "using Feature " + (coltosplit+1));
				  }
				  else{
					  bw.write(lines[k]+"\n");
				  }
				  k++;
			  }
			  bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		  
	  }
	  catch(FileNotFoundException e) {
          e.printStackTrace();
      }
   }
	

} 