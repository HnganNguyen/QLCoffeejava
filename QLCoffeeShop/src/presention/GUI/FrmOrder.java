package presention.GUI;
import business.BLL.*;
import shared.DTO.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FrmOrder  extends JFrame {
	
	private JPanel flpTable;
    private JTable tblBill, tblSanPham;
    private JComboBox<TypeProductDTO> cbLoaiThucUong;
    private JButton btnThanhToan, btnLamMoi, btnXoaSp;
    private JTextField txtHD, txtBan, txtTotalPrice, txtTuKhoa;
    private JLabel lblNgayHienTai;

    private TableDTO choseTable;
    private TableDTO currentTable;

    private Timer timer;

    public FrmOrder() {
        initUI();
        loadTable();
//        loadTypeProduct();
//        startTimer();
    }
    
    private void initUI() {
        setTitle("Quản lý Order");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        flpTable = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane spTable = new JScrollPane(flpTable);

        cbLoaiThucUong = new JComboBox<>();
        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setEnabled(false);

        txtHD = new JTextField(10);
        txtBan = new JTextField(10);
        txtTotalPrice = new JTextField("0");

        lblNgayHienTai = new JLabel();

        add(spTable, BorderLayout.WEST);
    }
    private void loadTable() {
        flpTable.removeAll();

        List<TableDTO> tableList = TableBLL.getAllListTable();

        for (TableDTO table : tableList) {
            JButton btn = new JButton(table.getNameTable());
            btn.setPreferredSize(new Dimension(120, 80));
            btn.putClientProperty("TABLE", table);

            if (TableBLL.getStatusByIDTable(table.getID()) == 0) {
                btn.setBackground(new Color(174, 214, 241));
                btn.setText(table.getNameTable() + "\nTrống");
            } else {
                btn.setBackground(new Color(52, 152, 219));
                btn.setText(table.getNameTable() + "\nCó người");
            }

//            btn.addActionListener(this::tableClick);
            flpTable.add(btn);
        }

        flpTable.revalidate();
        flpTable.repaint();
    }

}
