import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class Window extends JFrame {

	public Window() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
				 InstantiationException e) {
			throw new RuntimeException(e);
		}
		setDefaultLookAndFeelDecorated(true);
		setTitle("Bricks");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		PlayField playField = new PlayField(this);
		createMenuBar(playField);
		add(playField);
		setSize(1920, 800);
		setResizable(false);
		setVisible(true);
//		playField.restart();
	}

	public void createMenuBar(PlayField playField) {
		String currentPath;
		try {
			currentPath = new File(".").getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		JFileChooser jFileChooser = new JFileChooser(new File(currentPath));
		jFileChooser.setAcceptAllFileFilterUsed(false);

		// set a title for the dialog
		jFileChooser.setDialogTitle("Select a .txt file");

		// only allow files of .txt extension
		FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
		jFileChooser.addChoosableFileFilter(restrict);

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("menu");
		JMenuItem restart = new JMenuItem("restart");
		JMenuItem file = new JMenuItem("file");
		menu.add(restart);
		menu.add(file);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playField.restart();
			}
		});
		file.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = jFileChooser.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					System.out.println(jFileChooser.getSelectedFile().getAbsolutePath());
					String path = jFileChooser.getSelectedFile().getAbsolutePath();
					playField.restart(path);
				}
			}
		});
		menuBar.add(menu);
		setJMenuBar(menuBar);
	}
}