package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.ExpressionPresenter
import com.quane.vaadin.scala.DraggableComponent

class ToolboxItemComponent(name: String)
  extends DraggableComponent[ExpressionView[ExpressionPresenter]](new PrintComponent(name)) {

  // TODO should look like the real expression
  // TODO should carry the real expression as d&d payload

  def getStep: ExpressionView[ExpressionPresenter] = {
    component
  }

}