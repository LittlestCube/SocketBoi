import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Color;

import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;

public abstract class SocketGui
{
	static javax.swing.JTextArea jTextArea1;
	static javax.swing.JTextField jTextField1;
	static javax.swing.JButton jButton1;
	
	static String lastMessage;
	
	public static JFrame makeJFrame(String title)
	{
		JFrame frame = new JFrame(title);
		JPanel pane = SocketGui.makeJPanel();
		
		lastMessage = "";
		
		KeyListener focusTextField = new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				
			}
			
			public void keyReleased(KeyEvent e)
			{
				
			}
			
			public void keyTyped(KeyEvent e)
			{
				jTextField1.setText(jTextField1.getText() + e.getKeyChar());
				
				jTextField1.requestFocus();
			}
		};
		
		jTextArea1.addKeyListener(focusTextField);
		jButton1.addKeyListener(focusTextField);
		
		frame.add(pane);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		return frame;
	}
	
	public static JPanel makeJPanel()
	{
		JPanel pane = new JPanel();
		
		javax.swing.JScrollPane jScrollPane1;
		javax.swing.JScrollPane jScrollPane2;
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		
		jTextField1.setCaret(new DefaultCaret()
		{
			private final Highlighter.HighlightPainter unfocusedPainter =
				new DefaultHighlighter.DefaultHighlightPainter(new Color(230, 230, 210));
			private static final long serialVersionUID = 1L;
			private boolean isFocused;

			@Override
			protected Highlighter.HighlightPainter getSelectionPainter() {
				return isFocused ? super.getSelectionPainter() : unfocusedPainter;
			}

			@Override
			public void setSelectionVisible(boolean hasFocus) {
				if (hasFocus != isFocused) {
					isFocused = hasFocus;
					super.setSelectionVisible(false);
					super.setSelectionVisible(true);
				}
			}
		});
		
		
		
		Action sendAction = new AbstractAction("Send")
		{ 
			public void actionPerformed(ActionEvent e)
			{
				lastMessage = jTextField1.getText();
				
				if (!lastMessage.equals(""))
				{
					printText(lastMessage);
					
					jTextField1.setText("");
				}
			}
		};
		
		
		
		jButton1.setAction(sendAction);
		sendAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ENTER);
		String key = "Send";
		jButton1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), key);
		jButton1.getActionMap().put(key, sendAction);
		
		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		
		jScrollPane2.setViewportView(jTextField1);
		
		jButton1.setText("Send");
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(pane);
		pane.setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jScrollPane2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jButton1)))
				.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(16, 16, 16))
					.addGroup(layout.createSequentialGroup()
						.addGap(8, 8, 8)
						.addComponent(jButton1)
						.addContainerGap(9, Short.MAX_VALUE))))
		);
		
		jTextArea1.setEditable(false);
		
		return pane;
	}
	
	public static void printText(String text)
	{
		jTextArea1.setText(jTextArea1.getText() + text + "\n");
	}
}