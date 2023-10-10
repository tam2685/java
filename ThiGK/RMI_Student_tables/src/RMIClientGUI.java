import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class RMIClientGUI {
    private ScoreService scoreService;
    private DefaultTableModel tableModel;

    private JFrame frame;
    private JTextField codeTextField;
    private JButton searchButton;
    private JTable resultTable;

    public RMIClientGUI() {
        initialize();
        try {
            // Kết nối đến dịch vụ RMI
            String registryURL = "rmi://localhost:3456/ScoreService";
            scoreService = (ScoreService) Naming.lookup(registryURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame("Score Lookup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Nhập mã sinh viên hoặc mã môn học:");
        codeTextField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã môn học");
        tableModel.addColumn("Mã sinh viên");
        tableModel.addColumn("Tên sinh viên");
        tableModel.addColumn("Tên môn học");
        tableModel.addColumn("Điểm");
        resultTable = new JTable(tableModel);

        panel.add(label, BorderLayout.NORTH);
        panel.add(codeTextField, BorderLayout.CENTER);
        panel.add(searchButton, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeTextField.getText();
                try {
                    // Gọi phương thức tìm kiếm dựa trên mã sinh viên hoặc mã môn học
                    Object result = null;
                    if (code.startsWith("SV")) {
                        result = scoreService.getScoresByStudentCode(code);
                    } else if (code.startsWith("M")) {
                        result = scoreService.getScoresBySubjectCode(code);
                    }

                    // Xóa tất cả dữ liệu hiện có trong bảng
                    tableModel.setRowCount(0);

                    if (result != null) {
                        if (result instanceof Student) {
                            Student student = (Student) result;
                            List<Score> scores = student.getScores();
                            if (scores != null && !scores.isEmpty()) {
                                for (Score score : scores) {
                                    tableModel.addRow(new Object[]{
                                            score.getSubjectCode(),
                                            student.getStudentCode(),
                                            student.getStudentName(),
                                            score.getSubjectName(),
                                            score.getScore()
                                    });
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Không có điểm.");
                            }
                        } else if (result instanceof Subject) {
                            Subject subject = (Subject) result;
                            List<Score> scores = subject.getScores();
                            if (scores != null && !scores.isEmpty()) {
                                for (Score score : scores) {
                                    // Lấy thông tin sinh viên từ cơ sở dữ liệu dựa trên studentCode
                                    Student student = scoreService.getScoresByStudentCode(score.getStudentCode());
                                    if (student != null) {
                                        tableModel.addRow(new Object[]{
                                                score.getSubjectCode(),
                                                score.getStudentCode(),
                                                student.getStudentName(),
                                                subject.getSubjectName(),
                                                score.getScore()
                                        });
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Không có điểm.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Không tìm thấy thông tin.");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RMIClientGUI();
            }
        });
    }
}
