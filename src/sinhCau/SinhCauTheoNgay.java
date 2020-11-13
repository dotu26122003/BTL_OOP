package sinhCau;

import cacKieuDuLieu.ChiSoIndex;

import mauCau.MauCauTheoNgay;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class SinhCauTheoNgay extends AbstractSinhCau {
    
    private ArrayList<ChiSoIndex> dataList = new ArrayList<>();
    public static ArrayList<String> doanVan = new ArrayList<>();

    public void setDataList (ArrayList<ChiSoIndex> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<ChiSoIndex> getDataList() {
        return this.dataList;
    }

    public String chenDuLieu(String cau, ChiSoIndex data) {
        cau = cau.replace("|ngay|", data.getNgayGD() );
        cau = cau.replace("|maCK|", data.getMaChungKhoan() );
        cau = cau.replace("|thayDoi|", data.chuyenDoiThayDoi() );
        cau = cau.replace("|giaDongCua|", String.valueOf( data.getGiaDongCua() ) );
        cau = cau.replace("|giaTriThayDoi|", String.valueOf( data.getGiaTriThayDoi()));
        cau = cau.replace("|klgdThoaThuan|", String.valueOf(data.quyDoiKLGDThoaThuan()));
        cau = cau.replace("|gtgdThoaThuan|", String.valueOf(data.quyDoiGTGDThoaThuan()));
        cau = cau.replace("|giaMoCua|", String.valueOf(data.getGiaMoCua()));
        cau = cau.replace( "|gtgdKhopLenh|", String.valueOf(data.quyDoiGTGDKhopLenh()));
        cau = cau.replace("|klgdKhopLenh|", String.valueOf(data.quyDoiKLGDKhopLenh()));
        return cau;
    }


    public String sinhChotPhienGiaoDich(ChiSoIndex data) {
        String cau ;
        if(data.getGiaTriThayDoi() > 0) {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getChotPhienGiaoDichTang());
        } else {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getChotPhienGiaoDichGiam());
        }
        cau = chenDuLieu(cau , data);
        return cau;
    }


    public String sinhMoPhienGiaoDich(ChiSoIndex data) {
        String cau ;
        if(data.getThayDoiDauPhien() > 0 && data.getGiaTriThayDoi() > 0){
            cau = sinhCauNgauNhien(MauCauTheoNgay.getKhoiSacDauPhienTang());
        }
        else if(data.getThayDoiDauPhien() > 0 && data.getGiaTriThayDoi() < 0){
            cau = sinhCauNgauNhien(MauCauTheoNgay.getKhoiSacDauPhienGiam());
        }
        else if(data.getThayDoiDauPhien() < 0 && data.getGiaTriThayDoi() > 0){
            cau = sinhCauNgauNhien(MauCauTheoNgay.getGiamDauPhienTang());
        }
        else{
            cau = sinhCauNgauNhien(MauCauTheoNgay.getGiamDauPhienGiam());
        }
        cau = chenDuLieu(cau , data);
        return cau;
    }


    public String sinhGiaCaoNhat(ChiSoIndex data) {
        String cau;
        cau = sinhCauNgauNhien(MauCauTheoNgay.getGiaCaoNhat());
        cau = chenDuLieu(cau, data);
        return cau;
    }


    public String sinhGiaThapNhat(ChiSoIndex data) {
        String cau;
        cau = sinhCauNgauNhien(MauCauTheoNgay.getGiaThapNhat());
        cau = chenDuLieu(cau, data);
        return cau;
    }





    public String sinhGiaoDichKhopLenh(ChiSoIndex data) {
        String cau;
        cau = sinhCauNgauNhien(MauCauTheoNgay.getGiaoDichKhopLenh());
        cau = chenDuLieu(cau, data);
        return cau;
    }


    public String sinhGiaoDichThoaThuan(ChiSoIndex data) {
        String cau;
        cau = sinhCauNgauNhien(MauCauTheoNgay.getGiaoDichThoaThuan());
        cau = chenDuLieu(cau, data);
        return cau;
    }


    public String sinhCauDanhGia(ChiSoIndex data) {
        String cau;
        if(data.getTiLeThayDoi() > 1 ) {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getTangManh());
        }
        else if(data.getTiLeThayDoi() > 0 && data.getTiLeThayDoi() <= 1) {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getTangNhe());
        }
        else if(data.getTiLeThayDoi() < -1) {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getGiamManh());
        }
        else {
            cau = sinhCauNgauNhien(MauCauTheoNgay.getGiamNhe());
        }
        cau = chenDuLieu(cau, data);
        return cau;
    }

    public ArrayList<String> sinhDuLieuMoiNgay(ChiSoIndex data) {
        ArrayList<String> doanVan = new ArrayList<>();
        //Thông tin chứng khoán theo ngày sẽ có các thông tin
        //Thông tin về mở phiên giao dịch, về giao dich thõa thuận, giao dịch khớp lệnh
        //Thông tin chốt phiên giao dịch và sinh câu đánh giá chung
        doanVan.add("Tin tức về mã chứng khoán " + data.getMaChungKhoan() + " ngày "+ data.getNgayGD() + ": ");
        doanVan.add(sinhMoPhienGiaoDich(data));
        doanVan.add(sinhGiaoDichThoaThuan(data));
        doanVan.add(sinhGiaoDichKhopLenh(data));
        doanVan.add(sinhChotPhienGiaoDich(data));
        doanVan.add(sinhCauDanhGia(data));

        return doanVan;
    }

    public ArrayList<String> sinhDuLieuNhieuNgay() {
        ArrayList<String> doanVan = new ArrayList<>();

        int MAX_SIZE = this.dataList.size();

        //Thiết lập các giá trị max, min ban đầu là phiên đầu tiên trong danh sách
        ChiSoIndex giaCaoNhat = dataList.get(0);
        ChiSoIndex giaThapNhat = dataList.get(0);
        ChiSoIndex thayDoiManhNhat = dataList.get(0);
        ChiSoIndex thayDoiItNhat = dataList.get(0);

        //Tìm các phiên có giá đóng cửa cao nhất, thấp nhất, phiên tăng mạnh nhất, phiên tăng ít nhất
        for(int i = 0; i < MAX_SIZE; i++) {
            if(dataList.get(i).getGiaDongCua() > giaCaoNhat.getGiaDongCua()) {
                giaCaoNhat = dataList.get(i);
            }
            if(dataList.get(i).getGiaDongCua() < giaThapNhat.getGiaDongCua()) {
                giaThapNhat = dataList.get(i);
            }
            if(dataList.get(i).getGiaTriThayDoi() > thayDoiManhNhat.getGiaTriThayDoi()) {
                thayDoiManhNhat = dataList.get(i);
            }
            if(dataList.get(i).getGiaTriThayDoi() < thayDoiItNhat.getGiaTriThayDoi()) {
                thayDoiItNhat = dataList.get(i);
            }
        }

        //Sinh câu đánh giá tổng quát trong nhiều phiên
        doanVan.add("Đánh giá tổng quan :" );
        doanVan.add(sinhGiaCaoNhat(giaCaoNhat));
        doanVan.add(sinhGiaThapNhat(giaThapNhat));

        //Sinh câu tăng liên tục ,giảm liên tục trong nhiều phiên giao dịch
        //Chỉ xét những mã tăng liên tục hoặc giảm liên tục từ 3 ngày liện tiếp trở lên

        for (int i = 0; i < MAX_SIZE - 2; i++) {
            if(dataList.get(i).getGiaTriThayDoi() > 0 && dataList.get(i + 1).getGiaTriThayDoi() > 0 && dataList.get(i + 2).getGiaTriThayDoi() > 0) {
                double sum = dataList.get(i).getGiaTriThayDoi() + dataList.get(i + 1).getGiaTriThayDoi() + dataList.get(i + 2).getGiaTriThayDoi();
                int j = i + 2;
                while(j + 1 < MAX_SIZE) {
                    if(dataList.get(i + 1).getGiaTriThayDoi() > 0) {
                        j++;
                        sum += dataList.get(j+1).getGiaTriThayDoi();
                    } else {
                        break;
                    }
                }

            }
            if(dataList.get(i).getGiaTriThayDoi() < 0 && dataList.get(i + 1).getGiaTriThayDoi() < 0 && dataList.get(i + 2).getGiaTriThayDoi() < 0) {
                double sum = dataList.get(i).getGiaTriThayDoi() + dataList.get(i + 1).getGiaTriThayDoi() + dataList.get(i + 2).getGiaTriThayDoi();
                int j = i + 2;
                while(j + 1 < MAX_SIZE) {
                    if(dataList.get(i + 1).getGiaTriThayDoi() < 0) {
                        sum += dataList.get(j+1).getGiaTriThayDoi();
                        j++;
                    } else {
                        break;
                    }
                }

            }
        }
        return doanVan;
    }

    public ArrayList<String> ketHopDanhSach(ArrayList<String> danhSach1, ArrayList<String> danhSach2) {
        for (int i = 0; i < danhSach2.size(); i++) {
            danhSach1.add(danhSach2.get(i));
        }
        return danhSach1;
    }

    @Override
    public ArrayList<String> sinhDoanVan() {
        int MAX_SIZE = dataList.size();
        int i = 0;
        int count = 0;

        //sinh câu dữ liệu ngày đầu tiên trong danh sách :
        doanVan = ketHopDanhSach(doanVan, sinhDuLieuMoiNgay(dataList.get(MAX_SIZE - 1)));

        //sinh dữ liệu 3 ngày bất kì trong khoảng
        Random rand = new Random();//trừ hai ngày đầu và cuối của danh sách
        while(count < 3) {
            i = rand.nextInt(MAX_SIZE - 2) + 1;
            doanVan = ketHopDanhSach(doanVan, sinhDuLieuMoiNgay(dataList.get(i)));
            count ++;
        }

        //sinh dữ liệu cho ngày cuối cùng trong danh sách :
        doanVan = ketHopDanhSach(doanVan, sinhDuLieuMoiNgay(dataList.get(0)));

        //sinh dữ liệu chung cho nhiều ngày
        doanVan = ketHopDanhSach(doanVan, sinhDuLieuNhieuNgay());

        return doanVan;
    }

}
