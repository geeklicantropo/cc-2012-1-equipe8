package panel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CompilerButtonPanel extends JPanel {

	private JButton close;
	private JButton compilar;
	
	public CompilerButtonPanel() {
		initialize();
		layoutCompoenents();
	}

	private void layoutCompoenents() {
		String colunas = "left:pref,3dlu,pref,120dlu";
		String linhas = "top: 12dlu";
		CellConstraints cell = new CellConstraints();
		
		FormLayout layout = new FormLayout(colunas,linhas);
		PanelBuilder builder = new PanelBuilder(layout);
		
		builder.add(compilar,cell.xy(1, 1));
		builder.add(close,cell.xy(3,1));
		JPanel panel = builder.getPanel();
		panel.setBackground(Color.RED);
		add(panel);
		
	}

	private void initialize() {
		close = new JButton("Fechar");
		compilar = new JButton("Compilar");
	}

	public JButton getClose() {
		return close;
	}

	public JButton getCompilar() {
		return compilar;
	}
	
}
