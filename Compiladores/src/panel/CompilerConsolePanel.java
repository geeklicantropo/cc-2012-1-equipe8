package panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CompilerConsolePanel extends JPanel{

	private static final long serialVersionUID = -3618432274435587416L;
	JTextArea textArea;
	
	public CompilerConsolePanel() {
		initialize();
		layoutCompoenents();
	}

	private void layoutCompoenents() {
		String colunas = "left:pref";
		String linhas = "top:pref";
		CellConstraints cell = new CellConstraints();
		
		FormLayout layout = new FormLayout(colunas,linhas);
		PanelBuilder builder = new PanelBuilder(layout);
		JScrollPane sroll = new JScrollPane();
		sroll.setPreferredSize(new Dimension(400, 100));
		sroll.setVisible(true);
		sroll.setAutoscrolls(true);
		textArea.setEditable(false);
		sroll.setViewportView(textArea);
		
		
		sroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(sroll,cell.xy(1, 1));
		
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.RED);
		add(panel);
		
	}

	private void initialize() {
		textArea =  new JTextArea();
		
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
}
