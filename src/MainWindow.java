import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable
{
    @FXML
    private Button bOpen, bLoad;
    @FXML
    private AnchorPane pAnchor;
    @FXML
    private Text tPath;

    public Text gettPath()
    {
        return tPath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        bOpen.setOnAction(event ->
        {
            final DirectoryChooser chooseDir = new DirectoryChooser();

            Stage stage = (Stage) pAnchor.getScene().getWindow();

            File chosenFile = chooseDir.showDialog(stage);


            if(!String.valueOf(chosenFile).equals("") && chosenFile != null)
            {
                tPath.setText(String.valueOf(chosenFile));
                tPath.setVisible(true);
                bLoad.setVisible(true);
            }
            else
            {
                bLoad.setVisible(false);
                tPath.setVisible(false);
            }

        });

        bLoad.setOnAction(event ->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Controller.fxml"));
                Parent root = (Parent) loader.load();

                Controller c = loader.getController();
                c.setPath(tPath);

                Scene ControllerScene = new Scene(root);
                Stage window = new Stage();
                window.setScene(ControllerScene);
                window.setTitle("Refleksja - loader");
                window.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
