import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
public class Main {
    private static boolean keep = false;
    JFrame frame;
    JButton MoodDropDown;
    private final String title = "AI-Powered Scene Shot Generator";
    JButton GenreDropDown;
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> moods = new  ArrayList<>();
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == MoodDropDown) {
                JOptionPane.showOptionDialog(frame, "Mood Drop Down", title,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moods.toArray(), moods.toArray()[0]);
            }
            if (e.getSource() == GenreDropDown) {
                JOptionPane.showInternalOptionDialog(frame, "Genre Drop Down", title,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, genres.toArray(), genres.get(0));
            }
        }
    };
    public Main() { keep = true; }

    public static void main(String[] args) {
        Main mainC = new Main();
        mainC.readMoodsandGenres();
        if (keep) {
            mainC.run();
        }
        else {
            throw new RuntimeException("Program terminated");
        }

    }
    public void readMoodsandGenres() {
        try {
            File file = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Genres.txt");
            File file2 = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Moods.txt");
            BufferedReader scanner = new BufferedReader(new FileReader(file));
            BufferedReader gFile = new BufferedReader(new FileReader(file2));
            String line;

            while ((line = scanner.readLine()) != null) {
                moods.add(line);
            }
            while ((line = gFile.readLine()) != null) {
                genres.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        MoodDropDown = new JButton("Mood");
        MoodDropDown.addActionListener(actionListener);
        GenreDropDown = new JButton("Genre");
        GenreDropDown.addActionListener(actionListener);
        panel.add(MoodDropDown);
        panel.add(GenreDropDown);
        JTextField textField = new JTextField();
        textField.setColumns(10);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 20));

        panel.add(textField);
        frame.add(panel);
        frame.setVisible(true);
    }
}