package fa.cpl_java_05.app.controller.main_window.user;

import fa.cpl_java_05.model.book.BookModel;
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

    private final TableView<BookModel> tableListBook = createTable();

    private List<BookModel> data = initData();

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
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to log out?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            UserSession.cleanSession();
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
        int totalPage = data.size() / 10 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(UserSession.getUser());
        if(!UserSession.getUser().getDeleted()){
            titleField.setDisable(true);
            authorField.setDisable(true);
            pubField.setDisable(true);
            catgoField.setDisable(true);
            briefField.setDisable(true);
            contentArea.setDisable(true);
        }
        int totalPage = data.size() / 10 ;
        pagination.setPageCount(totalPage);
        pagination.setMaxPageIndicatorCount(3);
        pagination.setPageFactory(this::createPage);

        tableListBook.setRowFactory(tv -> {
            TableRow<BookModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1 && (!row.isEmpty())){
                    BookModel bookModel = row.getItem();
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
        tableListBook.setItems(FXCollections.observableArrayList(data.subList(fromIndex,data.size() > toIndex ? toIndex : data.size())));
        return tableListBook;
    }

    private List<BookModel> initData(){
        List<BookModel> list = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            BookModel bookModel = new BookModel();
            bookModel.setBookId(i+1);
            bookModel.setBookTitle("Book " +(i+1));
            bookModel.setAuthor("Author " + (i+1));
            bookModel.setPublisher("Publisher " + (i+1));
            bookModel.setCategory("Category " + (i+1));
            bookModel.setContent("Lorem Ipsum is simply dummy text of the printing and typesetting industry. \nLorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. \nIt has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \nIt was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            list.add(bookModel);
        }
        return list;
    }


}
