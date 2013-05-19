package com.quane.little.ide.controls

import javafx.fxml.FXMLLoader

/**
 *
 * @author Sean Connolly
 */
trait CustomControl {

  abstract def fxml: String

  private val resource = getClass.getResource(fxml)
  private val loader = new FXMLLoader(resource)
  loader.setRoot(this)
  loader.setController(this)
  loader.load()

}
