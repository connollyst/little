package com.quane.little.ide.language.statement

import com.quane.little.ide.language.ExpressionController
import com.quane.little.language.Statement

/** @author Sean Connolly
  */
abstract class StatementController[S <: Statement](override val view: StatementPanel)
    extends ExpressionController[S](view)
