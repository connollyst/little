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

  val user = UserService().upsert("connollyst")
  println("Logged in as " + user.username + ": " + user.id)

  view.registerViewPresenter(this)
  view.createToolbox()
  view.createWorkspace()

}
