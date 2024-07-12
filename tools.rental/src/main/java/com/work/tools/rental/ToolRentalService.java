package com.work.tools.rental;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import com.work.tools.rental.model.RentalAgreement;
import com.work.tools.rental.model.Tool;
import com.work.tools.rental.model.ToolType;

public class ToolRentalService {
    private static final Map<String, Tool> tools = new HashMap<>();

    static {
        ToolType chainsawType = new ToolType("Chainsaw", 1.49, true, false, true);
        ToolType ladderType = new ToolType("Ladder", 1.99, true, true, false);
        ToolType jackhammerType = new ToolType("Jackhammer", 2.99, true, false, false);

        tools.put("CHNS", new Tool("CHNS", chainsawType, "Stihl"));
        tools.put("LADW", new Tool("LADW", ladderType, "Werner"));
        tools.put("JAKD", new Tool("JAKD", jackhammerType, "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", jackhammerType, "Ridgid"));
    }

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = tools.get(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code.");
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeDays(tool, checkoutDate, dueDate);
        double preDiscountCharge = chargeDays * tool.getToolType().getDailyCharge();
        double discountAmount = (preDiscountCharge * discountPercent) / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(toolCode, tool.getToolType().getType(), tool.getBrand(), rentalDays, checkoutDate, 
            tool.getToolType().getDailyCharge(), chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    private int calculateChargeDays(Tool tool, LocalDate startDate, LocalDate endDate) {
        int chargeDays = 0;
        for (LocalDate date = startDate.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            if (isChargeableDay(tool, date)) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isChargeableDay(Tool tool, LocalDate date) {
        ToolType toolType = tool.getToolType();
        if (isHoliday(date) && !toolType.isHolidayCharge()) {
            return false;
        }
        if (isWeekend(date) && !toolType.isWeekendCharge()) {
            return false;
        }
        if (isWeekday(date) && !toolType.isWeekdayCharge()) {
            return false;
        }
        return true;
    }

    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        Month month = date.getMonth();
        int dayOfMonth = date.getDayOfMonth();
        if (month == Month.JULY && (dayOfMonth == 4 || (dayOfMonth == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY) || (dayOfMonth == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY))) {
            return true;
        }
        if (month == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY && dayOfMonth <= 7) {
            return true;
        }
        return false;
    }
}
