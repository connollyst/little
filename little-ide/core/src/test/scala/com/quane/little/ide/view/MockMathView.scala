package com.quane.little.ide.view

import com.quane.little.language.math.BasicMathOperation.BasicMathOperation

object MockMathView {
  def apply(): MockMathView = new MockMathView
}

class MockMathView
  extends MathView
  with MockViewOfLeftAndRightExpressions {

  def setOperation(operation: BasicMathOperation) = Unit

}
