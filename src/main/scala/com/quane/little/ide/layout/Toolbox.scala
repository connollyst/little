package com.quane.little.ide.layout

import javafx.scene.layout.VBox
import com.quane.little.ide.CustomControl
import com.quane.little.ide.controls.ToolboxItem
import com.quane.little.language.event.GlassEvent

/**
 *
 * @author Sean Connolly
 */
class Toolbox extends VBox with CustomControl {

  def fxml: String = "Toolbox.fxml"

  var isDirty = true


  override def layoutChildren() {
    super.layoutChildren()
    if (isDirty) {
      isDirty = false
      getChildren.clear()

      for (event <- GlassEvent.values) {
        getChildren.add(new ToolboxItem(event))
      }
    }
  }

}
