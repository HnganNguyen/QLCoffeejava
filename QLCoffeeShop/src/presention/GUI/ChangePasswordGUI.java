package presention.GUI;

import business.BLL.TaiKhoanBLL;
import shared.DTO.TaiKhoanDTO;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordGUI extends JFrame {

    private TaiKhoanDTO tk;

    /* ===== COLORS ===== */
    private final Color MAIN_COLOR = new Color(193, 154, 107);
    private final Color BTN_COLOR = new Color(176, 137, 91);
    private final Color MENU_ACTIVE = new Color(160, 120, 80);
    private final Color BG_COLOR = new Color(250, 245, 240);

    private JPasswordField txtOldPass;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;

    public ChangePasswordGUI(TaiKhoanDTO tk) {
        this.tk = tk;

        setTitle("Đổi mật khẩu");
        setSize(480, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(BG_COLOR);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        /* ================= HEADER ================= */
        JPanel pnlHeader = new JPanel();
        pnlHeader.setOpaque(false);
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));

        ImageIcon icon = new ImageIcon(
                getClass().getResource("/resources/images/reset-password.png")
        );

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("ĐỔI MẬT KHẨU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(MAIN_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Tài khoản: " + tk.getTenTK());
        lblUser.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblUser.setForeground(Color.DARK_GRAY);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlHeader.add(lblIcon);
        pnlHeader.add(Box.createVerticalStrut(5));
        pnlHeader.add(lblTitle);
        pnlHeader.add(Box.createVerticalStrut(5));
        pnlHeader.add(lblUser);

        /* ================= FORM ================= */
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(25, 0, 20, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtOldPass = new JPasswordField();
        txtNewPass = new JPasswordField();
        txtConfirmPass = new JPasswordField();

        styleField(txtOldPass);
        styleField(txtNewPass);
        styleField(txtConfirmPass);

        int row = 0;

        g.gridx = 0; g.gridy = row; g.weightx = 0;
        pnlForm.add(new JLabel("Mật khẩu hiện tại:"), g);

        g.gridx = 1; g.weightx = 1.0;
        pnlForm.add(txtOldPass, g);

        row++;
        g.gridx = 0; g.gridy = row; g.weightx = 0;
        pnlForm.add(new JLabel("Mật khẩu mới:"), g);

        g.gridx = 1; g.weightx = 1.0;
        pnlForm.add(txtNewPass, g);

        row++;
        g.gridx = 0; g.gridy = row; g.weightx = 0;
        pnlForm.add(new JLabel("Xác nhận mật khẩu:"), g);

        g.gridx = 1; g.weightx = 1.0;
        pnlForm.add(txtConfirmPass, g);

        /* ================= BUTTON ================= */
        JPanel pnlBtn = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlBtn.setOpaque(false);

        JButton btnSave = createButton("Lưu mật khẩu", BTN_COLOR);
        JButton btnBack = createButton("Quay lại", MENU_ACTIVE);

        btnSave.addActionListener(e -> handleChangePassword());
        btnBack.addActionListener(e -> {
            dispose();
            new SelectRoleGUI(tk).setVisible(true);
        });

        pnlBtn.add(btnSave);
        pnlBtn.add(btnBack);

        pnlMain.add(pnlHeader, BorderLayout.NORTH);
        pnlMain.add(pnlForm, BorderLayout.CENTER);
        pnlMain.add(pnlBtn, BorderLayout.SOUTH);

        add(pnlMain);
    }

    /* ================= HANDLE ================= */

    private void handleChangePassword() {

        String oldPass = String.valueOf(txtOldPass.getPassword());
        String newPass = String.valueOf(txtNewPass.getPassword());
        String confirm = String.valueOf(txtConfirmPass.getPassword());

        if (oldPass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        if (!newPass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp");
            return;
        }

        // ✅ GỌI BLL ĐỔI MẬT KHẨU THẬT
        boolean success = TaiKhoanBLL.changePassword(
                tk.getId(), oldPass, newPass
        );

        if (!success) {
            JOptionPane.showMessageDialog(this,
                    "Mật khẩu hiện tại không đúng!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Đổi mật khẩu thành công!\nVui lòng đăng nhập lại.");

        dispose();
        new LoginGUI().setVisible(true);
    }

    /* ================= STYLE ================= */

    private void styleField(JPasswordField txt) {
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setPreferredSize(new Dimension(260, 34));
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(0, 40));
        return btn;
    }
}
