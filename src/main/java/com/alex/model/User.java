package com.alex.model;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static com.alex.util.CostsUtil.DEFAULT_COSTS_PER_DAY;

public class User extends AbstractNamedEntity {

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int costsPerDay = DEFAULT_COSTS_PER_DAY;

    /*public User() {
    }*/

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getCostsPerDay(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, DEFAULT_COSTS_PER_DAY, true,new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, int costsPerDay, boolean enabled,Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.costsPerDay = costsPerDay;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getCostsPerDay() {
        return costsPerDay;
    }

    public void setCostsPerDay(int costsPerDay) {
        this.costsPerDay = costsPerDay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name=" + name +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", costsPerDay=" + costsPerDay +
                '}';
    }
}