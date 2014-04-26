package com.quane.little.ide.presenter

import com.quane.little.ide.model.{FunctionService, UserService}
import com.quane.little.ide.view.{IDEViewPresenter, IDEView}

/** Presenter for the base view of the IDE.
  *
  * @author Sean Connolly
  */
class IDEPresenter[V <: IDEView](view: V)
  extends IDEViewPresenter {

  view.registerViewPresenter(this)

  val toolbox = view.createToolbox()
  val workspace = view.createWorkspace()

  val username = "connollyst"
  val user = UserService.upsert(username)
  println("Logged in as " + user.username + ": " + user.id)

  FunctionService.findDefinitionsByUser(username)
}
