package presention.GUI;

import business.BLL.*;
import shared.DTO.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

public class FrmOrderNew extends JFrame {

    // ================= COLOR =================
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color BTN_COLOR  = new Color(176, 137, 91);
    private final Color BG_COLOR   = new Color(250, 245, 240);
    private final Color BTN_GRAY   = new Color(140, 140, 140);

    // ================= COMPONENT =================
    private JPanel pnlTableList;
    private JPanel pnlProductList;
    private JTable tblBill;
    private DefaultTableModel modelBill;

    private JTextField txtMaHD;
    private JTextField txtBan;
    private JTextField txtTongTien;

    private JPanel selectedTable = null;

    // ================= CONSTRUCTOR =================
    public FrmOrderNew() {
        setTitle("ORDER");
        setSize(1400, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        initHeader();
        initBody();

        loadTable();
        loadProduct();
    }

    // ================= HEADER =================
    private void initHeader() {
        JLabel lbl = new JLabel("ORDER", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        lbl.setOpaque(true);
        lbl.setBackground(MAIN_COLOR);
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(0, 60));
        add(lbl, BorderLayout.NORTH);
    }

    // ================= BODY =================
    private void initBody() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(new EmptyBorder(10, 10, 10, 10));
        main.setBackground(BG_COLOR);
        add(main);

        // LEFT – TABLE
        pnlTableList = new JPanel(new GridLayout(0, 2, 10, 10));
        pnlTableList.setBackground(BG_COLOR);
        JScrollPane spTable = new JScrollPane(pnlTableList);
        spTable.setPreferredSize(new Dimension(250, 0));
        main.add(spTable, BorderLayout.WEST);

        // CENTER – BILL
        main.add(initBillPanel(), BorderLayout.CENTER);

        // RIGHT – PRODUCT
        pnlProductList = new JPanel(new GridLayout(0, 2, 10, 10));
        pnlProductList.setBackground(BG_COLOR);
        JScrollPane spProduct = new JScrollPane(pnlProductList);
        spProduct.setPreferredSize(new Dimension(420, 0));
        main.add(spProduct, BorderLayout.EAST);
    }

    // ================= BILL PANEL =================
    private JPanel initBillPanel() {
        JPanel pnl = new JPanel(new BorderLayout(10, 10));
        pnl.setBackground(BG_COLOR);

        // TABLE BILL
        modelBill = new DefaultTableModel(
                new String[]{"Tên SP", "SL", "Đơn giá", "Tổng"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblBill = new JTable(modelBill);
        tblBill.setRowHeight(26);
        pnl.add(new JScrollPane(tblBill), BorderLayout.CENTER);

        // INFO
        JPanel info = new JPanel(new GridLayout(3, 2, 10, 10));
        info.setBorder(new TitledBorder("Thông tin hóa đơn"));
        info.setBackground(BG_COLOR);

        txtMaHD = new JTextField();
        txtBan = new JTextField();
        txtTongTien = new JTextField("0");

        txtMaHD.setEditable(false);
        txtBan.setEditable(false);
        txtTongTien.setEditable(false);

        info.add(new JLabel("Mã HĐ:"));
        info.add(txtMaHD);
        info.add(new JLabel("Bàn:"));
        info.add(txtBan);
        info.add(new JLabel("Tổng tiền:"));
        info.add(txtTongTien);

        pnl.add(info, BorderLayout.SOUTH);
        return pnl;
    }

    // ================= LOAD TABLE =================
    private void loadTable() {
        pnlTableList.removeAll();

        for (TableDTO t : TableBLL.getAllListTable()) {
            JPanel card = createTableCard(t);
            pnlTableList.add(card);
        }

        pnlTableList.revalidate();
        pnlTableList.repaint();
    }

    private JPanel createTableCard(TableDTO table) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BorderLayout());
        pnl.setBorder(new LineBorder(Color.GRAY));
        pnl.setBackground(table.getStatus() == 0
                ? new Color(213, 245, 227)
                : new Color(250, 219, 216));

        pnl.putClientProperty("TABLE", table);

        JLabel lbl = new JLabel(table.getNameTable(), SwingConstants.CENTER);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
        pnl.add(lbl);

        pnl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectTable(pnl);
            }
        });

        return pnl;
    }

    private void selectTable(JPanel pnl) {
        if (selectedTable != null)
            selectedTable.setBorder(new LineBorder(Color.GRAY));

        pnl.setBorder(new LineBorder(Color.BLUE, 2));
        selectedTable = pnl;

        TableDTO table = (TableDTO) pnl.getClientProperty("TABLE");
        txtBan.setText(table.getNameTable());

        showBill(table.getID());
    }

    // ================= LOAD PRODUCT =================
    private void loadProduct() {
        pnlProductList.removeAll();

        for (ProductDTO p : ProductBLL.getAllListProduct()) {
            pnlProductList.add(createProductCard(p));
        }

        pnlProductList.revalidate();
        pnlProductList.repaint();
    }

    private JPanel createProductCard(ProductDTO p) {
        JPanel pnl = new JPanel(new BorderLayout());
        pnl.setBorder(new LineBorder(Color.GRAY));
        pnl.setBackground(Color.WHITE);

        pnl.putClientProperty("PRODUCT", p);

        JLabel name = new JLabel(p.getNameProducts(), SwingConstants.CENTER);
        JLabel price = new JLabel(String.format("%,.0f VNĐ", p.getSalePrice()),
                SwingConstants.CENTER);

        name.setFont(new Font("Tahoma", Font.BOLD, 13));
        price.setForeground(Color.RED);

        pnl.add(name, BorderLayout.CENTER);
        pnl.add(price, BorderLayout.SOUTH);

        pnl.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addProductToBill(p);
            }
        });

        return pnl;
    }

    // ================= ADD PRODUCT =================
    private void addProductToBill(ProductDTO product) {

        if (selectedTable == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn!");
            return;
        }

        TableDTO table = (TableDTO) selectedTable.getClientProperty("TABLE");
        int idTable = table.getID();

        int idBill = BillBLL.getIDBillNoPaymentByIDTable(idTable);

        if (idBill == -1) {
            BillBLL.insertBill(new Date(), 0,
                    AppContext.taiKhoanDangNhap.getId(),
                    idTable);
            idBill = BillBLL.getIDBillMax();
            TableBLL.updateStatusTable(1, idTable);
        }

        int qty = ChiTietBillBLL.getSoLuongSanPham(idBill, product.getID());
        ChiTietBillBLL.insertChiTietBill(idBill, product.getID(), qty + 1);

        showBill(idTable);
        loadTable();
    }

    // ================= SHOW BILL =================
    private void showBill(int idTable) {
        modelBill.setRowCount(0);

        int idBill = BillBLL.getIDBillNoPaymentByIDTable(idTable);
        if (idBill == -1) return;

        txtMaHD.setText("HD" + idBill);

        double total = 0;
        for (MenuDTO m : MenuBLL.getListMenuByIDTable(idTable)) {
            modelBill.addRow(new Object[]{
                    m.getNameProduct(),
                    m.getQuantity(),
                    String.format("%,.0f", m.getPriceBasic()),
                    String.format("%,.0f", m.getTotalPrice())
            });
            total += m.getTotalPrice();
        }

        txtTongTien.setText(String.format("%,.0f", total));
    }
}
