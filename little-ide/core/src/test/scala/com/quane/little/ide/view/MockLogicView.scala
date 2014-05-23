package com.quane.little.ide.view

import com.quane.little.language.LogicOperation.LogicOperation

object MockLogicView {
  def apply(): MockLogicView = new MockLogicView
}

class MockLogicView
  extends LogicView
  with MockViewOfLeftAndRightExpressions {

  def setOperation(operation: LogicOperation) = Unit

}
