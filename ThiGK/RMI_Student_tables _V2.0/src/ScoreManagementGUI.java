import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class ScoreManagementGUI {
    private JFrame frame;
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton searchButton;
    private JButton addOrUpdateStudentButton;
    private JButton addOrUpdateSubjectButton;
    private JButton addOrUpdateScoreButton;
    private JButton deleteStudentButton;
    private JButton deleteSubjectButton;
    private JButton deleteScoreButton;
    private ScoreService scoreService;

    public ScoreManagementGUI() {
        initializeGUI();
        connectToRMIService();
    }

    private void initializeGUI() {
        frame = new JFrame("Score Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Text Input
        inputField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        mainPanel.add(inputField, gbc);

        // Output Area
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));

        // Add buttons to buttonPanel
        searchButton = new JButton("Tìm kiếm");
        addOrUpdateStudentButton = new JButton("Thêm/Cập nhật Sinh viên");
        addOrUpdateSubjectButton = new JButton("Thêm/Cập nhật Môn học");
        addOrUpdateScoreButton = new JButton("Thêm/Cập nhật Điểm số");
        deleteStudentButton = new JButton("Xóa Sinh viên");
        deleteSubjectButton = new JButton("Xóa Môn học");
        deleteScoreButton = new JButton("Xóa Điểm số");

        addButtonToPanel(searchButton, buttonPanel);
        addButtonToPanel(addOrUpdateStudentButton, buttonPanel);
        addButtonToPanel(addOrUpdateSubjectButton, buttonPanel);
        addButtonToPanel(addOrUpdateScoreButton, buttonPanel);
        addButtonToPanel(deleteStudentButton, buttonPanel);
        addButtonToPanel(deleteSubjectButton, buttonPanel);
        addButtonToPanel(deleteScoreButton, buttonPanel);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridheight = 2;
        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel);
        frame.setVisible(true);

        setupActionListeners();
    }

    private void connectToRMIService() {
        try {
            // Connect to the RMI service
            String registryURL = "rmi://localhost:3457/ScoreService";
            scoreService = (ScoreService) Naming.lookup(registryURL);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void addButtonToPanel(JButton button, JPanel panel) {
        panel.add(button);
    }

    private void setupActionListeners() {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = inputField.getText();
        
                // Determine if it's a student code or subject code
                if (searchText.startsWith("S")) {
                    // Search for a student
                    Student student = scoreService.getScoresByStudentCode(searchText);
                    if (student != null) {
                        outputArea.setText("Thông tin sinh viên:\n");
                        outputArea.append("Mã sinh viên: " + student.getStudentCode() + "\n");
                        outputArea.append("Tên sinh viên: " + student.getStudentName() + "\n");
                        List<Score> scores = student.getScores();
                        if (scores != null && !scores.isEmpty()) {
                            outputArea.append("Điểm số:\n");
                            for (Score score : scores) {
                                outputArea.append("Mã môn học: " + score.getSubjectCode() + "\n");
                                outputArea.append("Tên môn học: " + score.getSubjectName() + "\n");
                                outputArea.append("Điểm: " + score.getScore() + "\n");
                                outputArea.append("---\n");
                            }
                        } else {
                            outputArea.append("Không có điểm số.\n");
                        }
                    } else {
                        outputArea.setText("Không tìm thấy thông tin sinh viên.");
                    }
                } else if (searchText.startsWith("M")) {
                    // Search for a subject
                    Subject subject = scoreService.getScoresBySubjectCode(searchText);
                    if (subject != null) {
                        outputArea.setText("Thông tin môn học:\n");
                        outputArea.append("Mã môn học: " + subject.getSubjectCode() + "\n");
                        outputArea.append("Tên môn học: " + subject.getSubjectName() + "\n");
                        List<Score> scores = subject.getScores();
                        if (scores != null && !scores.isEmpty()) {
                            outputArea.append("Điểm số:\n");
                            for (Score score : scores) {
                                outputArea.append("Mã sinh viên: " + score.getStudentCode() + "\n");
                                outputArea.append("Tên sinh viên: " + score.getStudentName() + "\n");
                                outputArea.append("Điểm: " + score.getScore() + "\n");
                                outputArea.append("---\n");
                            }
                        } else {
                            outputArea.append("Không có điểm số.\n");
                        }
                    } else {
                        outputArea.setText("Không tìm thấy thông tin môn học.");
                    }
                } else {
                    outputArea.setText("Không tìm thấy thông tin.");
                }
            }
        });

        // Bắt sự kiện khi nút "Thêm/Cập nhật Sinh viên" được nhấn
        addOrUpdateStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to collect student information (code and name)
                String studentCode = JOptionPane.showInputDialog("Nhập mã sinh viên:");
                String studentName = JOptionPane.showInputDialog("Nhập tên sinh viên:");
        
                if (studentCode != null && studentName != null) {
                    Student newStudent = new Student(studentCode, studentName, null);
                    scoreService.addOrUpdateStudent(newStudent);
                    outputArea.setText("Thêm hoặc cập nhật sinh viên thành công.");
                } else {
                    outputArea.setText("Hủy thêm/cập nhật sinh viên.");
                }
            }
        });

        // Bắt sự kiện khi nút "Thêm/Cập nhật Môn học" được nhấn
        addOrUpdateSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to collect subject information (code and name)
                String subjectCode = JOptionPane.showInputDialog("Nhập mã môn học:");
                String subjectName = JOptionPane.showInputDialog("Nhập tên môn học:");
        
                if (subjectCode != null && subjectName != null) {
                    Subject newSubject = new Subject(subjectCode, subjectName, null);
                    scoreService.addOrUpdateSubject(newSubject);
                    outputArea.setText("Thêm hoặc cập nhật môn học thành công.");
                } else {
                    outputArea.setText("Hủy thêm/cập nhật môn học.");
                }
            }
        });

        // Bắt sự kiện khi nút "Thêm/Cập nhật Điểm số" được nhấn
        addOrUpdateScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mở cửa sổ hoặc thực hiện thao tác để thêm hoặc cập nhật Điểm số
            }
        });

        // Bắt sự kiện khi nút "Xóa Sinh viên" được nhấn
        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thực hiện xóa Sinh viên và cập nhật hiển thị
            }
        });

        // Bắt sự kiện khi nút "Xóa Môn học" được nhấn
        deleteSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thực hiện xóa Môn học và cập nhật hiển thị
            }
        });

        // Bắt sự kiện khi nút "Xóa Điểm số" được nhấn
        deleteScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thực hiện xóa Điểm số và cập nhật hiển thị
            }
        });



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScoreManagementGUI();
            }
        });
    }
}




