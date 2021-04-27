package fa.cpl_java_05.app.controller.main_window;

import fa.cpl_java_05.app.common.AlertBox;
import fa.cpl_java_05.app.main.Main;
import fa.cpl_java_05.model.book.BookCaseModel;
import fa.cpl_java_05.model.book.BookModel;
import fa.cpl_java_05.service.book.BookCaseService;
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

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyBookCaseController implements Serializable, Initializable {

    private boolean editBool = true;

    private static final int size = 20;

    private int currentId = 0;

    private final TableView<BookModel> tableListBook = createTable();

    private int idUser = 0;

    private List<BookModel> data;

    @FXML
    private TextField searchField;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField welcomfield;

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
    private Button removeBtn;


    @FXML
    private Button editBtn;


    @FXML
    void edit(ActionEvent event) {
        if(editBool){
            welcomfield.setDisable(false);
            editBtn.setText("Save");
            editBool = false;
        }else {
            String str = welcomfield.getText();
            BookCaseModel bookCase = new BookCaseService().findByUserId(idUser);
            Boolean bool = new BookCaseService().update(bookCase.getBook_case_id(),str);
            if(!bool){
                welcomfield.setText(bookCase.getBook_case_name());
            }
            welcomfield.setDisable(true);
            editBool = true;
            editBtn.setText("Edit");
        }
    }

    @FXML
    void back(ActionEvent event) {
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("/views/main_window/user_window.fxml"));
            Scene scene = new Scene(root);
            Main.mainStage.close();
            Main.mainStage.setScene(scene);
            Main.mainStage.show();
        }catch (Exception ex){
            ex.printStackTrace();
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
                root = FXMLLoader.load(getClass().getResource("/views/login/login.fxml"));
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
    void removeBook(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to remove this book to your book case?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int id = UserSession.getInstance().getUser().getId();
            int bookCaseId = new BookCaseService().findByUserId(id).getBook_case_id();
            Boolean bool = new ContainService().deleted(bookCaseId,currentId);
            if(bool){
                AlertBox.display("Success","This book is removed to your book case");
                data.clear();
                data = initData("");
                int totalPage = data.size() / 20 ;
                totalPage += totalPage%20 == 0 ? 0 : 1;
                pagination.setPageCount(totalPage == 0 ? 1 : totalPage);
                pagination.setMaxPageIndicatorCount(3);
                pagination.setPageFactory(this::createPage);
            }else {
                AlertBox.display("Warning","Can't remove this book!");
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
        pagination.setPageCount(totalPage==0?1:totalPage);
        pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
        pagination.setPageFactory(this::createPage);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idUser = UserSession.getInstance().getUser().getId();
        String name = new BookCaseService().findByUserId(idUser).getBook_case_name();
        welcomfield.setText(name);
        welcomfield.setDisable(true);
        welcomfield.setStyle("-fx-opacity: 1;");
        data = initData("");
        removeBtn.setDisable(true);
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
        contentArea.setTextFormatter(new TextFormatter<String>(change ->  {
            change.setAnchor(change.getCaretPosition());
            return change ;
        }));


        int totalPage = data.size() / 20 ;
        pagination.setPageCount(totalPage==0?1:totalPage);
        pagination.setMaxPageIndicatorCount(totalPage < 3 ? totalPage+1 : 3);
        pagination.setPageFactory(this::createPage);
        tableListBook.setRowFactory(tv -> {
            TableRow<BookModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1 && (!row.isEmpty())){
                    BookModel bookModel = row.getItem();
                    currentId = bookModel.getBookId();
                    removeBtn.setDisable(false);
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
        int bookCaseId = new BookCaseService().findByUserId(idUser).getBook_case_id();
        if("".equals(text)){
            list = new ContainService().findByBookCaseId(bookCaseId);
        }else {
            list = new ContainService().search(bookCaseId,text);
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
