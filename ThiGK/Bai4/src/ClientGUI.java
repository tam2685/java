import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientGUI extends JFrame {
    private ComplexOperation remoteObject; // Remote RMI object
    private JTextField realField1, imaginaryField1, realField2, imaginaryField2;
    private JTextArea resultArea;

    public ClientGUI() {
        // Set up the JFrame
        super("Complex Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create GUI components
        realField1 = new JTextField(10);
        imaginaryField1 = new JTextField(10);
        realField2 = new JTextField(10);
        imaginaryField2 = new JTextField(10);
        JButton addButton = new JButton("Add");
        JButton subtractButton = new JButton("Subtract");
        JButton multiplyButton = new JButton("Multiply");
        JButton divideButton = new JButton("Divide");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // Layout components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Number 1:"));
        inputPanel.add(new JLabel("Number 2:"));
        inputPanel.add(realField1);
        inputPanel.add(realField2);
        inputPanel.add(imaginaryField1);
        inputPanel.add(imaginaryField2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);

        // Add components to the JFrame
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Set up RMI connection
        try {
            String registryURL = "rmi://127.0.0.1:8000/ComplexOperation"; // Replace with your server's URL
            remoteObject = (ComplexOperation) Naming.lookup(registryURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performOperation("add");
            }
        });

        subtractButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performOperation("subtract");
            }
        });

        multiplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performOperation("multiply");
            }
        });

        divideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performOperation("divide");
            }
        });
    }

    private void performOperation(String operation) {
        try {
            double real1 = Double.parseDouble(realField1.getText());
            double imaginary1 = Double.parseDouble(imaginaryField1.getText());
            double real2 = Double.parseDouble(realField2.getText());
            double imaginary2 = Double.parseDouble(imaginaryField2.getText());

            Complex num1 = new Complex(real1, imaginary1);
            Complex num2 = new Complex(real2, imaginary2);

            Complex result = null;
            switch (operation) {
                case "add":
                    result = remoteObject.add(num1, num2);
                    break;
                case "subtract":
                    result = remoteObject.subtract(num1, num2);
                    break;
                case "multiply":
                    result = remoteObject.multiply(num1, num2);
                    break;
                case "divide":
                    result = remoteObject.divide(num1, num2);
                    break;
            }

            resultArea.setText("Result: " + result.toString());
        } catch (RemoteException ex) {
            ex.printStackTrace();
            resultArea.setText("Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            resultArea.setText("Error: Invalid input. Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }
}
