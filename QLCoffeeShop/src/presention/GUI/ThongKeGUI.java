package presention.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.toedter.calendar.JDateChooser;


import java.io.FileOutputStream;

public class ThongKeGUI extends JFrame {

    private JComboBox<String> cboType;
    private JDateChooser dateFrom, dateTo;
    private JPanel chartContainer;

    private String username;
    private String role;

    public ThongKeGUI(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("Thống Kê");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "STATISTIC", username, role), BorderLayout.NORTH);

        // ===== FILTER PANEL =====
        JPanel topFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topFilter.setBorder(BorderFactory.createTitledBorder("Bộ lọc thống kê"));
        topFilter.setBackground(Color.WHITE);

        cboType = new JComboBox<>(new String[]{
                "Doanh thu theo ngày",
                "Doanh thu theo tháng",
                "Sản phẩm bán chạy",
                "Số hóa đơn"
        });
        cboType.setPreferredSize(new Dimension(200, 30));

        dateFrom = new JDateChooser();
        dateFrom.setPreferredSize(new Dimension(130, 30));

        dateTo = new JDateChooser();
        dateTo.setPreferredSize(new Dimension(130, 30));

        JButton btnFilter = new JButton("Lọc");
        btnFilter.setBackground(new Color(33,150,243));
        btnFilter.setForeground(Color.WHITE);

        JButton btnExport = new JButton("Xuất Excel");
        btnExport.setBackground(new Color(76,175,80));
        btnExport.setForeground(Color.WHITE);

        topFilter.add(new JLabel("Loại:"));
        topFilter.add(cboType);
        topFilter.add(new JLabel("Từ:"));
        topFilter.add(dateFrom);
        topFilter.add(new JLabel("Đến:"));
        topFilter.add(dateTo);
        topFilter.add(btnFilter);
        topFilter.add(btnExport);

        add(topFilter, BorderLayout.SOUTH);

        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.WHITE);
        add(chartContainer, BorderLayout.CENTER);

        btnFilter.addActionListener(e -> loadChart());
        btnExport.addActionListener(e -> exportToExcel());

        loadChart();
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/qlcoffeeshop",
                "root",
                ""
        );
    }

    private void loadChart() {
        chartContainer.removeAll();

        String selected = cboType.getSelectedItem().toString();

        if (selected.equals("Doanh thu theo ngày")) {
            loadRevenueByDay();
        } else if (selected.equals("Doanh thu theo tháng")) {
            loadRevenueByMonth();
        } else if (selected.equals("Sản phẩm bán chạy")) {
            loadBestSeller();
        } else {
            loadBillCount();
        }

        chartContainer.revalidate();
        chartContainer.repaint();
    }

    /* ================= DOANH THU NGÀY ================= */

    private void loadRevenueByDay() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT DATE(NGAYTAO) NGAY, SUM(TONGTIEN) DOANHTHU
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    """;

            if (dateFrom.getDate() != null && dateTo.getDate() != null) {
                sql += " AND DATE(NGAYTAO) BETWEEN ? AND ? ";
            }

            sql += " GROUP BY DATE(NGAYTAO) ORDER BY NGAY";

            PreparedStatement ps = conn.prepareStatement(sql);

            if (dateFrom.getDate() != null && dateTo.getDate() != null) {
                ps.setDate(1, new java.sql.Date(dateFrom.getDate().getTime()));
                ps.setDate(2, new java.sql.Date(dateTo.getDate().getTime()));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(rs.getDouble("DOANHTHU"),
                        "Doanh thu",
                        rs.getString("NGAY"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo ngày",
                "Ngày",
                "Doanh thu",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(new Color(245,245,245));

        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    /* ================= DOANH THU THÁNG ================= */

    private void loadRevenueByMonth() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT MONTH(NGAYTAO) THANG,
                           YEAR(NGAYTAO) NAM,
                           IFNULL(SUM(TONGTIEN),0) DOANHTHU
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    GROUP BY YEAR(NGAYTAO), MONTH(NGAYTAO)
                    ORDER BY NAM, THANG
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int thang = rs.getInt("THANG");
                int nam = rs.getInt("NAM");
                double doanhthu = rs.getDouble("DOANHTHU");

                System.out.println("DEBUG MONTH: " + thang + "/" + nam + " = " + doanhthu);

                dataset.addValue(doanhthu, "Doanh thu", thang + "/" + nam);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo tháng",
                "Tháng",
                "Doanh thu",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getPlot().setBackgroundPaint(new Color(245,245,245));

        chartContainer.removeAll();
        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }

    /* ================= TOP SẢN PHẨM ================= */

    private void loadBestSeller() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT sp.TENSANPHAM,
                           SUM(ct.SOLUONG) TONGSL
                    FROM chitiethoadon ct
                    JOIN hoadon hd ON ct.MAHOADON = hd.MA
                    JOIN sanpham sp ON ct.MASANPHAM = sp.MA
                    WHERE hd.TRANGTHAI = 1
                    GROUP BY sp.TENSANPHAM
                    ORDER BY TONGSL DESC
                    LIMIT 5
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.setValue(rs.getString("TENSANPHAM"),
                        rs.getDouble("TONGSL"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Top 5 sản phẩm bán chạy",
                dataset,
                true, true, false
        );

        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    /* ================= SỐ HÓA ĐƠN ================= */

    private void loadBillCount() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT DATE(NGAYTAO) NGAY,
                           COUNT(MA) SOHOADON
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    GROUP BY DATE(NGAYTAO)
                    ORDER BY NGAY
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(rs.getInt("SOHOADON"),
                        "Số hóa đơn",
                        rs.getString("NGAY"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Số hóa đơn theo ngày",
                "Ngày",
                "Số hóa đơn",
                dataset
        );

        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    /* ================= XUẤT EXCEL ================= */

    private void exportToExcel() {
        try (Connection conn = getConnection()) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file CSV");

            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
                return;

            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".csv";

            String selected = cboType.getSelectedItem().toString();
            String sql = "";

            if (selected.equals("Doanh thu theo ngày")) {
                sql = """
                        SELECT DATE(NGAYTAO) NGAY,
                               SUM(TONGTIEN) GIATRI
                        FROM hoadon
                        WHERE TRANGTHAI = 1
                        GROUP BY DATE(NGAYTAO)
                        ORDER BY NGAY
                        """;
            } else if (selected.equals("Doanh thu theo tháng")) {
                sql = """
                        SELECT CONCAT(MONTH(NGAYTAO), '/', YEAR(NGAYTAO)) NGAY,
                               SUM(TONGTIEN) GIATRI
                        FROM hoadon
                        WHERE TRANGTHAI = 1
                        GROUP BY YEAR(NGAYTAO), MONTH(NGAYTAO)
                        ORDER BY YEAR(NGAYTAO), MONTH(NGAYTAO)
                        """;
            } else if (selected.equals("Số hóa đơn")) {
                sql = """
                        SELECT DATE(NGAYTAO) NGAY,
                               COUNT(MA) GIATRI
                        FROM hoadon
                        WHERE TRANGTHAI = 1
                        GROUP BY DATE(NGAYTAO)
                        ORDER BY NGAY
                        """;
            } else {
                sql = """
                        SELECT sp.TENSANPHAM NGAY,
                               SUM(ct.SOLUONG) GIATRI
                        FROM chitiethoadon ct
                        JOIN hoadon hd ON ct.MAHOADON = hd.MA
                        JOIN sanpham sp ON ct.MASANPHAM = sp.MA
                        WHERE hd.TRANGTHAI = 1
                        GROUP BY sp.TENSANPHAM
                        ORDER BY GIATRI DESC
                        LIMIT 5
                        """;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            java.io.PrintWriter writer = new java.io.PrintWriter(filePath, "UTF-8");

            // Header
            writer.println("Ten/Ngay,GiaTri");

            while (rs.next()) {
                writer.println(rs.getString("NGAY") + "," + rs.getDouble("GIATRI"));
            }

            writer.close();

            JOptionPane.showMessageDialog(this, "Xuất CSV thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất CSV thất bại!");
        }
    }
}