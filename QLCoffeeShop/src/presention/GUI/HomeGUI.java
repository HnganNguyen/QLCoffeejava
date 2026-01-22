package presention.GUI;

import javax.swing.*;
import java.awt.*;
import business.BLL.TableBLL;

public class HomeGUI extends JFrame {

    private final Color SUB_COLOR = new Color(250, 245, 240);

    private JLabel lblTotalTable;
    private JLabel lblUsingTable;

    public HomeGUI(String username, String role) {

        setTitle("Coffee Shop System");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TopPanel(this, "HOME", username, role), BorderLayout.NORTH);

        initDashboard();
        loadTableStatistic();
    }

    private void initDashboard() {

        JPanel pnl = new JPanel(new GridLayout(2, 2, 20, 20));
        pnl.setBackground(SUB_COLOR);
        pnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTotalTable = new JLabel("0", JLabel.CENTER);
        lblUsingTable = new JLabel("0", JLabel.CENTER);

        pnl.add(createCard("Tổng số bàn", lblTotalTable));
        pnl.add(createCard("Bàn đang sử dụng", lblUsingTable));
        pnl.add(createCard("Hóa đơn hôm nay", new JLabel("")));
        pnl.add(createCard("Doanh thu hôm nay", new JLabel("")));

        add(pnl, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, JLabel value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));

        value.setFont(new Font("Segoe UI", Font.BOLD, 28));

        p.add(lblTitle, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    private void loadTableStatistic() {
        lblTotalTable.setText(String.valueOf(TableBLL.getAllListTable().size()));
        lblUsingTable.setText(String.valueOf(TableBLL.getListTableHaveStatusOne().size()));
    }
}
