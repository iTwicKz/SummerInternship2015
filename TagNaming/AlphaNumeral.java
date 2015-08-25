import java.util.Scanner;
import java.io.*;

class AlphaNumeralConvertor{
	public static void main(String[] args) throws IOException{

		File outFile = new File("outputAlphaNumeral.txt");
		FileWriter fWriter = new FileWriter(outFile);
		PrintWriter pWriter = new PrintWriter(fWriter);

		File originTag = new File("inputAlphaNumeral.txt");

		Scanner input = new Scanner(originTag);

		while(input.hasNextLine()){
			String fullString = input.nextLine();
			String registerStr = "";
			String numStr = "";

			for(int i = 0; i < fullString.length(); i++){
				if(fullString.charAt(i) == ','){
					registerStr = fullString.substring(0,i);
					numStr = fullString.substring(i+1,fullString.length());
					break;
				}
			}

			int type = Integer.parseInt(registerStr);
			String register;

			switch (type){
				case 8: register = "R";
						break;
				case 10: register = "AI";
						break;
				case 12: register = "AQ";
						break;
				case 70: register = "I";
						break;
				case 72: register = "Q";
						break;
				case 74: register = "T";
						break;
				case 76: register = "M";
						break;
				case 78: register = "SA";
						break;
				case 80: register = "SB";
						break;
				case 82: register = "SC";
						break;
				case 84: register = "S";
						break;
				case 86: register = "G";
						break;
				case 100: register = "EGD";
						break;
				default: register = "ERROR";
			}

			//System.out.println(register);
			//System.out.println("%" + register + numStr);



			pWriter.println("%" + register + numStr);


		}
		pWriter.close();
	}

}
