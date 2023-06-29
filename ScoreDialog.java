import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileWriter;

public class ScoreDialog extends JFrame {
    ScoreDialog(long score) {
        setSize(100, 100);
        setLayout(new BorderLayout());
        JTextField textField = new JTextField();
        add(textField, BorderLayout.NORTH);
        JButton button = new JButton("submit");
        JFrame dialog = this;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileWriter fileWriter = new FileWriter("score.txt", true)) {
                    fileWriter.append(textField.getText()).append(" ").append(String.valueOf(score)).append('\n');
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
                dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
            }
        });
        add(button, BorderLayout.SOUTH);
        setVisible(true);
    }
}
