package View;

import DAO.LichTrinhDAO;
import Model.LichTrinh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class ThemLichView extends JFrame {
    private JTextField txtTieuDe, txtNgay, txtBatDau, txtKetThuc;
    private JButton btnLuu, btnXoa, btnTaiLai;
    private JTable tblLichTrinh;
    private DefaultTableModel model;
    private Random random = new Random();

    public ThemLichView() {
        setTitle("Quản Lý Lịch Trình");
        setSize(1020, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // 1. Thanh thêm lịch trình nằm ngang ở phía trên
        JPanel pnThem = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnThem.setBorder(BorderFactory.createTitledBorder("Thêm lịch trình"));

        txtTieuDe = new JTextField(14);
        txtNgay = new JTextField("2026-11-25", 10);
        txtBatDau = new JTextField("14:00", 7);
        txtKetThuc = new JTextField("16:00", 7);
        btnLuu = new JButton("Lưu (Mã DM ngẫu nhiên)");
        btnXoa = new JButton("Xóa");
        btnTaiLai = new JButton("Tải lại");

        pnThem.add(new JLabel("Tiêu đề:"));
        pnThem.add(txtTieuDe);
        pnThem.add(new JLabel("Ngày:"));
        pnThem.add(txtNgay);
        pnThem.add(new JLabel("Bắt đầu:"));
        pnThem.add(txtBatDau);
        pnThem.add(new JLabel("Kết thúc:"));
        pnThem.add(txtKetThuc);
        pnThem.add(btnLuu);
        pnThem.add(btnXoa);
        pnThem.add(btnTaiLai);

        add(pnThem, BorderLayout.NORTH);

        // 2. Bảng danh sách lịch trình ở giữa
        String[] tenCot = {"Tiêu đề", "Ngày", "Bắt đầu", "Kết thúc", "Mã DM"};
        model = new DefaultTableModel(tenCot, 0);
        tblLichTrinh = new JTable(model);
        add(new JScrollPane(tblLichTrinh), BorderLayout.CENTER);

        // 3. Sự kiện nút bấm
        btnLuu.addActionListener(e -> luuLichTrinh());
        btnXoa.addActionListener(e -> xoaLichTrinhDangChon());
        btnTaiLai.addActionListener(e -> hienThiDanhSach());

        // Nạp dữ liệu ngay khi mở giao diện
        hienThiDanhSach();
    }

    // Kiểm tra các mục không cho phép người dùng xóa (khớp dòng trong ảnh)
    private boolean laMucKhongDuocXoa(String tieuDe, String ngay, String batDau, String ketThuc, String maDM) {
        boolean khopDongTrongAnh = "di ỉa".equalsIgnoreCase(tieuDe.trim())
                && "2026-11-25".equals(ngay.trim())
                && batDau.trim().startsWith("14:00")
                && ketThuc.trim().startsWith("16:00")
                && "1".equals(maDM.trim());
        return khopDongTrongAnh;
    }

    private void luuLichTrinh() {
        String tieuDe = txtTieuDe.getText().trim();
        String ngay = txtNgay.getText().trim();
        String batDau = txtBatDau.getText().trim();
        String ketThuc = txtKetThuc.getText().trim();

        if (tieuDe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập tiêu đề!");
            return;
        }

        // Sinh mã danh mục ngẫu nhiên từ 1 đến 100
        int maDMNgauNhien = random.nextInt(100) + 1;

        LichTrinh lt = new LichTrinh(tieuDe, ngay, batDau, ketThuc, maDMNgauNhien);
        LichTrinhDAO dao = new LichTrinhDAO();

        if (dao.themLichTrinh(lt)) {
            JOptionPane.showMessageDialog(this, "Lưu thành công! " + maDMNgauNhien);
            txtTieuDe.setText("");
            hienThiDanhSach();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thất bại!");
        }
    }

    private void xoaLichTrinhDangChon() {
        int dongChon = tblLichTrinh.getSelectedRow();

        if (dongChon < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch trình trên bảng để xóa!");
            return;
        }

        String tieuDe = String.valueOf(model.getValueAt(dongChon, 0));
        String ngay = String.valueOf(model.getValueAt(dongChon, 1));
        String batDau = String.valueOf(model.getValueAt(dongChon, 2));
        String ketThuc = String.valueOf(model.getValueAt(dongChon, 3));
        String maDM = String.valueOf(model.getValueAt(dongChon, 4));

        if (laMucKhongDuocXoa(tieuDe, ngay, batDau, ketThuc, maDM)) {
            JOptionPane.showMessageDialog(this, "Mục này đang được bảo vệ, bạn không thể xóa!");
            return;
        }

        int xacNhan = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa lịch trình \"" + tieuDe + "\" không?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }

        LichTrinhDAO dao = new LichTrinhDAO();
        if (dao.xoaLichTrinh(tieuDe, ngay, batDau, ketThuc)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            hienThiDanhSach();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }

    private void hienThiDanhSach() {
        model.setRowCount(0);
        LichTrinhDAO dao = new LichTrinhDAO();
        List<LichTrinh> danhSach = dao.layTatCaLichTrinh();

        for (LichTrinh lt : danhSach) {
            model.addRow(new Object[]{
                lt.getTieuDe(),
                lt.getNgay(),
                lt.getGioBatDau(),
                lt.getGioKetThuc(),
                lt.getMaDM()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThemLichView().setVisible(true));
    }
}