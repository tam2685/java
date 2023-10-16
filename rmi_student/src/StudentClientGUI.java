import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;

public class StudentClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Nhập mã học sinh:");
        JTextField codeTextField = new JTextField(10);
        JButton searchButton = new JButton("Tìm kiếm");
        JTextArea resultTextArea = new JTextArea(8, 30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Customize the appearance of the search button
        searchButton.setBackground(new Color(64, 142, 255)); // Blue color
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font

        // Customize the appearance of the text field
        codeTextField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add components to the panel
        panel.add(label, BorderLayout.NORTH);
        panel.add(codeTextField, BorderLayout.WEST);
        panel.add(searchButton, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(panel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String studentCode = codeTextField.getText();
                    StudentService studentService = (StudentService) Naming.lookup("rmi://localhost:3456/StudentService");
                    Student student = studentService.findStudentByCode(studentCode);
                    

                    // Display results in three lines with two columns
                    if (student != null) {
                        resultTextArea.setText("");
                        appendResult(resultTextArea, "Mã học sinh:", student.getCode());
                        appendResult(resultTextArea, "Tên học sinh:", student.getName());
                        appendResult(resultTextArea, "Điểm:", String.valueOf(student.getScore()));
                    } else {
                        resultTextArea.setText("Không tìm thấy học sinh có mã = " + studentCode);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void appendResult(JTextArea textArea, String label, String value) {
        textArea.append(label + "\t" + value + "\n");
    }

    // Replace this method with your actual database retrieval logic
    private static Student fetchStudentFromDatabase(String studentCode) {
        // Replace with code to fetch student from your database
        // Example:
        // return databaseService.findStudentByCode(studentCode);
        return null; // Placeholder for demo
    }
}
