package Controlador;

import Modelo.Comunes.Ejecutar;
import Modelo.Comunes.Enums.*;
import Modelo.Comunes.Parametros;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.util.Optional;

/**
 * Clase InicioController
 *  Controlador que maneja ventana principal de la aplicación gráfica
 */
public class InicioController {

    @FXML
    private ChoiceBox<Cruce> choiceBox_tipo_cruce;

    @FXML
    private ChoiceBox<Mutacion> choiceBox_tipo_mutacion;

    @FXML
    private ChoiceBox<Poblaciones> choiceBox_tipo_poblacion;

    @FXML
    private ChoiceBox<Est_reemplazamiento> choiceBox_tipo_reemplazamiento;

    @FXML
    private ChoiceBox<SeleccionIndividuos> choiceBox_tipo_seleccion;

    @FXML
    private CheckBox ckbox_csv;

    @FXML
    private LineChart<String, Number> chrt_grafico_pob;

    @FXML
    private TextArea text_area;

    @FXML
    private TextField lab_num_elitistas;

    @FXML
    private TextField lab_num_generaciones;

    @FXML
    private TextField lab_prob_cruce;

    @FXML
    private TextField lab_prob_mutacion;

    @FXML
    private TextField lab_tam_poblacion;

    /**
     * Método que ejecuta el algoritmo genético si se le pasan los parámetros correctos
     *
     * @param event Cuando se pulsa el botón generar
     */
    @FXML
    void generarPoblacion(ActionEvent event) {
        text_area.setText("");
        chrt_grafico_pob.getData().clear();

        boolean cancelado = false;
        Parametros parametros = new Parametros();

        parametros.setTIPO_POBLACION(choiceBox_tipo_poblacion.getValue());
        parametros.setTIPO_SELECCION_INDIVIUOS(choiceBox_tipo_seleccion.getValue());
        parametros.setTIPO_OPERADOR_MUTACION(choiceBox_tipo_mutacion.getValue());
        parametros.setTIPO_OPERADOR_CRUCE(choiceBox_tipo_cruce.getValue());
        parametros.setESTRATEGIA_REEMPLAZAMIENTO(choiceBox_tipo_reemplazamiento.getValue());

        parametros.setEXPORTAR_A_CSV(ckbox_csv.isSelected());

        try {
            parametros.setNUM_ELITISTAS(Integer.parseInt(lab_num_elitistas.getText()));
            parametros.setTAM_POBLACION(Integer.parseInt(lab_tam_poblacion.getText()) + parametros.getNUM_ELITISTAS());
            parametros.setNUM_GENERACIONES(Integer.parseInt(lab_num_generaciones.getText()));
            parametros.setPROB_CRUCE(Double.parseDouble(lab_prob_cruce.getText()));
            parametros.setPROB_MUTACION(Double.parseDouble(lab_prob_mutacion.getText()));

        } catch (NumberFormatException exception) {
            cancelado = true;
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setOnCloseRequest(dialogEvent -> alerta.close());

            alerta.setTitle("Parámetros Incorrectos");
            alerta.setHeaderText("Parámetros por defecto");
            alerta.setContentText("Se usarán los parámetros por defecto");
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent()) {
                if (resultado.get() == ButtonType.OK) {
                    cancelado = false;
                    if (comprobarMutacion()) {
                        Ejecutar ejecucion = new Ejecutar(parametros, text_area, chrt_grafico_pob);
                        ejecucion.ejecutar();
                    }
                }
            }
        }
        if (!cancelado) {
            if (comprobarMutacion()) {
                Ejecutar ejecucion = new Ejecutar(parametros, text_area, chrt_grafico_pob);
                ejecucion.ejecutar();
            }
        }
    }

    private boolean comprobarMutacion() {
        if (choiceBox_tipo_cruce.getValue().equals(Cruce.MULTIPUNTO) && !choiceBox_tipo_poblacion.getValue().equals(Poblaciones.RASTRIGIN)) {
            choiceBox_tipo_cruce.setStyle("-fx-border-color: red");
            return false;
        }
        return true;
    }

    /**
     * Método que inicializa los valores por defecto
     *
     */
    public void initialize() {
        choiceBox_tipo_cruce.getItems().addAll(Cruce.values());
        choiceBox_tipo_cruce.setValue(Cruce.UNIFORME);

        choiceBox_tipo_poblacion.getItems().addAll(Poblaciones.values());
        choiceBox_tipo_poblacion.setValue(Poblaciones.RASTRIGIN);

        choiceBox_tipo_mutacion.getItems().addAll(Mutacion.values());
        choiceBox_tipo_mutacion.setValue(Mutacion.UNIFORME);

        choiceBox_tipo_reemplazamiento.getItems().addAll(Est_reemplazamiento.values());
        choiceBox_tipo_reemplazamiento.setValue(Est_reemplazamiento.ESTACIONARIA);

        choiceBox_tipo_seleccion.getItems().addAll(SeleccionIndividuos.values());
        choiceBox_tipo_seleccion.setValue(SeleccionIndividuos.RULETA);
    }
}
