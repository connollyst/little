package com.quane.glass.ide

import scala.swing.event.Event
import com.quane.glass.ide.language.ExpressionPanelController
import com.quane.glass.core.language.Expression

class StepAddedEvent(val controller: ExpressionPanelController[Expression[Any]])
    extends Event