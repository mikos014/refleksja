import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.*;

import java.io.IOException;


public class Main extends Application
{

    private Stage window;


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Refleksja - opener");
        window.show();
    }



}
