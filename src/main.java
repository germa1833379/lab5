import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaxHeight(600);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaximized(true);

        Group grFinale = loginScene();
        primaryStage.setScene(new Scene(signUpScreen()));
        primaryStage.show();
    }

    public static Group loginScene(){

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
        Group rep= new Group(login,signUp,allLog,notWorkPass);
        return rep;
    }
    public static Group connectedScene(){

        Group rep = new Group();
        return rep;
    }
    public static Group signUpScreen(){
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

        VBox allStuff = new VBox(firstNameLabel,firstNameField,lastNameLabel,lastNameField,usernameLabel,usernameField,passwordLabel,passwordField,confirmPasswordLabel,confirmPasswordField,genreLabel,genreChoices,age,ageSpinner,condUtilisation,allButtons);
        allStuff.setSpacing(5);
        allStuff.setTranslateY(50);
        allStuff.setTranslateX(325);
        Group rep = new Group(allStuff);
        return rep;
    }
}
