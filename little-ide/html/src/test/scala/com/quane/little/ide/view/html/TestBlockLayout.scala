package com.quane.little.ide.view.html

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/**
 *
 *
 * @author Sean Connolly
 */
@RunWith(classOf[JUnitRunner])
class TestBlockLayout extends WordSpec with ShouldMatchers with MockitoSugar {

  "BlockLayout componentIndex" should {
    "return 1" in {
      new BlockLayout().getComponentIndex(0) should be(1)
    }
    "return 3" in {
      new BlockLayout().getComponentIndex(1) should be(3)
    }
    "return 5" in {
      new BlockLayout().getComponentIndex(2) should be(5)
    }
    "return 7" in {
      new BlockLayout().getComponentIndex(3) should be(7)
    }
    "return 9" in {
      new BlockLayout().getComponentIndex(4) should be(9)
    }
  }

  "BlockLayout" should {

    "append first step at index 1" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      // when
      view.addLogicStep(0)
      // then
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
    }
    "append second step at index 4" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      // when
      view.addLogicStep(1)
      // then
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
    }
    "append third step at index 6" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      // when
      view.addLogicStep(2)
      // then
      view.getComponent(5).getClass should be(classOf[BlockStep])
      view.getComponent(5).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
    }
    "append fourth step at index 8" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      view.addCodeMenu(2)
      // when
      view.addLogicStep(3)
      // then
      view.getComponent(7).getClass should be(classOf[BlockStep])
      view.getComponent(7).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
    }
  }

  "BlockLayout" should {
    "add new second step at index 5" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      view.addCodeMenu(2)
      // when
      view.addLogicStep(2)
      // then
      view.getComponent(5).getClass should be(classOf[BlockStep])
      view.getComponent(5).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
    }
  }

  "BlockLayout" should {

    "add first code menu at index 0" in {
      // given
      val view = new BlockLayout
      // when
      view.addCodeMenu(-1)
      // then
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
    }
    "add second code menu at index 2" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
    }
    "add third code menu at index 4" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      // when
      view.addCodeMenu(1)
      // then
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
    }
    "add fourth code menu at index 6" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      // when
      view.addCodeMenu(2)
      // then
      view.getComponent(6).getClass should be(classOf[BlockStepSeparator])
    }
    "add fifth code menu at index 8" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      view.addCodeMenu(2)
      view.addMathStep(3)
      // when
      view.addCodeMenu(3)
      // then
      view.getComponent(8).getClass should be(classOf[BlockStepSeparator])
    }
    "add new fourth code menu at index 8" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      view.addCodeMenu(2)
      view.addMathStep(2)
      // when
      view.addCodeMenu(2)
      // then
      view.getComponent(8).getClass should be(classOf[BlockStepSeparator])
    }
  }

  "BlockLayoutSeparator menu" should {

    "have step index 0 at index 0 when initialized" in {
      // given
      val view = new BlockLayout
      // when
      view.addCodeMenu(-1)
      // then
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
    }
    "have step index 0 at index 0 when appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
    }
    "have step index 1 at index 2 when appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
    }
    "have step index 2 at index 4 when appended" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      // when
      view.addCodeMenu(1)
      // then
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
    }
    "have step index 2 at index 4 when added" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
    }
    "have step index 3 at index 6 when appended" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      // when
      view.addCodeMenu(2)
      // then
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
    "have step index 3 at index 6 when added to start" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
    "have step index 3 at index 6 when added to middle" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(1)
      // when
      view.addCodeMenu(1)
      // then
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
    "have step index 3 at index 6 when added (mixed)" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(0)
      // when
      view.addCodeMenu(0)
      // then
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
    "have step index 4 at index 8 when appended" in {
      val view = new BlockLayout
      // given
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addMathStep(1)
      view.addCodeMenu(1)
      view.addMathStep(2)
      view.addCodeMenu(2)
      view.addMathStep(3)
      // when
      view.addCodeMenu(3)
      // then
      view.getComponent(8).asInstanceOf[BlockStepSeparator].menu.index() should be(4)
    }
  }

  "BlockLayout" should {
    "remove step #0 when it is the only step" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      val step = view.addMathStep(0)
      view.addCodeMenu(0)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(1)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
    }
    "remove step #0 from the beginning when it had been appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      val step = view.addMathStep(0)
      view.addCodeMenu(0)
      view.addLogicStep(1)
      view.addCodeMenu(1)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(3)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
    }
    "remove step #0 from the beginning when it had been inserted" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addLogicStep(0)
      view.addCodeMenu(0)
      val step = view.addMathStep(0) // inserted before previous step
      view.addCodeMenu(0)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(3)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
    }
    "remove step #1 from the end when it had been appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      val step = view.addLogicStep(1)
      view.addCodeMenu(1)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(3)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
    }
    "remove step #1 from the middle when it had been appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      val step = view.addLogicStep(1)
      view.addCodeMenu(1)
      view.addFunctionStep(2)
      view.addCodeMenu(2)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(5)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[FunctionReferenceLayout])
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
    }
    "remove step #1 from the middle when it had been inserted" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addFunctionStep(1)
      view.addCodeMenu(1)
      val step = view.addLogicStep(1) // inserted before previous step
      view.addCodeMenu(1)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(5)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[FunctionReferenceLayout])
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
    }
    "remove step #2 from the end when it had been appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addLogicStep(1)
      view.addCodeMenu(1)
      val step = view.addFunctionStep(2)
      view.addCodeMenu(2)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(5)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
    }
    "remove step #2 from the middle when it had been inserted" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addLogicStep(1)
      view.addCodeMenu(1)
      view.addPrintStep(2)
      view.addCodeMenu(2)
      val step = view.addFunctionStep(2) // inserted before previous step
      view.addCodeMenu(2)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(7)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(5).getClass should be(classOf[BlockStep])
      view.getComponent(6).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
      view.getComponent(5).asInstanceOf[BlockStep].step.getClass should be(classOf[PrinterLayout])
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
    "remove step #3 from the end when it had been appended" in {
      // given
      val view = new BlockLayout
      view.addCodeMenu(-1)
      view.addMathStep(0)
      view.addCodeMenu(0)
      view.addLogicStep(1)
      view.addCodeMenu(1)
      view.addFunctionStep(2)
      view.addCodeMenu(2)
      val step = view.addPrintStep(3)
      view.addCodeMenu(3)
      // when
      step.removeFromParent()
      // then
      view.componentCount should be(7)
      view.getComponent(0).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(1).getClass should be(classOf[BlockStep])
      view.getComponent(2).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(3).getClass should be(classOf[BlockStep])
      view.getComponent(4).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(5).getClass should be(classOf[BlockStep])
      view.getComponent(6).getClass should be(classOf[BlockStepSeparator])
      view.getComponent(0).asInstanceOf[BlockStepSeparator].menu.index() should be(0)
      view.getComponent(1).asInstanceOf[BlockStep].step.getClass should be(classOf[MathLayout])
      view.getComponent(2).asInstanceOf[BlockStepSeparator].menu.index() should be(1)
      view.getComponent(3).asInstanceOf[BlockStep].step.getClass should be(classOf[LogicLayout])
      view.getComponent(4).asInstanceOf[BlockStepSeparator].menu.index() should be(2)
      view.getComponent(5).asInstanceOf[BlockStep].step.getClass should be(classOf[FunctionReferenceLayout])
      view.getComponent(6).asInstanceOf[BlockStepSeparator].menu.index() should be(3)
    }
  }

}
