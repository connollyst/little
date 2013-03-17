package com.quane.little.ide.language.statement

import com.quane.little.ide.language.ExpressionPanelController
import com.quane.little.language.Statement

/** @author Sean Connolly
  */
abstract class StatementPanelController[S <: Statement[_]](override val view: StatementPanel)
    extends ExpressionPanelController[S](view)
