import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.DataBufferDouble;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Main implements Director {
    private static boolean keep = false;
    JFrame frame;
    final int FILE_MAX = 20;

    int fileCounter = 0;
    JButton MoodDropDown;
    JButton generate;
    JButton save;
    Path path;
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
                            int op = JOptionPane.showConfirmDialog(frame, "Overwrite oldest file?", "Override File", JOptionPane.YES_NO_OPTION);
                            if (op == JOptionPane.YES_OPTION) {
                                int tempCount = fileCounter;
                                fileCounter = 0;
                                writeToFile(outputArea.getText());
                                fileCounter = tempCount;
                            }
                            else if (op == JOptionPane.NO_OPTION) {
                                int q = 0;
                            }
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
    public Main() {
        keep = true;
        File fi = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Counter.txt");
        try {
            BufferedReader bw = new BufferedReader(new FileReader(fi));
            String numOfFiles = bw.readLine();
            fileCounter = Integer.parseInt(numOfFiles);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.pink);
        panel1.setLayout(new GridLayout(2, 2));
        JLabel entry = new JLabel(title);
        entry.setHorizontalAlignment(JLabel.CENTER);
        entry.setFont(new Font("Arial", Font.BOLD, 14));
        entry.setForeground(Color.black);
        entry.setBackground(Color.pink);
        entry.setVerticalAlignment(JLabel.CENTER);
        panel.add(entry);
        MoodDropDown = new JButton("Mood");
        MoodDropDown.addActionListener(actionListener);
        MoodDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
        GenreDropDown = new JButton("Genre");
        GenreDropDown.addActionListener(actionListener);
        panel1.add(MoodDropDown);
        panel1.add(GenreDropDown);
        frame.add(panel);
        frame.setVisible(true);
        frame.setBackground(Color.pink);
        generate = new JButton("Generate");
        generate.addActionListener(actionListener);
        panel1.add(generate);
        save = new JButton("Save");
        save.addActionListener(actionListener);
        panel1.add(save);
        textField = new JTextField();
        textField.setColumns(10);
        textField.setMaximumSize(new Dimension(100, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel1.add(textField);
        panel.add(panel1);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.white);
        outputArea.setFont(new Font("Arial", Font.ITALIC, 13));
        outputArea.setText("Shot Description Here");
        outputArea.setSize(800, 10);
        panel.add(outputArea);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void writeToFile(String faxOut) {
        File newFile = new File("scene_suggest" + fileCounter + ".txt");
        File count = new File("C:\\Users\\amigo\\IdeaProjects\\personal-ai-shot-list-generator\\CinemaShotArt\\Counter.txt");
        path = Paths.get(newFile.getAbsolutePath());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
            BufferedReader br = new BufferedReader(new FileReader(count));
            String line = br.readLine();
            int numOfFiles = Integer.parseInt(line);
            numOfFiles++;
            String newNum = numOfFiles + "";
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(count));
            bw2.write(newNum);
            bw.write(faxOut);
            bw.close();
            br.close();
            bw2.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                        double mins1 = 0;
                        double mins2 = 0;
                        double warmth = 0;
                        switch (mood) {

                            case "Suspenseful":
                                outputArea.setText("* dark lighting\n* cold colors\n *handheld movement\n");
                                num = 1;
                                mins1 += 2;
                                warmth += 0.15;
                                break;
                            case "Emotional":
                                outputArea.setText("* warm lighting\n* slow zoom\n* reaction shots\n");
                                num = 2;
                                mins1 += 3;
                                warmth += 0.75;
                                break;
                            case "Melancholy":
                                outputArea.setText("* grayed environment\n* slow movement\n* shadowy shots\n");
                                num = 3;
                                mins1 += 3;
                                warmth += 0.10;
                                break;
                            case "Energetic":
                                outputArea.setText("* bright lighting\n* fast cuts\n* close-up\n");
                                num = 4;
                                mins1 += 1;
                                warmth += 1;
                                break;
                            case  "Inspirational":
                                outputArea.setText("* hopeful lighting\n * steady and stable shots\n * silhouette captures\n");
                                num = 5;
                                mins1 += 2;
                                warmth += 0.35;
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
                                mins2 += 3;
                                warmth += 0.4;
                                break;
                            case "Animation":
                                outputArea.setText(outputArea.getText() +
                                        "* Special case\n* CGI shots\n* Colored environments");
                                num += 20;
                                mins2 += 1;
                                warmth += 1;
                                break;
                            case "Adventure":
                                outputArea.setText(outputArea.getText() +
                                        "* Set piece shots\n* Focus on main actions\n* align shots with musical score");
                                num += 30;
                                mins2 += 3;
                                warmth += 0.8;
                                break;
                            case "Sci-Fi":
                                outputArea.setText(outputArea.getText() +
                                        "* Random creature shots\n* Mysterious atmosphere\n* Wide environments");
                                num += 40;
                                mins2 += 2;
                                warmth += 0.5;
                                break;
                            case "Comedy":
                                outputArea.setText(outputArea.getText() +
                                        "* Normalized lighting\n* Sepia or technicolor if set in the past\n* Slo-mo if possible");
                                num += 50;
                                mins2 += 2;
                                warmth += 0.7;
                                break;
                            case "Thriller":
                                outputArea.setText(outputArea.getText() +
                                        "* close-up\n* dark lighting\n* handheld movement");
                                num += 60;
                                mins2 += 2;
                                warmth += 0.3;
                                break;
                            case "Horror":
                                outputArea.setText(outputArea.getText() +
                                        "*Facial emphasis\n* dark lighting\n* Similar to 'Thriller', but with more monsters");
                                num += 70;
                                mins2 += 1;
                                warmth += 0.1;
                                break;
                            default:
                                System.out.println("* Don't Know");
                                break;
                        }
                        outputArea.setText(outputArea.getText() +
                                "\n1. Establishing Shot");
                        if (num > 60) {
                            outputArea.setText(outputArea.getText() +
                                    "\n2. Slow Dialogue Shot\n3. Close-Up Reaction Shot\n4. Sudden Cut");
                        }
                        else if (num > 50 && num < 60) {
                            outputArea.setText(outputArea.getText() +
                                    "\n2. Fast Dialgoue Shot\n3. Medium Reaction Shot\n4. Sudden Cut");
                        }
                        else if (num > 10 && num < 20) {
                            outputArea.setText(outputArea.getText() +
                                    "\n2. Medium Dialogue Shot\n3. Close-Up Reaction Shot\n4. Tracking Exit Shot");
                        }
                        else {
                            outputArea.setText(outputArea.getText() +
                                    "\n2. Medium Dialogue Shot\n3. Medium Reaction Shot\n4. Tracking Exit Shot");
                        }

                        if (warmth <= 0.25) {
                            outputArea.setText(outputArea.getText() + "\nEntirely bleak palette. Scenes should have " +
                                    "lots of blacks and dark blues");
                        }
                        else if (warmth <= 0.45) {
                            outputArea.setText(outputArea.getText() + "\nSinister lighting all around. Many dark blues " +
                                    "with a hint of orange or yellow");

                        }
                        else if (warmth <= 0.55) {
                            outputArea.setText(outputArea.getText() + "\nVersatile lighting, but leaning " +
                                    "towards cooler colors in the shots");
                        }
                        else if (warmth <= 0.75) {
                            outputArea.setText(outputArea.getText() + "\nUtilize the environment's sunlight and colors to" +
                                    "their greatest, incorporating a nice, golden undertone to all environments. Not heavily modified");
                        }
                        else if (warmth <= 0.85) {
                            outputArea.setText(outputArea.getText() + ("\nAlmost all gloom is gone, but mystery should still linger in " +
                                    "shadows within the warm colors of the sun"));
                        }
                        else if (warmth <= 1.05) {
                            outputArea.setText(outputArea.getText() + "\nRustic tones are good at this genre and mood intersection");
                        }
                        else if (warmth <= 1.35) {
                            outputArea.setText(outputArea.getText() + "\nStrong contrast between bright blues and greens with brilliant fire-like colors. " +
                                    "Would not hurt to add some pink and purple to the mix");
                        }
                        else if (warmth <= 1.7) {
                            outputArea.setText(outputArea.getText() + "\nExtremely saturated color palette, shadows are almost nowhere to be seen, and the environment " +
                                    "looks as though it could be midday");
                        }
                        else {
                            outputArea.setText(outputArea.getText() + "\nSo bright, your audience may need to squint their eyes " +
                                    "to see through the saturation. Everything is a warm color, and the surrounding landscape should reflect the joyous tone " +
                                    "left by the accentuated color scheme.");
                        }
                        double numOfMins = (mins1 + mins2) / 2;
                        outputArea.setText(outputArea.getText() +
                                "\nScene duration: " + numOfMins + "minutes");
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