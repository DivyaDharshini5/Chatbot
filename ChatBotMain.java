import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class ChatBotMain extends JFrame {

    private JPanel contentPane;
    private JTextField textField_1;
    private JButton btnSend;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton btnMaximize;

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatBotMain frame = new ChatBotMain();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public ChatBotMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 428, 657);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField_1 = new JTextField();
        textField_1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    action();
                }
            }
        });
        textField_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
        textField_1.setBounds(0, 555, 338, 60);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action();
            }
        });
        btnSend.setBounds(341, 555, 75, 59);
        contentPane.add(btnSend);


        btnMaximize = new JButton("Maximize");
        btnMaximize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        btnMaximize.setBounds(0, 10, 120, 30);
        contentPane.add(btnMaximize);

       
        textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 19));
        textArea.setEditable(false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, 50, 416, 500);
        contentPane.add(scrollPane);
    }

    void action() {
        String query = textField_1.getText();
        textArea.append(" You -> " + query + "\n");
        query = query.trim().toLowerCase();

        if (query.contains("clear screen") || query.contains("clr") || query.contains("cls")) {
            textArea.setText("");
            textField_1.setText("");
        } else if (query.contains("hi") || query.contains("hey") || query.contains("hola") || query.contains("hello")) {
            respondWithGreeting();
        } else if (query.contains("how are you") || query.contains("how are u")) {
            ai("I'm just a bot, but I'm functioning as expected!");
        } else if (query.contains("time")) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            ai("Current time is " + formatter.format(date));
        } else if (query.contains("date")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            ai("Today's date is " + formatter.format(date));
        } else if (query.contains("sing a song")) {
            singsongs();
        } 
        else if (query.matches(".*\\d+.*\\s*(\\+|\\-|\\*|\\/|\\%)\\s*.*\\d+.*")) {
            solveMath(query);
        } else {
            Random rand = new Random();
            int a = rand.nextInt(4);
            if (a == 0) {
                ai("Sorry, I can't get you.");
            } else if (a == 1) {
                ai("Please say it correctly.");
            } else if (a == 2) {
                ai("Please rephrase that.");
            } else if (a == 3) {
                ai("???");
            }
        }

       
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    void ai(String s) {
        textArea.append(" AI -> " + s + "\n\n");
        textField_1.setText("");
    }

    void respondWithGreeting() {
        LocalTime now = LocalTime.now();
        String greeting;
        if (now.isBefore(LocalTime.NOON)) {
            greeting = "Good morning!";
        } else if (now.isBefore(LocalTime.of(17, 0))) {
            greeting = "Good afternoon!";
        } else {
            greeting = "Good evening!";
        }
        ai(greeting + " How can I assist you today?");
    }

    void singsongs() {
        String[] songs = {
            "You know you love me (yo), I know you care (uh-huh)\n" + //
								"Just shout whenever (yo), and I'll be there (uh-huh)\n" + //
								"You are my love (yo), you are my heart (uh-huh)\n" + //
								"And we will never, ever, ever be apart (yo, uh-huh)",
            
        };
        Random rand = new Random();
        ai(songs[rand.nextInt(songs.length)]);
    }

    void solveMath(String query) {
        try {
            String[] parts = query.split("\\s*(\\+|\\-|\\*|\\/|\\%)\\s*");
            double num1 = Double.parseDouble(parts[0]);
            double num2 = Double.parseDouble(parts[1]);
            double result = 0;

            if (query.contains("+")) {
                result = num1 + num2;
            } else if (query.contains("-")) {
                result = num1 - num2;
            } else if (query.contains("*")) {
                result = num1 * num2;
            } else if (query.contains("/")) {
                result = num1 / num2;
            } else if (query.contains("%")) {
                result = num1 % num2;
            }

            ai("The result is: " + result);
        } catch (Exception e) {
            ai("There was an error in calculating that.");
        }
    }
}
