package presention.GUI;

import business.BLL.TypeProductBLL;
import shared.DTO.TypeProductDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TypeProductGUI extends JFrame {

    private JTable tblType;
    private DefaultTableModel model;
    private JTextField txtID, txtName;
    private JComboBox<String> cboStatus;

    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    /* ===== COLORS (ĐỒNG BỘ HOME) ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color SUB_COLOR = new Color(250, 245, 240);
    private final Color BTN_COLOR = new Color(176, 137, 91);
	private String username;
	private String role;

    public TypeProductGUI(String username, String role) {
    	this.username = username;
        this.role = role;
        
        setTitle("Coffee Shop System - Loại sản phẩm");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadData();
    }

    private void initUI() {

        setLayout(new BorderLayout());

        /* ================= TOP PANEL (DÙNG CHUNG) ================= */
        add(new TopPanel(this, "TYPE", username, role), BorderLayout.NORTH);

        /* ================= FORM ================= */
        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 12, 12));
        pnlForm.setBackground(SUB_COLOR);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin loại sản phẩm"));

        txtID = new JTextField();
        txtID.setEditable(false);
        txtName = new JTextField();
        cboStatus = new JComboBox<>(new String[]{"Ngưng hoạt động", "Hoạt động"});

        pnlForm.add(new JLabel("Mã loại:"));
        pnlForm.add(txtID);
        pnlForm.add(new JLabel("Tên loại:"));
        pnlForm.add(txtName);
        pnlForm.add(new JLabel("Trạng thái:"));
        pnlForm.add(cboStatus);

        /* ================= TABLE ================= */
        model = new DefaultTableModel(
                new Object[]{"Mã", "Tên loại", "Trạng thái"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblType = new JTable(model);
        tblType.setRowHeight(28);
        tblType.getTableHeader().setBackground(MAIN_COLOR);
        tblType.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tblType);

        /* ================= BUTTON ================= */
        JPanel pnlButton = new JPanel();
        pnlButton.setBackground(SUB_COLOR);

        btnAdd = createButton("Thêm");
        btnUpdate = createButton("Sửa");
        btnDelete = createButton("Xóa");
        btnRefresh = createButton("Làm mới");

        pnlButton.add(btnAdd);
        pnlButton.add(btnUpdate);
        pnlButton.add(btnDelete);
        pnlButton.add(btnRefresh);

        /* ================= CENTER ================= */
        JPanel pnlCenter = new JPanel(new BorderLayout(15, 15));
        pnlCenter.setBackground(SUB_COLOR);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlCenter.add(pnlForm, BorderLayout.WEST);
        pnlCenter.add(scroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        /* ================= EVENT ================= */
        tblType.getSelectionModel().addListSelectionListener(e -> showDetail());
        btnAdd.addActionListener(e -> addType());
        btnUpdate.addActionListener(e -> updateType());
        btnDelete.addActionListener(e -> deleteType());
        btnRefresh.addActionListener(e -> clearForm());
    }

    /* ================= UI HELPERS ================= */

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    /* ================= LOGIC ================= */

    private void loadData() {
        model.setRowCount(0);
        for (TypeProductDTO t : TypeProductBLL.getAllListTypeProduct()) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getNameType(),
                    t.getStatus() == 1 ? "Hoạt động" : "Ngưng"
            });
        }
    }

    private void showDetail() {
        int r = tblType.getSelectedRow();
        if (r >= 0) {
            txtID.setText(model.getValueAt(r, 0).toString());
            txtName.setText(model.getValueAt(r, 1).toString());
            cboStatus.setSelectedIndex(
                    model.getValueAt(r, 2).equals("Hoạt động") ? 1 : 0
            );
        }
    }

    private void addType() {
        if (txtName.getText().isEmpty()) return;
        TypeProductDTO t = new TypeProductDTO(0, txtName.getText(), cboStatus.getSelectedIndex());
        if (TypeProductBLL.insertTypeProduct(t)) {
            loadData();
            clearForm();
        }
    }

    private void updateType() {
        if (txtID.getText().isEmpty()) return;
        TypeProductDTO t = new TypeProductDTO(
                Integer.parseInt(txtID.getText()),
                txtName.getText(),
                cboStatus.getSelectedIndex()
        );
        if (TypeProductBLL.updateTypeProduct(t)) loadData();
    }

    private void deleteType() {
        if (txtID.getText().isEmpty()) return;
        if (JOptionPane.showConfirmDialog(
                this,
                "Xóa loại này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            TypeProductDTO t = new TypeProductDTO();
            t.setId(Integer.parseInt(txtID.getText()));
            if (TypeProductBLL.deleteTypeProduct(t)) {
                loadData();
                clearForm();
            }
        }
    }

    private void clearForm() {
        txtID.setText("");
        txtName.setText("");
        cboStatus.setSelectedIndex(0);
        tblType.clearSelection();
    }
}
