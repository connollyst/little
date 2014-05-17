package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.ide.IDEBindingModule
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends WordSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = IDEBindingModule

  "BlockPresenter" should {

    "register itself with it's view" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }

    /* Test adding steps to the block */

    "error when adding unknown expression" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      intercept[IllegalArgumentException] {
        presenter.add(mock[Expression])
      }
    }

    "add set statement to view" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addSetStatement()).thenReturn(new MockSetterView)
      presenter.add(new Setter("x", Value("y")))
      verify(view).addSetStatement()
    }

    "add set statement to presenter" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addSetStatement()).thenReturn(new MockSetterView)
      presenter.add(new Setter("x", Value("y")))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[SetterPresenter[_ <: SetterView]])
    }

    "initialize set statement when added" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addSetStatement()).thenReturn(new MockSetterView)
      val setPresenter = mock[SetterPresenter[SetterView]]
      val statement = new Setter("x", Value("y"))
      presenter.add(statement)
      // TODO test isn't applicable as presenter comes from factory
      verify(setPresenter).initialize(statement)
    }

    "add get statement to view" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addGetStatement()).thenReturn(new MockGetterView)
      presenter.add(new Getter("x"))
      verify(view).addGetStatement()
    }

    "add get statement to presenter" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addGetStatement()).thenReturn(new MockGetterView)
      presenter.add(new Getter("x"))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[GetterPresenter[_ <: GetterView]])
    }

    "initialize get statement when added" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addGetStatement()).thenReturn(new MockGetterView)
      val getPresenter = mock[GetterPresenter[GetterView]]
      val statement = new Getter("x")
      presenter.add(statement)
      // TODO test isn't applicable as presenter comes from factory
      verify(getPresenter).initialize(statement)
    }

    "add print statement to view" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addPrintStatement()).thenReturn(new MockPrinterView)
      presenter.add(new Printer(Value("x")))
      verify(view).addPrintStatement()
    }

    "add print statement to presenter" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addPrintStatement()).thenReturn(new MockPrinterView)
      presenter.add(new Printer(Value("x")))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[PrinterPresenter[_ <: PrinterView]])
    }

    "initialize print statement when added" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addPrintStatement()).thenReturn(new MockPrinterView)
      val printPresenter = mock[PrinterPresenter[PrinterView]]
      val statement = new Printer(Value("x"))
      presenter.add(statement)
      // TODO test isn't applicable as presenter comes from factory
      verify(printPresenter).initialize(statement)
    }

    "add function reference to view" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addFunctionReference()).thenReturn(new MockFunctionReferenceView)
      presenter.add(new FunctionReference("funName"))
      verify(view).addFunctionReference()
    }

    "add function reference to presenter" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addFunctionReference()).thenReturn(new MockFunctionReferenceView)
      presenter.add(new FunctionReference("funName"))
      presenter.steps.size should be(1)
      presenter.steps(0).getClass should be(classOf[FunctionReferencePresenter[_ <: FunctionReferenceView]])
    }

    "initialize function reference when added" in {
      val view = mock[BlockView]
      val presenter = new BlockPresenter(view)
      when(view.addFunctionReference()).thenReturn(new MockFunctionReferenceView)
      val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
      val statement = new FunctionReference("funName")
      presenter.add(statement)
      // TODO test isn't applicable as presenter comes from factory
      verify(functionPresenter).initialize(statement)
    }

  }

}
