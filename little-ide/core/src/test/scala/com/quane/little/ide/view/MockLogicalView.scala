package com.quane.little.ide.view

import com.quane.little.language.EvaluationOperator.EvaluationOperator

class MockLogicalView
  extends LogicalView
  with MockViewOfLeftAndRightExpressions {

  def setOperation(operation: EvaluationOperator) = Unit

}
