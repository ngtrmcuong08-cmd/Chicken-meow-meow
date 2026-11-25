package DAO;

import Model.LichTrinh;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LichTrinhDAO {

    public boolean themLichTrinh(LichTrinh lt) {
        String sql = "INSERT INTO LichTrinh (TieuDe, Ngay, GioBatDau, GioKetThuc, MaDM) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setNString(1, lt.getTieuDe());
            ps.setString(2, lt.getNgay());
            ps.setString(3, lt.getGioBatDau());
            ps.setString(4, lt.getGioKetThuc());
            ps.setInt(5, lt.getMaDM());

           int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu thêm thành công ít nhất 1 dòng
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
public java.util.List<Model.LichTrinh> layTatCaLichTrinh() {
        java.util.List<Model.LichTrinh> list = new java.util.ArrayList<>();
        String sql = "SELECT TieuDe, Ngay, GioBatDau, GioKetThuc, MaDM FROM LichTrinh";

        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Model.LichTrinh lt = new Model.LichTrinh(
                    rs.getNString("TieuDe"),
                    rs.getString("Ngay"),
                    rs.getString("GioBatDau"),
                    rs.getString("GioKetThuc"),
                    rs.getInt("MaDM")
                );
                list.add(lt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Lỗi đọc dữ liệu: " + e.toString());
        }
        return list;
}
public boolean xoaLichTrinh(String tieuDe, String ngay, String gioBatDau, String gioKetThuc) {
        String sql = "DELETE FROM LichTrinh WHERE TieuDe = ? AND Ngay = ? AND GioBatDau = ? AND GioKetThuc = ?";

        try (java.sql.Connection conn = DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setNString(1, tieuDe);
            ps.setString(2, ngay);
            ps.setString(3, gioBatDau);
            ps.setString(4, gioKetThuc);

            int soDongBiXoa = ps.executeUpdate();
            return soDongBiXoa > 0;
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Lỗi xóa dữ liệu: " + e.toString());
            return false;
        }
    }
}