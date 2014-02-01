package com.quane.little.web.view

import vaadin.scala.VerticalLayout

object ExpressionList {
  val Style = "l-step-list"
}
class ExpressionList extends VerticalLayout {
  styleName = ExpressionList.Style
  spacing = false

}