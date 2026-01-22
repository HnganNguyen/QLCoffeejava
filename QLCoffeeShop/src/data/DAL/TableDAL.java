package data.DAL;

import shared.DTO.TableDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAL {

    // ================= LẤY DANH SÁCH TẤT CẢ BÀN =================
    public static List<TableDTO> getAllListTable() {
        List<TableDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM BAN";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TableDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= UPDATE TRẠNG THÁI BÀN (FIX LỖI Ở ĐÂY) =================
    public static boolean updateStatusTable(int status, int id) {
        String sql = "{CALL USP_UPDATETRANGTHAITABLE(?, ?)}";

        try (Connection conn = MySQLConnect.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // ⚠️ THỨ TỰ ĐÚNG: (ID, STATUS)
            cs.setInt(1, id);      // ✅ ID BÀN
            cs.setInt(2, status);  // ✅ TRẠNG THÁI

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= THÊM BÀN =================
    public static boolean insertTable(TableDTO tb) {
        String sql = "{CALL USP_INSERTTABLE(?, ?)}";

        try (Connection conn = MySQLConnect.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, tb.getNameTable());
            cs.setInt(2, tb.getStatus());

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= XOÁ BÀN =================
    public static boolean deleteTable(TableDTO tb) {
        String sql = "{CALL USP_DELETETABLE(?)}";

        try (Connection conn = MySQLConnect.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, tb.getID());
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= LẤY DANH SÁCH BÀN THEO TRẠNG THÁI =================
    public static List<TableDTO> getTableByStatus(int status) {
        List<TableDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM BAN WHERE TRANGTHAI = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TableDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<TableDTO> getListTableHaveStatusOne() {
        return getTableByStatus(1);
    }

    public static List<TableDTO> getListTableHaveStatusZero() {
        return getTableByStatus(0);
    }
}
