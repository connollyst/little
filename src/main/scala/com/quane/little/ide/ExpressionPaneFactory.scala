package com.quane.little.ide

import com.quane.little.ide.layout.language.ExpressionPane
import com.quane.little.language.Expression

/**
 *
 * @author Sean Connolly
 */
abstract class ExpressionPaneFactory {

  def make: ExpressionPane[_ <: Expression[Any]]

}
