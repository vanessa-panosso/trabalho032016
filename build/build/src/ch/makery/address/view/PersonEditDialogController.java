package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.dialog.Dialogs;

import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**
 * Dialog para editar detalhes de uma pessoa.
 * 
 * @author Marco Jakob
 */
public class PersonEditDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Inicializa a classe controlle. Este m�todo � chamado automaticamente
     * ap�s o arquivo fxml ter sido carregado.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Define o palco deste dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Define a pessoa a ser editada no dialog.
     * 
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Retorna true se o usu�rio clicar OK,caso contr�rio false.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Chamado quando o usu�rio clica OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Chamado quando o usu�rio clica Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Valida a entrada do usu�rio nos campos de texto.
     * 
     * @return true se a entrada � v�lida
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nome inv�lido!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Sobrenome inv�lido!\n"; 
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Rua inv�lida!\n"; 
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "C�digo Postal inv�lido!\n"; 
        } else {
            // tenta converter o c�digo postal em um int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "C�digo Postal inv�lido (deve ser um inteiro)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Cidade inv�lida!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Anivers�rio inv�lido!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "Anivers�rio inv�lido. Use o formato dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostra a mensagem de erro.
            Dialogs.create()
                .title("Campos Inv�lidos")
                .masthead("Por favor, corrija os campos inv�lidos")
                .message(errorMessage)
                .showError();
            return false;
        }
    }
}