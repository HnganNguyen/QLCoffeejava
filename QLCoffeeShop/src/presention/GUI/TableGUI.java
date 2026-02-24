package presention.GUI;

import business.BLL.TableBLL;
import shared.DTO.TableDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TableGUI extends JFrame {

    private final Color SUB_COLOR = new Color(250, 245, 240);

    private JPanel pnlTableList;
    private JTextField txtTableName;

    private String username;
    private String role;

    public TableGUI(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("Quản lý Bàn");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
        loadTable();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== TOP =====
        add(new TopPanel(this, "TABLE", username, role), BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBackground(SUB_COLOR);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== ACTION =====
        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlAction.setBackground(SUB_COLOR);

        txtTableName = new JTextField(15);
        JButton btnAdd = new JButton("Thêm bàn");
        JButton btnDelete = new JButton("Xoá bàn");

        pnlAction.add(new JLabel("Tên bàn:"));
        pnlAction.add(txtTableName);
        pnlAction.add(btnAdd);
        pnlAction.add(btnDelete);

        // ===== LIST (GRID -> SCROLL DỌC) =====
        pnlTableList = new JPanel();
        pnlTableList.setBackground(SUB_COLOR);

        // 🔥 4 bàn / hàng, tự xuống dòng
        pnlTableList.setLayout(new GridLayout(0, 4, 15, 15));

        JScrollPane scroll = new JScrollPane(pnlTableList);
        scroll.setBorder(null);

        // ❌ KHÔNG CHO CUỘN NGANG
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        pnlCenter.add(pnlAction, BorderLayout.NORTH);
        pnlCenter.add(scroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addTable());
        btnDelete.addActionListener(e -> deleteTable());
    }

    // ================= LOAD TABLE =================
    private void loadTable() {
        pnlTableList.removeAll();

        List<TableDTO> list = TableBLL.getAllListTable();
        for (TableDTO tb : list) {
            pnlTableList.add(createTableButton(tb));
        }

        pnlTableList.revalidate();
        pnlTableList.repaint();
    }

    // ================= CREATE BUTTON =================
    private JButton createTableButton(TableDTO tb) {

        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(
                TableBLL.TAB_WIDTH,
                TableBLL.TAB_HEIGHT
        ));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);

        updateButtonUI(btn, tb);

        btn.addActionListener(e -> {
            int newStatus = tb.getStatus() == 0 ? 1 : 0;

            boolean success = TableBLL.updateStatusTable(newStatus, tb.getID());

            if (success) {
                tb.setStatus(newStatus);
                updateButtonUI(btn, tb);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cập nhật trạng thái bàn thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        return btn;
    }

    // ================= UPDATE UI =================
    private void updateButtonUI(JButton btn, TableDTO tb) {

        String text = """
                <html>
                    <center>
                        🪑 %s<br>
                        <b>%s</b>
                    </center>
                </html>
                """.formatted(
                tb.getNameTable(),
                tb.getStatus() == 0 ? "Trống" : "Đang dùng"
        );

        btn.setText(text);

        if (tb.getStatus() == 0) {
            btn.setBackground(new Color(120, 180, 140)); // xanh
        } else {
            btn.setBackground(new Color(180, 90, 90)); // đỏ
        }
    }

    // ================= ADD =================
    private void addTable() {
        String name = txtTableName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên bàn không được để trống!");
            return;
        }

        if (TableBLL.insertTable(new TableDTO(0, name, 0))) {
            txtTableName.setText("");
            loadTable();
        }
    }

    // ================= DELETE =================
    private void deleteTable() {
        String name = txtTableName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập tên bàn để xoá!");
            return;
        }

        for (TableDTO tb : TableBLL.getAllListTable()) {
            if (tb.getNameTable().equalsIgnoreCase(name)) {
                TableBLL.deleteTable(tb);
                loadTable();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy bàn!");
    }
}