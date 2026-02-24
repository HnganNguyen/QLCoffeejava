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

    public static boolean updateStatusTable(int id, int status) {
        return TableDAL.updateStatusTable(id, status);
    }

    public static boolean insertTable(TableDTO table) {
        return TableDAL.insertTable(table);
    }

    public static boolean deleteTable(TableDTO table) {
        return TableDAL.deleteTable(table);
    }

    public static int getStatusByIDTable(int idTable) {
        return TableDAL.getStatusByIDTable(idTable);
    }

    public static List<TableDTO> getListTableHaveStatusOne() {
        return TableDAL.getListTableHaveStatusOne();
    }

    public static List<TableDTO> getListTableHaveStatusZero() {
        return TableDAL.getListTableHaveStatusZero();
    }

    public static void updateTableStatus(int idTable, int status) {
        TableDAL.updateTableStatus(idTable, status);
    }

	
}
