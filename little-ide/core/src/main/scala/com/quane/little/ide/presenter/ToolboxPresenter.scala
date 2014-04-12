package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.language.FunctionReference

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter {

  view.registerViewPresenter(this)

  view.createToolboxTab("Sensing")
  view.createToolboxTab("Motion")
  view.createToolboxTab("Operators")
  view.createToolboxTab("Math")
  view.createToolboxTab("My Functions")
  view.createToolboxTab("My Variables")

  view.createToolboxItem("Motion", "move forward", new FunctionReference("move"))

}
