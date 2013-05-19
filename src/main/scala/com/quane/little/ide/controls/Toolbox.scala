package com.quane.little.ide.controls

import javafx.scene.layout.VBox

/**
 *
 * @author Sean Connolly
 */
class Toolbox extends VBox with CustomControl {

  abstract def fxml: String = "Toolbox.fxml"

}
