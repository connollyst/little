package com.quane.little.ide.language

import scala.swing.Panel

/** An expression panel is a Scala Swing panel that represents a piece of the
  * little programming language. Specifically, it is the 'view' component of
  * the MVC framework whereby the little code itself is the 'model' and the
  * {@code ExpressionController} is, as the name suggests, the
  * 'controller'. As such, an expression panel is responsible for displaying
  * an appropriate interface for the little code it fronts; both in terms of
  * readability and manipulation. All changes to the little code are handled
  * by the controller.
  *
  * @author Sean Connolly
  */
trait ExpressionPanel
    extends Panel