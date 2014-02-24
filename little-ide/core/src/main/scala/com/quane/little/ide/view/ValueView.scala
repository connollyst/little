package com.quane.little.ide.view

/** A view for an component representing a [[com.quane.little.language.data.Value]].
  *
  * @author Sean Connolly
  */
trait ValueView extends ExpressionView[ValueViewListener] {

  def setValue(value: String): Unit

}

/** A listener for [[com.quane.little.ide.view.ValueView]].
  *
  * @author Sean Connolly
  */
trait ValueViewListener extends ExpressionViewListener {

  def onValueChange(value: String): Unit

}
