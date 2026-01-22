package presention.GUI;

import business.BLL.TaiKhoanBLL;
import shared.DTO.TaiKhoanDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TaiKhoanGUI extends JFrame {

    private final Color SUB_COLOR = new Color(250, 245, 240);
    private final Color BTN_COLOR = new Color(176, 137, 91);

    private JTable tblTaiKhoan;
    private DefaultTableModel model;

    private JTextField txtId, txtTen, txtCCCD, txtSDT, txtDiaChi, txtLuong;
    private JPasswordField txtPass;
    private JComboBox<String> cboQuyen, cboTrangThai;

    public TaiKhoanGUI(String username, String role) {
        setTitle("Quản lý tài khoản");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "ACCOUNT", username, role), BorderLayout.NORTH);
        add(initContent(), BorderLayout.CENTER);

        loadTable();
        addTableEvent();
    }

    /* ================= CONTENT ================= */

    private JPanel initContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(SUB_COLOR);
        root.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                initForm(),
                initTable()
        );
        split.setResizeWeight(0);
        split.setDividerLocation(190);
        split.setDividerSize(4);
        split.setBorder(null);

        root.add(split, BorderLayout.CENTER);
        return root;
    }

    /* ================= FORM ================= */

    private JPanel initForm() {
        JPanel form = new JPanel(new GridLayout(1, 2, 20, 0));
        form.setBackground(SUB_COLOR);
        form.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));
        form.setPreferredSize(new Dimension(1000, 175));

        txtId = new JTextField();
        txtTen = new JTextField();
        txtPass = new JPasswordField();
        txtLuong = new JTextField();
        txtCCCD = new JTextField();
        txtSDT = new JTextField();
        txtDiaChi = new JTextField();

        cboQuyen = new JComboBox<>(new String[]{"Admin", "Phục vụ"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Khóa"});

        form.add(createColumnLeft());
        form.add(createColumnRight());

        return form;
    }

    private JPanel createColumnLeft() {
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(SUB_COLOR);

        GridBagConstraints g = baseGBC();

        addRow(left, g, 0, "Mã TK", txtId);
        addRow(left, g, 1, "Tên", txtTen);
        addRow(left, g, 2, "Mật khẩu", txtPass);
        addRow(left, g, 3, "Lương", txtLuong);
        addRow(left, g, 4, "Quyền", cboQuyen);
        addRow(left, g, 5, "Trạng thái", cboTrangThai);

        return left;
    }

    private JPanel createColumnRight() {
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(SUB_COLOR);

        GridBagConstraints g = baseGBC();

        addRow(right, g, 0, "CCCD", txtCCCD);
        addRow(right, g, 1, "SĐT", txtSDT);
        addRow(right, g, 2, "Địa chỉ", txtDiaChi);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlBtn.setBackground(SUB_COLOR);

        JButton btnAdd = createButton("Thêm");
        JButton btnUpdate = createButton("Sửa");
        JButton btnDelete = createButton("Xóa");
        JButton btnClear = createButton("Làm mới");

        btnAdd.addActionListener(e -> addTaiKhoan());
        btnUpdate.addActionListener(e -> updateTaiKhoan());
        btnDelete.addActionListener(e -> deleteTaiKhoan());
        btnClear.addActionListener(e -> clearForm());

        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        g.anchor = GridBagConstraints.EAST;
        right.add(pnlBtn, g);

        pnlBtn.add(btnAdd);
        pnlBtn.add(btnUpdate);
        pnlBtn.add(btnDelete);
        pnlBtn.add(btnClear);

        return right;
    }

    private GridBagConstraints baseGBC() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;
        return g;
    }

    private void addRow(JPanel pnl, GridBagConstraints g, int row, String label, JComponent field) {
        g.gridx = 0;
        g.gridy = row;
        g.weightx = 0.3;
        pnl.add(new JLabel(label), g);

        g.gridx = 1;
        g.weightx = 0.7;
        pnl.add(field, g);
    }

    /* ================= TABLE ================= */

    private JScrollPane initTable() {
        model = new DefaultTableModel(
                new String[]{"Mã TK", "Tên", "Quyền", "Trạng thái", "Lương"}, 0
        );
        tblTaiKhoan = new JTable(model);
        tblTaiKhoan.setRowHeight(30);

        JScrollPane sp = new JScrollPane(tblTaiKhoan);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));
        return sp;
    }

    /* ================= DATA ================= */

    private void loadTable() {
        model.setRowCount(0);
        for (TaiKhoanDTO tk : TaiKhoanBLL.getAllTaiKhoan()) {
            model.addRow(new Object[]{
                    tk.getId(),
                    tk.getTenTK(),
                    tk.getQuyen() == 1 ? "Admin" : "Phục vụ",
                    tk.getTrangThai() == 1 ? "Hoạt động" : "Khóa",
                    tk.getLuongByCa()
            });
        }
    }

    private void addTableEvent() {
        tblTaiKhoan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblTaiKhoan.getSelectedRow();
                if (row >= 0) {
                    String id = model.getValueAt(row, 0).toString();
                    loadForm(TaiKhoanBLL.getTaiKhoanById(id));
                }
            }
        });
    }

    private void loadForm(TaiKhoanDTO tk) {
        if (tk == null) return;

        txtId.setText(tk.getId());
        txtId.setEditable(false); // ❌ KHÓA MÃ TK

        txtTen.setText(tk.getTenTK());
        txtPass.setText(tk.getPassword()); // ✅ vẫn sửa được
        txtCCCD.setText(tk.getCccd());
        txtSDT.setText(tk.getSdt());
        txtDiaChi.setText(tk.getDiaChi());
        txtLuong.setText(String.valueOf(tk.getLuongByCa()));

        cboQuyen.setSelectedIndex(tk.getQuyen() == 1 ? 0 : 1);
        cboTrangThai.setSelectedIndex(tk.getTrangThai() == 1 ? 0 : 1);
    }

    /* ================= CRUD ================= */

    private void addTaiKhoan() {
        TaiKhoanDTO tk = getFormData();
        if (tk != null && TaiKhoanBLL.addTaiKhoan(tk)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadTable();
            clearForm();
        }
    }

    private void updateTaiKhoan() {
        if (txtId.getText().isEmpty()) return;
        TaiKhoanDTO tk = getFormData();
        if (tk != null && TaiKhoanBLL.updateTaiKhoan(tk)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadTable();
        }
    }

    private void deleteTaiKhoan() {
        if (txtId.getText().isEmpty()) return;
        if (JOptionPane.showConfirmDialog(this, "Xóa tài khoản?",
                "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            TaiKhoanBLL.deleteTaiKhoan(txtId.getText());
            loadTable();
            clearForm();
        }
    }

    private TaiKhoanDTO getFormData() {
        try {
            if (txtId.getText().trim().isEmpty())
                throw new Exception("Vui lòng nhập mã tài khoản");
            if (txtTen.getText().trim().isEmpty())
                throw new Exception("Vui lòng nhập tên");

            return new TaiKhoanDTO(
                    txtId.getText().trim(),
                    txtTen.getText(),
                    String.valueOf(txtPass.getPassword()),
                    txtCCCD.getText(),
                    txtSDT.getText(),
                    txtDiaChi.getText(),
                    cboQuyen.getSelectedIndex() == 0 ? 1 : 0,
                    cboTrangThai.getSelectedIndex() == 0 ? 1 : 0,
                    Double.parseDouble(txtLuong.getText())
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return null;
    }

    private void clearForm() {
        txtId.setText("");
        txtId.setEditable(true); // ✅ mở khóa khi thêm mới

        txtTen.setText("");
        txtPass.setText("");
        txtCCCD.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtLuong.setText("");

        cboQuyen.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        tblTaiKhoan.clearSelection();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}
