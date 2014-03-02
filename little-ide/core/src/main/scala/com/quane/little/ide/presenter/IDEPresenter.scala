package com.quane.little.ide.presenter

import com.quane.little.ide.view.{IDEViewPresenter, IDEView}

/** Presenter for the base view of the IDE.
  *
  * @author Sean Connolly
  */
class IDEPresenter[V <: IDEView](view: V)
  extends IDEViewPresenter {

  view.addViewListener(this)

  val toolbox = view.createToolbox()
  val workspace = view.createWorkspace()

}
