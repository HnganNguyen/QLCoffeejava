package presention.GUI;

import business.BLL.TaiKhoanBLL;
import shared.DTO.TaiKhoanDTO;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField txtMaTK;
    private JPasswordField txtPass;

    // ===== COLORS =====
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color BTN_COLOR = new Color(176, 137, 91);
    private final Color BG_COLOR = new Color(250, 245, 240);

    public LoginGUI() {
        setTitle("Coffee Shop - Đăng nhập");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(BG_COLOR);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        /* ================= LOGO + TITLE ================= */
        JPanel pnlTop = new JPanel();
        pnlTop.setOpaque(false);
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));

        // LOGO
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon icon = new ImageIcon("src/resources/images/logo.png");
            Image img = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("☕");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 40));
        }

        JLabel lblTitle = new JLabel("COFFEE SHOP", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(MAIN_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Đăng nhập hệ thống", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlTop.add(lblLogo);
        pnlTop.add(Box.createVerticalStrut(10));
        pnlTop.add(lblTitle);
        pnlTop.add(lblSub);

        /* ================= FORM ================= */
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtMaTK = new JTextField();
        txtPass = new JPasswordField();

        txtMaTK.setPreferredSize(new Dimension(200, 32));
        txtPass.setPreferredSize(new Dimension(200, 32));

        JLabel lblMaTK = new JLabel("Mã tài khoản");
        JLabel lblPass = new JLabel("Mật khẩu");

        lblMaTK.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 13));

        int row = 0;

        g.gridx = 0; g.gridy = row;
        pnlForm.add(lblMaTK, g);

        g.gridx = 1;
        pnlForm.add(txtMaTK, g);

        row++;
        g.gridx = 0; g.gridy = row;
        pnlForm.add(lblPass, g);

        g.gridx = 1;
        pnlForm.add(txtPass, g);

        /* ================= BUTTON ================= */
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBackground(BTN_COLOR);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(200, 38));

        JPanel pnlBtn = new JPanel();
        pnlBtn.setOpaque(false);
        pnlBtn.add(btnLogin);

        /* ================= ADD ================= */
        pnlMain.add(pnlTop, BorderLayout.NORTH);
        pnlMain.add(pnlForm, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);

        add(pnlMain);

        btnLogin.addActionListener(e -> doLogin());
        getRootPane().setDefaultButton(btnLogin);
    }

    /* ================= LOGIN ================= */
    private void doLogin() {

        String maTK = txtMaTK.getText().trim();
        String pass = String.valueOf(txtPass.getPassword());

        if (maTK.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        TaiKhoanDTO tk = TaiKhoanBLL.login(maTK, pass);

        if (tk == null) {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu");
            return;
        }

        // Đăng nhập thành công
        dispose();
        new SelectRoleGUI(tk).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
