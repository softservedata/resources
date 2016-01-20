package org.registrator.community.entity.forms;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

public class RegistrationForm {

    @Min(value = 5, message = "Логін повинен містити не менше 5 символів")
    @Max(value = 16, message = "Логін не може містити більше 16 символів")
    private String login;

    @Min(value = 8, message = "")
    @Max(value = 25, message = "")
    private String password;

    @Min(value = 8, message = "")
    @Max(value = 25, message = "")
    @AssertTrue
    private String confirm_password;

    @Min(value = 2, message = "")
    @Max(value = 15, message = "")
    private String firstName;

    @Min(value = 4, message = "")
    @Max(value = 15, message = "")
    private String lastName;

    @Min(value = 5, message = "")
    @Max(value = 15, message = "")
    private String middleName;

    @Email
    private String email;
    @Size(min = 1, max = 80, message = "Title must not be empty or longer than 80 characters")
    private String title;

    @Size(min = 1, message = "Detail must not be empty")
    private String detail;

    // @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    //@Pattern(regexp = "[0-9]+.[0-9]{2}")
    // @Size(min = 1, message = "Price must not be empty")
    //@Digits(integer = 20, fraction = 2, message = "Price is invalid")
    private double price;

    @Min(value = 1, message = "Quantity must be at least 1")
    private long quantity;

    private Timestamp startTime;

    @Future(message = "End time is invalid")
    private Date endTime;
//    private String paymentMethod;

    @Size(min = 1, message = "Shipping service must not be empty")
    private String shippingService;

    @Size(min = 1, message = "Shipping cost must not be empty")
    private String shippingCost;

    @Size(min = 1, message = "Package detail must not be empty")
    private String packageDetail;

    @Size(min = 1, message = "Return policy must not be empty")
    private String returnPolicy;

//    @AssertTrue(message = "Selling method must be selected")
//    private boolean isValid() {
//        return this.sellingType != null;
//    }
//
//    @AssertTrue(message = "Price must be higher than 0")
//    private boolean isValid2() {
//        return this.price > 0;
//    }
//
//    @AssertTrue(message = "The difference between end time and current time must be at least 3 minutes")
//    private boolean isValid3() {
//        if(this.sellingType == SellingType.BID){
//            if(this.endTime == null)
//                return false;
//            return this.endTime.getTime() - System.currentTimeMillis() > 1000*60*3;
//        }
//        return true;
    }

