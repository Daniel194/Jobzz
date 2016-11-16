package ro.jobzz.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class EmployeePosting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employeePostingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", nullable = false)
    private Posting posting;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer currency;

    @Column(nullable = false)
    private Date date;

    private String comment;

    @Column(nullable = false)
    private Integer status;

    public EmployeePosting() {
    }

    public Integer getEmployeePostingId() {
        return employeePostingId;
    }

    public void setEmployeePostingId(Integer employeePostingId) {
        this.employeePostingId = employeePostingId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
