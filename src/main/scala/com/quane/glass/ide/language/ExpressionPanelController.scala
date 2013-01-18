package com.quane.glass.ide.language

import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Scope

/** The Glass panel controller is, well, the controller for the
  * {@code GlassPanel}. If the 'view' is responsible for rendering the UI and
  * detecting user interactions, and the 'model' is Glass code itself, then the
  * 'controller' is responsible for translating the 'view' into the 'model'.
  *
  * @author Sean Connolly
  */
abstract class ExpressionPanelController[+E <: Expression[Any]](val view: ExpressionPanel) {

    def validate: Unit

    def compile(scope: Scope): E

}