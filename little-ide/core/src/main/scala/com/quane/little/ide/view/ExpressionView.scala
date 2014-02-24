package com.quane.little.ide.view

/** A view for a component representing an [[com.quane.little.language.Expression]].
  *
  * @author Sean Connolly
  */
trait ExpressionView[L <: ExpressionViewListener] extends View[L]

/** A listener for [[com.quane.little.ide.view.ExpressionView]].
  *
  * @author Sean Connolly
  */
trait ExpressionViewListener extends ViewListener
