package window;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import controller.CompilerController;

import pacote.MiniJava;
import panel.CompilerButtonPanel;
import panel.CompilerConsolePanel;
import panel.CompilerFilterPanel;
import panel.CompilerInputPanel;
import syntaxtree.TProgram;
import visitor.BuildSymbolTableVisitor;
import visitor.PrettyPrintVisitor;
import visitor.TypeCheckVisitor;


public class CompilerWindow extends JFrame{

	private static final long serialVersionUID = -3858642174570058470L;

	private CompilerFilterPanel filterPanel;
	private CompilerInputPanel inputPanel;
	private CompilerConsolePanel consolePanel;
	private CompilerButtonPanel buttonPanel;
	private CompilerController controller;
	
	public CompilerWindow() {
		initialize();
		layoutComponets();
		linkListener();
	}

	private void linkListener() {
		this.buttonPanel.getClose().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		this.filterPanel.getProgram().addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getItem().toString().equals(arg0.getItemSelectable().getSelectedObjects()[0].toString())){
					loadFile(arg0.getItemSelectable().getSelectedObjects()[0].toString());
				}
				
			}

			
		});
		this.buttonPanel.getCompilar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compile();
				
			}
		});
	}
	private MiniJava miniJava=null;
	private void compile(){
		if(miniJava==null)
			miniJava = new MiniJava(new ByteArrayInputStream((inputPanel.getTextArea().getText().toString()+" $").getBytes()));
		else
			miniJava.ReInit(new ByteArrayInputStream((inputPanel.getTextArea().getText().toString()+" $").getBytes()));
		String message = "";
		TProgram tProgram = null;
		try{
		 tProgram = MiniJava.Program();
		message += "Análise sintática sucesso.\n";
		}catch(Exception e){
			message= e.getMessage();
		}
		if(tProgram!=null){
		//Checagem de Tipo
		BuildSymbolTableVisitor bTableVisitor = new BuildSymbolTableVisitor();
		tProgram.accept(bTableVisitor);
		tProgram.accept(new TypeCheckVisitor(bTableVisitor.getSymTab()));
		if(bTableVisitor.getErros().length()>0){
			message+=bTableVisitor.getErros();
			consolePanel.getTextArea().setCaretColor(Color.RED);
			consolePanel.getTextArea().setText(message);
		}
		else{
			message+="Análise Semântica sucesso.\n";
		
		
		// Traducao do codigo
		translate.Translate t = new translate.Translate(tProgram, new Mips.MipsFrame());
		tProgram.accept(t);
		message+="Tradução foi realizada com sucesso.\n";
		// Imprimindo traducao de codigo
		message+="\n";
		message+="Árvore Intermediária:\n\n";
		message+=t.printResults();
		message+="\n";
		consolePanel.getTextArea().setCaretColor(Color.GREEN);
		consolePanel.getTextArea().setText(message);
		}
		}else{
			consolePanel.getTextArea().setCaretColor(Color.RED);
			consolePanel.getTextArea().setText(message);
		}
		
	}
	
	private void loadFile(String program) {
	    if(program!=null && program.trim().length()>0){
		try {
			this.inputPanel.getTextArea().setText(controller.getFile(program));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File not found.", "Message", JOptionPane.ERROR_MESSAGE, null);
		}
		}else{
			this.inputPanel.getTextArea().setText("");
		}
	}

	private void layoutComponets() {
		//Jgoodies
		String colunas = "left:5dlu,200dlu,5dlu";
		String linhas = "pref,5dlu,pref,5dlu,pref,5dlu,pref,5dlu";
		CellConstraints cell = new CellConstraints();
		
		FormLayout layout = new FormLayout(colunas,linhas);
		PanelBuilder builder = new PanelBuilder(layout/*,new FormDebugPanel()*/);
		
		builder.add(filterPanel,cell.xy(2, 1));
		builder.add(inputPanel,cell.xy(2, 3));
		builder.add(consolePanel,cell.xy(2, 5));
		builder.add(buttonPanel,cell.xy(2, 7));
		
		JPanel x = builder.getPanel();
		x.setBackground(Color.RED);
		getContentPane().add(x);
		pack();
	}

	
	private void initialize() {
		setTitle("Compilador MiniJava - Equipe 8");
		filterPanel 	= new CompilerFilterPanel();
		filterPanel.setBackground(Color.RED);
		inputPanel 		= new CompilerInputPanel();
		inputPanel.setBackground(Color.RED);
		consolePanel	= new CompilerConsolePanel();
		consolePanel.setBackground(Color.RED);
		buttonPanel 	= new CompilerButtonPanel();
		buttonPanel.setBackground(Color.RED);
		controller 		= new CompilerController(this);
	}
	
	public static void main(String[] args) {
		CompilerWindow window = new CompilerWindow();
		window.setBackground(Color.RED);
		window.setVisible(true);
		
	}
}