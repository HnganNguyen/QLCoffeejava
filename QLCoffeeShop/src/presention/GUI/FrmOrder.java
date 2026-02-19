	package presention.GUI;
	
	import business.BLL.*;
	import shared.DTO.*;

	
	import javax.swing.*;
	import java.awt.*;
	import java.util.List;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;
	import javax.swing.table.DefaultTableModel;
	import javax.swing.border.BevelBorder;
	import java.awt.Window.Type;
	import javax.swing.border.LineBorder;
	import java.awt.event.*;
	import java.util.Date;

	
	public class FrmOrder extends JFrame {
	
	    private JPanel flpTable;
	    private JScrollPane spTable;
	
	    // ICON BÀN
		    private ImageIcon tableIcon;
		    private JTextField txtTuKhoa;
		    private JTable lstSanPham;
		    private JTable lstBill;
		    private JTextField txtHD;
		    private JTextField txtBan;
		    private JTextField txttotalPrice;
		    private DefaultTableModel modelSanPham;
		    private DefaultTableModel modelBill;
		    private JPanel chosenTable = null;
		    private JComboBox<TypeProductDTO> cbLoaiThucUong;
		    private JButton choseTable;   // bàn đang chọn
		    private JButton objTable = null; // bàn đang được chọn
		

	    private JButton btnThanhToan;

	    public FrmOrder() {
	    	setBackground(new Color(255, 255, 255));
	    	getContentPane().setBackground(Color.WHITE);
	        initUI();
	        loadTable();
	        System.out.println(cbLoaiThucUong);
	        loadTypeProduct();
	        
	    }
	
	    private void initUI() {
	        setTitle("Quản lý Order");
	        setSize(1394, 678);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	        // ===== LOAD ICON =====
	        tableIcon = new ImageIcon(
	        		getClass().getResource("/resources/images/ban.png")
	
	        );
	        getContentPane().setLayout(null);
	
	        // ===== PANEL LEFT =====
	        JPanel pnlLeft = new JPanel(new BorderLayout());
	        pnlLeft.setBounds(10, 40, 560, 500);
	        getContentPane().add(pnlLeft);
	
	        // ===== FLOW PANEL =====
	        flpTable = new JPanel();
	        flpTable.setLayout(new GridLayout(0, 3, 10, 10)); // 3 cột
	        flpTable.setBackground(Color.WHITE);

	        flpTable.setBackground(Color.WHITE);
	
	        // ===== SCROLL =====
	        spTable = new JScrollPane(flpTable);
	        spTable.setHorizontalScrollBarPolicy(
	                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
	        );
	        spTable.getVerticalScrollBar().setUnitIncrement(16);
	
	        pnlLeft.add(spTable, BorderLayout.CENTER);
	        
	        Panel panel = new Panel();
	        panel.setBackground(new Color(255, 235, 205));
	        panel.setBounds(1061, 46, 307, 573);
	        getContentPane().add(panel);
	        panel.setLayout(null);
	        
	        JLabel lblNewLabel_1 = new JLabel("Loại Sản Phẩm:");
	        lblNewLabel_1.setBounds(20, 48, 107, 15);
	        lblNewLabel_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
	        panel.add(lblNewLabel_1);
	        
	        txtTuKhoa = new JTextField();
	        txtTuKhoa.setFont(new Font("Tahoma", Font.PLAIN, 12));
	        txtTuKhoa.setBounds(137, 74, 160, 28);
	        panel.add(txtTuKhoa);
	        txtTuKhoa.setColumns(10);
	        
	        cbLoaiThucUong = new JComboBox<>();
	        cbLoaiThucUong.setBounds(137, 41, 160, 22);
	        panel.add(cbLoaiThucUong);

	        cbLoaiThucUong.addActionListener(e -> {
	            TypeProductDTO type = (TypeProductDTO) cbLoaiThucUong.getSelectedItem();
	            if (type != null) {
	                // status = 1 → sản phẩm đang bán
	                List<ProductDTO> listProduct =
	                        ProductBLL.getSanPhamByIDLoaiSP(type.getId(), 1);
	                loadSanPham(listProduct);
	            }
	        });


	        JLabel lblNewLabel_1_1 = new JLabel("Tên Sản phẩm");
	        lblNewLabel_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
	        lblNewLabel_1_1.setBounds(20, 87, 107, 15);
	        panel.add(lblNewLabel_1_1);
	        
	     // ===== TABLE SẢN PHẨM =====
	        modelSanPham = new DefaultTableModel(
	        		 new String[]{"ID", "Tên sản phẩm", "Giá bán"}, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        lstSanPham = new JTable(modelSanPham);
	        lstSanPham.setRowHeight(25);
	        lstSanPham.getTableHeader().setReorderingAllowed(false);
	        lstSanPham.getSelectionModel().addListSelectionListener(e -> {
	            if (!e.getValueIsAdjusting()) {
	                handleSelectSanPham();
	            }
	        });
	 

	        // scroll cho table
	        JScrollPane spSanPham = new JScrollPane(lstSanPham);
	        spSanPham.setBounds(10, 152, 287, 416);
	        panel.add(spSanPham);

	        
	        JButton btnTimKiem = new JButton("Tìm");
	        btnTimKiem.setForeground(new Color(0, 0, 0));
	        btnTimKiem.setBackground(new Color(128, 64, 0));
	        btnTimKiem.setFont(new Font("Microsoft Tai Le", Font.BOLD, 15));
	        btnTimKiem.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	        btnTimKiem.setBounds(39, 113, 89, 28);
	        panel.add(btnTimKiem);
	        
	        JButton btnLamMoisp = new JButton("Làm mới");
	        btnLamMoisp.setBackground(new Color(128, 64, 0));
	        btnLamMoisp.setForeground(new Color(0, 0, 0));
	        btnLamMoisp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	        btnLamMoisp.setFont(new Font("Tahoma", Font.BOLD, 14));
	        btnLamMoisp.setBounds(190, 112, 107, 29);
	        panel.add(btnLamMoisp);
	        
	        JLabel lblNewLabel = new JLabel("THÔNG TIN SẢN PHẨM");
	        lblNewLabel.setBounds(20, 16, 190, 14);
	        panel.add(lblNewLabel);
	        lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
	        panel_1.setBackground(new Color(255, 239, 213));
	        panel_1.setBounds(580, 46, 475, 573);
	        getContentPane().add(panel_1);
	        panel_1.setLayout(null);
	        
	        String[] columnNames = {
	        	    "STT",
	        	    "Tên sản phẩm",
	        	    "Số lượng",
	        	    "Đơn giá",
	        	    "Tổng tiền"
	        	};

	        	modelBill = new DefaultTableModel(columnNames, 0) {
	        	    @Override
	        	    public boolean isCellEditable(int row, int column) {
	        	        return false;
	        	    }
	        	};

	        	lstBill = new JTable(modelBill);
	        	lstBill.setRowHeight(25);
	        	lstBill.getTableHeader().setReorderingAllowed(false);

	        	lstBill.getColumnModel().getColumn(0).setPreferredWidth(40);
	        	lstBill.getColumnModel().getColumn(1).setPreferredWidth(200);
	        	lstBill.getColumnModel().getColumn(2).setPreferredWidth(70);
	        	lstBill.getColumnModel().getColumn(3).setPreferredWidth(90);
	        	lstBill.getColumnModel().getColumn(4).setPreferredWidth(110);

	        	JScrollPane spBill = new JScrollPane(lstBill);
	        	spBill.setBounds(10, 35, 455, 350);
	        	panel_1.add(spBill);

	        
	        JLabel lblThngTinBn = new JLabel("DANH MỤC ĐANG ĐƯỢC ORDER");
	        lblThngTinBn.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
	        lblThngTinBn.setBounds(10, 11, 263, 14);
	        panel_1.add(lblThngTinBn);
	        
	        JPanel panel_2 = new JPanel();
	        panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	        panel_2.setBackground(new Color(210, 180, 140));
	        panel_2.setBounds(10, 413, 455, 155);
	        panel_1.add(panel_2);
	        panel_2.setLayout(null);
	        
	        JLabel lblNewLabel_2 = new JLabel("Mã HĐ");
	        lblNewLabel_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
	        lblNewLabel_2.setBounds(25, 24, 63, 14);
	        panel_2.add(lblNewLabel_2);
	        
	        txtHD = new JTextField();
	        txtHD.setBounds(98, 11, 186, 31);
	        panel_2.add(txtHD);
	        txtHD.setColumns(10);
	        
	        JLabel lblNewLabel_2_1 = new JLabel("Bàn số:");
	        lblNewLabel_2_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 15));
	        lblNewLabel_2_1.setBounds(25, 69, 62, 14);
	        panel_2.add(lblNewLabel_2_1);
	        
	        txtBan = new JTextField();
	        txtBan.setColumns(10);
	        txtBan.setBounds(98, 58, 186, 29);
	        panel_2.add(txtBan);
	        
	        JLabel lblNewLabel_2_1_1 = new JLabel("Thời gian:");
	        lblNewLabel_2_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	        lblNewLabel_2_1_1.setBounds(25, 94, 81, 25);
	        panel_2.add(lblNewLabel_2_1_1);
	        
	        JLabel lblNewLabel_2_1_1_1 = new JLabel("Tổng tiền: ");
	        lblNewLabel_2_1_1_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	        lblNewLabel_2_1_1_1.setBounds(25, 130, 81, 14);
	        panel_2.add(lblNewLabel_2_1_1_1);
	        
	        txttotalPrice = new JTextField();
	        txttotalPrice.setText("0");
	        txttotalPrice.setHorizontalAlignment(SwingConstants.LEFT);
	        txttotalPrice.setColumns(10);
	        txttotalPrice.setBounds(98, 119, 186, 29);
	        panel_2.add(txttotalPrice);
	        
	        JLabel lblNewLabel_2_2 = new JLabel("(VNĐ)");
	        lblNewLabel_2_2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
	        lblNewLabel_2_2.setBounds(301, 130, 46, 14);
	        panel_2.add(lblNewLabel_2_2);
	        
	        btnThanhToan = new JButton("Thanh Toán");
	        btnThanhToan.setFont(new Font("Microsoft Tai Le", Font.BOLD, 14));
	        btnThanhToan.setBackground(new Color(255, 0, 0));
	        btnThanhToan.setForeground(new Color(255, 235, 205));
	        btnThanhToan.setBounds(310, 35, 135, 39);
	        panel_2.add(btnThanhToan);
	        
	        JButton btnChuyenBan = new JButton("Chuyển bàn");
	        btnChuyenBan.setForeground(new Color(255, 255, 255));
	        btnChuyenBan.setBackground(new Color(128, 64, 0));
	        btnChuyenBan.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
	        btnChuyenBan.setBounds(49, 551, 154, 33);
	        getContentPane().add(btnChuyenBan);
	        
	        JButton btnLamMoi = new JButton("Làm mới");
	        btnLamMoi.setForeground(new Color(255, 255, 255));
	        btnLamMoi.setBackground(new Color(128, 64, 64));
	        btnLamMoi.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
	        btnLamMoi.setBounds(323, 551, 154, 33);
	        getContentPane().add(btnLamMoi);
	        btnLamMoi.addActionListener(e -> btnLamMoiActionPerformed());

	        
	        JPanel panel_3 = new JPanel();
	        panel_3.setBackground(new Color(210, 180, 140));
	        panel_3.setBounds(0, -14, 1368, 56);
	        getContentPane().add(panel_3);
	        panel_3.setLayout(null);
	        
	        JLabel lblNewLabel_3 = new JLabel("ORDER");
	        lblNewLabel_3.setForeground(new Color(255, 255, 255));
	        lblNewLabel_3.setBounds(610, 11, 90, 45);
	        panel_3.add(lblNewLabel_3);
	        lblNewLabel_3.setBackground(new Color(0, 64, 0));
	        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
	        lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.PLAIN, 25));
	        


	    }
	
	    private void loadTable() {
	    	flpTable.removeAll();
	
	        List<TableDTO> tableList = TableBLL.getAllListTable();
	
	        for (TableDTO table : tableList) {
	            JPanel tableCard = createTableCard(table);
	            flpTable.add(tableCard);
	        }
	
	        flpTable.revalidate();
	        flpTable.repaint();
	        
	    }
	
	    private JPanel createTableCard(TableDTO table) {

	        JPanel pnlCard = new JPanel();
	        pnlCard.setPreferredSize(new Dimension(0, 160));
	        pnlCard.setLayout(new BoxLayout(pnlCard, BoxLayout.Y_AXIS));
	        pnlCard.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        pnlCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        pnlCard.setOpaque(true);
	        pnlCard.putClientProperty("TABLE", table);

	        // ===== TÊN BÀN =====
	        JLabel lblName = new JLabel(table.getNameTable(), SwingConstants.CENTER);
	        lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
	        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

	        // ===== ICON =====
	        Image img = tableIcon.getImage()
	                .getScaledInstance(48, 48, Image.SCALE_SMOOTH);
	        JLabel lblIcon = new JLabel(new ImageIcon(img));
	        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

	        // ===== TRẠNG THÁI =====
	        JLabel lblStatus = new JLabel("", SwingConstants.CENTER);
	        lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
	        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

	        if (table.getStatus() == 0) {
	            pnlCard.setBackground(new Color(213, 245, 227));
	            lblStatus.setText("Trống");
	            lblStatus.setForeground(new Color(0, 150, 0));
	        } else {
	            pnlCard.setBackground(new Color(250, 219, 216));
	            lblStatus.setText("Có người");
	            lblStatus.setForeground(Color.RED);
	        }

	        pnlCard.add(Box.createVerticalStrut(10));
	        pnlCard.add(lblName);
	        pnlCard.add(Box.createVerticalStrut(10));
	        pnlCard.add(lblIcon);
	        pnlCard.add(Box.createVerticalStrut(10));
	        pnlCard.add(lblStatus);

	        // ✅ CLICK BÀN → LOAD BILL
	        pnlCard.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {

	                // đổi màu bàn
	                hoverClickTable(pnlCard);

	                // load thông tin
	                txtBan.setText(table.getNameTable());
	                showBill(table.getID());
	            }
	        });

	       

	        // ⚠️ RETURN LUÔN Ở CUỐI
	        return pnlCard;
	    }


	    
	    private void showBill(int idTable) {

	        DefaultTableModel model = (DefaultTableModel) lstBill.getModel();
	        model.setRowCount(0); // clear table

	        int idBill = BillBLL.getIDBillNoPaymentByIDTable(idTable);
	        if (idBill == -1) {
	            txtHD.setText("");
	            txttotalPrice.setText("0");
	            return;
	        }
	        txtHD.setText("HD00" + idBill);

	        List<MenuDTO> menuList = MenuBLL.getListMenuByIDTable(idTable);
	        double totalPrice = 0;
	        int stt = 1;

	        for (MenuDTO m : menuList) {
	            Object[] row = {
	                stt++,
	                m.getNameProduct(),
	                m.getQuantity(),
	                String.format("%,.0f", m.getPriceBasic()),
	                String.format("%,.0f", m.getTotalPrice())
	            };
	            modelBill.addRow(row);
	            totalPrice += m.getTotalPrice();
	        }


	        txttotalPrice.setText(String.format("%,.0f", totalPrice));
	    }

	    private void hoverClickTable(JPanel clickedPanel) {

	        // 🔁 Trả màu bàn cũ
	        if (chosenTable != null) {
	            TableDTO oldTable = (TableDTO) chosenTable.getClientProperty("TABLE");

	            if (oldTable.getStatus() == 1) { // có người
	                chosenTable.setBackground(new Color(250, 219, 216)); // hồng
	            } else { // trống
	                chosenTable.setBackground(new Color(213, 245, 227)); // xanh
	            }
	        }

	        // 🎯 Tô màu bàn đang chọn
	        clickedPanel.setBackground(new Color(173, 216, 230)); // LightBlue
	        chosenTable = clickedPanel;
	    }
	    private void handleTableClick(JPanel pnlCard) {
	        // 🔵 đổi màu bàn (tương đương hoverClickButton)
	        hoverClickTable(pnlCard);
	        // reset thông tin
	        txtHD.setText("");
	        txtBan.setText("");
	        txttotalPrice.setText("0");
	        chosenTable = pnlCard;

	        // lấy DTO bàn
	        TableDTO table = (TableDTO) pnlCard.getClientProperty("TABLE");
	        int idTable = table.getID();

	        // set bàn
	        txtBan.setText(table.getNameTable());

	        // trạng thái control
	        btnThanhToan.setEnabled(false);
	        // cbLoaiThucUong.setEnabled(true);  // nếu bạn khai báo global
	        lstSanPham.setEnabled(true);

	        // kiểm tra trạng thái bàn
	        if (TableBLL.getStatusByIDTable(idTable) == 1) {
	            int idBill = BillBLL.getIDBillNoPaymentByIDTable(idTable);
	            if (idBill != -1) {
	                btnThanhToan.setEnabled(true);
	                showBill(idTable); // ❌ truyền table → dễ reset
	            }
	        }

	    }
	    private void loadTypeProduct() {

	        cbLoaiThucUong.removeAllItems(); // clear combobox
	  
	    
	        // load loại thức uống có trạng thái = 1
	        List<TypeProductDTO> listType =
	                TypeProductBLL.getListTypeProductByStatus(1);


	        for (TypeProductDTO type : listType) {
	            cbLoaiThucUong.addItem(type);
	        }
	    }

	    
	    private void loadSanPham(List<ProductDTO> listProduct) {
	        modelSanPham.setRowCount(0); // clear table

	        int stt = 1;
	        for (ProductDTO p : listProduct) {
	            Object[] row = {
	            		  p.getID(),       
	                p.getNameProducts(),
	                String.format("%,.0f VNĐ", p.getSalePrice()) // ✅ FIX Ở ĐÂY
	            };
	            modelSanPham.addRow(row);
	        }
	    }

	    private TableDTO createAddBillByIDTable(ProductDTO product) {

	        // 1️⃣ Lấy bàn đang chọn
	        if (chosenTable == null) return null;

	        TableDTO table = (TableDTO) chosenTable.getClientProperty("TABLE");
	        int idTable = table.getID();

	        // 2️⃣ Lấy hóa đơn chưa thanh toán theo bàn
	        int idBill = BillBLL.getIDBillNoPaymentByIDTable(idTable);

	        int idProduct = product.getID();
	        int quantity = 1;

	        // 3️⃣ Nếu chưa có hóa đơn → tạo mới
	        if (idBill == -1) {
	            BillBLL.insertBill(
	                new Date(),
	                0.0,
	                String.valueOf(AppContext.taiKhoanDangNhap.getId()), // ✔ KHỚP String
	                table.getID()
	            );

	            idBill = BillBLL.getIDBillMax();
	        }

	        // 4️⃣ Lấy số lượng sản phẩm hiện tại trong bill
	        quantity = ChiTietBillBLL.getSoLuongSanPham(idBill, idProduct);

	        // 5️⃣ Thêm / cập nhật chi tiết bill
	        ChiTietBillBLL.insertChiTietBill(
	                idBill,
	                idProduct,
	                quantity + 1
	        );

	        return table;
	    }
	    //=============================================================//
	    private void handleSelectSanPham() { // Hàm này chạy chưa HOÀN CHỈNH=> KHI THAO TÁC CHỌN SẢN PHẨM TỪ lstSamPham nó ko nhảy qua lstBill

	    	int row = lstSanPham.getSelectedRow();
	        if (row == -1) return;

	        // ===== LẤY ID SẢN PHẨM =====
	        int productId = Integer.parseInt(
	                modelSanPham.getValueAt(row, 0).toString()
	        );

	        String name = modelSanPham.getValueAt(row, 1).toString();

	        String priceStr = modelSanPham.getValueAt(row, 2).toString()
	                .replace("VNĐ", "")
	                .replace(",", "")
	                .trim();

	        double price = Double.parseDouble(priceStr);

	        ProductDTO product = new ProductDTO(
	                productId,
	                name,
	                price,
	                0,
	                1,
	                0
	        );

	        // ===== CHƯA CHỌN BÀN =====
	        if (chosenTable == null) {
	            JOptionPane.showMessageDialog(
	                    this,
	                    "Vui lòng chọn bàn trước khi thêm sản phẩm!",
	                    "Thông báo",
	                    JOptionPane.WARNING_MESSAGE
	            );
	            return;
	        }

	        // ===== LẤY BÀN =====
	        TableDTO table = (TableDTO) chosenTable.getClientProperty("TABLE");
	        int idTable = table.getID();

	        // ===== THÊM VÀO BILL =====
	        createAddBillByIDTable(product);

	        // ===== LOAD LẠI BILL =====
	        showBill(idTable);

	        // ===== LOAD LẠI MÀU BÀN =====
	        loadTable();
	    }
	    //============================================================================//
	    private void btnLamMoiActionPerformed() {

	        // ===== RESET THÔNG TIN HÓA ĐƠN =====
	        txtHD.setText("");
	        txtBan.setText("");
	        txttotalPrice.setText("0");

	        // ===== CLEAR BẢNG BILL =====
	        modelBill.setRowCount(0);

	        // ===== RESET BÀN ĐANG CHỌN =====
	        chosenTable = null;

	        // ===== DISABLE BUTTON =====
	        btnThanhToan.setEnabled(false);

	        // ===== RESET TÌM KIẾM / LOẠI SP =====
	        txtTuKhoa.setText("");
	        loadTypeProduct();      // tương đương _loadTypeProduct()

	        // ===== LOAD LẠI DANH SÁCH BÀN =====
	        loadTable();
	  
	    }
	   

 }
	
	
