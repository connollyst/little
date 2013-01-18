package com.quane.glass.ide.language

import scala.swing.Panel

/** A Glass panel is a Scala Swing panel that represents a piece of the Glass
  * programming language. Specifically, it is the 'view' component of the MVC
  * framework whereby the Glass code itself is the 'model' and the
  * {@code GlassPanelController} is, as the name suggests, the 'controller'. As
  * such, a Glass panel is responsible for displaying an appropriate interface
  * for the Glass code it fronts; both in terms of readability and
  * manipulation. All changes to the Glass code are handled by the controller.
  *
  * @author Sean Connolly
  */
trait ExpressionPanel
        extends Panel