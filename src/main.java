import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setResizable(false);

        Group grFinale = loginScene(primaryStage);
        primaryStage.setScene(new Scene(loginScene(primaryStage)));
        primaryStage.show();
    }

    public static Group loginScene(Stage st){

        Label notWorkPass = new Label("Something is wrong here");
        notWorkPass.setTranslateX(325);
        notWorkPass.setTranslateY(340);
        notWorkPass.setTextFill(Color.WHITE);
        Button login = new Button("Login");
        login.setTranslateX(325);
        login.setTranslateY(300);

        Button signUp = new Button("Sign up");
        signUp.setTranslateX(418);
        signUp.setTranslateY(300);

        Label userNameLabel = new Label("Username");
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter username here");
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password here");

        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        errorLabel.setTranslateY(325);
        errorLabel.setTranslateX(400);


        VBox allLog = new VBox(userNameLabel,userNameField,passwordLabel,passwordField);
        login.setOnAction(((event)->{
            final String username;
            final String pwd;
            username = userNameField.textProperty().getValue();
            pwd=passwordField.textProperty().getValue();
        }));

        allLog.setTranslateX(325);
        allLog.setTranslateY(200);
        allLog.setSpacing(3);
        Group rep= new Group(login,signUp,allLog,notWorkPass,errorLabel);

        signUp.setOnAction((event -> st.setScene(new Scene(signUpScreen(st)))));

        login.setOnAction((event -> {
            Boolean canLogin = false;
            List<String> allLines = new ArrayList<String>();
            Boolean noUsers=false;
            Boolean noFile = false;
            if(!new File("userInfo.csv").exists())
                noFile=true;
            else {
                try {
                    allLines = Files.readAllLines(Paths.get("userInfo.csv"));
                    if (allLines.size() == 0) {
                        noUsers = true;
                    } else
                        for (int i = 0; i < allLines.size(); i++) {
                            ArrayList<String> allUserNames = new ArrayList<String>();
                            String thisLine[] = allLines.get(i).split(",");
                            if (thisLine[2].equalsIgnoreCase(userNameField.textProperty().getValue()) && thisLine[3].equals(hashIt(passwordField.textProperty().getValue())))
                                canLogin = true;
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(canLogin){
                st.setScene(new Scene(connectedScene(st)));
            }else if(noUsers){
                errorLabel.setText("No users found");
            }else if(noFile){
                errorLabel.setText("No file found");
            }else
                errorLabel.setText("Error login in");
        }));
        return rep;
    }
    public static Group connectedScene(Stage st){
        ProgressIndicator bar = new ProgressIndicator();
        bar.setTranslateX(360);
        bar.setTranslateY(260);
        Label lb = new Label();
        lb.setText("Chargement en cours.");
        lb.setTranslateX(330);
        lb.setTranslateY(320);
        Group rep = new Group(bar,lb);
        return rep;
    }
    public static Group signUpScreen(Stage st){
        Label firstNameLabel = new Label("First Name");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter first name here");
        Label lastNameLabel = new Label("Last Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter last name here");
        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username here");
        Label passwordLabel = new Label("Password");
        PasswordField passwordField =new PasswordField();
        passwordField.setPromptText("Enter password here");
        Label confirmPasswordLabel = new Label("Password");
        PasswordField confirmPasswordField =new PasswordField();
        confirmPasswordField.setPromptText("Confirm password here");
        Label genreLabel = new Label("Genre");
        RadioButton male=new RadioButton("Male");
        RadioButton female=new RadioButton("female");
        RadioButton other=new RadioButton("Other");
        ToggleGroup  tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        other.setToggleGroup(tg);
        HBox genreChoices= new HBox(male,female,other);
        genreChoices.setSpacing(3);
        Label age = new Label("Age");
        Spinner ageSpinner = new Spinner(5,100,18);
        CheckBox condUtilisation = new CheckBox("I would like for you to sell my data");
        Button signUp = new Button("Sign Up");
        Button erase = new Button("Erase");
        Button back = new Button("Back");
        HBox allButtons = new HBox(signUp,erase,back);
        allButtons.setSpacing(5);

        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);

        VBox allStuff = new VBox(firstNameLabel,firstNameField,lastNameLabel,lastNameField,usernameLabel,usernameField,passwordLabel,passwordField,confirmPasswordLabel,confirmPasswordField,genreLabel,genreChoices,age,ageSpinner,condUtilisation,allButtons,errorLabel);
        allStuff.setSpacing(5);
        allStuff.setTranslateY(50);
        allStuff.setTranslateX(325);
        Group rep = new Group(allStuff);

        back.setOnAction((event)->st.setScene(new Scene(loginScene(st))));

        erase.setOnAction((event -> st.setScene(new Scene(signUpScreen(st)))));

        signUp.setOnAction((event ->{
            Boolean firstName = (!firstNameField.textProperty().getValue().equals(""));
            Boolean lastName = (!lastNameField.textProperty().getValue().equals(""));
            Boolean userName = (!usernameField.textProperty().getValue().equals(""));
            Boolean passwd = (passwordField.textProperty().getValue().equals(confirmPasswordField.textProperty().getValue())||!passwordField.textProperty().getValue().equals(""));
            Boolean checkTogg =tg.getSelectedToggle()!=null;
            Boolean condTogg = condUtilisation.isSelected();
            Boolean userNameTaken = false;
            String labelErrorText="";
            //CHECK USERNAME
            List<String> allLines;
            File csvFile = new File("userInfo.csv");
            if (csvFile.isFile()) {
                try {
                    allLines = Files.readAllLines(Paths.get("userInfo.csv"));
                    for (int i = 0; i < allLines.size(); i++) {
                        ArrayList<String> allUserNames = new ArrayList<String>();
                        String thisLine[] = allLines.get(i).split(",");
                        if(thisLine[2].equalsIgnoreCase(usernameField.textProperty().getValue()))
                            userNameTaken=true;
                    }
                }catch (IOException e){
                    e.printStackTrace();}

            }else
                userNameTaken=false;
            //Write or no write
            if(userNameTaken)
                errorLabel.setText("Username already taken");
            else if(firstName&&lastName&&userName&&passwd&&checkTogg&&condTogg) {
                try {

                    if(!userNameTaken) {
                        //WRITER
                        String genre = "No selected";
                        if (tg.getSelectedToggle().equals(male)) {
                            genre = "male";
                        } else if (tg.getSelectedToggle().equals(female))
                            genre = "female";
                        else if (tg.getSelectedToggle().equals(other))
                            genre = "other";

                        FileWriter csvWriter = new FileWriter("userInfo.csv", true);
                        csvWriter.append(firstNameField.textProperty().getValue()
                                    + "," + lastNameField.textProperty().getValue()
                                    + "," + usernameField.textProperty().getValue()
                                    + "," + hashIt(passwordField.textProperty().getValue())
                                    + "," + genre
                                    + "," + ageSpinner.getValue()
                                    + "\n");
                        csvWriter.flush();
                        csvWriter.close();
                        st.setScene(new Scene(loginScene(st)));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(!firstName)
                errorLabel.setText("First name error");
            else if(!lastName)
                errorLabel.setText("Last name error");
            else if(!userName)
                errorLabel.setText("Username error");
            else if(!passwd)
                errorLabel.setText("Password error / confirmation error");
            else if(!checkTogg)
                errorLabel.setText("No gender selected");
            else if(!condTogg)
                errorLabel.setText("You like your money?");


        }));
        return rep;
    }
    public static String hashIt(String x){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = digest.digest(x.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
