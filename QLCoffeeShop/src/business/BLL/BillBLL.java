package business.BLL;

import java.util.List;
import java.time.LocalDateTime;
import shared.DTO.BillDTO;
import shared.DTO.*;
import data.DAL.*;

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

//    public static void insertBill(LocalDateTime thoiGian,
//                                  double tongTien,
//                                  int employ,
//                                  int idTable) {
//        BillDAL.insertBill(thoiGian, tongTien, employ, idTable);
//    }
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
}
