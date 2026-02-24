package presention.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import business.BLL.TableBLL;

public class HomeGUI extends JFrame {

    private final Color SUB_COLOR = new Color(250, 245, 240);

    private JLabel lblTotalTable;
    private JLabel lblUsingTable;
    private JLabel lblBillToday;
    private JLabel lblRevenueToday;

    public HomeGUI(String username, String role) {

        setTitle("Coffee Shop System");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "HOME", username, role), BorderLayout.NORTH);

        initDashboard();
        loadTableStatistic();
        loadTodayStatistic(); // thêm dòng này
    }

    /* ================= DASHBOARD ================= */

    private void initDashboard() {

        JPanel pnl = new JPanel(new GridLayout(2, 2, 20, 20));
        pnl.setBackground(SUB_COLOR);
        pnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTotalTable = new JLabel("0", JLabel.CENTER);
        lblUsingTable = new JLabel("0", JLabel.CENTER);
        lblBillToday = new JLabel("0", JLabel.CENTER);
        lblRevenueToday = new JLabel("0 VNĐ", JLabel.CENTER);

        pnl.add(createCard("Tổng số bàn", lblTotalTable));
        pnl.add(createCard("Bàn đang sử dụng", lblUsingTable));
        pnl.add(createCard("Hóa đơn hôm nay", lblBillToday));
        pnl.add(createCard("Doanh thu hôm nay", lblRevenueToday));

        add(pnl, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10,0,5,0));

        value.setFont(new Font("Segoe UI", Font.BOLD, 30));
        value.setForeground(new Color(33,150,243));

        p.add(lblTitle, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);

        return p;
    }

    /* ================= LOAD BÀN ================= */

    private void loadTableStatistic() {
        lblTotalTable.setText(String.valueOf(TableBLL.getAllListTable().size()));
        lblUsingTable.setText(String.valueOf(TableBLL.getListTableHaveStatusOne().size()));
    }

    /* ================= LOAD HÔM NAY ================= */

    private void loadTodayStatistic() {

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/qlcoffeeshop",
                "root",
                "")) {

            /* ===== HÓA ĐƠN HÔM NAY ===== */
            String sqlBill = """
                    SELECT COUNT(*) AS SOHD
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    AND DATE(NGAYTAO) = CURDATE()
                    """;

            PreparedStatement psBill = conn.prepareStatement(sqlBill);
            ResultSet rsBill = psBill.executeQuery();

            if (rsBill.next()) {
                lblBillToday.setText(rsBill.getString("SOHD"));
            }

            /* ===== DOANH THU HÔM NAY ===== */
            String sqlRevenue = """
                    SELECT IFNULL(SUM(TONGTIEN),0) AS DOANHTHU
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    AND DATE(NGAYTAO) = CURDATE()
                    """;

            PreparedStatement psRevenue = conn.prepareStatement(sqlRevenue);
            ResultSet rsRevenue = psRevenue.executeQuery();

            if (rsRevenue.next()) {
                double revenue = rsRevenue.getDouble("DOANHTHU");
                lblRevenueToday.setText(String.format("%,.0f VNĐ", revenue));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi load thống kê hôm nay!");
        }
    }
}