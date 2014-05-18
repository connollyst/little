package com.quane.little.ide.view

import com.quane.little.language.LogicalOperation.LogicalOperation

class MockLogicalView
  extends LogicalView
  with MockViewOfLeftAndRightExpressions {

  def setOperation(operation: LogicalOperation) = Unit

}
