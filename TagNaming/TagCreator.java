import java.util.Scanner;
import java.io.*;

class TagCreator{
	public static void main(String[] args) throws IOException{

		File outFile = new File("outputTagInfo.txt");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("inputTagInfo.txt");

		Scanner input = new Scanner(originTag);

		while(input.hasNextLine()){
			String fullString = input.nextLine();

			fullString = fullString.replaceAll(" ", "_").toUpperCase();

			String building = "";
			String machine = "";
			String tagLine = "";

			boolean firstComma = true;
			int locComma = 0;

			for(int i = 0; i < fullString.length(); i++){
				if(fullString.charAt(i) == ',' && firstComma){
					building = fullString.substring(0,i);
					locComma = i+1;
					firstComma = false;
				}
				else if(fullString.charAt(i) == ','){
					machine = fullString.substring(locComma, i);
					tagLine = fullString.substring(i+1,fullString.length());
					break;
				}
			}

			//System.out.println(tagLine);

			for(int i = 0; i < tagLine.length(); i++){
				if(tagLine.charAt(i) <= 90 && tagLine.charAt(i) >= 65){
						tagLine = tagLine.substring(i);
						//System.out.println(i);
						break;
				}
			}

			//System.out.println(tagLine);
			System.out.println(building + "_" + machine + "_" + tagLine);

			pWriter.println(building + "_" + machine + "_" + tagLine);


		}
		pWriter.close();
	}

}
