package Model;

import java.util.Date;

public class User extends BaseEntity<Long> {

    private String name;
    private String lastName;
    private String Password;
    private String phoneNumber;
    private String email;
    private double credit;
    private String nationalCode;
    private boolean gender;
    private Date dateOfBirth;
    private Date signUpDate;
    private String telephone;
    private String accountNumber;

    public User() {
    }

    public User(String name, String lastName, String password, String phoneNumber, String email, double credit, String nationalCode, boolean gender, Date dateOfBirth, Date signUpDate, String telephone, String accountNumber) {
        this.name = name;
        this.lastName = lastName;
        Password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.credit = credit;
        this.nationalCode = nationalCode;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.signUpDate = signUpDate;
        this.telephone = telephone;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
