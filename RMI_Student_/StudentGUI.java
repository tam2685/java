import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class StudentGUI extends JFrame implements ActionListener {
    private JTextField idField;
    private JButton searchButton;
    private JTable resultTable;
    private JScrollPane resultScrollPane;
    private StudentInfo studentInfo;

    public StudentGUI(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;

        setTitle("Student Search");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("Enter student ID: ");
        idField = new JTextField(15);
        searchButton = new JButton("Search");

        // Create a table model with column names and 0 rows (initially)
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[] { "Student ID", "Student Name", "Student Class" }, 0);
        resultTable = new JTable(tableModel);
        resultScrollPane = new JScrollPane(resultTable);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(searchButton);

        searchButton.addActionListener(this);

        add(panel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String studentId = idField.getText();
            if (!studentId.isEmpty()) {
                try {
                    Student student = studentInfo.findStudentById(studentId);
                    DefaultTableModel tableModel = (DefaultTableModel) resultTable.getModel();
                    tableModel.setRowCount(0); // Clear previous rows

                    if (student != null) {
                        // Add the student's information to the table
                        tableModel.addRow(new Object[] { student.getStudentId(), student.getStudentName(),
                                student.getStudentClass() });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            StudentInfo studentInfo = (StudentInfo) Naming.lookup("rmi://localhost:1234/StudentInfo");
            StudentGUI gui = new StudentGUI(studentInfo);
            gui.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
