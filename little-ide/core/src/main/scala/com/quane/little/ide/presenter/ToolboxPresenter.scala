package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewListener}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewListener {

  view.addViewListener(this)

}
