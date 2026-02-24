package shared.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import business.BLL.ProductBLL;
import shared.DTO.*;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class BillPDFUtil {

    // ================== FONT PATH ==================
    private static final String FONT_REGULAR_PATH = "fonts/NotoSans-Regular.ttf";
    private static final String FONT_BOLD_PATH    = "fonts/NotoSans-Bold.ttf";

    private static BaseFont BF_REGULAR;
    private static BaseFont BF_BOLD;

    private static Font FONT_TITLE;
    private static Font FONT_NORMAL;
    private static Font FONT_BOLD;

    // ================== INIT FONT ==================
    private static void initFont() throws Exception {
        if (BF_REGULAR != null) return;

        BF_REGULAR = BaseFont.createFont(
                FONT_REGULAR_PATH,
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        BF_BOLD = BaseFont.createFont(
                FONT_BOLD_PATH,
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        FONT_TITLE  = new Font(BF_BOLD, 18);
        FONT_NORMAL = new Font(BF_REGULAR, 11);
        FONT_BOLD   = new Font(BF_BOLD, 11);
    }

    // ================== MAIN ==================
    public static void exportBill(
            String filePath,
            BillDTO bill,
            List<ChiTietBillDTO> details,
            double discountPercent,
            double customerPay
    ) throws Exception {

        initFont();

        Document document = new Document(PageSize.A5, 20, 20, 20, 20);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addHeader(document);
        addBillInfo(document, bill);
        addLine(document);

        double total = tinhTongTien(details);
        double discountMoney = total * discountPercent / 100;
        double finalTotal = total - discountMoney;
        double change = customerPay - finalTotal;

        addTable(document, details);
        addLine(document);

        addPaymentInfo(
                document,
                total,
                discountPercent,
                finalTotal,
                customerPay,
                change
        );

        addFooter(document);
        document.close();
    }

    // ================== HEADER ==================
    private static void addHeader(Document doc) throws Exception {
        Paragraph title = new Paragraph("COFFEE SHOP", FONT_TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);

        Paragraph sub = new Paragraph("HÓA ĐƠN THANH TOÁN", FONT_BOLD);
        sub.setAlignment(Element.ALIGN_CENTER);
        sub.setSpacingAfter(8);
        doc.add(sub);
    }

    // ================== INFO ==================
    private static void addBillInfo(Document doc, BillDTO bill) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        PdfPTable info = new PdfPTable(2);
        info.setWidthPercentage(100);

        info.addCell(cell("Ngày:", FONT_BOLD));
        info.addCell(cell(sdf.format(bill.getCreateDay()), FONT_NORMAL));

        info.addCell(cell("Bàn:", FONT_BOLD));
        info.addCell(cell(String.valueOf(bill.getIdTable()), FONT_NORMAL));

        info.addCell(cell("Nhân viên:", FONT_BOLD));
        info.addCell(cell(
                AppContext.taiKhoanDangNhap != null
                        ? AppContext.taiKhoanDangNhap.getTenTK()
                        : "Không xác định",
                FONT_NORMAL
        ));

        info.setSpacingAfter(6);
        doc.add(info);
    }

    // ================== TABLE ==================
    private static void addTable(Document doc, List<ChiTietBillDTO> details) throws Exception {

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 4, 1, 2, 2});

        table.addCell(header("STT"));
        table.addCell(header("Món"));
        table.addCell(header("SL"));
        table.addCell(header("Giá"));
        table.addCell(header("T.Tiền"));

        int stt = 1;

        for (ChiTietBillDTO ct : details) {

            List<ProductDTO> list = ProductBLL.getListProductByID(ct.getIdProduct());
            ProductDTO p = (list != null && !list.isEmpty()) ? list.get(0) : null;

            String tenMon = (p != null) ? p.getNameProducts() : "Không xác định";

            double gia = (p != null)
                    ? (p.getSalePrice() > 0 ? p.getSalePrice() : p.getPriceBasic())
                    : 0;

            int sl = ct.getSoLuong();
            double thanhTien = gia * sl;

            table.addCell(cellCenter(String.valueOf(stt++)));
            table.addCell(cell(tenMon, FONT_NORMAL));
            table.addCell(cellCenter(String.valueOf(sl)));
            table.addCell(cellRight(format(gia)));
            table.addCell(cellRight(format(thanhTien)));
        }

        table.setSpacingAfter(6);
        doc.add(table);
    }

    // ================== PAYMENT INFO ==================
    private static void addPaymentInfo(
            Document doc,
            double total,
            double discountPercent,
            double finalTotal,
            double customerPay,
            double change
    ) throws Exception {

        PdfPTable pay = new PdfPTable(2);
        pay.setWidthPercentage(100);

        pay.addCell(cell("Tổng tiền:", FONT_BOLD));
        pay.addCell(cellRight(format(total)));

        pay.addCell(cell("Giảm giá:", FONT_BOLD));
        pay.addCell(cellRight(discountPercent + " %"));

        pay.addCell(cell("Phải trả:", FONT_BOLD));
        pay.addCell(cellRight(format(finalTotal)));

        pay.addCell(cell("Khách đưa:", FONT_BOLD));
        pay.addCell(cellRight(format(customerPay)));

        pay.addCell(cell("Tiền thối:", FONT_BOLD));
        pay.addCell(cellRight(format(change)));

        pay.setSpacingBefore(6);
        doc.add(pay);
    }

    // ================== FOOTER ==================
    private static void addFooter(Document doc) throws Exception {
        Paragraph p = new Paragraph(
                "\nCảm ơn quý khách!\nHẹn gặp lại ❤️",
                FONT_NORMAL
        );
        p.setAlignment(Element.ALIGN_CENTER);
        doc.add(p);
    }

    // ================== LINE ==================
    private static void addLine(Document doc) throws Exception {
        LineSeparator ls = new LineSeparator();
        ls.setLineWidth(0.7f);
        doc.add(new Chunk(ls));
    }

    // ================== CELL ==================
    private static PdfPCell cell(String text, Font font) {
        PdfPCell c = new PdfPCell(new Phrase(text, font));
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private static PdfPCell header(String text) {
        PdfPCell c = new PdfPCell(new Phrase(text, FONT_BOLD));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        return c;
    }

    private static PdfPCell cellCenter(String text) {
        PdfPCell c = new PdfPCell(new Phrase(text, FONT_NORMAL));
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private static PdfPCell cellRight(String text) {
        PdfPCell c = new PdfPCell(new Phrase(text, FONT_NORMAL));
        c.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c.setBorder(Rectangle.NO_BORDER);
        return c;
    }

    private static String format(double v) {
        return String.format("%,.0f đ", v);
    }

    // ================== TOTAL ==================
    private static double tinhTongTien(List<ChiTietBillDTO> details) {

        double tong = 0;

        for (ChiTietBillDTO ct : details) {
            List<ProductDTO> list = ProductBLL.getListProductByID(ct.getIdProduct());
            if (list == null || list.isEmpty()) continue;

            ProductDTO p = list.get(0);
            double gia = p.getSalePrice() > 0 ? p.getSalePrice() : p.getPriceBasic();
            tong += gia * ct.getSoLuong();
        }
        return tong;
    }
}