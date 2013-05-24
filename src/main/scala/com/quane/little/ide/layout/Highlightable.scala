package com.quane.little.ide.layout

import javafx.scene.Node

object Highlightable {

  private val highlighted = "highlight"

  private val unhighlighted = "no-highlight"

}

/**
 * A [[javafx.scene.Node]] which can be highlighted.
 * A highlighted node has a bright border.
 *
 * @author Sean Connolly
 */
trait Highlightable extends Node {

  def highlight() {
    getStyleClass.remove(Highlightable.unhighlighted)
    getStyleClass.add(Highlightable.highlighted)
  }

  def unhighlight() {
    getStyleClass.remove(Highlightable.highlighted)
    getStyleClass.add(Highlightable.unhighlighted)
  }

}
