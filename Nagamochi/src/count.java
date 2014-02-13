import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;


public class count {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		int[] noOfFailedCasesCovered = new int[1590];
		int[] noOfFailedCasesNotCovered = new int[1590];
		int[] noOfSuccessCasesCovered = new int[1590];
		int[] noOfSuccessCasesNotCovered = new int[1590];
		int[] lineNumber = new int[1590];
		
		
		try
		{
			File directory = new File(args[0]);
			File[] myarray = new File[directory.listFiles().length];
			
			myarray = directory.listFiles();
			
			
			
			int[][] failedCoverageArray = new int[1590][myarray.length];
			
			
			for(int j = 0; j< myarray.length; j++)
			{
				File path = myarray[j];
				
				FileReader freader = new FileReader(path);
				  
				BufferedReader br = new BufferedReader(freader);
				String strLine;
				br.readLine();
			  
				int line =0;
								
				while ((strLine = br.readLine()) != null)   
				{
					String[] splitline = strLine.split("       ");
					
					if(j ==0)
					{
						lineNumber[line] = Integer.parseInt(splitline[0]);
					}
					
					failedCoverageArray[line][j] = Integer.parseInt(splitline[1]);
					line++;
				}
			 // System.out.println("lines:" + line);
			  br.close();
				
			}
			
			for(int i=0; i< lineNumber.length; i++)
			{
				for(int j=0 ; j< myarray.length; j++){
				//System.out.println(lineNumber[i] + "\t" + coverageArray[i][1]);
				if(failedCoverageArray[i][j] == 0)
					noOfFailedCasesNotCovered[i]++;
				else if(failedCoverageArray[i][j] == 1)
					noOfFailedCasesCovered[i]++;
				}
			}

			
//			for(int i=0; i< lineNumber.length; i++)
//			{
//				System.out.print(lineNumber[i] + " ");
//				for(int j=0 ; j< myarray.length; j++)
//				{
//					System.out.print(coverageArray[i][j] + "  ");
//				}
//				System.out.println("");
//			}
			
//			System.out.println("Statement \t Covered \t Not Covered");
//			for(int i=0; i< lineNumber.length; i++)
//			{				
//				 System.out.println(lineNumber[i] + " \t\t " + noOfFailedCasesCovered[i] + " \t\t " + noOfFailedCasesNotCovered[i]);				
//			}
			
		}
		catch(FileNotFoundException fe)
		{
			fe.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		
		try
		{
			File directory = new File(args[1]);      
			File[] myarraySuccess = new File[directory.listFiles().length];
			
			myarraySuccess = directory.listFiles();
			
			
			int[][] successCoverageArray = new int[1590][myarraySuccess.length];
			
			for(int j = 0; j< myarraySuccess.length; j++)
			{
				File path = myarraySuccess[j];
				
				FileReader freader = new FileReader(path);
				  
				BufferedReader br = new BufferedReader(freader);
				String strLine;
				br.readLine();
			  
				int line =0;
								
				while ((strLine = br.readLine()) != null)   
				{
					String[] splitline = strLine.split("       ");
									
					successCoverageArray[line][j] = Integer.parseInt(splitline[1]);
					line++;
				}
			 // System.out.println("lines:" + line);
			  br.close();
				
			}
			
			for(int i=0; i< lineNumber.length; i++)
			{
				for(int j=0 ; j< myarraySuccess.length; j++){
				//System.out.println(lineNumber[i] + "\t" + coverageArray[i][1]);
				if(successCoverageArray[i][j] == 0)
					noOfSuccessCasesNotCovered[i]++;
				else if(successCoverageArray[i][j] == 1)
					noOfSuccessCasesCovered[i]++;
				}
			}

			
//			for(int i=0; i< lineNumber.length; i++)
//			{
//				System.out.print(lineNumber[i] + " ");
//				for(int j=0 ; j< myarray.length; j++)
//				{
//					System.out.print(coverageArray[i][j] + "  ");
//				}
//				System.out.println("");
//			}
			
			
			
		}
		catch(FileNotFoundException fe)
		{
			fe.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		try
		{
		File file = new File("result.xls");
		int filenum=0;
		while (file.exists()) {
			file = new File("result"+filenum+".xls");
			filenum++;
			
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		String content = "Statement \t alpha \t beta \t gamma \t eta \t ae+1/(1+bg) \t a2e/(1+bg) \t e^a.e^n+e^-b.e^-g \t e^((a+e)/(1+b+g)) \t e^(a.e).e \n";
		bw.write(content);
		System.out.println("Statement \t alpha \t beta \t gamma \t eta \t ae+1/(1+bg) \t a2e/(1+bg) \t e^a.e^n+e^-b.e^-g \t e^((a+e)/(1+b+g)) \t e^(a.e).e ");
		for(int i=0; i< lineNumber.length; i++)
		{	
			double alpha, beta, gamma, eta;
			double suspicionness1 = 0, suspicionness2 =0;
			String suspicionness5;
			String suspicionness4;
			String suspicionness3;

			alpha = noOfFailedCasesCovered[i];
			beta = noOfSuccessCasesCovered[i];
			gamma = noOfFailedCasesNotCovered[i];
			eta = noOfSuccessCasesNotCovered[i];
			DecimalFormat df = new DecimalFormat("#.##");
		
			suspicionness1 = (alpha*eta) + (1/(beta*gamma+1));
			suspicionness2 = (alpha*alpha*eta)/ (1+(beta*gamma));
			suspicionness3 = df.format((Math.pow(Math.E, alpha)* Math.pow(Math.E, eta)) + ( Math.pow(Math.E, -beta)*Math.pow(Math.E, -gamma)));
			suspicionness4 = df.format(Math.pow(Math.E, ((alpha+eta)/(beta+gamma+1))));
			suspicionness5 = df.format(Math.pow(Math.E, (alpha*eta))*eta);
			
			content = lineNumber[i] + " \t " + alpha + " \t " + beta + "\t " + gamma + "\t" + eta + "\t" + suspicionness1 +"\t" + suspicionness2 +"\t" +suspicionness3 +"\t"+ suspicionness4 + "\t" +suspicionness5 +"\n";
			bw.write(content);
			System.out.println(lineNumber[i] + " \t " + alpha + " \t " + beta + "\t " + gamma + "\t" + eta + "\t" + suspicionness1 +"\t" + suspicionness2 +"\t" +suspicionness3 +"\t"+ suspicionness4 + "\t" +suspicionness5);				
		}
		bw.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
}