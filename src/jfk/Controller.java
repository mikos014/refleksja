package jfk;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class Controller
{

    private Label lInfo, lInput, lOutput;
    private Button bRun;
    private TextArea taData;
    private TextField tfParam1, tfParam2, tfResult;
    private ListView lvMethods;

    private String pathOfDir, selectedMethod;
    private File directory;
    private LinkedList<String> listOfPaths;
    private Map<String, Class> classes;

    public void makeScene(String path)
    {
        Stage window = new Stage();
        GridPane node = new GridPane();

        pathOfDir = path;

        lInfo = new Label("Choose method:");
        lInput = new Label("Input:");
        lOutput = new Label("Output:");

        bRun = new Button("Run");
        taData = new TextArea();
        taData.prefHeight(122.0);
        taData.setEditable(false);

        tfParam1 = new TextField();
        tfParam1.setPromptText("Parametr 1");
        tfParam1.setEditable(false);
        tfParam2 = new TextField();
        tfParam2.setPromptText("Parametr 2");
        tfParam2.setEditable(false);
        tfResult = new TextField();
        tfResult.setEditable(false);

        lvMethods = new ListView();

        node.setPadding(new Insets(10, 10, 10, 10));

        node.setHgap(23.0);
        node.setVgap(10.0);

        node.add(lInfo, 0,0);
        node.add(lvMethods, 0, 1);
        node.add(taData, 1,1);
        node.add(lInput, 0,2);
        node.add(tfParam1, 0,3);
        node.add(tfParam2, 1,3);
        node.add(bRun, 2, 3);
        node.add(lOutput, 0,4);
        node.add(tfResult, 0,5);

        taData.setText("Path of selected directory:---------" + pathOfDir + "------------\n");
        tfParam1.setEditable(true);
        tfParam2.setEditable(true);

        if (isDirContains())           //czy w folderze jest plik .jar
        {
            try
            {
                showMethods();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        bRun.setOnAction(event ->
        {
            if(selectedMethod != null)
            {
                Class<?> c = classes.get(selectedMethod.substring(1,selectedMethod.length() - 1));
                String className = c.getName();
                ICallable callable = null;

                if (!ICallable.class.isAssignableFrom(c))
                {
                    try {
                        throw new Exception("Class " + className + " does not implement the contract.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try
                {
                    callable = (ICallable) c.newInstance();
                }
                catch (InstantiationException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }

                selectedMethod.substring(1, selectedMethod.length() - 1);

                switch (selectedMethod.substring(1, selectedMethod.length() - 1))
                {
                    case "factorial":
                        int i = 0;
                        try
                        {
                            i = Integer.parseInt(tfParam1.getText());
                        }
                        catch (NumberFormatException e)
                        {
                            taData.appendText("Method has one parameter type: 'int'\n");
                        }
                        if (i < 17 && i >= 0)
                            tfResult.setText(String.valueOf(callable.factorial(i)));
                        else
                            taData.appendText("Sorry, method type is 'int' it causes counting on param less than 17, but bigger or equal 0\n");
                        break;

                    case "concat":
                        if(tfParam1 != null && tfParam2 != null)
                            tfResult.setText(callable.concat(tfParam1.getText(), tfParam2.getText()));
                        else
                            taData.appendText("Method has two parameters type: 'String'\n");
                        break;

                    case "printMyGroup":
                        tfResult.setText(callable.printMyGroup());
                        break;

                    case "compareString":
                        boolean answer;
                        if(tfParam1 != null && tfParam2 != null)
                        {
                            answer = callable.compareString(tfParam1.getText(), tfParam2.getText());
                            tfResult.setText(String.valueOf(answer));
                        }
                        else
                            taData.appendText("Method has two parameters type: 'String'\n");
                        break;
                }

//                    if (null == callable)
//                        throw new Exception();

            }

        });

        lvMethods.setOnMouseClicked(event ->
        {
            selectedMethod = lvMethods.getSelectionModel().getSelectedItems().toString();

            System.out.println(selectedMethod.substring(1, selectedMethod.length() - 1));
            switch (selectedMethod.substring(1, selectedMethod.length() - 1))
            {
                case "factorial":
                    tfResult.setText("");
                    tfParam1.setEditable(true);
                    tfParam2.setEditable(false);
                    tfParam1.setPromptText("Parameter1");
                    tfParam2.setPromptText("");
                    tfParam1.setText("");
                    tfParam2.setText("");
                    break;
                case "concat":
                    tfResult.setText("");
                    tfParam1.setEditable(true);
                    tfParam2.setEditable(true);
                    tfParam1.setPromptText("Parameter1");
                    tfParam2.setPromptText("Parameter2");
                    break;
                case "printMyGroup":
                    tfResult.setText("");
                    tfParam1.setEditable(false);
                    tfParam2.setEditable(false);
                    tfParam1.setText("");
                    tfParam2.setText("");
                    tfParam1.setPromptText("");
                    tfParam2.setPromptText("");
                    break;
                case "compareString":
                    tfParam1.setEditable(true);
                    tfParam2.setEditable(true);
                    tfParam1.setPromptText("Parameter1");
                    tfParam2.setPromptText("Parameter2");
                    break;
            }

        });


        node.setMinHeight(500.0);
        node.setMinWidth(500.0);
        Scene scene = new Scene(node);
        window.setScene(scene);
        window.setTitle("Refleksja - loader");
        window.show();
    }

    private boolean isDirContains()
    {
        boolean jarclassExist = false;
        directory = new File(pathOfDir);
        listOfPaths = new LinkedList<>();

        if (directory.list().length > 0)
        {
            int i = 0;
            for(String s : directory.list())
            {
                if(s.endsWith(".jar"))
                {
                    listOfPaths.add(pathOfDir + "\\" + s);
                    i++;
                }
            }

            if(i == 0)
            {
                taData.appendText("W tym folderze brak pliku z rozszerzeniem 'jar'\n");
                jarclassExist = false;
            }
            else
                jarclassExist = true;
        }

        return jarclassExist;
    }

    private void showMethods() throws IOException
    {
        classes = new HashMap<>();

        for (String path : listOfPaths)
        {
            if (path.contains(".jar"))
            {
                JarFile jarFile = new JarFile(path);
                Enumeration<JarEntry> entries = jarFile.entries();

                URL[] urls = { new URL("jar:file:" + path + "!/") };
                URLClassLoader cl = URLClassLoader.newInstance(urls);


                while (entries.hasMoreElements())
                {
                    JarEntry je = entries.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class"))
                    {
                        continue;
                    }

                    String className = je.getName().substring(0, je.getName().length()-6);
                    className = className.replace('/', '.');
                    try
                    {
                        Class<?>c = cl.loadClass(className);

                        Method[] methods = c.getDeclaredMethods();

                        Class<?>[] pType;

                        if (!c.isAnnotationPresent(Description.class))
                            continue;

                        Description description = (Description) c.getAnnotation(Description.class);

                        for (int i = 0; i < methods.length; i++)
                        {
//                            zakladam, że metoda ktora chce uruchomic musi byc wspomniana w "description" classy
                            if(!description.description().contains(methods[i].getName()))
                                continue;

                            classes.put(methods[i].getName(), c);
                            lvMethods.getItems().add(methods[i].getName());

                            taData.appendText("Method '" + methods[i].getName() + "' requires " + methods[i].getParameterCount() + "-parametres type: ");

                            pType = methods[i].getParameterTypes();

                            if(pType.length == 0)
                                taData.appendText(" --- ");

                            for (int j = 0; j < pType.length; j++)
                            {
                                taData.appendText(" - " + pType[j] + " - ");
                            }


                            taData.appendText("\n");
//                            System.out.println("Method '" + description.description() + "' requires " + methods[i].getParameterCount() + " type:" + methods[i].getParameterTypes());
//                            System.out.println(c + " : method:" + methods[i]);
                        }

                    }
                    catch (ClassNotFoundException exp)
                    {
                        continue;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                }
            }

        }
    }

}
