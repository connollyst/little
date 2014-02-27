package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}
import com.quane.little.language.{Functions, FunctionDefinition}

/** Presenter for the workspace in which the user can build code.
  *
  * @author Sean Connolly
  */
class WorkspacePresenter[V <: WorkspaceView](view: V)
  extends WorkspaceViewListener {

  view.addViewListener(this)

  override def openFunctionDefinition(name: String) = {
    val fun: FunctionDefinition =
      name match {
        case "blank" => Functions.blank
        case "move" => Functions.move
        case "stop" => Functions.stop
        case "turn" => Functions.turn
        case "voyage" => Functions.voyage
        case "print dir" => Functions.printDirection
        case _ => throw new IllegalArgumentException("Unknown function: '" + name + "'")
      }
    val presenter = view.createFunctionDefinition()
    presenter.name = fun.name
    presenter.setSteps(fun.steps)
    presenter.setParameters(fun.params)
  }

}
