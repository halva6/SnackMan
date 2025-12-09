module SnackMan
{
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;

	opens de.halva6.snackman.controller to javafx.graphics, javafx.fxml;
	opens de.halva6.snackman.view to javafx.fxml;
}
