package com.quane.little.ide

import com.quane.little.ide.layout.language.{SetterPane, ListenerPane, ExpressionPane}
import com.quane.little.language.Expression
import com.quane.little.language.event.LittleEvent

/**
 *
 * @author Sean Connolly
 */
abstract class ExpressionPaneFactory
  extends Serializable {

  def make: ExpressionPane[_ <: Expression[Any]]

}

class ListenerPaneFactory(event: LittleEvent)
  extends ExpressionPaneFactory {

  override def make: ListenerPane = {
    new ListenerPane(event)
  }

}

class SetterPaneFactory
  extends ExpressionPaneFactory {

  override def make: SetterPane = {
    new SetterPane
  }

}