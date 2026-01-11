package business.BLL;

import java.util.List;

import data.DAL.TableDAL; 
import shared.DTO.*;

public class TableBLL {

    // Kích thước hiển thị bàn (giống WinForms)
    public static final int TAB_WIDTH = 110;
    public static final int TAB_HEIGHT = 90;

    // Lấy danh sách tất cả các bàn
    public static List<TableDTO> getAllListTable() {
        return TableDAL.getAllListTable();
    }

    // Cập nhật trạng thái bàn
    public static void updateStatusTable(int status, int id) {
        TableDAL.updateStatusTable(status, id);
    }

    // Lấy trạng thái bàn theo ID
    public static int getStatusByIDTable(int id) {
        return TableDAL.getStatusByIDTable(id);
    }

    // Lấy danh sách bàn đang có người (status = 1)
    public static List<TableDTO> getListTableHaveStatusOne() {
        return TableDAL.getListTableHaveStatusOne();
    }

    // Lấy danh sách bàn khác ID hiện tại
    public static List<TableDTO> getListTableDifferentID(int id) {
        return TableDAL.getListTableDifferentID(id);
    }

    // Thêm bàn mới
    public static boolean insertTable(TableDTO table) {
        return TableDAL.insertTable(table);
    }

    // Xoá bàn
    public static boolean deleteTable(TableDTO table) {
        return TableDAL.deleteTable(table);
    }

    // Lấy danh sách bàn trống (status = 0)
    public static List<TableDTO> getListTableHaveStatusZero() {
        return TableDAL.getListTableHaveStatusZero();
    }
}
