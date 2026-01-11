package data.DAL;


import shared.DTO.ChiTietBillDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ChiTietDAL {

	    // 1️⃣ Kiểm tra sản phẩm có tồn tại trong bill hay không
	    public static boolean checkSPCoTrongBill(int idBill) {
	        String sql = "SELECT * FROM CHITIETHOADON WHERE MAHOADON = ?";

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, idBill);
	            ResultSet rs = ps.executeQuery();
	            return rs.next();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    // 2️⃣ Thêm chi tiết bill (CALL Stored Procedure)
	    public static void insertChiTietBill(int maBill, int idProduct, int soLuong) {
	        String sql = "CALL USP_INSERTCHITIETBILL(?, ?, ?)";

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, maBill);
	            ps.setInt(2, idProduct);
	            ps.setInt(3, soLuong);
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 3️⃣ Lấy số lượng sản phẩm trong bill
	    public static int getSoLuongSanPham(int idBill, int idProduct) {
	        String sql = """
	            SELECT MAHOADON, MASANPHAM, SOLUONG
	            FROM CHITIETHOADON
	            WHERE MAHOADON = ? AND MASANPHAM = ?
	        """;

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, idBill);
	            ps.setInt(2, idProduct);

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                ChiTietBillDTO ct = new ChiTietBillDTO(rs);
	                return ct.getSoLuong();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    // 4️⃣ Lấy danh sách sản phẩm theo mã bill
	    public static List<ChiTietBillDTO> getListProductByIDBill(int idBill) {
	        List<ChiTietBillDTO> list = new ArrayList<>();
	        String sql = "SELECT * FROM CHITIETHOADON WHERE MAHOADON = ?";

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, idBill);
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                list.add(new ChiTietBillDTO(rs));
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    // 5️⃣ Xóa 1 sản phẩm trong bill
	    public static void deleteChiTietBill(int idBill, int idProduct) {
	        String sql = "DELETE FROM CHITIETHOADON WHERE MAHOADON = ? AND MASANPHAM = ?";

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, idBill);
	            ps.setInt(2, idProduct);
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // 6️⃣ Xóa toàn bộ chi tiết bill theo mã bill
	    public static void deleteChiTietBillByBillID(int idBill) {
	        String sql = "DELETE FROM CHITIETHOADON WHERE MAHOADON = ?";

	        try (Connection conn = MySQLConnect.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            ps.setInt(1, idBill);
	            ps.executeUpdate();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
