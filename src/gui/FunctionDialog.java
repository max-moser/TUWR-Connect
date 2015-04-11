package gui;

import handler.FnctDialogHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Panel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import etc.FixPoint;

public class FunctionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8689543601357688249L;
	
	//private HashMap<String,FixPoint> param;
	private List<String> params;
	private List<Label> labels;
	private List<JTextField> input;
	private JButton accept;
	private String fnct;
	
	/**
	 * Creates a new dialog with the defined amount of parameters.
	 * 
	 * @param gui the main gui of the program - needed for unblocking
	 * @param fnct the name of the function
	 * @param params list of parameter-names
	 */
	public FunctionDialog(GUI gui, String fnct, List<String> params){
		
		this.fnct = fnct;
		this.params = params;
		this.setTitle(this.fnct);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		String path = new File("").getAbsolutePath();
		if(System.getProperty("os.name").contains("Windows") ||
				System.getProperty("os.name").contains("Microsoft")){
			this.setIconImage(new ImageIcon(path+"\\"+"resources"+"\\"+"icon.png").getImage());
		}else{
			this.setIconImage(new ImageIcon(path+"/resources/icon.png").getImage());
		}
		labels = new ArrayList<Label>();
		input = new ArrayList<JTextField>();
		
		/* init components */
		initcomponents();
		this.setLocationRelativeTo(null);
		
		/* set listener*/
		accept.addActionListener(new FnctDialogHandler(this));
		
	}
	
	/**
	 * initializes all components, apart from this window itself.
	 * !only for internal use
	 */
	private void initcomponents(){
		
		Panel inputpanel = new Panel();
		BoxLayout bl = new BoxLayout(inputpanel, BoxLayout.Y_AXIS);
		inputpanel.setLayout(bl);
		
		for(String param : params){
			
			Label newlabel = new Label();
			newlabel.setText(param+ ":");
			JTextField newinput = new JTextField();
			Dimension size = new Dimension(200, 30);
			newinput.setPreferredSize(size);
			newinput.setSize(size);
			this.add(newlabel);
			this.add(newinput);
			labels.add(newlabel);
			input.add(newinput);
			
			Panel temp = new Panel();
			temp.add("West",newlabel);
			temp.add("East",newinput);
			
			inputpanel.add(temp);
			
		}
		accept = new JButton();
		accept.setText("send command");
		Panel bpanel = new Panel();
		bpanel.add(accept);
		
		this.getContentPane().add(inputpanel);
		this.getContentPane().add(bpanel, BorderLayout.PAGE_END);
		
		pack();
		
	}
	
	/**
	 * Returns a HashMap containing all changed parameters.
	 * Parameters which have been left empty will be ignored.
	 * 
	 * @return mapping of the changed parameters
	 */
	public HashMap<String,FixPoint> getParameters(){
		HashMap<String,FixPoint> ret = new HashMap<String,FixPoint>();
		for(int i=0; i<params.size(); i++){
			
			if(!input.get(i).getText().equals("")){
				
				try{
					Double.valueOf(input.get(i).getText());
					ret.put(params.get(i), new FixPoint(input.get(i).getText()));
				}catch(NumberFormatException nfe){
					//do nothing
				}
				
			}
			
		}
		return ret;
	}
	
	/**
	 * Returns the name of the functions, this dialog has been for.
	 * 
	 * @return function-name
	 */
	public String getFunctionName(){
		return this.fnct;
	}

}
