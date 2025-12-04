module SnackMan {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens de.halva6.snackman.controller to javafx.graphics, javafx.fxml;
}
