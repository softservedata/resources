package org.registrator.community.forms;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.registrator.community.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationForm {

    @Autowired
    UserRepository userRepository;

    @NotNull
    @Size(min=5, max=16, message="Логін повинен містити від {min} до {max} символів")
    @Pattern(regexp = "(?=.*[a-zA-Z])[a-zA-Z0-9].{5,16}",message = "Логін може складатись лише з латинських літер і/або цифр")
    private String login;

    @NotNull
    @Size(min=6, max=20, message="Пароль повинен містити від {min} до {max} символів")
//    @Pattern(regexp = "[a-zA-Z0-9].{6,20}",message = "Пароль може складатись лише з латинських літер (великих і малих) і/або цифр")
    @Pattern(regexp = "(?=.*[a-zA-Z])[a-zA-Z0-9].{6,20}",message = "lower/uppercase letter and at least one digit")
//    @Pattern(regexp = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})+ ",message = "at least one Upper and Lower character + at least one digit and special symbol. Password length: 6 - 20")
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    @Size(min=2, max=30, message="Ім\'я повинно містити від {min} до {max} символів")
    private String firstName;

    @NotNull
    @Size(min=4, max=30, message="Прізвище повинне містити від {min} до {max} символів")
//    @Size(min=4, max=30, message="{lastName.size}")
    private String lastName;

    @NotEmpty
    @Size(min=4, max=30, message="Поле повинне містити від {min} до {max} символів")
    private String middleName;

    @NotEmpty
    @Email(message = "Введена вами e-mail адреса невірна")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?=.*[a-zA-Z]).{2}",message = "Серія паспорту складається із двох букв")
    private String seria;

    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9]).{6}", message = "Поле повинне містити 6 цифр")
    private String number;

    @NotEmpty
    private String publishedByData;

    @AssertTrue(message = "Введене вами підтвердження паролю невірне")
    private boolean isValidConfirmPassword(){
        return confirmPassword != password;
    }

    @AssertTrue(message = "Sorry, but this login name is already taken. Try to enter another one")
    private boolean loginIsAlreadyTaken(){
        return userRepository.findUserByLogin(login) != null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPublishedByData() {
        return publishedByData;
    }

    public void setPublishedByData(String publishedByData) {
        this.publishedByData = publishedByData;
    }
}

