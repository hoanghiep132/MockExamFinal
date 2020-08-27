package fa.cpl_java_05.app.controller.main_window.user;

import fa.cpl_java_05.app.views.common.AlertBox;
import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.service.book.BookService;
import fa.cpl_java_05.session.UserSession;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserWindowController implements Serializable, Initializable {


    private static final int size = 20;

    private int currentId = 0;

    private final TableView<BookModel> tableListBook = createTable();

    private List<BookModel> data;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Pagination pagination;

    @FXML
    private Label welcomLabel;

    @FXML
    private Button myBookCaseBtn;

    @FXML
    private Button logoutBtn;


    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private TextArea contentArea;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField catgoField;

    @FXML
    private TextField pubField;

    @FXML
    private TextField briefField;

    @FXML
    void clear(ActionEvent event) {
        currentId = 0;
        titleField.setText("");
        authorField.setText("");
        pubField.setText("");
        briefField.setText("");
        catgoField.setText("");
        contentArea.setText("");
    }

    @FXML
    void deleteBook(ActionEvent event) {
        if(currentId == 0){
            AlertBox.display("Notification","You must choose a book!");
        }else{
            Boolean bool = new BookService().deleted(currentId);
            if(bool){
                currentId = 0;
                AlertBox.display("Notification", "This book is deleted");
            }else{
                AlertBox.display("Notification", "This book can't deleted");

            }
        }
    }


    @FXML
    void saveBook(ActionEvent event){
        BookModel bookModel = new BookModel();
        bookModel.setBookTitle(titleField.getText());
        bookModel.setAuthor(authorField.getText());
        bookModel.setPublisher(pubField.getText());
        bookModel.setCategory(catgoField.getText());
        bookModel.setBrief(briefField.getText());
        bookModel.setContent(contentArea.getText());
        if(currentId != 0){
            bookModel.setBookId(currentId);
            Boolean bool = new BookService().update(bookModel);
            if(bool){
                AlertBox.display("Notification", "This book is updated");
            }else {
                AlertBox.display("Warning", "Upload is not succeed");
            }
        }else{
            Boolean bool = new BookService().save(bookModel);
            if(bool){
                AlertBox.display("Notification", "This book is uploaded");
            }else {
                AlertBox.display("Warning", "Update is not succeed");
            }
        }
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            UserSession.getInstance().cleanSession();
            Parent root;
            try{
                Stage stage = new Stage();
                root = FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/login/login.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }else {
            alert.close();
        }
    }

    @FXML
    void search(ActionEvent event) {
        data.clear();
        String text = searchField.getText();
        data = initData(text);
        int totalPage = data.size() / 20 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = initData("");
        if(!UserSession.getInstance().getUser().getRole()){
            titleField.setDisable(true);
            authorField.setDisable(true);
            pubField.setDisable(true);
            catgoField.setDisable(true);
            briefField.setDisable(true);
            contentArea.setDisable(true);
            saveBtn.setVisible(false);
            deleteBtn.setVisible(false);
            clearBtn.setVisible(false);
            welcomLabel.setText("Welcome " + UserSession.getInstance().getUser().getUsername().toUpperCase());
        }else{
            welcomLabel.setText("Welcome ADMIN");
        }
        int totalPage = data.size() / 20 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);

        tableListBook.setRowFactory(tv -> {
            TableRow<BookModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1 && (!row.isEmpty())){
                    BookModel bookModel = row.getItem();
                    currentId = bookModel.getBookId();
                    titleField.setText(bookModel.getBookTitle());
                    authorField.setText(bookModel.getAuthor());
                    pubField.setText(bookModel.getPublisher());
                    catgoField.setText(bookModel.getCategory());
                    briefField.setText(bookModel.getCategory());
                    contentArea.setText(bookModel.getContent());
                }
            });
            return row;
        });
    }

    private TableView<BookModel> createTable(){
        TableView<BookModel> tableView = new TableView<>();
        tableView.setPlaceholder(new Label("No data to display"));

        TableColumn<BookModel,Integer> sttCol = new TableColumn<>("STT");
        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        sttCol.setPrefWidth(50);

        TableColumn<BookModel,Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        idCol.setPrefWidth(50);

        TableColumn<BookModel,String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        nameCol.setPrefWidth(300);

        TableColumn<BookModel,String> authorCol = new TableColumn<>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorCol.setPrefWidth(200);

        TableColumn<BookModel,String> cateCol = new TableColumn<>("Category");
        cateCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        cateCol.setPrefWidth(200);

        TableColumn<BookModel,String> briefCol = new TableColumn<>("Brief");
        briefCol.setCellValueFactory(new PropertyValueFactory<>("brief"));
        briefCol.setPrefWidth(200);

        TableColumn<BookModel,String> publisherCol = new TableColumn<>("Publisher");
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publisherCol.setPrefWidth(200);
        tableView.getColumns().addAll(sttCol,idCol,nameCol,authorCol,cateCol,briefCol,publisherCol);

        return tableView;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * size;
        int toIndex = (pageIndex+1) * size - 1 ;
        toIndex = data.size() > toIndex ? toIndex : data.size();
        tableListBook.setItems(FXCollections.observableArrayList(data.subList(fromIndex,toIndex)));
        return tableListBook;
    }

    private List<BookModel> initData(String text){
        List<BookModel> list;
        if("".equals(text)){
            list = new BookService().findBookAll();
        }else {
            list = new BookService().search(text);
        }
        return list;
    }


}
