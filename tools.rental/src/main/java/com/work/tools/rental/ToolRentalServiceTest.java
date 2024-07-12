package com.work.tools.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.work.tools.rental.model.RentalAgreement;

public class ToolRentalServiceTest {
    private ToolRentalService rentalService;

    @BeforeEach
    public void setUp() {
        rentalService = new ToolRentalService();
    }

    @Test
    public void testInvalidDiscountPercent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testInvalidRentalDays() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 0, 10, LocalDate.of(2020, 7, 2));
        });
        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testInvalidToolCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("INVALID", 5, 10, LocalDate.of(2020, 7, 2));
        });
        assertEquals("Invalid tool code.", exception.getMessage());
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutLADWWithValidInputs() {
        RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutCHNSWithValidInputs() {
        RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutJAKDWithValidInputs() {
        RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutJAKRWithValidInputs() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutJAKRWith50PercentDiscount() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutLADWWithHolidayAndWeekend() {
        RentalAgreement agreement = rentalService.checkout("LADW", 7, 0, LocalDate.of(2021, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutCHNSWithHolidayAndWeekend() {
        RentalAgreement agreement = rentalService.checkout("CHNS", 7, 0, LocalDate.of(2021, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutJAKDWithHolidayAndWeekend() {
        RentalAgreement agreement = rentalService.checkout("JAKD", 7, 0, LocalDate.of(2021, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutJAKRWithHolidayAndWeekend() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 7, 0, LocalDate.of(2021, 7, 2));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }
    

    @Test
    public void testCheckoutOnLaborDay() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 3, 20, LocalDate.of(2021, 9, 4));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }

    @Test
    public void testCheckoutOnIndependenceDay() {
        RentalAgreement agreement = rentalService.checkout("JAKR", 3, 20, LocalDate.of(2021, 7, 4));
        assertNotNull(agreement);
        agreement.printAgreement();
        System.out.println("*********************************************\n\n\n\n\n\n\n");
    }
}
