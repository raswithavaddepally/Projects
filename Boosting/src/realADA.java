import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
import java.lang.Math;
class realADA{
	public static String f =" ";
	public static double [] f_temp;
	public static double zeta = 1;
	class minimumg{
		public double probabilities[] = new double [4];
		public double g;
		public char dir;
		public int ind;
	}
	public double round3(double n){
		 n = Math.round(n*1000)/1000.0;
		 return n;
	}
	public double[] iterate(double x[], int y[], double p[],int n, double epsilon){
		minimumg m = calculate_probs(y,p,n);
		double weak_class;
		   if (m.ind == 0){
			   weak_class = (x[m.ind]- 1) ;
		   }
		   else
				weak_class = ((double)(x[m.ind]+ x[m.ind-1])/(double)2) ;
				System.out.println("The selected weak classifier, ht: " + weak_class);
				System.out.println ("The G error value of ht: " + m.g);
				double c1 ,c2;
				c1 = (1.0/2.0) * Math.log ((double)(m.probabilities[0]+epsilon) / (double)(m.probabilities[3]+epsilon) );
				c2 = (1.0/2.0) * Math.log ((double)(m.probabilities[2]+epsilon) / (double)(m.probabilities[1]+epsilon) );
				System.out.println("The weights ct+, ct-: " + c1 + "    " + c2);	
				double sum = 0;
				for ( int i = 0 ; i < n ;i++){
				if (i < m.ind){
					p[i] = p[i] * Math.pow(Math.E,(-1)* c1 *y[i]) ;
				}
				else
					p[i] = p[i] * Math.pow(Math.E,(-1)* c2 * y[i]);
					sum = sum +p[i];
			}
			System.out.println("The probabilities normalization factor, Zt: " + sum);
			System.out.println("The probabilities after normalization, Pi: "); 
			for ( int i = 0 ; i < n ;i++){
				p[i] = (double)p[i] /(double)sum;
				System.out.print( p[i]+ " ");
			}
			System.out.println();
			int errors = 0;
			System.out.println("The values ft(xi) for each one of the examples: ");
			for (int i=0 ; i < n ; i ++){
				if (x[i] < weak_class){
					f_temp[i] = f_temp[i] + c1 ;
				}
				else
					f_temp[i] = f_temp[i] + c2 ;
					System.out.print(f_temp[i] + "  ");
					if ((y[i] > 0 && f_temp[i] > 0)|| (y[i] < 0 && f_temp[i]< 0)   ){
					}
				else
				errors = errors + 1;
			}
			System.out.println();
			System.out.println ( "The error of the boosted classifer, Et: "+ (double)errors/(double)n);
			zeta = zeta * sum;
			System.out.println ("The bound on Et: " + zeta);	
			return p;
	}
	public static double round(double d){
		DecimalFormat f = new DecimalFormat("#.####");
		return Double.valueOf(f.format(d));
	}
	public minimumg calculate_probs(int y[], double p[], int n){
		double g,minimumg = Double.MAX_VALUE;
		char dir = 'N';
		minimumg m = new minimumg();	
		double ind;
		for (int i = 1;i<n;i++){
			double prob1=0,prob2=0,probw1=0,probw2=0;	  
			for (int j =0; j <n;j++){
			   if (j<i){
				   if (y[j] == -1 )
					   probw2 = probw2 + p[j];
				   else
					   prob1 = prob1 +p[j];
				}
				else{
				   if (y[j] == -1)
					   prob2 = prob2 +p[j];
				   else
					   probw1 = probw1 + p[j];
				}
			}
			g = Math.sqrt(prob1*probw2) + Math.sqrt(probw1*prob2);
			dir = 'N';
			if (g> 0.5){
				g = 1-g;
				double temp = prob1;
				prob1 = probw1;
				probw1 = temp;
				temp = prob2;
				prob2 = probw2;
				probw2 = temp;
				dir = 'R';
			}
			if (g< minimumg){
				minimumg = g;
				m.g = g;
				m.dir = dir;
				m.ind = i;
				m.probabilities[0]=prob1;
				m.probabilities[1]= prob2;
				m.probabilities[2]=probw1;
				m.probabilities[3] = probw2;
			}
		}
		return m;
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
			double epsilon = input.nextDouble();
			String line= input.nextLine();
			line= input.nextLine();
			Scanner linescan = new Scanner (line);
			double[] x = new double[n];
			int[] y = new int[n];
			f_temp = new double[n];
			for(int l=0;l<n;l++)
			f_temp[l] = 0;
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
			realADA r = new realADA();
			for (int j =0; j < T ; j++){
			   System.out.println( "Iteration " + (j+1));
				 System.out.println( "**********************************");
			p = r.iterate(x,y,p,n,epsilon);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}