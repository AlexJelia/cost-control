package com.alex.to;

import com.alex.util.UserUtil;
import com.alex.web.HasEmail;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo extends BaseTo implements HasEmail, Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml // https://stackoverflow.com/questions/17480809
    private String email;

    @NotBlank
    @Size(min = 5, max = 32, message = "length must be between 5 and 32 characters")
    private String password;

    @Range(min = 10, max = 10000)
    @NotNull
    private Integer costsPerDay = UserUtil.DEFAULT_COSTS_PER_DAY;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, int costsPerDay) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.costsPerDay = costsPerDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCostsPerDay(Integer costsPerDay) {
        this.costsPerDay = costsPerDay;
    }

    public Integer getCostsPerDay() {
        return costsPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", costsPerDay='" + costsPerDay + '\'' +
                '}';
    }
}