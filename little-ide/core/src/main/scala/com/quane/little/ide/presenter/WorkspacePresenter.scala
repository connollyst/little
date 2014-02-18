package com.quane.little.ide.presenter

import com.quane.little.ide.view.{WorkspaceViewListener, WorkspaceView}
import com.quane.little.language.{Functions, FunctionDefinition}


class WorkspacePresenter[V <: WorkspaceView](val view: V)
  extends WorkspaceViewListener {

  view.addViewListener(this)

  override def openFunctionDefinition(name: String) = {
    // TODO find function definition and create view & presenter
    val fun: FunctionDefinition =
      name match {
        case "move" => Functions.move
        case "stop" => Functions.stop
        case "turn" => Functions.turn
        case "voyage" => Functions.voyage
        case _ => throw new IllegalArgumentException("Unknown function: '" + name + "'")
      }
    val presenter = view.createFunctionDefinition()
    println("Opening '" + fun.name + "': " + fun.steps)
    presenter.name = fun.name
    presenter.setSteps(fun.steps)
    //    presenter.params_=(fun.params)

  }

}
