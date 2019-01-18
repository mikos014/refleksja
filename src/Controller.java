import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable
{
    @FXML
    private Button bRun;
    @FXML
    private ListView lvList;
    @FXML
    private TextArea taMetadata;
    @FXML
    private TextField tfParam1, tfParam2, tfResult;

    private String Path;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        bRun.setOnAction(event ->
        {
            tfResult.setText(Path);

        });
    }

    public void setPath(Text s)
    {
        Path = s.getText();
    }

}
