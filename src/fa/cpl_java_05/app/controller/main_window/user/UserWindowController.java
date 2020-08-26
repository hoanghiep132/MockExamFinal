package fa.cpl_java_05.app.controller.main_window.user;

import fa.cpl_java_05.entities.book.Book;
import fa.cpl_java_05.session.UserSession;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserWindowController implements Serializable, Initializable {


    private static final int size = 20;

    private final TableView<Book> tableListBook = createTable();

    private List<Book> data = initData();

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextArea contentArea;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            UserSession.cleanSession();
        }else {
            alert.close();
        }
    }

    @FXML
    void search(ActionEvent event) {
        int totalPage = data.size() / 10 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contentArea.setDisable(true);
        int totalPage = data.size() / 10 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);

        tableListBook.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1 && (!row.isEmpty())){
                    Book book = row.getItem();
                    titleLabel.setText(book.getBookTitle());
                    authorLabel.setText(book.getAuthor());
                    publisherLabel.setText(book.getPublisher());
                    categoryLabel.setText(book.getCategory());
                    contentArea.setText(book.getContent());
                }
            });
            return row;
        });
    }

    private TableView<Book> createTable(){
        TableView<Book> tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No data to display"));
        TableColumn<Book,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        idCol.setPrefWidth(50);

        TableColumn<Book,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        nameCol.setPrefWidth(250);

        TableColumn<Book,String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(150);

        TableColumn<Book,String> publisherCol = new TableColumn<>("Publisher");
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publisherCol.setPrefWidth(150);
        tableView.getColumns().addAll(idCol,nameCol,authorCol,publisherCol);

        return tableView;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * size;
        int toIndex = (pageIndex+1) * size - 1 ;
        tableListBook.setItems(FXCollections.observableArrayList(data.subList(fromIndex,data.size() > toIndex ? toIndex : data.size())));
        return tableListBook;
    }

    private List<Book> initData(){
        List<Book> list = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            Book book = new Book();
            book.setBookId(i+1);
            book.setBookTitle("Book " +(i+1));
            book.setAuthor("Author " + (i+1));
            book.setPublisher("Publisher " + (i+1));
            book.setCategory("Category " + (i+1));
            book.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \nLorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \nIt has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \nIt was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            list.add(book);
        }
        return list;
    }


}
