import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class batchFilesRemover extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	File path;
	long noOfFiles;
	private JTextField textField;
	private JLabel lblNumberOfFolders;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					batchFilesRemover frame = new batchFilesRemover();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public long run(String p) {
		String absolutePath = p;
		try {
			path = new File(absolutePath);
			File listOfFiles[] = path.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String ext = listOfFiles[i].getName().substring(
							listOfFiles[i].getName().length()
									- textField_2.getText().length(),
							listOfFiles[i].getName().length());
					if (ext.equalsIgnoreCase(textField_2.getText())) {
						noOfFiles++;
						System.out.println(noOfFiles);
						listOfFiles[i].delete();
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return (noOfFiles);
	}

	public batchFilesRemover() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Particular extension remover");
		textField = new JTextField();
		textField.setBounds(50, 11, 387, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(348, 116, 89, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		lblNumberOfFolders = new JLabel("Number of files deleted:");
		lblNumberOfFolders.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumberOfFolders.setBounds(173, 119, 204, 14);
		contentPane.add(lblNumberOfFolders);

		JLabel lblPath = new JLabel("Path:");
		lblPath.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPath.setBounds(10, 14, 46, 14);
		contentPane.add(lblPath);

		JLabel lblFileExtension = new JLabel("File extension:");
		lblFileExtension.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFileExtension.setBounds(180, 69, 136, 14);
		contentPane.add(lblFileExtension);

		textField_2 = new JTextField();
		textField_2.setBounds(348, 66, 89, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JButton btnCleanUp = new JButton("Clean up!");
		btnCleanUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				noOfFiles = 0;
				textField_1.setText("");
				if (textField.getText() != null) {
					long num = run(textField.getText());
					textField_1.setText(Long.toString(num));
				} else
					textField.setText("Enter a path!");
			}
		});
		btnCleanUp.setBounds(348, 163, 89, 23);
		contentPane.add(btnCleanUp);

	}
}
