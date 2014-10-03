package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.ide.MockIDEBindingModule
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the [[com.quane.little.ide.presenter.BlockPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends WordSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "BlockPresenter" should {

    "register itself with its view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "error when adding unknown expression" in {
      val presenter = new BlockPresenter(MockBlockView.mocked())
      intercept[IllegalArgumentException] {
        presenter.add(mock[Expression])
      }
    }

    /* Assert that step views are added.. */

    "add set statement to view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new Setter("x", Value("y")))
      verify(view).addSetStep(anyInt)
    }
    "add get statement to view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new Getter("x"))
      verify(view).addGetStep(anyInt)
    }
    "add print statement to view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new Printer(Value("x")))
      verify(view).addPrintStep(anyInt)
    }
    "add function reference to view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new FunctionReference("MyFunction"))
      verify(view).addFunctionStep(anyInt)
    }
    "add lots of steps to view" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new Setter("x", Value("y")))
      presenter.add(new Getter("x"))
      presenter.add(new Printer(Value("x")))
      presenter.add(new FunctionReference("MyFunction1"))
      presenter.add(new Setter("z", Value("y")))
      presenter.add(new Getter("z"))
      presenter.add(new Printer(Value("z")))
      presenter.add(new FunctionReference("MyFunction2"))
      verify(view).addSetStep(0)
      verify(view).addGetStep(1)
      verify(view).addPrintStep(2)
      verify(view).addFunctionStep(3)
      verify(view).addSetStep(4)
      verify(view).addGetStep(5)
      verify(view).addPrintStep(6)
      verify(view).addFunctionStep(7)
    }

    /* Assert that step presenters are added.. */

    "add set statement to presenter" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      presenter.add(new Setter("x", Value("y")))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[SetterPresenter[_ <: SetterView]])
    }
    "add get statement to presenter" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      when(view.addGetStep(0)).thenReturn(new MockGetterView)
      presenter.add(new Getter("x"))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[GetterPresenter])
    }
    "add print statement to presenter" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      when(view.addPrintStep(0)).thenReturn(new MockPrinterView)
      presenter.add(new Printer(Value("x")))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[PrinterPresenter[_ <: PrinterView]])
    }
    "add function reference to presenter" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      when(view.addFunctionStep(0)).thenReturn(new MockFunctionReferenceView)
      presenter.add(new FunctionReference("funName"))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[FunctionReferencePresenter])
    }

    /* Assert that steps are initialized when added.. */

    "initialize set statement when added" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      val setterView = mock[SetterView]
      val valueView = mock[ValueView]
      when(view.addSetStep(0)).thenReturn(setterView)
      when(view.addSetStep(anyInt)).thenReturn(setterView)
      when(setterView.createValueExpression()).thenReturn(valueView)
      val statement = new Setter("x", Value("y"))
      presenter.add(statement)
      verify(setterView).createValueExpression()
      verify(setterView).setName("x")
      verify(valueView).setValue("y")
    }
    "initialize get statement when added" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      val getterView = mock[GetterView]
      when(view.addGetStep(0)).thenReturn(getterView)
      when(view.addGetStep(anyInt())).thenReturn(getterView)
      val statement = new Getter("x")
      presenter.add(statement)
      verify(getterView).setName("x")
    }
    "initialize print statement when added" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      val printerView = mock[PrinterView]
      val valueView = mock[ValueView]
      when(view.addPrintStep(0)).thenReturn(printerView)
      when(view.addPrintStep(anyInt)).thenReturn(printerView)
      when(printerView.createValueStatement()).thenReturn(valueView)
      val statement = new Printer(Value("x"))
      presenter.add(statement)
      verify(printerView).createValueStatement()
      verify(valueView).setValue("x")
    }
    "initialize function reference when added" in {
      val view = MockBlockView.mocked()
      val presenter = new BlockPresenter(view)
      val functionView = mock[FunctionReferenceView]
      when(view.addFunctionStep(0)).thenReturn(functionView)
      when(view.addFunctionStep(anyInt)).thenReturn(functionView)
      val statement = new FunctionReference("TestFunction")
      presenter.add(statement)
      verify(functionView).setName("TestFunction")
    }

  }

}
