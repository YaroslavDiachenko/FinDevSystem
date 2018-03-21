package findev.model.dto;

import java.math.BigDecimal;

public class EmployeeDTOGet {
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal hourRate;
    private String positionName;
    private String departmentName;
    private String statusName;
    private String userUsername;

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

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getHourRate() {
        return hourRate;
    }
    public void setHourRate(BigDecimal hourRate) {
        this.hourRate = hourRate;
    }

    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserUsername() {
        return userUsername;
    }
    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
