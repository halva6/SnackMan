module SnackMan {
	requires javafx.controls;
	
	opens de.halva6.snackman.controller to javafx.graphics, javafx.fxml;
}
