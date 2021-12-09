/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowershop;

/**
 *
 * @author areeb
 */
public class EmployeeList {
    private int employeeid;
    private String fname;
    private String lname;
    private String gender;
    private String paddress;
    private String birthdate;
    private String hiredate;
    private Double salary;
    private String contactno;
    private String tshift;

    public EmployeeList(int employeeid, String fname, String lname, String gender, String paddress, String birthdate, String hiredate, Double salary, String contactno, String tshift) {
        this.employeeid = employeeid;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.paddress = paddress;
        this.birthdate = birthdate;
        this.hiredate = hiredate;
        this.salary = salary;
        this.contactno = contactno;
        this.tshift = tshift;
    }

    /**
     * @return the employeeid
     */
    public int getEmployeeid() {
        return employeeid;
    }

    /**
     * @param employeeid the employeeid to set
     */
    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    /**
     * @return the fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname the fname to set
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname the lname to set
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the paddress
     */
    public String getPaddress() {
        return paddress;
    }

    /**
     * @param paddress the paddress to set
     */
    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    /**
     * @return the birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate the birthdate to set
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return the hiredate
     */
    public String getHiredate() {
        return hiredate;
    }

    /**
     * @param hiredate the hiredate to set
     */
    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    /**
     * @return the salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * @return the contactno
     */
    public String getContactno() {
        return contactno;
    }

    /**
     * @param contactno the contactno to set
     */
    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    /**
     * @return the tshift
     */
    public String getTshift() {
        return tshift;
    }

    /**
     * @param tshift the tshift to set
     */
    public void setTshift(String tshift) {
        this.tshift = tshift;
    }
    
    
    

}