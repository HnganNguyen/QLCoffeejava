package presention.GUI;

import business.BLL.ProductBLL;
import business.BLL.TableBLL;
import business.BLL.TypeProductBLL;
import shared.DTO.ProductDTO;
import shared.DTO.TableDTO;
import shared.DTO.TypeProductDTO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class FrmOrder extends JFrame {

    // ===== TABLE =====
	// đang tạo hóa đơn hay chưa
	private boolean isOrdering = false;
    private JPanel pnlTableWrap;
    private TableDTO selectedTable;
    private JPanel selectedTablePanel;

    // ===== PRODUCT =====
    private JPanel pnlProductWrap;
    private JTextField txtSearchProduct;
    private JComboBox<TypeProductDTO> cboTypeProduct;

    // ===== ORDER =====
    private JPanel pnlOrderList;
    private JLabel lblBillId, lblTableName, lblTotalPrice;

    private Map<Integer, List<OrderItem>> orderMap = new HashMap<>();

    public FrmOrder() {
        initUI();
        loadTables();
        loadTypeProduct();
        cboTypeProduct.addActionListener(e -> searchProduct());
        loadProducts();
    }

    private void initUI() {
        setTitle("Quản lý Order");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JLabel lblHeader = new JLabel("ORDER", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(210, 180, 140));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setPreferredSize(new Dimension(0, 70));
        add(lblHeader, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        add(body, BorderLayout.CENTER);

        // ================= LEFT: TABLE =================
        pnlTableWrap = new JPanel(new GridBagLayout());
        pnlTableWrap.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pnlTableWrap.setBackground(Color.WHITE);

        JScrollPane spTable = new JScrollPane(
                pnlTableWrap,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(460, 0));
        left.add(spTable, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(0, 45));
        btnRefresh.setBackground(new Color(139, 69, 19));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> loadTables());
        left.add(btnRefresh, BorderLayout.SOUTH);

        body.add(left, BorderLayout.WEST);

        // ================= CENTER: ORDER =================
        JPanel center = new JPanel(new BorderLayout());
        center.setPreferredSize(new Dimension(360, 0));
        center.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel lblOrder = new JLabel("MÓN ĐÃ CHỌN");
        lblOrder.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblOrder.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        center.add(lblOrder, BorderLayout.NORTH);

        pnlOrderList = new JPanel();
        pnlOrderList.setLayout(new BoxLayout(pnlOrderList, BoxLayout.Y_AXIS));
        pnlOrderList.setBackground(Color.WHITE);

        JScrollPane spOrder = new JScrollPane(pnlOrderList);
        spOrder.setBorder(null);
        center.add(spOrder, BorderLayout.CENTER);

        JPanel bill = new JPanel(new GridLayout(4, 1, 5, 5));
        bill.setBorder(
        	    BorderFactory.createCompoundBorder(
        	        new LineBorder(new Color(200, 200, 200)),
        	        BorderFactory.createEmptyBorder(10, 10, 10, 10)
        	    )
        	);
        bill.setBackground(new Color(240, 240, 240));

        lblBillId = new JLabel("Mã HĐ: ---");
        lblTableName = new JLabel("Bàn: ---");
        lblTotalPrice = new JLabel("Tổng tiền: 0 đ");
        lblTotalPrice.setForeground(Color.RED);
        lblTotalPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnPay = new JButton("THANH TOÁN");
        btnPay.setBackground(new Color(220, 53, 69)); 
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnPay.setFocusPainted(false);
        btnPay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPay.addActionListener(e -> handlePay());


        bill.add(lblBillId);
        bill.add(lblTableName);
        bill.add(lblTotalPrice);
        bill.add(btnPay);

        center.add(bill, BorderLayout.SOUTH);
        body.add(center, BorderLayout.CENTER);

     // ================= RIGHT: PRODUCT =================
        JPanel right = new JPanel(new BorderLayout(10, 10));
        right.setPreferredSize(new Dimension(480, 0));
        right.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblSP = new JLabel("SẢN PHẨM");
        lblSP.setFont(new Font("Segoe UI", Font.BOLD, 18));

        cboTypeProduct = new JComboBox<>();
        txtSearchProduct = new JTextField();

        JButton btnSearch = new JButton("Tìm");
        btnSearch.addActionListener(e -> searchProduct());

        JButton btnResetFilter = new JButton("Làm mới");
        btnResetFilter.addActionListener(e -> resetProductFilter());

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        btnPanel.add(btnSearch);
        btnPanel.add(btnResetFilter);

        JPanel filter = new JPanel(new BorderLayout(5, 5));
        filter.add(cboTypeProduct, BorderLayout.WEST);
        filter.add(txtSearchProduct, BorderLayout.CENTER);
        filter.add(btnPanel, BorderLayout.EAST);

        JPanel topRight = new JPanel(new BorderLayout(5, 5));
        topRight.add(lblSP, BorderLayout.NORTH);
        topRight.add(filter, BorderLayout.SOUTH);

        right.add(topRight, BorderLayout.NORTH);

        // ===== PRODUCT GRID: 3 SP / 1 DÒNG =====
        pnlProductWrap = new JPanel();
        pnlProductWrap.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 12));
        pnlProductWrap.setBackground(Color.WHITE);
        pnlProductWrap.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JScrollPane spProduct = new JScrollPane(
                pnlProductWrap,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        spProduct.getVerticalScrollBar().setUnitIncrement(16);
        spProduct.setBorder(null);

        right.add(spProduct, BorderLayout.CENTER);
        body.add(right, BorderLayout.EAST);

    }

    // ================= TABLE =================
    private void loadTables() {
        pnlTableWrap.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        int col = 0, row = 0;

        for (TableDTO t : TableBLL.getAllListTable()) {
            gbc.gridx = col;
            gbc.gridy = row;

            pnlTableWrap.add(createTableCard(t), gbc);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // ép layout dồn lên trên, không giãn
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        pnlTableWrap.add(Box.createGlue(), gbc);

        pnlTableWrap.revalidate();
        pnlTableWrap.repaint();
    }


    private JPanel createTableCard(TableDTO table) {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(135, 135));
        p.setLayout(new BorderLayout());
        p.setBorder(new LineBorder(Color.GRAY, 1));

        boolean isBusy = table.getStatus() == 1;

        p.setBackground(isBusy
                ? new Color(255, 220, 220)
                : new Color(220, 255, 235));

        JLabel name = new JLabel(table.getNameTable(), JLabel.CENTER);
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel icon = new JLabel("☕", JLabel.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        JLabel status = new JLabel(
                isBusy ? "Có người" : "Trống",
                JLabel.CENTER
        );
        status.setForeground(isBusy ? Color.RED : new Color(0, 130, 0));

        p.add(name, BorderLayout.NORTH);
        p.add(icon, BorderLayout.CENTER);
        p.add(status, BorderLayout.SOUTH);

        // ===== BÀN CÓ NGƯỜI → KHÓA CỨNG =====
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (selectedTablePanel != null) {
                    selectedTablePanel.setBorder(new LineBorder(Color.GRAY, 1));
                }

                selectedTablePanel = p;
                selectedTable = table;
                p.setBorder(new LineBorder(Color.BLUE, 2));

                refreshOrderList();
            }
        });

        // ===== CHỈ BÀN TRỐNG MỚI GẮN CLICK =====
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // đang tạo hóa đơn → không đổi bàn
//                if (isOrdering && selectedTable != null
//                        && table.getID() != selectedTable.getID()) {
//
//                    JOptionPane.showMessageDialog(
//                            FrmOrder.this,
//                            "Đang tạo hóa đơn cho bàn hiện tại!",
//                            "Cảnh báo",
//                            JOptionPane.WARNING_MESSAGE
//                    );
//                    return;
//                }

                if (selectedTablePanel != null) {
                    selectedTablePanel.setBorder(new LineBorder(Color.GRAY, 1));
                }

                selectedTablePanel = p;
                selectedTable = table;
                p.setBorder(new LineBorder(Color.BLUE, 2));
                refreshOrderList();
            }
        });

        return p;
    }



    // ================= PRODUCT =================
    private void resetProductFilter() {
        // reset ô tìm kiếm
        txtSearchProduct.setText("");

        // reset loại sản phẩm về "Tất cả"
        if (cboTypeProduct.getItemCount() > 0) {
            cboTypeProduct.setSelectedIndex(0);
        }

        // load lại toàn bộ sản phẩm
        loadProducts();
    }

    private void loadTypeProduct() {
        cboTypeProduct.removeAllItems();
        cboTypeProduct.addItem(new TypeProductDTO(0, "Tất cả", 1));
        for (TypeProductDTO t : TypeProductBLL.getListTypeProductByStatus(1))
            cboTypeProduct.addItem(t);
    }

    private void loadProducts() {
        pnlProductWrap.removeAll();

        for (ProductDTO p : ProductBLL.getAllListProduct()) {
            pnlProductWrap.add(createProductCard(p));
        }

        pnlProductWrap.revalidate();
        pnlProductWrap.repaint();
    }

    private void searchProduct() {
        pnlProductWrap.removeAll();

        String keyword = txtSearchProduct.getText().trim().toLowerCase();
        TypeProductDTO type = (TypeProductDTO) cboTypeProduct.getSelectedItem();

        for (ProductDTO p : ProductBLL.getAllListProduct()) {

            // lọc loại
            if (type != null && type.getId() != 0) {
                if (p.getIDTypeProduct() != type.getId()) continue;
            }

            // lọc tên
            if (!keyword.isEmpty()) {
                if (!p.getNameProducts().toLowerCase().contains(keyword)) continue;
            }

            pnlProductWrap.add(createProductCard(p));
        }

        pnlProductWrap.revalidate();
        pnlProductWrap.repaint();
    }

    private JPanel createProductCard(ProductDTO p) {

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(135, 190));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));

        // ===== HÌNH =====
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setPreferredSize(new Dimension(140, 100));

        try {
            ImageIcon icon = new ImageIcon(p.getImg());
            Image img = icon.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText("No Image");
        }

        // ===== TÊN + GIÁ =====
        JLabel name = new JLabel(
                "<html><center>" + p.getNameProducts() + "</center></html>",
                JLabel.CENTER
        );
        name.setFont(new Font("Segoe UI", Font.BOLD, 13));

        double price = p.getSalePrice() > 0 ? p.getSalePrice() : p.getPriceBasic();
        JLabel lblPrice = new JLabel(
                String.format("%,.0f đ", price),
                JLabel.CENTER
        );
        lblPrice.setForeground(new Color(0, 140, 0));
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel bottom = new JPanel(new GridLayout(2, 1));
        bottom.setOpaque(false);
        bottom.add(name);
        bottom.add(lblPrice);

        card.add(imgLabel, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);

        // ===== CLICK =====
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedTable == null) {
                    JOptionPane.showMessageDialog(FrmOrder.this, "Chọn bàn trước!");
                    return;
                }
                addProductToOrder(p);
            }
        });

        return card;
    }


    // ================= ORDER =================
    private void addProductToOrder(ProductDTO p) {
    	 int id = selectedTable.getID();

    	    orderMap.putIfAbsent(id, new ArrayList<>());

    	    // Nếu bàn chưa có món → tức là mới bắt đầu order
    	    if (orderMap.get(id).isEmpty()) {

    	        // Nếu bàn đang trống thì hỏi xác nhận
    	        if (selectedTable.getStatus() == 0) {

    	            int confirm = JOptionPane.showConfirmDialog(
    	                    null,
    	                    "Bạn đang tạo bàn mới?\nTiếp tục order cho bàn này?",
    	                    "Xác nhận",
    	                    JOptionPane.YES_NO_OPTION
    	            );

    	            if (confirm != JOptionPane.YES_OPTION) {
    	                return; // Người dùng chọn No → hủy add món
    	            }

    	            // Update DB thành Có người
    	            TableBLL.updateStatusTable(id, 1);

    	            // Cập nhật lại trạng thái trong object
    	            selectedTable.setStatus(1);

    	            // Reload lại bàn để đổi màu
    	            loadTables();
    	        }
    	    }

    	    // Nếu sản phẩm đã tồn tại thì tăng số lượng
    	    for (OrderItem it : orderMap.get(id)) {
    	        if (it.product.getID() == p.getID()) {
    	            it.quantity++;
    	            refreshOrderList();
    	            return;
    	        }
    	    }

    	    // Nếu chưa có thì thêm mới
    	    orderMap.get(id).add(new OrderItem(p));
    	    refreshOrderList();
    }

    private void refreshOrderList() {
        pnlOrderList.removeAll();

        if (selectedTable == null) {
            lblBillId.setText("Mã HĐ: ---");
            lblTableName.setText("Bàn: ---");
            lblTotalPrice.setText("Tổng tiền: 0 đ");
            isOrdering = false;
            return;
        }

        double total = 0;
        List<OrderItem> list = orderMap.get(selectedTable.getID());

        if (list != null && !list.isEmpty()) {
            for (OrderItem it : list) {
                pnlOrderList.add(createOrderRow(it));
                total += it.getTotal();
            }
            // ===== đang có món → khóa bàn =====
            isOrdering = true;
        } else {
            // ===== không còn món → mở khóa =====
            isOrdering = false;
        }

        lblBillId.setText("Mã HĐ: HD-" + selectedTable.getID());
        lblTableName.setText("Bàn: " + selectedTable.getNameTable());
        lblTotalPrice.setText(String.format("Tổng tiền: %,.0f đ", total));

        pnlOrderList.revalidate();
        pnlOrderList.repaint();
    }
    private void handlePay() {

        // chưa chọn bàn
        if (selectedTable == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn!");
            return;
        }

        List<OrderItem> list = orderMap.get(selectedTable.getID());

        // chưa có món
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có món để thanh toán!");
            return;
        }

        // hỏi xác nhận
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xác nhận thanh toán cho " + selectedTable.getNameTable() + "?",
                "Thanh toán",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        // mở form thanh toán
        FrmPayment frm = new FrmPayment(this, selectedTable, list);
        frm.setVisible(true);

        // ===== SAU KHI ĐÓNG FORM THANH TOÁN =====
        // reset order
        orderMap.remove(selectedTable.getID());
        selectedTable = null;

        // mở khóa bàn
        isOrdering = false;
        selectedTablePanel = null;

        refreshOrderList();
        loadTables();
    }


    private JPanel createOrderRow(OrderItem it) {
        JPanel row = new JPanel(new BorderLayout(5, 5));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        row.setBorder(new LineBorder(new Color(220, 220, 220)));
        row.setBackground(Color.WHITE);

        // ===== TÊN + GIÁ =====
        double priceOne = it.product.getSalePrice() > 0
                ? it.product.getSalePrice()
                : it.product.getPriceBasic();

        JLabel lblName = new JLabel(it.product.getNameProducts());
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblPrice = new JLabel(
                String.format("%,.0f đ", it.getTotal())
        );
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPrice.setForeground(new Color(0, 140, 0));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        left.add(lblName);
        left.add(lblPrice);

     // ===== NÚT  -   SỐ LƯỢNG   +  =====
        Dimension btnSize = new Dimension(36, 36);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);

        // ----- NÚT GIẢM -----
        JButton btnMinus = new JButton("-");
        btnMinus.setPreferredSize(btnSize);
        btnMinus.setMinimumSize(btnSize);
        btnMinus.setMaximumSize(btnSize);
        btnMinus.setFont(btnFont);
        btnMinus.setMargin(new Insets(0, 0, 0, 0));
        btnMinus.setFocusPainted(false);
        btnMinus.setBackground(new Color(220, 53, 69)); // đỏ
        btnMinus.setForeground(Color.WHITE);
        btnMinus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ----- SỐ LƯỢNG -----
        JLabel lblQty = new JLabel(String.valueOf(it.quantity), JLabel.CENTER);
        lblQty.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblQty.setPreferredSize(new Dimension(36, 36));
        lblQty.setBorder(new LineBorder(new Color(200, 200, 200)));
        lblQty.setOpaque(true);
        lblQty.setBackground(Color.WHITE);

        // ----- NÚT TĂNG -----
        JButton btnPlus = new JButton("+");
        btnPlus.setPreferredSize(btnSize);
        btnPlus.setMinimumSize(btnSize);
        btnPlus.setMaximumSize(btnSize);
        btnPlus.setFont(btnFont);
        btnPlus.setMargin(new Insets(0, 0, 0, 0));
        btnPlus.setFocusPainted(false);
        btnPlus.setBackground(new Color(40, 167, 69)); // xanh
        btnPlus.setForeground(Color.WHITE);
        btnPlus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ===== SỰ KIỆN =====
        btnMinus.addActionListener(e -> {
            it.quantity--;
            if (it.quantity <= 0) {
                orderMap.get(selectedTable.getID()).remove(it);
            }
            refreshOrderList();
        });

        btnPlus.addActionListener(e -> {
            it.quantity++;
            refreshOrderList();
        });

        // ===== PANEL PHẢI =====
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        right.setOpaque(false);
        right.add(btnMinus);
        right.add(lblQty);
        right.add(btnPlus);

        row.add(left, BorderLayout.CENTER);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    class OrderItem {
        ProductDTO product;
        int quantity = 1;
        OrderItem(ProductDTO p) { product = p; }
        double getTotal() {
            double price = product.getSalePrice() > 0 ? product.getSalePrice() : product.getPriceBasic();
            return price * quantity;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmOrder().setVisible(true));
    }
}
