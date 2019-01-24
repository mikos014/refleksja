package jfk;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


public class Main extends Application
{
    private Stage window;

    private Button bOpen, bLoad;
    private Label lInfo, lPath;

    File chosenFile;


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        window = primaryStage;

//        MainWindow mainWindow = new MainWindow();
//        Parent root = mainWindow.makeWindow(window);

        AnchorPane root = new AnchorPane();

        lInfo = new Label("Open and choose directory, which constains Jar file or Class file.");
        lPath = new Label();

        bOpen = new Button("Open");
        bLoad = new Button("Load");

        lPath.setVisible(false);
        bLoad.setVisible(false);

        lInfo.setLayoutX(31.0);
        lInfo.setLayoutY(25.0);
        lPath.setLayoutX(42.0);
        lPath.setLayoutY(85.0);

        bOpen.setLayoutX(386.0);
        bOpen.setLayoutY(144.0);
        bLoad.setLayoutX(388.0);
        bLoad.setLayoutY(85.0);

        bOpen.setOnAction(event ->
        {
            DirectoryChooser chooseDir = new DirectoryChooser();
            Stage stage = (Stage) root.getScene().getWindow();
            chosenFile = chooseDir.showDialog(stage);

            if(!String.valueOf(chosenFile).equals("") && chosenFile != null)
            {
                lPath.setText(String.valueOf(chosenFile));
                lPath.setVisible(true);
                bLoad.setVisible(true);
            }
            else
            {
                bLoad.setVisible(false);
                lPath.setVisible(false);
            }

        });

        bLoad.setOnAction(event ->
        {
            new Controller().makeScene(String.valueOf(chosenFile));

        });

        root.getChildren().addAll(lInfo, lPath, bOpen, bLoad);

        Scene scene = new Scene(root, 500,200 );
        window.setScene(scene);
        window.setTitle("Refleksja - opener");
        window.show();
    }
}
