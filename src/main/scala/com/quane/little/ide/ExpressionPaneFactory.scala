package com.quane.little.ide

import com.quane.little.ide.layout.language.ExpressionPane

/**
 *
 * @author Sean Connolly
 */
abstract class ExpressionPaneFactory {

  def make: ExpressionPane

}
