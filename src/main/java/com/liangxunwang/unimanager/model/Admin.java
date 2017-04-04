package com.liangxunwang.unimanager.model;

/**
 */
public class Admin {
    private String manager_id;
    private String manager_admin;
    private String manager_cover;
    private String manager_pass;
    private String is_use;
    private String emp_id;
    private String permissions;

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_admin() {
        return manager_admin;
    }

    public void setManager_admin(String manager_admin) {
        this.manager_admin = manager_admin;
    }

    public String getManager_cover() {
        return manager_cover;
    }

    public void setManager_cover(String manager_cover) {
        this.manager_cover = manager_cover;
    }

    public String getManager_pass() {
        return manager_pass;
    }

    public void setManager_pass(String manager_pass) {
        this.manager_pass = manager_pass;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
