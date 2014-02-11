package com.quane.little.ide.presenter

import com.quane.little.ide.view.{IDEViewListener, IDEView}

/** Presenter for the base view of the IDE.
  *
  * @author Sean Connolly
  */
class IDEPresenter[V <: IDEView](val view: V)
  extends IDEViewListener {

  view.addViewListener(this)

  val workspace: WorkspacePresenter[_] = view.createWorkspace()

}
