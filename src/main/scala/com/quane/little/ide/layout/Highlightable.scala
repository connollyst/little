package com.quane.little.ide.layout

import javafx.scene.Node

/**
 *
 * @author Sean Connolly
 */
trait Highlightable extends Node {

  // TODO highlight

  def highlight

  def unhighlight

}
