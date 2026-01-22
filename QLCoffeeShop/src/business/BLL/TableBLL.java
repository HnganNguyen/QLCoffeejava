package business.BLL;

import java.util.List;
import data.DAL.TableDAL;
import shared.DTO.TableDTO;

public class TableBLL {

    public static final int TAB_WIDTH = 110;
    public static final int TAB_HEIGHT = 90;

    public static List<TableDTO> getAllListTable() {
        return TableDAL.getAllListTable();
    }

    public static boolean updateStatusTable(int status, int id) {
        return TableDAL.updateStatusTable(status, id);
    }

    public static boolean insertTable(TableDTO table) {
        return TableDAL.insertTable(table);
    }

    public static boolean deleteTable(TableDTO table) {
        return TableDAL.deleteTable(table);
    }

    public static List<TableDTO> getListTableHaveStatusOne() {
        return TableDAL.getListTableHaveStatusOne();
    }

    public static List<TableDTO> getListTableHaveStatusZero() {
        return TableDAL.getListTableHaveStatusZero();
    }
}
