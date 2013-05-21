package com.quane.little.ide

import com.quane.little.ide.layout.language.{ListenerPane, ExpressionPane}
import com.quane.little.language.Expression
import com.quane.little.language.event.LittleEvent

/**
 *
 * @author Sean Connolly
 */
abstract class ExpressionPaneFactory {

  def make: ExpressionPane[_ <: Expression[Any]]

}

class ListenerPaneFactory(event: LittleEvent)
  extends ExpressionPaneFactory {

  def make: ExpressionPane[_ <: Expression[Any]] = {
    new ListenerPane(event)
  }

}