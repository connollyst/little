package com.quane.little.ide.presenter

import com.quane.little.ide.view.{IDEViewPresenter, IDEView}
import com.quane.little.data.service.{UserService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** Presenter for the base view of the IDE.
  *
  * @author Sean Connolly
  */
class IDEPresenter[V <: IDEView](view: V)(implicit val bindingModule: BindingModule)
  extends IDEViewPresenter
  with Injectable {

  private val userService = inject[UserService]
  private val functionService = inject[FunctionService]

  // TODO this is temporary
  userService.init()
  functionService.init()
  val user = userService.upsert("connollyst")
  // TODO that is temporary

  view.registerViewPresenter(this)

  val toolbox = new ToolboxPresenter(view.createToolbox())
  val gamespace = new GamespacePresenter(view.createGamespace())
  val workspace = new WorkspacePresenter(view.createWorkspace())

  toolbox.workspace = workspace
  gamespace.workspace = workspace

}
