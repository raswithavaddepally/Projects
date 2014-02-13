import java.text.DecimalFormat;
import java.util.*;
import java.io.*;

class binaryADA{
	
	public static String f=" ";
	public static double [] f_temp;
	public static double zeta = 1;
	class error{
		public double error;
		public int ind;
		public char dir;
	}
	public double[] iterate(double x[], int y[], double p[],int n){
		String s ;
		error min_error = calculate_error(y,p,n);
		double weak_class;
		if (min_error.ind == (n-1)){
		   weak_class = (x[min_error.ind]+ 1) ;
		   }
	   else
		weak_class = ((double)(x[min_error.ind]+ x[min_error.ind+1])/(double)2) ;
		System.out.println("The selected weak classifier, ht: " + weak_class);
		System.out.println("The error of ht: " + min_error.error );
		if (min_error.dir == 'N'){
			s = "<";
		 }
		 else
			s = ">"; 
			double alpha = ((double)1/(double)2)*Math.log((double)(1-min_error.error)/(double)min_error.error );
			System.out.println("The weight of ht: " + alpha);
			double sumpq = 0 ;
			int h =0;
			if ( min_error.dir == 'N')
			h=1;
			else
			h=-1;
			for (int i =0; i <n;i++){
			if (i <= min_error.ind){
				p[i]= (double)p[i]* (Math.pow(Math.E,(-1)*alpha*y[i]*h));  
			}
			else{
				p[i]= (double)p[i]* (Math.pow(Math.E,alpha*y[i]*h));  
			}
			sumpq= sumpq+p[i];
		}
		System.out.println("The probabilities normalization factor, Zt: "+ sumpq);
		System.out.println("The probabilities after normalization, pi: ");
		for (int i =0; i <n;i++){
			p[i] = round((double)p[i] / (double)sumpq);
			System.out.print( p[i] + "  ");
		}
		System.out.println();
		f = f + alpha + " " + "I (x < " + weak_class + ") + ";
		System.out.println ("The boosted classifier, ft: ");
		System.out.println (f);
		int errors = 0;
		for (int i=0 ; i < n ; i ++){
			if (min_error.dir  == 'N'){
			if (x[i] < weak_class)
			f_temp[i]= f_temp[i] + (alpha * 1) ;
			else 
				f_temp[i]= f_temp[i] + (alpha * -1) ;
			}
			else{
				if (x[i] > weak_class)
					f_temp[i]= f_temp[i] + (alpha * 1) ;
					else 
					f_temp[i]= f_temp[i] + (alpha * -1) ;	
			}
			if ((y[i] > 0 && f_temp[i] > 0)|| (y[i] < 0 && f_temp[i]< 0)   ){
				
			}
			else
				errors = errors + 1;
		}
		System.out.println ( "The error of the boosted classifer, Et: "+ (double)errors/(double)n);
		zeta =zeta * sumpq;
		System.out.println ("The bound on Et: " + zeta);
		return p;
	}
	public static double round(double d){
		DecimalFormat f = new DecimalFormat("#.#####");
		return Double.valueOf(f.format(d));
	}
	public error calculate_error(int y[], double p[], int n){
	  double error= 0;
	  int ind = 0;
	  double min_error = 1;
	  char dir = 'N';
	  double temp;
      for (int i=1 ;i < n; i++){
		  if (y[i] == 1){
			error = round(p[i] + error);
		  }	  
	  }
	  if (error > 0.5){
		  temp= 1-error;
		  if (temp < min_error){
			  min_error = temp;
			  dir = 'R';
		  }
	  }
	  else{
		  if (error < min_error){
			  min_error = error;
			  dir = 'N';
		  }
	  }
	  for (int i= 1; i <n ; i++){
		  if (y[i] == -1){
			  error = round(error + p[i]);
		  }
		  else{
			  error = round(error - p[i]);
		  }
		  if (error > 0.5){
			  temp= 1-error;
			  if (temp < min_error){
				  min_error = temp;
				  dir = 'R';
				  ind = i;
			  }
		  }
		  else{
			  if (error < min_error){
				  min_error = error;
				  dir = 'N';
				  ind = i;
			  }
		  }
	  }  
	  error e = new error();
	  e.ind = ind;
	  e.dir = dir;
	  e.error =min_error;
	  return e;
	}
	public static void main (String argv[]){
		Scanner inputfile = new Scanner(System.in);
		String datasetfile = inputfile.next();
		File file = new File(datasetfile);
        Scanner input;
		try {
			input = new Scanner(file);
			int T = input.nextInt();
			int n = input.nextInt();
			double eps = input.nextDouble();
			String line= input.nextLine();
			line= input.nextLine();
			Scanner linescan = new Scanner (line);
			double[] x = new double[n];
			int[] y = new int[n];
			double[] p = new double [n];
			int i=0;
			while (linescan.hasNext()){
				x[i]= linescan.nextDouble();
				i++;
			}
			linescan.close();
		   line = input.nextLine();
		   linescan = new Scanner (line);
		   i = 0;
		   while (linescan.hasNext()){
				y[i]= linescan.nextInt();
				i++;
			}
		   linescan.close();
		   line = input.nextLine();
		   linescan = new Scanner (line);
		   i = 0;
		   while (linescan.hasNext()){
				p[i]= linescan.nextDouble();
				i++;
			}
		   binaryADA b = new binaryADA();
		   f_temp = new double [n];
		   for (int k = 0; k< n ;k++)
			   f_temp[k] = 0;
		 for (int j =0; j < T ; j++){
			 System.out.println( "Iteration " + (j+1));
			 System.out.println( "*************************");
		 p =   b.iterate(x,y,p,n);	
		 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}