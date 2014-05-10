package com.quane.little.ide.presenter

import com.quane.little.ide.view.{IDEViewPresenter, IDEView}
import com.quane.little.data.service.{UserService, FunctionService}

/** Presenter for the base view of the IDE.
  *
  * @author Sean Connolly
  */
class IDEPresenter[V <: IDEView](view: V)
  extends IDEViewPresenter {

  UserService().init()
  FunctionService().init()

  // TODO this is temporary
  val user = UserService().upsert("connollyst")

  view.registerViewPresenter(this)

  val toolbox = view.createToolbox()
  val workspace = view.createWorkspace()
  val gamespace = view.createGamespace()

  toolbox.workspace = workspace
  gamespace.workspace = workspace

}
