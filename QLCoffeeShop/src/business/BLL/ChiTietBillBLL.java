package business.BLL;

import data.DAL.ChiTietDAL;
import shared.DTO.ChiTietBillDTO;

import java.util.List;

public class ChiTietBillBLL {

    public static void insertChiTietBill(int maBill, int idProduct, int soLuong) {
        ChiTietDAL.insertChiTietBill(maBill, idProduct, soLuong);
    }

    public static boolean checkSPCoTrongBill(int idBill) {
        return ChiTietDAL.checkSPCoTrongBill(idBill);
    }

    public static int getSoLuongSanPham(int idBill, int idProduct) {
        return ChiTietDAL.getSoLuongSanPham(idBill, idProduct);
    }

    public static void deleteChiTietBill(int idBill, int idProduct) {
    	ChiTietDAL.deleteChiTietBill(idBill, idProduct);
    }

    public static void deleteChiTietBillByBillID(int idBill) {
    	ChiTietDAL.deleteChiTietBillByBillID(idBill);
    }

    public static List<ChiTietBillDTO> getListProductByIDBill(int idBill) {
        return ChiTietDAL.getListProductByIDBill(idBill);
    }
}
