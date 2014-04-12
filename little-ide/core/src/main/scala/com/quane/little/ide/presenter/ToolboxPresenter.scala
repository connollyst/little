package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.language.FunctionReference
import com.quane.little.language.data.Value
import com.quane.little.language.math.{Multiplication, Division, Subtraction, Addition}

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

  view.createToolboxItem("Math", "+", new Addition(Value(1), Value(1)))
  view.createToolboxItem("Math", "-", new Subtraction(Value(1), Value(1)))
  view.createToolboxItem("Math", "*", new Multiplication(Value(1), Value(1)))
  view.createToolboxItem("Math", "/", new Division(Value(1), Value(1)))

  view.createToolboxItem("My Functions", "blank", new FunctionReference("blank"))
  view.createToolboxItem("My Functions", "move", new FunctionReference("move"))
  view.createToolboxItem("My Functions", "stop", new FunctionReference("stop"))
  view.createToolboxItem("My Functions", "turn", new FunctionReference("turn"))

}
