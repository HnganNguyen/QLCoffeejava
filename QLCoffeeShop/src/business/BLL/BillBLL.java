package business.BLL;

import java.util.List;
import java.time.LocalDateTime;
import shared.DTO.BillDTO;
import shared.DTO.*;
import data.DAL.*;
import java.util.Date;

public class BillBLL {

    public static List<BillDTO> getAllListBill() {
        return BillDAL.getAllListBill();
    }

//    public static List<BillUpDTO> getAllListBillup() {
//        return BillDAL.getAllListBill();
//    }

    public static int getIDBillNoPaymentByIDTable(int id) {
        return BillDAL.getIDBillNoPaymentByIDTable(id);
    }

    public static int getIDBillMax() {
        return BillDAL.getIDBillMax();
    }

    public static void insertBill(
            Date dateTime,
            double total,
            String employId,
            int idTable) {

        BillDAL.insertBill(dateTime, total, employId, idTable);
    }


//
//    public static void updateBill(int id,
//                                  double totalBill,
//                                  double promotion,
//                                  double cusPrice,
//                                  double outPrice,
//                                  double revenue,
//                                  LocalDateTime dateTime,
//                                  int employ) {
//        BillDAL.updateBill(id, totalBill, promotion,
//                           cusPrice, outPrice,
//                           revenue, dateTime, employ);
//    }

    public static void deleteBill(int id) {
        BillDAL.deleteBill(id);
    }

    public static int getIDBillByIDTable(int idTable) {
        return BillDAL.getIDBillByIDTable(idTable);
    }

}
