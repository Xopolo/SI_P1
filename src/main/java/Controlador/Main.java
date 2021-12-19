package Controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Clase principal Main que ejecuta el programaº
 */
public class Main extends Application {

    /**
     * Cargador de ficheros fxml, es decir la interfaz generada por sceneBuilder
     */
    FXMLLoader fxmlLoader;
    /**
     * Clase controlador del modelo MVC
     */
    InicioController controller;

    /**
     * La entrada principal del programa
     *
     * @param args Los parámetros de entrada
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Metodo que lanza la interfaz de usuario del programa
     * @param stage La escena anterior
     * @throws Exception En caso de que no encuentre algún fichero
     */
    @Override
    public void start(Stage stage) throws Exception {

//      Cargo el loader
        try {
            fxmlLoader = new FXMLLoader(new File("Resources/Controller.fxml").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Pane bp = fxmlLoader.load();
        Scene escena = new Scene(bp);
        controller = fxmlLoader.getController();


        stage.setResizable(false);
        stage.setOnCloseRequest(e -> Platform.exit());
        stage.getIcons().add(new Image(new File("Resources/iconoSI.jpg").toURI().toURL().toString()));
        stage.setTitle("Practica 1 Sistemas Inteligentes Pablo Hernández");
        stage.setScene(escena);
        stage.show();
    }

    /**
     * Método redefinido de stop
     *
     *
     * @throws Exception En caso de que falle
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
