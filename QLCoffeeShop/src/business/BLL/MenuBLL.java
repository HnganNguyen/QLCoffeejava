package business.BLL;

import java.util.List;
import shared.DTO.*;
import data.DAL.*;

public class MenuBLL {

    public static List<MenuDTO> getListMenuByIDTable(int id) {
        return MenuDAL.getListMenuByIDTable(id);
    }

    public static List<MenuDTO> getListMenuByIDBill(int idBill) {
        return MenuDAL.getListMenuByIDBill(idBill);
    }

    public static List<MenuDTO> getReviewBill(int idBill) {
        return MenuDAL.getReviewBill(idBill);
    }
}
