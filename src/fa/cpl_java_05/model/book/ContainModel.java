package fa.cpl_java_05.model.book;

import java.util.Date;

public class ContainModel {

    private int bookCaseId;
    private int bookId;
    private Date creat_date;
    private Boolean deleted;

    public ContainModel() {
    }

    public ContainModel(int bookCaseId, int bookId, Date creat_date, Boolean deleted) {
        this.bookCaseId = bookCaseId;
        this.bookId = bookId;
        this.creat_date = creat_date;
        this.deleted = deleted;
    }

    public int getBookCaseId() {
        return bookCaseId;
    }

    public void setBookCaseId(int bookCaseId) {
        this.bookCaseId = bookCaseId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int book_id) {
        this.bookId = book_id;
    }

    public Date getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(Date creat_date) {
        this.creat_date = creat_date;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
