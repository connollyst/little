package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewListener}


class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewListener {

  view.addViewListener(this)

}
