package presention.GUI;

import business.BLL.NguyenLieuBLL;
import business.BLL.TaiKhoanBLL;
import shared.DTO.NguyenLieuDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class NguyenLieuGUI extends JFrame {

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color SUB_COLOR = new Color(250, 245, 240);
    private final Color BTN_COLOR = new Color(176, 137, 91);

    private String maTaiKhoan;

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMa, txtTen, txtGia;
    private JTextArea txtGhiChu;

    public NguyenLieuGUI(String username, String role) {
        this.maTaiKhoan = TaiKhoanBLL.getMaTaiKhoanByUsername(username);

        setTitle("Quản lý Nguyên Liệu");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "INGREDIENT", username, role), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        content.setBackground(SUB_COLOR);

        content.add(createFormPanel(), BorderLayout.WEST);
        content.add(createTablePanel(), BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);

        loadTable();
    }

    /* ================= FORM ================= */
    private JPanel createFormPanel() {
        JPanel pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(330, 0));
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin nguyên liệu"));

        txtMa = createField(pnl, "Mã nguyên liệu");
        txtMa.setEnabled(false);

        txtTen = createField(pnl, "Tên nguyên liệu");
        txtGia = createField(pnl, "Giá gốc");

        pnl.add(new JLabel("Ghi chú"));
        txtGhiChu = new JTextArea(4, 20);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        pnl.add(new JScrollPane(txtGhiChu));

        pnl.add(Box.createVerticalStrut(15));

        pnl.add(createButton("Thêm", e -> insert()));
        pnl.add(createButton("Sửa", e -> update()));
        pnl.add(createButton("Xóa", e -> delete()));
        pnl.add(createButton("Làm mới", e -> clearForm()));

        return pnl;
    }

    private JTextField createField(JPanel pnl, String label) {
        JLabel lb = new JLabel(label);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnl.add(lb);

        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        pnl.add(txt);
        pnl.add(Box.createVerticalStrut(8));
        return txt;
    }

    private JButton createButton(String text, java.awt.event.ActionListener evt) {
        JButton btn = new JButton(text);
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.addActionListener(evt);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        pnlHover(btn);
        return btn;
    }

    private void pnlHover(JButton btn) {
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_COLOR);
            }
        });
    }

    /* ================= TABLE ================= */
    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(
                new String[]{"Mã", "Tên nguyên liệu", "Giá gốc", "Ghi chú"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        table.getSelectionModel().addListSelectionListener(e -> {
            if (table.getSelectedRow() >= 0) {
                int r = table.getSelectedRow();
                txtMa.setText(model.getValueAt(r, 0).toString());
                txtTen.setText(model.getValueAt(r, 1).toString());
                txtGia.setText(model.getValueAt(r, 2).toString());
                txtGhiChu.setText(model.getValueAt(r, 3).toString());
            }
        });

        return new JScrollPane(table);
    }

    /* ================= CRUD ================= */
    private void loadTable() {
        model.setRowCount(0);
        List<NguyenLieuDTO> list = NguyenLieuBLL.getAll();
        for (NguyenLieuDTO nl : list) {
            model.addRow(new Object[]{
                    nl.getMa(),
                    nl.getTen(),
                    nl.getGiaGoc(),
                    nl.getGhiChu()
            });
        }
    }

    private void insert() {
        if (!validateInput()) return;

        NguyenLieuDTO nl = new NguyenLieuDTO();
        nl.setTen(txtTen.getText().trim());
        nl.setGiaGoc(Double.parseDouble(txtGia.getText()));
        nl.setGhiChu(txtGhiChu.getText());
        nl.setNgayTao(new Date());
        nl.setMaTaiKhoan(maTaiKhoan);

        if (NguyenLieuBLL.insert(nl)) {
            JOptionPane.showMessageDialog(this, "✅ Thêm nguyên liệu thành công");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        if (txtMa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn nguyên liệu cần sửa");
            return;
        }
        if (!validateInput()) return;

        NguyenLieuDTO nl = new NguyenLieuDTO();
        nl.setMa(Integer.parseInt(txtMa.getText()));
        nl.setTen(txtTen.getText().trim());
        nl.setGiaGoc(Double.parseDouble(txtGia.getText()));
        nl.setGhiChu(txtGhiChu.getText());

        if (NguyenLieuBLL.update(nl)) {
            JOptionPane.showMessageDialog(this, "✅ Cập nhật thành công");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Cập nhật thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        if (txtMa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Chưa chọn nguyên liệu để xóa");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa nguyên liệu này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int ma = Integer.parseInt(txtMa.getText());
            if (NguyenLieuBLL.delete(ma)) {
                JOptionPane.showMessageDialog(this, "🗑 Đã xóa thành công");
                loadTable();
                clearForm();
            }
        }
    }

    private boolean validateInput() {
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Tên nguyên liệu không được để trống");
            return false;
        }
        try {
            double gia = Double.parseDouble(txtGia.getText());
            if (gia < 0) throw new NumberFormatException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Giá gốc không hợp lệ");
            return false;
        }
        return true;
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtGia.setText("");
        txtGhiChu.setText("");
        table.clearSelection();
    }
}
