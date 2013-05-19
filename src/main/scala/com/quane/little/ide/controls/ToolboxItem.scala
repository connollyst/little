package com.quane.little.ide.controls

import javafx.scene.control.Label
import com.quane.little.ide.CustomControl
import com.quane.little.language.event.GlassEvent

/**
 *
 * @author Sean Connolly
 */
class ToolboxItem extends Label with CustomControl {


  def this(label: String) = {
    this
    setText(label)
  }

  def this(event: GlassEvent) = {
    this
    setText(event.getClass.getSimpleName)
  }

  def fxml: String = "ToolboxItem.fxml"

}
