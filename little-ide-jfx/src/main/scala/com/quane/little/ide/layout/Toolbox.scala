package com.quane.little.ide.layout

import javafx.scene.layout.VBox
import com.quane.little.ide.{Tool, Tools, CustomControl}
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
    makeGroup("Setters", Tools.setters)
  }

  def gettersToolboxGroup: ToolboxGroup = {
    makeGroup("Getters", Tools.getters)
  }

  def mathToolboxGroup: ToolboxGroup = {
    makeGroup("Math", Tools.math)
  }

  def makeGroup(title: String, tools: Vector[Tool]) = {
    val group = new ToolboxGroup
    group.setTitle(title)
    for (tool <- tools) {
      val item = new ToolboxItem(tool)
      group.getChildren.add(item)
    }
    group
  }

}
