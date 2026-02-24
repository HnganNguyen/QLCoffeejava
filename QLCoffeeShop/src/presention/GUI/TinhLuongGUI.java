package presention.GUI;

import business.BLL.*;
import shared.DTO.*;
import data.DAL.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;
public class TinhLuongGUI extends JFrame {

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color SUB_COLOR = new Color(250, 245, 240);
    private final Color BTN_COLOR = new Color(176, 137, 91);

    private JComboBox<TaiKhoanDTO> cbNhanVien;
    private JTextField txtThang, txtSoCa;
    private JTextArea txtGhiChu;

    private JTable table;
    private DefaultTableModel model;

    private TinhLuongBLL bll = new TinhLuongBLL();

    public TinhLuongGUI(String username, String role) {

        setTitle("Quản lý Lương");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "SALARY", username, role), BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        content.setBackground(SUB_COLOR);

        content.add(createFormPanel(), BorderLayout.WEST);
        content.add(createTablePanel(), BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);

        loadComboBoxNhanVien();
        loadTable();
    }

    /* ================= FORM ================= */
    private JPanel createFormPanel() {

        JPanel pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(330, 0));
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBackground(Color.WHITE);
        pnl.setBorder(BorderFactory.createTitledBorder("Thông tin tính lương"));

        pnl.add(new JLabel("Nhân viên"));
        cbNhanVien = new JComboBox<>();
        pnl.add(cbNhanVien);
        pnl.add(Box.createVerticalStrut(10));

        txtThang = createField(pnl, "Tháng");
        txtSoCa = createField(pnl, "Số ca làm");

        pnl.add(new JLabel("Ghi chú"));
        txtGhiChu = new JTextArea(4, 20);
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        pnl.add(new JScrollPane(txtGhiChu));

        pnl.add(Box.createVerticalStrut(15));

        pnl.add(createButton("Thêm", e -> themLuong()));
        pnl.add(createButton("Thanh toán", e -> thanhToan()));
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
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(evt);
        hoverEffect(btn);
        return btn;
    }

    private void hoverEffect(JButton btn) {
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
                new String[]{
                        "STT", "Mã NV", "Tên NV", "SĐT", "Địa chỉ",
                        "Tháng", "Năm", "Ca", "Tổng", "Ghi chú", "Tình trạng"
                }, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        return new JScrollPane(table);
    }

    /* ================= LOAD DATA ================= */

    private void loadComboBoxNhanVien() {
        cbNhanVien.removeAllItems();
        List<TaiKhoanDTO> list = TinhLuongDAL.getAllNhanVien();
        for (TaiKhoanDTO tk : list) {
            if (tk.getQuyen() == 0) {
                cbNhanVien.addItem(tk);
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<TinhLuongDTO> list = bll.getAllTinhLuong();
        int stt = 1;

        for (TinhLuongDTO tl : list) {
            model.addRow(new Object[]{
                    stt++,
                    tl.getMaTaiKhoan(),
                    bll.getTenNhanVien(tl.getMaTaiKhoan()),
                    bll.getSDTNhanVien(tl.getMaTaiKhoan()),
                    bll.getDiaChiNhanVien(tl.getMaTaiKhoan()),
                    tl.getThang(),
                    tl.getNam(),
                    tl.getCa(),
                    formatTien(tl.getTong()),
                    tl.getGhiChu(),
                    tl.getTinhTrang() == 1 ? "Đã thanh toán" : "Chưa thanh toán"
            });
        }
    }

    /* ================= CHỨC NĂNG ================= */

    private void themLuong() {
        try {
            TaiKhoanDTO tk = (TaiKhoanDTO) cbNhanVien.getSelectedItem();
            if (tk == null) {
                JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên!");
                return;
            }

            String maTK = tk.getId();
            int thang = Integer.parseInt(txtThang.getText());
            int nam = LocalDateTime.now().getYear();
            int ca = Integer.parseInt(txtSoCa.getText());

            double luongCB = bll.getLuongCoBan(maTK);
            double tong = luongCB * ca;

            TinhLuongDTO tl = new TinhLuongDTO(
                    0,
                    thang,
                    nam,
                    Timestamp.valueOf(LocalDateTime.now()),
                    ca,
                    tong,
                    txtGhiChu.getText(),
                    0,
                    maTK
            );

            if (bll.themLuong(tl)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable();
                clearForm();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void thanhToan() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần thanh toán!");
            return;
        }

        String maTK = model.getValueAt(row, 1).toString();
        int thang = Integer.parseInt(model.getValueAt(row, 5).toString());
        int nam = Integer.parseInt(model.getValueAt(row, 6).toString());

        if (bll.thanhToanLuong(maTK, thang, nam)) {
            JOptionPane.showMessageDialog(this, "Đã thanh toán!");
            loadTable();
        }
    }
    private String formatTien(double amount) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getNumberInstance(localeVN);
        return currencyVN.format(amount) + " đ";
    }
    private void clearForm() {
        txtThang.setText("");
        txtSoCa.setText("");
        txtGhiChu.setText("");
        table.clearSelection();
    }
}