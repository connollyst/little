package com.quane.little.ide

import com.quane.little.ide.layout.language.{SetterPane, ListenerPane, ExpressionPane}
import com.quane.little.language.Expression
import com.quane.little.language.event.LittleEvent
import com.quane.little.language.data.{ValueTypeSafe, Bool, Number, Text}

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

class SetterPaneFactory[V <: ValueTypeSafe]
  extends ExpressionPaneFactory {

  override def make: SetterPane[V] = {
    new SetterPane[V]
  }

}