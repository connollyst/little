package com.quane.little.ide

import com.quane.little.ide.layout.language.{GetterPane, SetterPane, ListenerPane, ExpressionPane}
import com.quane.little.language.Expression
import com.quane.little.language.event.LittleEvent
import com.quane.little.language.data.Value

/**
 *
 * @author Sean Connolly
 */
sealed trait ExpressionPaneFactory
  extends Serializable {

  def make: ExpressionPane[_ <: Expression[Any]]

}

class ListenerPaneFactory(event: LittleEvent)
  extends ExpressionPaneFactory {

  override def make: ListenerPane = new ListenerPane(event)

}

class SetterPaneFactory
  extends ExpressionPaneFactory {

  override def make: SetterPane = new SetterPane

}

class GetterPaneFactory
  extends ExpressionPaneFactory {

  override def make: ExpressionPane[_ <: Expression[Any]] = new GetterPane

}