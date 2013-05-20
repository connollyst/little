package com.quane.little.ide.layout

import javafx.scene.layout.VBox
import com.quane.little.ide.{Tools, CustomControl}
import com.quane.little.language.event.GlassEvent
import com.quane.little.ide.controls.ToolboxItem

/**
 *
 * @author Sean Connolly
 */
class Toolbox extends VBox with CustomControl {

  def fxml: String = "Toolbox.fxml"

  getChildren.addAll(
    eventsToolboxGroup,
    settersToolboxGroup,
    gettersToolboxGroup,
    mathToolboxGroup
  )

  def eventsToolboxGroup: ToolboxGroup = {
    makeGroup("Events", Tools.events)
  }

  def settersToolboxGroup: ToolboxGroup = {
    makeGroup("Setters", Tools.events)
  }

  def gettersToolboxGroup: ToolboxGroup = {
    makeGroup("Getters", Tools.events)
  }

  def mathToolboxGroup: ToolboxGroup = {
    makeGroup("Math", Tools.events)
  }

  def makeGroup(title: String, tools: Vector[Any]) = {
    val group = new ToolboxGroup
    group.setTitle(title)
    for (tool <- tools) {
      val item = new ToolboxItem
      item.setName(tool.getClass.getSimpleName)
      group.getChildren.add(item)
    }
    group
  }

}
