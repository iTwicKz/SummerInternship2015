import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Tags{
	String tagName;
	int tagNumber;

	public Tags(String name, int number){
		tagName = name;
		tagNumber = number;
	}
}

class convertFC{
	public static void main(String[] args) throws IOException{

		InputOutput gui = new InputOutput();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(650,200);
		gui.setTitle("CSV to Historian File Collector");
		gui.setVisible(true);
		gui.setResizable(false);

		JButton executeButton = new JButton("Convert");
		gui.add(executeButton);
		
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				try{
					String outputFileName = gui.tfOutputTags.getText();
					File outFile = new File(outputFileName);
					FileWriter fWriter = new FileWriter(outFile);		//output writer setup
					PrintWriter pWriter = new PrintWriter(fWriter);

					//String outputDataFileName = gui.tfOutputData.getText();
					//File outDataFile = new File(outputDataFileName);
					//FileWriter fDataWriter = new FileWriter(outDataFile);
					//PrintWriter pDataWriter = new PrintWriter(fDataWriter);

					String originTagFileName = gui.tfInputFile.getText();
					File originTag = new File(originTagFileName);		//input CSV from Historian Excel
					Scanner input = new Scanner(originTag);

					boolean firstLine = true;
					boolean firstTag = true;

					String location = gui.location;
					String area = gui.tfArea.getText();

					ArrayList<Tags> allTags = new ArrayList<Tags>();
					ArrayList<String> allData = new ArrayList<String>();

					pWriter.println("[Tags]");					//NEW TAGS ONLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					pWriter.println("Tagname,Description,DataType,HiEngineeringUnits,LoEngineeringUnits");

					
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					while(input.hasNextLine()){

						String wholeLine = input.nextLine();

						if(firstLine){
							// ADD!!: check if it has the right text
							firstLine = false;
							continue;
						}
						String[] lineCont = wholeLine.split(",");
						String nameOTag = lineCont[3];
						int numOTag = Integer.parseInt(lineCont[4]);

						boolean repeat = false;

						//allTags.add( new Tags(nameOTag, numOTag));
						/*
						if(firstTag){
							pWriter.println(nameOTag + "," + nameOTag + ": #" + numOTag + ",SingleFloat,35000,0");
							allTags.add( new Tags(nameOTag, numOTag));
							firstTag = false;
							continue;
						}
						*/

						//System.out.println(allTags.size());

						for(int i = 0; i < allTags.size(); i++){
							
							if(allTags.get(i).tagNumber == numOTag){
								
								//System.out.println(allTags.get(i).tagNumber + "=" + numOTag);
								repeat = true;
								break;
							}
						}

						//------------------------------------------------
						nameOTag = location.toUpperCase() + "_" + area.toUpperCase() + "_" + nameOTag.toUpperCase();
						nameOTag = nameOTag.replace(" ", "_");
						nameOTag = nameOTag.replace(".", "");
						nameOTag = nameOTag.replace("#", "_");
						nameOTag = nameOTag.replace("/", "_");
						nameOTag = nameOTag.replace("(", "");
						nameOTag = nameOTag.replace(")", "");
						nameOTag = nameOTag.replace("&", "_");
						nameOTag = nameOTag.replace("?", "_");
						nameOTag = nameOTag.replace("@", "_AT_");
						nameOTag = nameOTag.replace("-", "_");
						nameOTag = nameOTag.replace("____", "_");
						nameOTag = nameOTag.replace("___", "_");
						nameOTag = nameOTag.replace("__", "_");
						//------------------------------------------------

						if(!(repeat)){
							//TAG CREATION
							pWriter.println(nameOTag + "," + nameOTag + ": #" + numOTag + ",SingleFloat,35000,0");
							System.out.println(nameOTag + "," + numOTag);
							allTags.add( new Tags(nameOTag, numOTag));
						}

						String quality = "Good";
						if(wholeLine.charAt(wholeLine.length() - 1) == ','){
							quality = "Bad";
							continue;
						}
						//+++++++++++++++++++++++++++++++++++
						int outputRow = Integer.parseInt(gui.tfValueRow.getText()); //++++++++++++++++
						//+++++++++++++++++++++++++++++++++++

						//String data1 = nameOTag + "," + lineCont[1] + "," + lineCont[outputRow] + ",Good";
						//String data2 = nameOTag + "," + lineCont[2] + "," + lineCont[outputRow] + ",Good"
						allData.add(nameOTag + "," + lineCont[1] + "," + lineCont[outputRow] + ",Good");
						allData.add(nameOTag + "," + lineCont[2] + "," + lineCont[outputRow] + ",Good");
						/*
						switch(quality){
							case "Good":	pDataWriter.println(nameOTag + "," + lineCont[1] + "," + lineCont[6] + ",Good");
											///pDataWriter.println(nameOTag + "," + lineCont[2] + "," + lineCont[6] + ",Good");
											break;
							case "Bad":		pDataWriter.println(nameOTag + "," + lineCont[1] + ",,Bad");
											pDataWriter.println(nameOTag + "," + lineCont[2] + ",,Bad");
											break;
						}
						*/
						//pDataWriter.println(nameOTag + "," + lineCont[1] + "," + lineCont[6] + ",Good");
						//pDataWriter.println(nameOTag + "," + lineCont[2] + "," + lineCont[6] + ",Good");

						

					}

					pWriter.println("[Data]");
					pWriter.println("Tagname,TimeStamp,Value,DataQuality");

					for(int i = 0; i < allData.size(); i++){
						pWriter.println(allData.get(i));
					}

					pWriter.close();
					//pDataWriter.close();

					if(gui.openThatFile){
							Desktop.getDesktop().open(outFile);
							//Desktop.getDesktop().open(outDataFile);
					}


		/*
					for(int i = 0; i < allTags.size(); i++){
						pWriter.println(allTags.get(i).tagName + "," + allTags.get(i).tagNumber);
					}
		*/
				} catch(IOException exc){exc.printStackTrace();}


			}
		});

			

			
	}
}
