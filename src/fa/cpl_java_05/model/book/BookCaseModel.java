package fa.cpl_java_05.model.book;

public class BookCaseModel {

    private Integer book_case_id;
    private String book_case_name;

    public BookCaseModel() {
    }

    public BookCaseModel(Integer book_case_id, String book_case_name) {
        this.book_case_id = book_case_id;
        this.book_case_name = book_case_name;
    }

    public Integer getBook_case_id() {
        return book_case_id;
    }

    public void setBook_case_id(Integer book_case_id) {
        this.book_case_id = book_case_id;
    }

    public String getBook_case_name() {
        return book_case_name;
    }

    public void setBook_case_name(String book_case_name) {
        this.book_case_name = book_case_name;
    }

}
