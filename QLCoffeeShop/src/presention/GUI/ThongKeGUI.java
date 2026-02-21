package presention.GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.toedter.calendar.JDateChooser;

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

        JPanel topFilter = new JPanel();
        topFilter.setBorder(BorderFactory.createTitledBorder("Bộ lọc"));

        cboType = new JComboBox<>(new String[]{
                "Doanh thu theo ngày",
                "Doanh thu theo tháng",
                "Sản phẩm bán chạy",
                "Số hóa đơn"
        });

        dateFrom = new JDateChooser();
        dateTo = new JDateChooser();

        JButton btnFilter = new JButton("Lọc");

        topFilter.add(new JLabel("Loại thống kê:"));
        topFilter.add(cboType);
        topFilter.add(new JLabel("Từ ngày:"));
        topFilter.add(dateFrom);
        topFilter.add(new JLabel("Đến ngày:"));
        topFilter.add(dateTo);
        topFilter.add(btnFilter);

        add(topFilter, BorderLayout.SOUTH);

        chartContainer = new JPanel(new BorderLayout());
        add(chartContainer, BorderLayout.CENTER);

        btnFilter.addActionListener(e -> loadChart());

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

    private String getDateCondition() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (dateFrom.getDate() == null || dateTo.getDate() == null) {
            return "";
        }

        String from = sdf.format(dateFrom.getDate());
        String to = sdf.format(dateTo.getDate());

        return " AND DATE(NGAYTAO) BETWEEN '" + from + "' AND '" + to + "' ";
    }

    /* ================= DOANH THU THEO NGÀY ================= */

    private void loadRevenueByDay() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT DATE(NGAYTAO) AS NGAY,
                           SUM(TONGTIEN) AS DOANHTHU
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    """ + getDateCondition() +
                    " GROUP BY DATE(NGAYTAO) ORDER BY NGAY";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(
                        rs.getDouble("DOANHTHU"),
                        "Doanh thu",
                        rs.getString("NGAY")
                );
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

        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    /* ================= DOANH THU THEO THÁNG ================= */

    private void loadRevenueByMonth() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT MONTH(NGAYTAO) AS THANG,
                           YEAR(NGAYTAO) AS NAM,
                           SUM(TONGTIEN) AS DOANHTHU
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    """ + getDateCondition() +
                    " GROUP BY YEAR(NGAYTAO), MONTH(NGAYTAO)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String label = rs.getInt("THANG") + "/" + rs.getInt("NAM");
                dataset.addValue(
                        rs.getDouble("DOANHTHU"),
                        "Doanh thu",
                        label
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Doanh thu theo tháng",
                "Tháng",
                "Doanh thu",
                dataset
        );

        chartContainer.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    /* ================= SẢN PHẨM BÁN CHẠY ================= */

    private void loadBestSeller() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try (Connection conn = getConnection()) {

            String sql = """
                    SELECT sp.TENSANPHAM,
                           SUM(ct.SOLUONG) AS TONGSL
                    FROM chitiethoadon ct
                    JOIN hoadon hd ON ct.MAHOADON = hd.MA
                    JOIN sanpham sp ON ct.MASANPHAM = sp.MA
                    WHERE hd.TRANGTHAI = 1
                    """ + getDateCondition() +
                    " GROUP BY sp.TENSANPHAM ORDER BY TONGSL DESC LIMIT 5";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.setValue(
                        rs.getString("TENSANPHAM"),
                        rs.getDouble("TONGSL")
                );
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
                    SELECT DATE(NGAYTAO) AS NGAY,
                           COUNT(MA) AS SOHOADON
                    FROM hoadon
                    WHERE TRANGTHAI = 1
                    """ + getDateCondition() +
                    " GROUP BY DATE(NGAYTAO)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(
                        rs.getInt("SOHOADON"),
                        "Số hóa đơn",
                        rs.getString("NGAY")
                );
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
}
