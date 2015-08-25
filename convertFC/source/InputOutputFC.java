import java.util.Scanner;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class InputOutput extends JFrame{

	JLabel expLabel;

	JLabel labelInputFile;
	JTextField tfInputFile;
	JButton buttonInputFile;

	JLabel labelArea;
	JTextField tfArea;

	JLabel labelOutputTags;
	JTextField tfOutputTags;
	JButton buttonOutputTags;

	JLabel labelOutputData;
	JTextField tfOutputData;
	JButton buttonOutputData;

	JLabel labelValueRow;
	JTextField tfValueRow; // look into making formatted

	JCheckBox checkBox;

	JButton executeButton;

	String location;
	String inputFile;
	String outputData;
	String outputTags;

	boolean openThatFile = false;

	private enum Actions{
		INPUTBROWSE,
		OUTPUTDATABROWSE,
		OUTPUTTAGSBROWSE,
		EXECUTEACT,

		MANUCHOSEN,
		COMPOCHOSEN,
		SUPPCHOSEN,
		EXTCHOSEN,
		UTILITIESCHOSEN,
		OPENFILEAFTER
	}

	public InputOutput(){

		setLayout(new FlowLayout());

		JRadioButton fmoButton = new JRadioButton("MANU");
		fmoButton.setActionCommand(Actions.MANUCHOSEN.name());

		JRadioButton fcoButton = new JRadioButton("COMPO");
		fcoButton.setActionCommand(Actions.COMPOCHOSEN.name());

		JRadioButton scoButton = new JRadioButton("SUPP");
		scoButton.setActionCommand(Actions.SUPPCHOSEN.name());

		JRadioButton ehsButton = new JRadioButton("EXT");
		ehsButton.setActionCommand(Actions.EXTCHOSEN.name());

		JRadioButton utilButton = new JRadioButton("Utilities  ");
		utilButton.setActionCommand(Actions.UTILITIESCHOSEN.name());

		ButtonGroup group = new ButtonGroup();
		group.add(fmoButton);
		group.add(fcoButton);
		group.add(scoButton);
		group.add(ehsButton);
		group.add(utilButton);

		expLabel = new JLabel("                                  To convert your CSV for Historian File Collector, insert the following information                                  ");
		add(expLabel); 

		JLabel label = new JLabel("   Location  ");
		add(label);

		add(fmoButton);
		add(fcoButton);
		add(scoButton);	
		add(ehsButton);			
		add(utilButton);

		event e4 = new event();
		event e5 = new event();
		event e6 = new event();
		event e7 = new event();
		event e8 = new event();

		fmoButton.addActionListener(e4);
		fcoButton.addActionListener(e5);
		scoButton.addActionListener(e6);
		ehsButton.addActionListener(e7);
		utilButton.addActionListener(e8);
	
	//------------------------------------------------------------------------

		labelArea = new JLabel("  | Area  ");
		add(labelArea, "span 3");

		tfArea = new JTextField(10);
		add(tfArea);


		JSeparator separator = new JSeparator();
		add(separator);


	//------------------------------------------------------------------------
	
		labelValueRow = new JLabel("Column of Values  ");
		add(labelValueRow);
		tfValueRow = new JTextField(4);
		add(tfValueRow);

	//------------------------------------------------------------------------

		labelInputFile = new JLabel("     |        Input File  ");
		add(labelInputFile);

		tfInputFile = new JTextField(15);
		add(tfInputFile);

		buttonInputFile = new JButton("Browse");
		add(buttonInputFile);

		event e2 = new event();
		buttonInputFile.setActionCommand(Actions.INPUTBROWSE.name());
		buttonInputFile.addActionListener(e2);

	//------------------------------------------------------------------------

		labelOutputTags = new JLabel(" Output Tags File      ");
		add(labelOutputTags);

		tfOutputTags = new JTextField(20);
		add(tfOutputTags);

		buttonOutputTags = new JButton("Browse");
		add(buttonOutputTags);

		event e3 = new event();
		//tf3.setActionCommand(Actions.OUTPUTFILEACT.name());
		//tf3.addActionListener(e3);
		buttonOutputTags.setActionCommand(Actions.OUTPUTTAGSBROWSE.name());
		buttonOutputTags.addActionListener(e3);

	//------------------------------------------------------------------------
		/*

		labelOutputData = new JLabel("                Output Data File      ");
		add(labelOutputData);

		tfOutputData = new JTextField(28);
		add(tfOutputData);

		buttonOutputData = new JButton("Browse");
		add(buttonOutputData);

		event e20 = new event();
		//tf3.setActionCommand(Actions.OUTPUTFILEACT.name());
		//tf3.addActionListener(e3);
		buttonOutputData.setActionCommand(Actions.OUTPUTDATABROWSE.name());
		buttonOutputData.addActionListener(e20);

		*/

	//------------------------------------------------------------------------
		
		checkBox = new JCheckBox("Open File");
		checkBox.setActionCommand(Actions.OPENFILEAFTER.name());
		event e9 = new event();
		add(checkBox);
		checkBox.addActionListener(e9);

	}

	public class event implements ActionListener{
		public void actionPerformed(ActionEvent e){

			if(e.getActionCommand() == Actions.MANUCHOSEN.name()){
				location = "MANU";
			}

			else if(e.getActionCommand() == Actions.COMPOCHOSEN.name()){
				location = "COMPO";
			}			

			else if(e.getActionCommand() == Actions.SUPPCHOSEN.name()){
				location = "SUPP";
			}			

			else if(e.getActionCommand() == Actions.EXTCHOSEN.name()){
				location = "EXT";
			}

			else if(e.getActionCommand() == Actions.UTILITIESCHOSEN.name()){
				location = "UTIL";
			}
			else if(e.getActionCommand() == Actions.OPENFILEAFTER.name()){
				if(checkBox.isSelected()){
					openThatFile = true;
				}
				else{
					openThatFile = false;
				}
			}

			else{
				OpenFile of = new OpenFile();

				String fileName;
				try{
					of.PickMe();
					fileName = of.sb;
				} catch(Exception ex){fileName = "error: event fail";}

				if(e.getActionCommand() == Actions.INPUTBROWSE.name()){
					if(!(fileName.substring(fileName.length()-4).equals(".csv"))){
						fileName = "ERROR: FILE MUST BE '.CSV'";
					}
					tfInputFile.setText(fileName);
					inputFile = fileName;
				}
				else if(e.getActionCommand() == Actions.OUTPUTDATABROWSE.name()){
					if(!(fileName.substring(fileName.length()-4).equals(".csv")) && !(fileName.equals("No file was selected"))) fileName += ".csv";
					tfOutputData.setText(fileName);
					outputData = fileName;
				}
				else if(e.getActionCommand() == Actions.OUTPUTTAGSBROWSE.name()){
					if(!(fileName.substring(fileName.length()-4).equals(".csv")) && !(fileName.equals("No file was selected"))) fileName += ".csv";
					tfOutputTags.setText(fileName);
					outputTags = fileName;
				}
			}
		}
	}
}

class OpenFile{

	JFileChooser fileChooser = new JFileChooser();
	String sb = "";

	public void PickMe() throws Exception{

		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){

			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			sb = fileChooser.getSelectedFile().getAbsolutePath();
			//File file = fileChooser.getSelectedFile();
			//sb = file.getInitialDirectory();
		}
		else{
			sb = "No file was selected";
		}
	}
}






