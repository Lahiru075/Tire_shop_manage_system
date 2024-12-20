package lk.ijse.gdse.model;

import lk.ijse.gdse.dto.OrderViewDto;
import lk.ijse.gdse.dto.ReportDto;
import lk.ijse.gdse.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportModel {

    public ArrayList<ReportDto> getAllReport() throws SQLException {
        ResultSet rst = CrudUtil.execute("select p.pId, p.amount, p.date, p.status, d.descId, d.amount, p.paymentMethod from payment p join discount d on p.pId = d.pId");

        ArrayList<ReportDto> reportDtos = new ArrayList<>();

        while (rst.next()) {
            ReportDto reportDto = new ReportDto();
            reportDto.setPaymentId(rst.getString(1));
            reportDto.setPaymentAmount(rst.getDouble(2));
            reportDto.setDate(rst.getString(3));
            reportDto.setPaymentStatus(rst.getString(4));
            reportDto.setDiscountId(rst.getString(5));
            reportDto.setDiscountAmount(rst.getDouble(6));
            reportDto.setPaymentMethod(rst.getString(7));

            reportDtos.add(reportDto);
        }
        return reportDtos;
    }

    public ArrayList<ReportDto> searchByDay(String day1, String day2) throws SQLException {
        ResultSet rst = CrudUtil.execute("select p.pId, p.amount, p.date, p.status, d.descId, d.amount, p.paymentMethod from payment p join discount d on p.pId = d.pId where p.date between ? and ?", day1, day2);

        ArrayList<ReportDto> reportDtos = new ArrayList<>();

        while (rst.next()) {
            ReportDto reportDto = new ReportDto();
            reportDto.setPaymentId(rst.getString(1));
            reportDto.setPaymentAmount(rst.getDouble(2));
            reportDto.setDate(rst.getString(3));
            reportDto.setPaymentStatus(rst.getString(4));
            reportDto.setDiscountId(rst.getString(5));
            reportDto.setDiscountAmount(rst.getDouble(6));
            reportDto.setPaymentMethod(rst.getString(7));

            reportDtos.add(reportDto);
        }
        return reportDtos;
    }
}
