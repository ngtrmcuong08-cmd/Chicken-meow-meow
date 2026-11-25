package Model;

public class LichTrinh {
   // private int maLT;
    private String tieuDe;
    private String ngay;
    private String gioBatDau;
    private String gioKetThuc;
    private int maDM;

    // Constructor dùng khi thêm mới (chưa cần maLT vì SQL tự tăng)
    public LichTrinh(String tieuDe, String ngay, String gioBatDau, String gioKetThuc, int maDM) {
        this.tieuDe = tieuDe;
        this.ngay = ngay;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.maDM = maDM;
    }

    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }

    public String getNgay() { return ngay; }
    public void setNgay(String ngay) { this.ngay = ngay; }

    public String getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(String gioBatDau) { this.gioBatDau = gioBatDau; }

    public String getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(String gioKetThuc) { this.gioKetThuc = gioKetThuc; }

    public int getMaDM() { return maDM; }
    public void setMaDM(int maDM) { this.maDM = maDM; }
}