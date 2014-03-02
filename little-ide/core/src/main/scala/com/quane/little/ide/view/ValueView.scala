package com.quane.little.ide.view

/** A view for an component representing a [[com.quane.little.language.data.Value]].
  *
  * @author Sean Connolly
  */
trait ValueView extends ExpressionView[ValueViewPresenter] {

  def setValue(value: String): Unit

}

/** A listener for [[com.quane.little.ide.view.ValueView]].
  *
  * @author Sean Connolly
  */
trait ValueViewPresenter extends ExpressionViewPresenter {

  def onValueChange(value: String): Unit

}
