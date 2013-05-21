package com.quane.little.ide.layout.language

import javafx.scene.layout.Pane
import com.quane.little.language.{Scope, Expression}

/** An expression pane is a JavaFX pane that represents a piece of the
  * little programming language. Specifically, it is the 'view' component of
  * the MVC framework whereby the little code itself is the 'model' and the
  * {@link ExpressionController} is, as the name suggests, the
  * 'controller'. As such, an expression panel is responsible for displaying
  * an appropriate interface for the little code it fronts; both in terms of
  * readability and manipulation. All changes to the little code are handled
  * by the controller.
  *
  * @author Sean Connolly
  */
trait ExpressionPane[+E <: Expression[Any]] extends Pane {

  def compile(scope: Scope): E

}
