package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
public class Controller implements Initializable {

    DSSV ds = new DSSV();
    DSSV search = new DSSV();


    //Thêm sinh viên
    @FXML private TextField Id;
    @FXML private TextField Name;
    @FXML private TextField Year;


    //bắt sự kiện khi bấm
    public void xacNhanThemSV() {

        List<SinhVien> temp = ds.getList();
        if (Name.getText().equals("") || Id.getText().equals("") || Year.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nhập thông tin không đúng");
            alert.setHeaderText("Bạn đang nhập thiếu thông tin sinh viên");
            alert.setContentText("Vui lòng nhập lại!!!");
            alert.showAndWait();
        }

        else {
            String name = Name.getText();
            String id = Id.getText().replaceAll(" ","");
            int year = Integer.parseInt(Year.getText());


            for (int i = 0; i < temp.size(); i++) {
                SinhVien sv = temp.get(i);

                if (sv.getId().equals(id)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Nhập thông tin không đúng");
                    alert.setHeaderText("Bạn đang nhập trùng mã số sinh viên");
                    alert.setContentText("Vui lòng chọn chính xác!");
                    alert.showAndWait();
                    return;
                }
            }

            SinhVien sv = new SinhVien(id, name, year);
            ds.add(sv);
            Name.clear();
            Id.clear();
            Year.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nhập thông tin thành công");
            alert.setHeaderText("Bạn đã nhập thông tin thành công");
            alert.setContentText("Nhấn OK để tiếp tục");
            alert.showAndWait();


        }

    }
    //Xóa sinh viên
    @FXML private TextField Xoa_mssv;

    //Phương thức xacNhanXoaSV: xóa sinh viên khi nhấn Button
    public void xacNhanXoaSV(){
        String xoa_mssv = Xoa_mssv.getText();
        //Nếu có mã số sinh viên trong danh sách sinh viên
        if(!ds.checkId(xoa_mssv)){
            //Thông báo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Xóa thông tin sinh viên");
            alert.setHeaderText("Đã xóa thành công");
            alert.setContentText("Nhấn OK để tiếp tục");
            alert.showAndWait();
            //Xóa sinh viên và clear TextField
            ds.remove(xoa_mssv);
            Xoa_mssv.clear();
        }
        //Nếu không có mã số sinh viên này trong danh sách
        else{
            //Thông báo không có sinh viên
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Xóa thông tin sinh viên");
            alert.setHeaderText("Không có sinh viên mang mã số này");
            alert.setContentText("Vui lòng nhập lại");
            alert.showAndWait();

            //Clear TextField
            Xoa_mssv.clear();
        }
    }


    //In danh sách sinh viên
    @FXML private TableView<SinhVien> table;

    //Khai báo các cột để hiển thị danh sách
    @FXML private TableColumn<SinhVien, String> idColumn;
    @FXML private TableColumn<SinhVien, String> nameColumn;
    @FXML private TableColumn<SinhVien, Integer> yearColumn;



    //Phương thức inDSSV: in danh sách ra table khi nhấn Button Cập nhật danh sách
    public void inDSSV(){
        //reset Table
        table.refresh();

        //khai báo list danh sách để chứa tất cả sinh viên
        List<SinhVien> list = ds.getList();



        //Nếu không in danh sách ra table
        list = FXCollections.observableArrayList(
                ds.getList()
        );
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));


        table.setItems((ObservableList<SinhVien>) list);

        //Nếu danh sách trống
        if(list.size() == 0){
            //Thông báo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Danh sách sinh viên trống");
            alert.setHeaderText("Bạn cần thêm sinh viên để có thể hiển thị danh sách");
            alert.setContentText("Nhấn OK để tiếp tục");
            alert.showAndWait();
            return;
        }
    }


        //Tìm kiếm sinh viên
    @FXML private TextField findId;
    @FXML private TextField findName;
    @FXML private TextField findYear;
    //Khai báo table để hiện thông tin sau khi tìm kiếm
    @FXML private TableView<SinhVien> table2;

    @FXML private TableColumn<SinhVien, String> idColumn2;
    @FXML private TableColumn<SinhVien, String> nameColumn2;

    @FXML private TableColumn<SinhVien, Integer> yearColumn2;



    //Khai báo danh sách sinh viên listFind
    List<SinhVien> listFind;


    //Phương thức timTheoMSSV: trả về sinh viên có mã số đã nhập ở TextField và in ra table
    public void timTheoMSSV(){
        String id = findId.getText();
        //Nếu đã nhập mã số cần tìm
        if(!id.trim().isEmpty()){
            //Khai báo một sinh viên tạm
            SinhVien temp;
            //Tìm sinh viên có mã số thỏa mãn
            temp = ds.searchById(id);
            //Nếu không tìm thấy
            if(temp == null){
                //Thông báo
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Không tim thấy sinh viên");
                alert.setHeaderText("Mã số sinh viên bạn nhập không có sinh viên thỏa mãn");
                alert.setContentText("Vui lòng nhập lại!!!");
                alert.showAndWait();
            }
            //Nếu tìm thấy, thêm sinh viên vào danh sách tim
            search.add(temp);

            //Hiển thị danh sách ra table
            listFind = FXCollections.observableArrayList(
                    search.getList()
            );
            nameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
            idColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
            yearColumn2.setCellValueFactory(new PropertyValueFactory<>("year"));

            table2.setItems((ObservableList<SinhVien>)listFind);

            search.removeAll();
        }

        else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Chưa nhập thông tin");
            alert.showAndWait();
        }
    }

    //Khai báo phương thức initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
