import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;

public class ProductClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Nhập mã sản phẩm:");
        JTextField codeTextField = new JTextField(10);
        JButton searchButton = new JButton("Tìm kiếm");

        JTextArea resultTextArea = new JTextArea(8, 30);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        panel.add(label);
        panel.add(codeTextField);
        panel.add(searchButton);
        panel.add(scrollPane);

        frame.add(panel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String productCode = codeTextField.getText();

                    // Tạo đối tượng Product từ mã sản phẩm nhập vào
                    Product product = new Product(productCode, "", "", 0.0);

                    // Gọi phương thức từ máy chủ
                    ProductService productService = (ProductService) Naming.lookup("rmi://localhost:3456/ProductService");
                    List<Product> products = productService.findProductByCode(product);

                    // Kiểm tra xem danh sách sản phẩm có rỗng không
                    if (!products.isEmpty()) {
                        // Hiển thị kết quả
                        resultTextArea.setText("");
                        for (Product p : products) {
                            resultTextArea.append("Mã sản phẩm: " + p.getCode() + "\n");
                            resultTextArea.append("Tên sản phẩm: " + p.getName() + "\n");
                            resultTextArea.append("Đơn vị tính: " + p.getUnit() + "\n");
                            resultTextArea.append("Giá: " + p.getPrice() + "\n");
                            resultTextArea.append("------------\n");
                        }
                    } else {
                        resultTextArea.setText("Không tìm thấy sản phẩm");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}
