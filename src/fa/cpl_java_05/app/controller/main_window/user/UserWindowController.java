package fa.cpl_java_05.app.controller.main_window.user;

import fa.cpl_java_05.app.main.Main;
import fa.cpl_java_05.app.views.common.AlertBox;
import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.model.book.ContainModel;
import fa.cpl_java_05.service.book.BookCaseService;
import fa.cpl_java_05.service.book.BookService;
import fa.cpl_java_05.service.book.ContainService;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserWindowController implements Serializable, Initializable {


    private static final int size = 20;

    private int currentId = 0;

    private Boolean deletedBool = false;

    private final TableView<BookModel> tableListBook = createTable();

    private List<BookModel> data;

    @FXML
    private TextField searchField;


    @FXML
    private Pagination pagination;

    @FXML
    private Label welcomLabel;

    @FXML
    private Button myBookCaseBtn;


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
    private ComboBox<String> choiceCombobox;




    @FXML
    void openBookCase(ActionEvent event){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/main_window/user/my_book_case/my_book_case.fxml"));
            Scene scene = new Scene(root);
            Main.mainStage.close();
            Main.mainStage.setScene(scene);
            Main.mainStage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure want to delete this book?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                Boolean bool = new BookService().deleted(currentId);
                if(bool){
                    currentId = 0;
                    AlertBox.display("Notification", "This book is deleted");
                    data.clear();
                    String text = searchField.getText();
                    data = initData(text);
                    int totalPage = data.size() / 20 ;
                    totalPage += totalPage%20 == 0 ? 0 : 1;
                    pagination.setPageCount(totalPage==0?1:totalPage);
                    pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
                    pagination.setPageFactory(this::createPage);
                }else{
                    AlertBox.display("Warning", "This book can't deleted");

                }
            }else{
                alert.close();
            }
        }
    }

    @FXML
    void choice(ActionEvent event){
        String value = choiceCombobox.getValue();
        if(value.equals("Deleted")){
            deletedBool = true;
            deleteBtn.setDisable(true);
            saveBtn.setDisable(true);
            clearBtn.setDisable(true);
            titleField.setDisable(true);
            titleField.setStyle("-fx-opacity: 1;");
            authorField.setDisable(true);
            authorField.setStyle("-fx-opacity: 1;");
            pubField.setDisable(true);
            pubField.setStyle("-fx-opacity: 1;");
            catgoField.setDisable(true);
            catgoField.setStyle("-fx-opacity: 1;");
            briefField.setDisable(true);
            briefField.setStyle("-fx-opacity: 1;");
            contentArea.setDisable(true);
            contentArea.setStyle("-fx-opacity: 1;");
        }else {
            deletedBool = false;
            deleteBtn.setDisable(false);
            saveBtn.setDisable(false);
            clearBtn.setDisable(false);
        }
        data.clear();
        String text = searchField.getText();
        data = initData(text);
        int totalPage = data.size() / 20 ;
        totalPage += totalPage%20 == 0 ? 0 : 1;
        pagination.setPageCount(totalPage==0?1:totalPage);
        pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
        pagination.setPageFactory(this::createPage);
    }


    @FXML
    void saveBook(ActionEvent event){
       if(UserSession.getInstance().getUser().getRole()){
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
                   AlertBox.display("Notification", "This book is updated!");
               }else {
                   AlertBox.display("Warning", "Upload is not succeed!");
               }
           }else{
               Boolean bool = new BookService().save(bookModel);
               if(bool){
                   AlertBox.display("Notification", "This book is uploaded");
               }else {
                   AlertBox.display("Warning", "Update is not succeed");
               }
           }
       }else {
           int userId = UserSession.getInstance().getUser().getId();
           int bookCaseID = new BookCaseService().findByUserId(userId).getBook_case_id();
           System.out.printf(bookCaseID + ", " + userId);
           int bookId = currentId;
           Boolean bool = new ContainService().save(bookCaseID,bookId,new java.sql.Date(new Date().getTime()));
           if(bool){
               AlertBox.display("Success","This book is added to your book case");
           }else if(bool == null){
               AlertBox.display("Danger", "Server Internal Error");
           }else {
               AlertBox.display("Warning", "This book is exist in book case");

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
                root = FXMLLoader.load(getClass().getResource("/fa/cpl_java_05/app/views/login/login.fxml"));
                Scene scene = new Scene(root);
                Main.mainStage.close();
                Main.mainStage.setScene(scene);
                Main.mainStage.show();
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
        totalPage += totalPage%20 == 0 ? 0 : 1;
        pagination.setPageCount(totalPage==0?1:totalPage);
        pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
        pagination.setPageFactory(this::createPage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = initData("");
        if(!UserSession.getInstance().getUser().getRole()){
            choiceCombobox.setVisible(false);
            titleField.setDisable(true);
            titleField.setStyle("-fx-opacity: 1;");
            authorField.setDisable(true);
            authorField.setStyle("-fx-opacity: 1;");
            pubField.setDisable(true);
            pubField.setStyle("-fx-opacity: 1;");
            catgoField.setDisable(true);
            catgoField.setStyle("-fx-opacity: 1;");
            briefField.setDisable(true);
            briefField.setStyle("-fx-opacity: 1;");
            contentArea.setDisable(true);
            contentArea.setStyle("-fx-opacity: 1;");
            saveBtn.setText("ADD");
            saveBtn.setDisable(true);
            deleteBtn.setVisible(false);
            clearBtn.setVisible(false);
            welcomLabel.setText("Welcome " + UserSession.getInstance().getUser().getUsername().toUpperCase());
        }else{
            choiceCombobox.getItems().addAll("Available","Deleted");
            welcomLabel.setText("Welcome ADMIN");
            myBookCaseBtn.setVisible(false);
        }
        int totalPage = data.size() / 20 ;
        totalPage += totalPage%20 == 0 ? 0 : 1;
        pagination.setPageCount(totalPage==0?1:totalPage);
        pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
        pagination.setPageFactory(this::createPage);

        tableListBook.setRowFactory(tv -> {
            TableRow<BookModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1 && (!row.isEmpty())){
                    BookModel bookModel = row.getItem();
                    currentId = bookModel.getBookId();
                    saveBtn.setDisable(false);
                    titleField.setText(bookModel.getBookTitle());
                    authorField.setText(bookModel.getAuthor());
                    pubField.setText(bookModel.getPublisher());
                    catgoField.setText(bookModel.getCategory());
                    briefField.setText(bookModel.getBrief());
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
            if(deletedBool){
                list = new BookService().findBookAllDeleted();
            }else {
                list = new BookService().findBookAll();
            }
        }else {
            if(deletedBool){
                list = new BookService().searchBookDeleted(text);
            }else {
                list = new BookService().search(text);
            }
        }
        return convertToRowModel(list);
    }

    private List<BookModel> convertToRowModel(List<BookModel> books){
        List<BookModel> list = IntStream.range(0,books.size())
                .mapToObj(i -> {
                    BookModel b = books.get(i);
                    b.setStt(i+1);
                    return b;
                }).collect(Collectors.toList());
        return list;
    }


}
