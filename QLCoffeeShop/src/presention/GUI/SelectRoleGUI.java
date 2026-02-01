package presention.GUI;

import shared.DTO.TaiKhoanDTO;

import javax.swing.*;
import java.awt.*;

public class SelectRoleGUI extends JFrame {

    private TaiKhoanDTO tk;

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color BTN_COLOR = new Color(176, 137, 91);
    private final Color BG_COLOR = new Color(250, 245, 240);
    private final Color BTN_GRAY = new Color(140, 140, 140);

    public SelectRoleGUI(TaiKhoanDTO tk) {
        this.tk = tk;

        setTitle("Chọn chức năng");
        setSize(500, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(BG_COLOR);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        /* ================= TOP ================= */
        JPanel pnlTop = new JPanel();
        pnlTop.setOpaque(false);
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));

        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon("src/resources/images/logochinh.png");
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("☕");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        }

        JLabel lblTitle = new JLabel("CHỌN CHỨC NĂNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(MAIN_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlTop.add(lblLogo);
        pnlTop.add(Box.createVerticalStrut(10));
        pnlTop.add(lblTitle);

        /* ================= CENTER ================= */
        JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 18, 18));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JButton btnManage = createMainButton(
                "Quản lý hệ thống",
                "src/resources/images/manage.png",
                BTN_COLOR
        );

        JButton btnSale = createMainButton(
                "Bán hàng",
                "src/resources/images/sale.png",
                BTN_COLOR
        );

        // Nhân viên → ẩn quản lý
        if (tk.getQuyen() == 0) {
            btnManage.setVisible(false);
        }

        btnManage.addActionListener(e -> {
            dispose();
            new HomeGUI(tk.getTenTK(), "ADMIN").setVisible(true);
        });

        btnSale.addActionListener(e -> {
            dispose();
            new FrmOrder ().setVisible(true);
        });

        pnlCenter.add(btnManage);
        pnlCenter.add(btnSale);

        /* ================= BOTTOM ================= */
        JPanel pnlBottom = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlBottom.setOpaque(false);

        JButton btnChangePass = createMainButton(
                "Đổi mật khẩu",
                "src/resources/images/password.png",
                BTN_GRAY
        );

        JButton btnLogout = createMainButton(
                "Đăng xuất",
                "src/resources/images/logout.png",
                BTN_GRAY
        );

        // 👉 ĐỔI MẬT KHẨU
        btnChangePass.addActionListener(e -> {
            dispose();
            new ChangePasswordGUI(tk).setVisible(true); // form chưa làm
        });

        // 👉 ĐĂNG XUẤT
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn đăng xuất không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginGUI().setVisible(true);
            }
        });

        pnlBottom.add(btnChangePass);
        pnlBottom.add(btnLogout);

        pnlMain.add(pnlTop, BorderLayout.NORTH);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        add(pnlMain);
    }

    /* ================= BUTTON STYLE ================= */
    private JButton createMainButton(String text, String iconPath, Color bg) {

        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(18);
        btn.setPreferredSize(new Dimension(0, 60));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));

        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}

        return btn;
    }
}
