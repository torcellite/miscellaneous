import java.awt.Checkbox;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class emptyFoldersRemover extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField pathField;
	private JTextField numberOfFoldersDeletedField;
	private JLabel lblNumberOfFolders;
	private JLabel lblDeletedFolders;
	private JLabel lblStartingDirectory;
	private JLabel lblDirectoriesTraversed;
	private JButton btnCleanUp;
	private JProgressBar progressBar;
	private TextArea deleteFoldersTextArea;
	private TextArea console;
	private Checkbox removeWindowsDirectories;

	private final String windowsDirectory = "C:\\Windows";

	private File path;
	
	private int numberOfFolders;

	private FileFilter directoryFilter;
	
	private SwingWorker<Integer, Object> swingWorker;
	
	private ActionListener actionListener;

	public emptyFoldersRemover() {
		
		swingWorker = new SwingWorker<Integer, Object>() {
			
			@Override
			protected Integer doInBackground() throws Exception {
				numberOfFoldersDeletedField.setText("0");
				btnCleanUp.setVisible(false);
				progressBar.setVisible(true);
				numberOfFolders = 0;
				removeEmptyFolders(pathField.getText());
				return numberOfFolders;
			}
			
			@Override
		       protected void done() {
				progressBar.setVisible(false);
				btnCleanUp.setVisible(true);
				if(numberOfFolders == 0)
					deleteFoldersTextArea.setText("No empty folders found");
		    }
			 
		};
		
		actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				numberOfFoldersDeletedField.setText("");
				String path = pathField.getText();
				if (path != null && !path.equals("")) {
					if(new File(path).exists()) {
						swingWorker.execute();
					} else
						pathField.setText("Invalid path");
				} else
					pathField.setText("Enter a path");	
			}			
		};
		
		directoryFilter = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if(!removeWindowsDirectories.getState())
					if(pathname.getAbsolutePath().contains(windowsDirectory))
						return false;
				return pathname.isDirectory();
			}
		};
		
		//set default system's look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		this.setTitle("Remove Empty Folders");
		pathField = new JTextField();
		pathField.setBounds(46, 11, 428, 20);
		contentPane.add(pathField);
		pathField.setColumns(10);

		numberOfFoldersDeletedField = new JTextField();
		numberOfFoldersDeletedField.setBounds(534, 408, 50, 20);
		contentPane.add(numberOfFoldersDeletedField);
		numberOfFoldersDeletedField.setColumns(10);

		btnCleanUp = new JButton("Clean");
		btnCleanUp.addActionListener(actionListener);
		btnCleanUp.setBounds(495, 439, 89, 23);
		contentPane.add(btnCleanUp);

		lblNumberOfFolders = new JLabel("Number of folders deleted:");
		lblNumberOfFolders.setBounds(349, 414, 175, 14);
		contentPane.add(lblNumberOfFolders);

		deleteFoldersTextArea = new TextArea();
		deleteFoldersTextArea.setBounds(10, 105, 574, 132);
		//console.setEditable(false);
		contentPane.add(deleteFoldersTextArea);

		removeWindowsDirectories = new Checkbox(
				"Remove empty folders in Windows System directories");
		removeWindowsDirectories.setBounds(10, 52, 464, 22);
		contentPane.add(removeWindowsDirectories);
		removeWindowsDirectories.setState(false);

		lblStartingDirectory = new JLabel("Path:");
		lblStartingDirectory.setBounds(10, 14, 115, 14);
		contentPane.add(lblStartingDirectory);
		
		lblDeletedFolders = new JLabel("Deleted folders:");
		lblDeletedFolders.setBounds(10, 87, 464, 14);
		contentPane.add(lblDeletedFolders);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(495, 439, 89, 23);
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		contentPane.add(progressBar);
		
		lblDirectoriesTraversed = new JLabel("Directories traversed:");
		lblDirectoriesTraversed.setBounds(10, 243, 464, 14);
		contentPane.add(lblDirectoriesTraversed);
		
		console = new TextArea();
		console.setBounds(10, 263, 574, 132);
		contentPane.add(console);

	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					emptyFoldersRemover frame = new emptyFoldersRemover();
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
	public long removeEmptyFolders(String absolutePath) {
		path = new File(absolutePath);
		File listOfFolders[] = path.listFiles(directoryFilter);
		for (int i = 0; i < listOfFolders.length; i++) {
			console.append(listOfFolders[i].getAbsolutePath()+"\n");
			if(listOfFolders[i].list() == null) {
				numberOfFolders++;
				deleteFoldersTextArea.append(listOfFolders[i].getAbsolutePath()+"\n");
				listOfFolders[i].delete();
			} else {
				if (listOfFolders[i].list().length > 0) {
					removeEmptyFolders(listOfFolders[i].getAbsolutePath());
				} else {
					numberOfFolders++;
					deleteFoldersTextArea.append(listOfFolders[i].getAbsolutePath()+"\n");
					listOfFolders[i].delete();
					while (listOfFolders[i].getParentFile().list().length == 0) {
						listOfFolders[i] = listOfFolders[i].getParentFile();
						deleteFoldersTextArea.append(listOfFolders[i].getAbsolutePath()+"\n");
						listOfFolders[i].delete();
						numberOfFolders++;
					}
					numberOfFoldersDeletedField.setText(numberOfFolders+"");
				}
			}
		}
		return numberOfFolders;
	}
}
