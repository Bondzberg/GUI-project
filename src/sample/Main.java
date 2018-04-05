package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.File;

public class Main extends Application {

    private TextArea center;
    private TextField textField;
    private Button button;
    private TextArea right;
    private File Root;
    private DirectoryChooser directoryChooser;
    private String input;
    private int fol;
    private int dir;
    private Text top;
    private Button saveB;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        top = new Text("File searcher Directory on: ");
        top.setTextAlignment(TextAlignment.CENTER);
        root.setTop(top);

        Pane bottom = new Pane();
        textField = new TextField("Enter String here");
        button = new Button("Get Root");
        //DirectoryChooser directoryChooser = new DirectoryChooser();
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directoryChooser = new DirectoryChooser();
                File file =directoryChooser.showDialog(primaryStage.getOwner());
                input = textField.getText();
                textField.clear();
                setRoot(file);
                walk(file);
            }
        });
        button.setText("get root directory");
        button.setLayoutX(150);

        saveB = new Button("Save");
        /*button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                directoryChooser = new DirectoryChooser();
                String path = directoryChooser.showDialog(primaryStage.getOwner()).getPath();
                File file = new File(path,textField.getText());
            }
        });*/

        bottom.getChildren().addAll(textField,button);
        root.setBottom(bottom);


        center = new TextArea();
        center.setText("");
        center.setEditable(false);
        center.setMinWidth(200);
        root.setCenter(center);

        right = new TextArea("Folders: \nFiles: ");
        right.setEditable(false);
        root.setRight(right);

        primaryStage.setTitle("File Searcher: ");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setRoot(File file)
    {
        Root = file;
    }

    public void walk(File file)
    {
        File[] files = file.listFiles();
        try {

            for (File fiel : files) {
                if (fiel.isDirectory()) {
                    top.setText(top.getText().substring(0,top.getText().lastIndexOf(" "))+" "+fiel.getAbsolutePath());
                    dir++;
                    right.setText("Folders:"+dir+ "\nFiles:"+fol);
                    walk(fiel);
                } else {
                    fol++;
                    right.setText("Folders:"+dir+ "\nFiles:"+fol);
                    //center.setText(center.getText() + "\nFile:" + fiel.getCanonicalPath());
                }
                String name = fiel.getName();
                int index = fiel.getName().indexOf(input);
                if(similarity(name,input)||index!=-1)
                {
                    center.setText(center.getText() + "\nFile:" + fiel.getName());
                    center.setText(center.getText() + "\nDir:"+fiel.getAbsolutePath());
                }

            }
        }catch (Exception e){

        }
    }
    public boolean similarity(String a,String b)
    {
        double x =0;
        double length = a.length();
        if(length>b.length())
            length=b.length();
        if(Math.abs(a.length()-b.length())>2)
            return false;
        for(int i=0;i<length;i++)
        {
            if(a.substring(i,i+1).equals(b.substring(i,i+1)))
            {
                x++;
            }
        }
        System.out.println(x+" "+length);
        if((x/length)>=0.7)
            return true;
        return false;
    }

    public void saveOutputs()
    {


    }
}
