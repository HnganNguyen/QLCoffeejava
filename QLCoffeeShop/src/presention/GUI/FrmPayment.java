package presention.GUI;

import business.BLL.BillBLL;
import business.BLL.ChiTietBillBLL;
import business.BLL.TableBLL;
import shared.DTO.*;
import shared.utils.BillPDFUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrmPayment extends JDialog {

    private JLabel lblTotal, lblFinal, lblChange;
    private JTextField txtDiscount, txtCustomerPay;

    private double total;
    private TableDTO table;
    private List<FrmOrder.OrderItem> items;

    public FrmPayment(JFrame parent, TableDTO table, List<FrmOrder.OrderItem> items) {
        super(parent, "Thanh toán", true);
        this.table = table;
        this.items = items;

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

        btnOk.addActionListener(e -> doPayment());
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

    // ================== THANH TOÁN + XUẤT BILL ==================
    private void doPayment() {
        try {
            double finalTotal = parse(lblFinal.getText());

            if (finalTotal <= 0) {
                JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!");
                return;
            }

            // ===== LẤY GIẢM GIÁ & TIỀN KHÁCH ĐƯA =====
            double discountPercent = 0;
            double customerPay = 0;

            try {
                discountPercent = Double.parseDouble(txtDiscount.getText());
            } catch (Exception ignored) {}

            try {
                customerPay = Double.parseDouble(txtCustomerPay.getText());
            } catch (Exception ignored) {}

            if (customerPay < finalTotal) {
                JOptionPane.showMessageDialog(this, "Khách đưa chưa đủ tiền!");
                return;
            }

            // ===== 1. INSERT BILL =====
            BillDTO bill = new BillDTO();
            bill.setCreateDay(new Date());
            bill.setTotal(finalTotal);
            bill.setIdTable(table.getID());
            bill.setEmploy(AppContext.taiKhoanDangNhap.getId());
            bill.setStatus(1);

            int billId = BillBLL.insertBill(bill);
            if (billId <= 0) {
                JOptionPane.showMessageDialog(this, "Không tạo được hóa đơn!");
                return;
            }
            bill.setID(billId);

            // ===== 2. INSERT CHI TIẾT BILL =====
            List<ChiTietBillDTO> listCT = new ArrayList<>();

            for (FrmOrder.OrderItem it : items) {
                ChiTietBillDTO ct = new ChiTietBillDTO();
                ct.setMaBill(billId);
                ct.setIdProduct(it.product.getID());
                ct.setSoLuong(it.quantity);

                double gia = it.product.getSalePrice() > 0
                        ? it.product.getSalePrice()
                        : it.product.getPriceBasic();

                ChiTietBillBLL.insertChiTietBill(ct, gia);
                listCT.add(ct);
            }

            // ===== 3. UPDATE TABLE -> TRỐNG =====
            TableBLL.updateTableStatus(table.getID(), 0);

            // ===== 4. XUẤT + MỞ BILL PDF =====
            String filePath =
                    "bill_" + billId + "_" + System.currentTimeMillis() + ".pdf";

            BillPDFUtil.exportBill(
                    filePath,
                    bill,
                    listCT,
                    discountPercent,
                    customerPay
            );

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(filePath));
            }

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi thanh toán / xuất bill!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================== HỖ TRỢ ==================
    private double calcTotal(List<FrmOrder.OrderItem> list) {
        double sum = 0;
        for (FrmOrder.OrderItem it : list) {
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
        return Double.parseDouble(
                s.replace("đ", "")
                 .replace(",", "")
                 .trim()
        );
    }
}