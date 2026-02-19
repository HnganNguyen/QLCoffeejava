package presention.GUI;

import shared.DTO.TableDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmPayment extends JDialog {

    private JLabel lblTotal, lblFinal, lblChange;
    private JTextField txtDiscount, txtCustomerPay;
    private double total;

    public FrmPayment(JFrame parent, TableDTO table, List<?> items) {
        super(parent, "Thanh toán", true);
        setSize(420, 360);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        total = calcTotal(items);

        // ===== HEADER =====
        JLabel lblTitle = new JLabel("THANH TOÁN", JLabel.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color(220, 53, 69));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setPreferredSize(new Dimension(0, 55));
        add(lblTitle, BorderLayout.NORTH);

        // ===== BODY =====
        JPanel body = new JPanel(new GridLayout(5, 2, 10, 10));
        body.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblTotal = new JLabel(format(total));
        lblFinal = new JLabel(format(total));
        lblChange = new JLabel("0 đ");

        txtDiscount = new JTextField("0");
        txtCustomerPay = new JTextField();

        body.add(new JLabel("Tổng tiền:"));
        body.add(lblTotal);

        body.add(new JLabel("Giảm giá (%):"));
        body.add(txtDiscount);

        body.add(new JLabel("Phải trả:"));
        body.add(lblFinal);

        body.add(new JLabel("Khách đưa:"));
        body.add(txtCustomerPay);

        body.add(new JLabel("Tiền thối:"));
        body.add(lblChange);

        add(body, BorderLayout.CENTER);

        // ===== FOOTER =====
        JButton btnOk = new JButton("XÁC NHẬN");
        JButton btnCancel = new JButton("HỦY");

        btnOk.setBackground(new Color(40, 167, 69));
        btnOk.setForeground(Color.WHITE);

        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);

        btnOk.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        footer.add(btnCancel);
        footer.add(btnOk);
        add(footer, BorderLayout.SOUTH);

        // ===== EVENT =====
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateFinal();
            }
        });

        txtCustomerPay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateChange();
            }
        });
    }

    private double calcTotal(List<?> list) {
        double sum = 0;
        for (Object o : list) {
            FrmOrder.OrderItem it = (FrmOrder.OrderItem) o;
            sum += it.getTotal();
        }
        return sum;
    }

    private void updateFinal() {
        try {
            double d = Double.parseDouble(txtDiscount.getText());
            double f = total - (total * d / 100);
            lblFinal.setText(format(f));
        } catch (Exception ignored) {}
    }

    private void updateChange() {
        try {
            double pay = Double.parseDouble(txtCustomerPay.getText());
            double f = parse(lblFinal.getText());
            lblChange.setText(format(pay - f));
        } catch (Exception ignored) {}
    }

    private String format(double v) {
        return String.format("%,.0f đ", v);
    }

    private double parse(String s) {
        return Double.parseDouble(s.replace("đ", "").replace(",", "").trim());
    }
}
