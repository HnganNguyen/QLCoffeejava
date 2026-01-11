package data.DAL;

import shared.DTO.TableDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

public class TableDAL {

    // 1️⃣ Lấy tất cả bàn
    public static List<TableDTO> getAllListTable() {
        List<TableDTO> tableList = new ArrayList<>();
        String sql = "SELECT * FROM BAN";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TableDTO table = new TableDTO(rs);
                tableList.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableList;
    }

    // 2️⃣ Update trạng thái bàn (Stored Procedure)
    public static void updateStatusTable(int status, int id) {
        String sql = "{CALL USP_UPDATETRANGTHAITABLE(?, ?)}";

        try (Connection conn = MySQLConnect.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, status);
            cs.setInt(2, id);
            cs.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3️⃣ Lấy trạng thái bàn theo ID
    public static int getStatusByIDTable(int id) {
        String sql = "SELECT * FROM BAN WHERE MA = ?";
        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TableDTO table = new TableDTO(rs);
                return table.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 4️⃣ Danh sách bàn trạng thái = 1
    public static List<TableDTO> getListTableHaveStatusOne() {
        return getTableByStatus(1);
    }

    // 5️⃣ Danh sách bàn trạng thái = 0
    public static List<TableDTO> getListTableHaveStatusZero() {
        return getTableByStatus(0);
    }

    private static List<TableDTO> getTableByStatus(int status) {
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

    // 6️⃣ Lấy danh sách bàn khác ID
    public static List<TableDTO> getListTableDifferentID(int id) {
        List<TableDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM BAN WHERE MA != ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TableDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7️⃣ Insert bàn (Stored Procedure)
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

    // 8️⃣ Delete bàn (Stored Procedure)
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

    // 9️⃣ Kiểm tra bàn tồn tại
    public static boolean isTableExists(int idTable) {
        String sql = "SELECT COUNT(*) FROM BAN WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTable);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
