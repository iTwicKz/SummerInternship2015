import java.util.Scanner;
import java.io.*;

class XMLCheck{
	public static void main(String[] args) throws IOException{

		File outFile = new File("outputFileLOCATION.txt");
		FileWriter fWriter = new FileWriter(outFile);		//output writer setup
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("exportLOCATION.xml");		//input CSV from Historian Excel
		Scanner input = new Scanner(originTag);

		int falseOnes = 0;

		while(input.hasNextLine()){
			String wholeLine = input.nextLine();

			if(wholeLine.contains("<Property PropertyName=")){
				int j = 0;
				while(!(wholeLine.substring(j,j+5).equals("Value"))){
					j++;
				}
				if(wholeLine.contains("Value=\"false\"")){
					pWriter.print(" FALSE VALUE!!!!!!");
					falseOnes++;
				}
				pWriter.println(wholeLine.substring(32,j-2));

			}
		
		}
		pWriter.println("False Ones = " + falseOnes);
		pWriter.close();

	}

}
