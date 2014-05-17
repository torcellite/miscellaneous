import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class hiddenFileLocator extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private File path;
	private int noOfFolders;
	String hiddenFiles = "";
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hiddenFileLocator frame = new hiddenFileLocator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String run(String p) {
		String absolutePath = p;
		path = new File(absolutePath);
		try {
			File listOfFolders[] = path.listFiles();
			for (int i = 0; i < listOfFolders.length; i++) {
				if (listOfFolders[i].isDirectory()) {
					if (listOfFolders[i].list().length > 0) {
						run(listOfFolders[i].getAbsolutePath());
					} else {
						noOfFolders++;
						System.out.println(noOfFolders);
						listOfFolders[i].delete();
					}
				}
				if (listOfFolders[i].isFile() && listOfFolders[i].isHidden()) {
					hiddenFiles = hiddenFiles
							+ listOfFolders[i].getAbsolutePath() + '\n';
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (hiddenFiles);
	}

	/**
	 * Create the frame.
	 */
	public hiddenFileLocator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 11, 414, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSeek = new JButton("Seek!");
		btnSeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				noOfFolders = 0;
				textField_1.setText("");
				if (textField.getText() != null) {
					String files = run(textField.getText());
					textField_1.setText(files);
				} else
					textField.setText("Enter a path!");
			}
		});
		btnSeek.setBounds(335, 48, 89, 23);
		contentPane.add(btnSeek);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 82, 414, 169);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblHiddenFilesList = new JLabel("Hidden files list:");
		lblHiddenFilesList.setBounds(10, 52, 156, 14);
		contentPane.add(lblHiddenFilesList);
	}

}
