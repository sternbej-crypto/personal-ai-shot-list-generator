import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Main implements Director {
    private static boolean keep = false;
    JFrame frame;
    final int FILE_MAX = 20;
    int fileCounter = 0;
    JButton MoodDropDown;
    JButton generate;
    JButton save;
    JTextField textField;
    JTextArea outputArea;
    private final String title = "AI-Powered Scene Shot Generator";
    JButton GenreDropDown;
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> moods = new  ArrayList<>();
    String[] thisArray = new String[2];
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == MoodDropDown) {
                String modd = (String)JOptionPane.showInputDialog(frame, "Mood Drop Down", title
                        , JOptionPane.QUESTION_MESSAGE, null, moods.toArray(), moods.toArray()[0]);
                thisArray[0] = modd;
            }
            if (e.getSource() == GenreDropDown) {
                String gen = (String)JOptionPane.showInputDialog(frame, "Genre Drop Down", title
                        , JOptionPane.QUESTION_MESSAGE, null, genres.toArray(), genres.toArray()[0]);
                thisArray[1] = gen;
                System.out.println(thisArray[1]);
            }
            if (e.getSource() == generate) {
                if (thisArray[0] != null && thisArray[1] != null) {
                    generateShots(thisArray[0], thisArray[1]);
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Please enter the data before generating the scenario!", "y", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == save) {
                if (!(outputArea.getText().equals(""))) {
                    int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to save your shot suggestion to a file?", "Save Y/N", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        if (fileCounter >= FILE_MAX) {
                            JOptionPane.showMessageDialog(frame, "Maximum number of files exceeded.", "Save Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                          writeToFile(outputArea.getText());
                          fileCounter++;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Need to have something to save", "Save Error", JOptionPane.ERROR_MESSAGE);
                }
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

        File file = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Genres.txt");
        File file2 = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Moods.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                genres.add(line);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
        try (BufferedReader bri = new BufferedReader(new FileReader(file2))) {
            String line;
            while ((line = bri.readLine()) != null) {
                moods.add(line);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void run() {
        frame = new JFrame();
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel entry = new JLabel(title);
        entry.setHorizontalAlignment(JLabel.CENTER);
        entry.setFont(new Font("Arial", Font.PLAIN, 14));
        entry.setForeground(Color.black);
        entry.setBackground(Color.pink);
        entry.setVerticalAlignment(JLabel.CENTER);
        panel.add(entry);
        MoodDropDown = new JButton("Mood");
        MoodDropDown.addActionListener(actionListener);
        GenreDropDown = new JButton("Genre");
        GenreDropDown.addActionListener(actionListener);
        panel.add(MoodDropDown);
        panel.add(GenreDropDown);
        frame.add(panel);
        frame.setVisible(true);
        frame.setBackground(Color.pink);
        generate = new JButton("Generate");
        generate.addActionListener(actionListener);
        panel.add(generate);
        save = new JButton("Save");
        save.addActionListener(actionListener);
        panel.add(save);
        textField = new JTextField();
        textField.setColumns(10);
        textField.setMaximumSize(new Dimension(100, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textField);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.red);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        outputArea.setText("Shot Description Here");
        panel.add(outputArea);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void writeToFile(String faxOut) {
        File newFile = new File("scene_suggest" + fileCounter + ".txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            bw.write(faxOut);
            bw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error with writing", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void generateShots(String mood, String genre) {

        outputArea.setEditable(true);
        File genres = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Genres.txt");
        File moods = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Moods.txt");
        try (BufferedReader reader1 = new BufferedReader(new FileReader(genres)); BufferedReader reader2 =
                new BufferedReader(new FileReader(moods))) {

            String genreLine;
            String moodLine;
            reader2.mark(500);
            boolean stop = false;
            while ((genreLine = reader1.readLine()) != null) {
                while ((moodLine = reader2.readLine()) != null) {
                    if ((genreLine.equals(genre)) && (moodLine.equals(mood))) {
                        outputArea.setEditable(true);
                        int num = -1;
                        switch (mood) {

                            case "Suspenseful":
                                outputArea.setText("* dark lighting\n* cold colors\n *handheld movement\n");
                                num = 1;
                                break;
                            case "Emotional":
                                outputArea.setText("* warm lighting\n* slow zoom\n* reaction shots\n");
                                num = 2;
                                break;
                            case "Melancholy":
                                outputArea.setText("* grayed environment\n* slow movement\n* shadowy shots\n");
                                num = 3;
                                break;
                            case "Energetic":
                                outputArea.setText("* bright lighting\n* fast cuts\n* close-up\n");
                                num = 4;
                                break;
                            case  "Inspirational":
                                outputArea.setText("* hopeful lighting\n * steady and stable shots\n * silhouette captures\n");
                                num = 5;
                                break;
                            default:
                                System.out.println("* Don't Know");
                                num = 6;
                                break;
                        }
                        switch (genre) {
                            case "Drama":
                                outputArea.setText(outputArea.getText() +
                                        "* Long shots\n* character conversations\n* focus on faces");
                                num += 10;
                                break;
                            case "Animation":
                                outputArea.setText(outputArea.getText() +
                                        "* Special case\n* CGI shots\n* Colored environments");
                                num += 20;
                                break;
                            case "Adventure":
                                outputArea.setText(outputArea.getText() +
                                        "* Set piece shots\n* Focus on main actions\n* align shots with musical score");
                                num += 30;
                                break;
                            case "Sci-Fi":
                                outputArea.setText(outputArea.getText() +
                                        "* Random creature shots\n* Mysterious atmosphere\n* Wide environments");
                                num += 40;
                                break;
                            case "Comedy":
                                outputArea.setText(outputArea.getText() +
                                        "* Normalized lighting\n* Sepia or technicolor if set in the past\n* Slo-mo if possible");
                                num += 50;
                                break;
                            case "Thriller":
                                outputArea.setText(outputArea.getText() +
                                        "* close-up\n* dark lighting\n* handheld movement");
                                num += 60;
                                break;
                            case "Horror":
                                outputArea.setText(outputArea.getText() +
                                        "*Facial emphasis\n* dark lighting\n* Similar to 'Thriller', but with more monsters");
                                num += 70;
                                break;
                            default:
                                System.out.println("* Don't Know");
                                break;
                        }
                        outputArea.setText(outputArea.getText() +
                                "\n1. Establishing Shot");
                        if (num > 60) {
                            outputArea.setText(outputArea.getText() +
                                    "2. Slow Dialogue Shot\n3. Close-Up Reaction Shot\n4. Sudden Cut");
                        }
                        else if (num > 50 && num < 60) {
                            outputArea.setText(outputArea.getText() +
                                    "2. Fast Dialgoue Shot\n3. Medium Reaction Shot\n4. Sudden Cut");
                        }
                        else if (num > 10 && num < 20) {
                            outputArea.setText(outputArea.getText() +
                                    "2. Medium Dialogue Shot\n3. Close-Up Reaction Shot\n4. Tracking Exit Shot");
                        }
                        else {
                            outputArea.setText(outputArea.getText() +
                                    "2. Medium Dialogue Shot\n3. Medium Reaction Shot\n4. Tracking Exit Shot");
                        }
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
                reader2.reset();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}