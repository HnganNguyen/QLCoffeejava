package presention.GUI;

import business.BLL.ProductBLL;
import business.BLL.TypeProductBLL;
import shared.DTO.ProductDTO;
import shared.DTO.TypeProductDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class ProductGUI extends JFrame {

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color SUB_COLOR = new Color(250, 245, 240);
    private final Color BTN_COLOR = new Color(176, 137, 91);

    private JTable tblProduct;
    private DefaultTableModel model;

    private JTextField txtID, txtName, txtPrice, txtSalePrice, txtImg;
    private JComboBox<String> cboStatus;
    private JComboBox<TypeProductDTO> cboType;
    private JLabel lblPreview;

    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnChooseImg;
	private String role;
	private String username;

    public ProductGUI(String username, String role) {
    	this.username = username;
        this.role = role;
        setTitle("Coffee Shop System - Sản phẩm");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadTypeProduct();
        loadData();
    }

    /* ================= UI ================= */

    private void initUI() {

        setLayout(new BorderLayout());

        /* ================= TOP PANEL (DÙNG CHUNG) ================= */
        add(new TopPanel(this, "PRODUCT", username, role), BorderLayout.NORTH);

        /* ================= FORM ================= */
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(SUB_COLOR);
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 8, 6, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtID = new JTextField(); txtID.setEditable(false);
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtSalePrice = new JTextField();
        txtImg = new JTextField();

        cboStatus = new JComboBox<>(new String[]{"Ngưng bán", "Đang bán"});
        cboType = new JComboBox<>();

        int row = 0;
        addRow(pnlForm, g, row++, "Mã SP:", txtID);
        addRow(pnlForm, g, row++, "Tên SP:", txtName);
        addRow(pnlForm, g, row++, "Giá cơ bản:", txtPrice);
        addRow(pnlForm, g, row++, "Giá KM:", txtSalePrice);
        addRow(pnlForm, g, row++, "Loại SP:", cboType);
        addRow(pnlForm, g, row++, "Trạng thái:", cboStatus);

        g.gridx = 0; g.gridy = row;
        pnlForm.add(new JLabel("Ảnh:"), g);

        JPanel pnlImg = new JPanel(new GridBagLayout());
        pnlImg.setBackground(SUB_COLOR);

        GridBagConstraints gi = new GridBagConstraints();
        gi.insets = new Insets(0, 0, 0, 6);
        gi.fill = GridBagConstraints.HORIZONTAL;

        gi.gridx = 0; gi.weightx = 1;
        pnlImg.add(txtImg, gi);

        btnChooseImg = createButton("Chọn ảnh");
        btnChooseImg.setPreferredSize(new Dimension(95, 28));
        btnChooseImg.addActionListener(e -> chooseImage());

        gi.gridx = 1; gi.weightx = 0;
        pnlImg.add(btnChooseImg, gi);

        g.gridx = 1; g.weightx = 1;
        pnlForm.add(pnlImg, g);

        /* ================= PREVIEW ================= */
        lblPreview = new JLabel();
        lblPreview.setHorizontalAlignment(JLabel.CENTER);
        lblPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblPreview.setPreferredSize(new Dimension(150, 150));

        JPanel pnlPreview = new JPanel(new BorderLayout());
        pnlPreview.setBackground(SUB_COLOR);
        pnlPreview.setBorder(BorderFactory.createTitledBorder("Xem trước"));
        pnlPreview.add(lblPreview);

        JPanel pnlLeft = new JPanel(new BorderLayout(10, 10));
        pnlLeft.setBackground(SUB_COLOR);
        pnlLeft.setPreferredSize(new Dimension(360, 0));
        pnlLeft.add(pnlForm, BorderLayout.CENTER);
        pnlLeft.add(pnlPreview, BorderLayout.SOUTH);

        /* ================= TABLE ================= */
        model = new DefaultTableModel(
                new Object[]{"Mã", "Tên", "Giá", "KM", "Loại", "Trạng thái", "IMG"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblProduct = new JTable(model);
        tblProduct.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tblProduct);

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
        pnlCenter.add(pnlLeft, BorderLayout.WEST);
        pnlCenter.add(scroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        /* ================= EVENT ================= */
        tblProduct.getSelectionModel().addListSelectionListener(e -> showDetail());
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnRefresh.addActionListener(e -> clearForm());
    }

    /* ================= LOGIC ================= */

    private void loadTypeProduct() {
        cboType.removeAllItems();
        for (TypeProductDTO t : TypeProductBLL.getAllListTypeProduct()) {
            if (t.getStatus() == 1) cboType.addItem(t);
        }
    }

    private void loadData() {
        model.setRowCount(0);
        for (ProductDTO p : ProductBLL.getAllListProduct()) {
            model.addRow(new Object[]{
                    p.getID(),
                    p.getNameProducts(),
                    p.getPriceBasic(),
                    p.getSalePrice(),
                    getTypeNameById(p.getIDTypeProduct()),
                    p.getStatus() == 1 ? "Đang bán" : "Ngưng bán",
                    p.getImg()
            });
        }
    }

    private void showDetail() {
        int r = tblProduct.getSelectedRow();
        if (r >= 0) {
            txtID.setText(model.getValueAt(r, 0).toString());
            txtName.setText(model.getValueAt(r, 1).toString());
            txtPrice.setText(model.getValueAt(r, 2).toString());
            txtSalePrice.setText(model.getValueAt(r, 3).toString());
            txtImg.setText(model.getValueAt(r, 6) == null ? "" : model.getValueAt(r, 6).toString());
            previewImage(txtImg.getText());
        }
    }

    private void addProduct() {
        ProductDTO p = new ProductDTO(
                0,
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtSalePrice.getText()),
                cboStatus.getSelectedIndex(),
                ((TypeProductDTO) cboType.getSelectedItem()).getId(),
                txtImg.getText()
        );
        if (ProductBLL.addProduct(p)) {
            loadData();
            clearForm();
        }
    }

    private void updateProduct() {
        if (txtID.getText().isEmpty()) return;
        ProductDTO p = new ProductDTO(
                Integer.parseInt(txtID.getText()),
                txtName.getText(),
                Double.parseDouble(txtPrice.getText()),
                Double.parseDouble(txtSalePrice.getText()),
                cboStatus.getSelectedIndex(),
                ((TypeProductDTO) cboType.getSelectedItem()).getId(),
                txtImg.getText()
        );
        if (ProductBLL.updateProduct(p)) loadData();
    }

    private void deleteProduct() {
        if (txtID.getText().isEmpty()) return;
        if (JOptionPane.showConfirmDialog(
                this,
                "Xóa sản phẩm này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            ProductBLL.deleteProduct(Integer.parseInt(txtID.getText()));
            loadData();
            clearForm();
        }
    }

    private void clearForm() {
        txtID.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtSalePrice.setText("");
        txtImg.setText("");
        lblPreview.setIcon(null);
        tblProduct.clearSelection();
    }

    /* ================= IMAGE ================= */

    private void chooseImage() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            txtImg.setText(f.getAbsolutePath());
            previewImage(f.getAbsolutePath());
        }
    }

    private void previewImage(String path) {
        if (path == null || path.isEmpty()) {
            lblPreview.setIcon(null);
            return;
        }
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        lblPreview.setIcon(new ImageIcon(img));
    }

    /* ================= HELPER ================= */

    private void addRow(JPanel p, GridBagConstraints g, int y, String label, JComponent comp) {
        g.gridx = 0; g.gridy = y; g.weightx = 0;
        p.add(new JLabel(label), g);
        g.gridx = 1; g.weightx = 1;
        p.add(comp, g);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private String getTypeNameById(int idType) {
        for (TypeProductDTO t : TypeProductBLL.getAllListTypeProduct()) {
            if (t.getId() == idType) return t.getNameType();
        }
        return "";
    }
}
