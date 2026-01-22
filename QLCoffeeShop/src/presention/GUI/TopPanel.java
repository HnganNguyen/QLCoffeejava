package presention.GUI;

import javax.swing.*;
import java.awt.*;
import shared.DTO.TaiKhoanDTO;

public class TopPanel extends JPanel {

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color BTN_COLOR = new Color(176, 137, 91);
    private final Color MENU_ACTIVE = new Color(160, 120, 80);

    private JFrame parent;
    private String username;
    private String role;

    public TopPanel(JFrame parent, String active, String username, String role) {

        this.parent = parent;
        this.username = username;
        this.role = role;

        setLayout(new BorderLayout());

        /* ================= HEADER ================= */
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(MAIN_COLOR);
        pnlHeader.setPreferredSize(new Dimension(0, 90));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        /* ================= LEFT (LOGO + TITLE) ================= */
        JPanel pnlLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlLeft.setOpaque(false);

        // LOGO (đường dẫn thường – không getResource)
        ImageIcon icon = new ImageIcon("src/resources/images/logo.png");
        JLabel lblLogo = new JLabel();

        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            lblLogo.setText("☕");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 32));
            lblLogo.setForeground(Color.WHITE);
        }

        JLabel lblTitle = new JLabel("COFFEE SHOP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);

        pnlLeft.add(lblLogo);
        pnlLeft.add(lblTitle);

        /* ================= RIGHT ================= */
        JPanel pnlRight = new JPanel();
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setOpaque(false);

        JLabel lblUser = new JLabel(username + " (" + role + ")");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel pnlAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        pnlAction.setOpaque(false);

        JButton btnExit = createHeaderButton("Thoát");
        btnExit.addActionListener(e -> {
            parent.dispose();

            TaiKhoanDTO tk = new TaiKhoanDTO();
            tk.setTenTK(username);
            tk.setQuyen(role.equalsIgnoreCase("ADMIN") ? 1 : 0);

            new SelectRoleGUI(tk).setVisible(true);
        });

        pnlAction.add(btnExit);

        pnlRight.add(lblUser);
        pnlRight.add(pnlAction);

        pnlHeader.add(pnlLeft, BorderLayout.WEST);
        pnlHeader.add(pnlRight, BorderLayout.EAST);

        /* ================= MENU ================= */
        JPanel pnlMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        pnlMenu.setBackground(new Color(225, 205, 180));

        pnlMenu.add(createMenuButton("Trang chủ", "HOME", active));
        pnlMenu.add(createMenuButton("Loại SP", "TYPE", active));
        pnlMenu.add(createMenuButton("Sản phẩm", "PRODUCT", active));
        pnlMenu.add(createMenuButton("Bàn", "TABLE", active));
        pnlMenu.add(createMenuButton("Nguyên liệu", "INGREDIENT", active));

        if (role.equalsIgnoreCase("ADMIN")) {
            pnlMenu.add(createMenuButton("Tài khoản", "ACCOUNT", active));
        }

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlMenu, BorderLayout.SOUTH);

        add(pnlTop, BorderLayout.CENTER);
    }

    /* ================= MENU BUTTON ================= */
    private JButton createMenuButton(String text, String key, String active) {

        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(active.equals(key) ? MENU_ACTIVE : BTN_COLOR);
        btn.setPreferredSize(new Dimension(120, 35));

        btn.addActionListener(e -> {
            parent.dispose();
            switch (key) {
                case "HOME" -> new HomeGUI(username, role).setVisible(true);
                case "TYPE" -> new TypeProductGUI(username, role).setVisible(true);
                case "PRODUCT" -> new ProductGUI(username, role).setVisible(true);
                case "TABLE" -> new TableGUI(username, role).setVisible(true);
                case "ACCOUNT" -> new TaiKhoanGUI(username, role).setVisible(true);
                case "INGREDIENT" -> new NguyenLieuGUI(username, role).setVisible(true);
            }
        });

        return btn;
    }

    /* ================= HEADER BUTTON ================= */
    private JButton createHeaderButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(new Color(150, 110, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}
