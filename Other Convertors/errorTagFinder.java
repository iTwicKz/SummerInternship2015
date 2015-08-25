import java.util.Scanner;
import java.io.*;

class errorTagFinder{
	public static void main(String[] args) throws IOException{

		File outFile = new File("outputErrorTags.txt");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("inputErrors.txt");

		Scanner input = new Scanner(originTag);

		while(input.hasNextLine()){
			String fullString = input.nextLine();

			boolean secondPeriod = false;
			for(int i = 0; i < fullString.length(); i++){
				if(fullString.charAt(i) == '.' && secondPeriod){
					fullString = fullString.substring(i+1);
					break;
				}
				else if(fullString.charAt(i) == '.'){
					secondPeriod = true;
				}
			}

			for(int i = 0; i < fullString.length(); i++){
				if(fullString.charAt(i) == 39){  //39 = ' in ASCII
					fullString = fullString.substring(0, i);
					break;
				}
			}

			System.out.println(fullString);
			pWriter.println(fullString);


		}
		pWriter.close();
	}

}
