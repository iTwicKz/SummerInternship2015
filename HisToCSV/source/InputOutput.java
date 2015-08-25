import java.util.Scanner;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class InputOutput extends JFrame{

	JLabel expLabel;

	JLabel label;
	JTextField tf;
	JButton button;
	JLabel label2;
	JTextField tf2;
	JButton button2;
	JLabel label3;
	JTextField tf3;
	JButton button3;

	JCheckBox checkBox;

	JButton executeButton;

	String outputFileName;
	String originTagFileName;
	String levelSetFileName;

	boolean openThatFile = false;

	private enum Actions{
		MANUCHOSEN,
		COMPOCHOSEN,
		SUPPCHOSEN,
		UTILITIESCHOSEN,

		LEVELSETACT,
		INPUTFILEACT,
		OUTPUTFILEACT,
		OPENFILEAFTER,
		EXECUTEACT
	}

	public InputOutput(){

		setLayout(new FlowLayout());

		JRadioButton fmoButton = new JRadioButton("MANU");
		fmoButton.setActionCommand(Actions.MANUCHOSEN.name());
		//fmoButton.setSelected(true);

		JRadioButton fcoButton = new JRadioButton("COMPO");
		fcoButton.setActionCommand(Actions.COMPOCHOSEN.name());

		JRadioButton scoButton = new JRadioButton("SUPP");
		scoButton.setActionCommand(Actions.SUPPCHOSEN.name());

		JRadioButton utilButton = new JRadioButton("Utilities  ");
		utilButton.setActionCommand(Actions.UTILITIESCHOSEN.name());

		ButtonGroup group = new ButtonGroup();
		group.add(fmoButton);
		group.add(fcoButton);
		group.add(scoButton);
		group.add(utilButton);

		expLabel = new JLabel("To convert your CSV (From the Historian Excel Add-In) for Provision Import, ");
		add(expLabel); 
		JLabel nextLine1 = new JLabel("                                                 insert the following information                                                 ");
		add(nextLine1); 


		label = new JLabel("  Level Set File        ");
		add(label);

		add(fmoButton);
		add(fcoButton);
		add(scoButton);				
		add(utilButton);		

		event e4 = new event();
		event e5 = new event();
		event e6 = new event();
		event e7 = new event();

		fmoButton.addActionListener(e4);
		fcoButton.addActionListener(e5);
		scoButton.addActionListener(e6);
		utilButton.addActionListener(e7);

//------------------------------------------------------------------------
/*
		expLabel = new JLabel("Go through the directory and select");
		add(expLabel); 
		JLabel nextLine1 = new JLabel("                                                 - MANU or COMPO Device                                                 ");
		add(nextLine1); 
		JLabel nextLine2 = new JLabel("                                                   - Input File                                                   ");
		add(nextLine2); 
		JLabel nextLine3 = new JLabel("                                                          - Output File                                                          ");
		add(nextLine3); 
*/
/*
		label = new JLabel("  Level Set File ");
		add(label);

		tf = new JTextField(28);
		add(tf);

		button = new JButton("Browse");
		add(button);

		event e = new event();
		button.setActionCommand(Actions.LEVELSETACT.name());
		button.addActionListener(e);
*/
//------------------------------------------------------------------------
		label2 = new JLabel("  Input File         ");
		add(label2);

		tf2 = new JTextField(28);
		add(tf2);

		button2 = new JButton("Browse");
		add(button2);

		event e2 = new event();
		button2.setActionCommand(Actions.INPUTFILEACT.name());
		button2.addActionListener(e2);

//------------------------------------------------------------------------
		label3 = new JLabel("  Output File      ");
		add(label3);

		tf3 = new JTextField(28);
		add(tf3);

		button3 = new JButton("Browse");
		add(button3);

		event e3 = new event();
		//tf3.setActionCommand(Actions.OUTPUTFILEACT.name());
		//tf3.addActionListener(e3);
		button3.setActionCommand(Actions.OUTPUTFILEACT.name());
		button3.addActionListener(e3);
//------------------------------------------------------------------------

		checkBox = new JCheckBox("Open File");
		checkBox.setActionCommand(Actions.OPENFILEAFTER.name());
		event e8 = new event();
		add(checkBox);
		checkBox.addActionListener(e8);

	}

	public class event implements ActionListener{
		public void actionPerformed(ActionEvent e){

			if(e.getActionCommand() == Actions.MANUCHOSEN.name()){
				levelSetFileName = "MANU";
			}

			else if(e.getActionCommand() == Actions.COMPOCHOSEN.name()){
				levelSetFileName = "COMPO";
			}			

			else if(e.getActionCommand() == Actions.SUPPCHOSEN.name()){
				levelSetFileName = "SUPP";
			}			

			else if(e.getActionCommand() == Actions.UTILITIESCHOSEN.name()){
				levelSetFileName = "Util";
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
					//String word = tf.getText();
				} catch(Exception ex){fileName = "error: event fail";}


				if(e.getActionCommand() == Actions.LEVELSETACT.name()){
					tf.setText(fileName);
					levelSetFileName = fileName;
				}
				else if(e.getActionCommand() == Actions.INPUTFILEACT.name()){
					if(!(fileName.substring(fileName.length()-4).equals(".csv"))){
						fileName = "ERROR: FILE MUST BE '.CSV'";
					}
					tf2.setText(fileName);
					originTagFileName = fileName;
				}
				else if(e.getActionCommand() == Actions.OUTPUTFILEACT.name()){
					if(!(fileName.substring(fileName.length()-4).equals(".xml")) && !(fileName.equals("No file was selected"))) fileName += ".xml";
					tf3.setText(fileName);
					outputFileName = fileName;
				}

				//temp = fileName;
				//tf.setText(fileName);
				//return fileName;
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
