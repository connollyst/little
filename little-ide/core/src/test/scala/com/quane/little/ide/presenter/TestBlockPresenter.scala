package com.quane.little.ide.presenter

import com.quane.little.data.model.FunctionRecord
import com.quane.little.data.service.FunctionService
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

/** Test cases for the [[com.quane.little.ide.presenter.BlockPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlockPresenter
  extends WordSpec with ShouldMatchers with MockitoSugar {

  "BlockPresenter" should {
    "register itself with its view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        // When
        val presenter = new BlockPresenter(view)
        // Then
        verify(view).registerViewPresenter(presenter)
      }
    }
    "error when adding unknown expression" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val presenter = new BlockPresenter(MockBlockView.mocked())
        // Then
        intercept[IllegalArgumentException] {
          // When
          presenter.add(mock[Code])
        }
      }
    }
  }

  /* Assert that step views are added.. */
  "BlockPresenter" should {
    "add set statement to view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Setter("x", Value("y")))
        // Then
        verify(view).addSetStep(anyInt)
      }
    }
    "add get statement to view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Getter("x"))
        // Then
        verify(view).addGetStep(anyInt)
      }
    }
    "add print statement to view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Printer(Value("x")))
        // Then
        verify(view).addPrintStep(anyInt)
      }
    }
    "add function reference to view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new FunctionReference("TestFunction"))
        // Then
        verify(view).addFunctionStep(anyInt)
      }
    }
    "add lots of steps to view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val username = "connollyst"
        val fun1Name = "TestFunction1"
        val fun2Name = "TestFunction2"
        val fun1Definition = new FunctionDefinition(fun1Name)
        val fun2Definition = new FunctionDefinition(fun2Name)
        val fun1Record = new FunctionRecord(null, null, fun1Definition)
        val fun2Record = new FunctionRecord(null, null, fun2Definition)
        val funService = mock[FunctionService]
        when(funService.findDefinition(username, fun1Name)).thenReturn(fun1Definition)
        when(funService.findDefinition(username, fun2Name)).thenReturn(fun2Definition)
        when(funService.findByUser(username)).thenReturn(List(fun1Record, fun2Record))
        testModule.bind[FunctionService] toSingle funService
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Setter("x", Value("y")))
        presenter.add(new Getter("x"))
        presenter.add(new Printer(Value("x")))
        presenter.add(new FunctionReference("TestFunction1"))
        presenter.add(new Setter("z", Value("y")))
        presenter.add(new Getter("z"))
        presenter.add(new Printer(Value("z")))
        presenter.add(new FunctionReference("TestFunction2"))
        // Then
        verify(view).addSetStep(0)
        verify(view).addGetStep(1)
        verify(view).addPrintStep(2)
        verify(view).addFunctionStep(3)
        verify(view).addSetStep(4)
        verify(view).addGetStep(5)
        verify(view).addPrintStep(6)
        verify(view).addFunctionStep(7)
      }
    }
  }

  /* Assert that step presenters are added.. */
  "BlockPresenter" should {
    "add set statement to presenter" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Setter("x", Value("y")))
        // Then
        presenter.steps.size should be(1)
        presenter.steps(0).getClass should be(classOf[SetterPresenter[_ <: SetterView]])
      }
    }
    "add get statement to presenter" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Getter("x"))
        // Then
        presenter.steps.size should be(1)
        presenter.steps(0).getClass should be(classOf[GetterPresenter])
      }
    }
    "add print statement to presenter" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new Printer(Value("x")))
        // Then
        presenter.steps.size should be(1)
        presenter.steps(0).getClass should be(classOf[PrinterPresenter[_ <: PrinterView]])
      }
    }
    "add function reference to presenter" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        // When
        presenter.add(new FunctionReference("TestFunction"))
        // Then
        presenter.steps.size should be(1)
        presenter.steps(0).getClass should be(classOf[FunctionReferencePresenter])
      }
    }
  }

  /* Assert that steps are initialized when added.. */
  "BlockPresenter" should {
    "initialize set statement when added" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        val setterView = mock[SetterView]
        val valueView = mock[ValueView]
        when(view.addSetStep(0)).thenReturn(setterView)
        when(setterView.createValueExpression()).thenReturn(valueView)
        val statement = new Setter("x", Value("y"))
        // When
        presenter.add(statement)
        // Then
        verify(setterView).createValueExpression()
        verify(setterView).setName("x")
        verify(valueView).setValue("y")
      }
    }
    "initialize get statement when added" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        val getterView = mock[GetterView]
        when(view.addGetStep(0)).thenReturn(getterView)
        // When
        presenter.add(new Getter("x"))
        // Then
        verify(getterView).setName("x")
      }
    }
    "initialize print statement when added" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        val printerView = MockPrinterView.mocked()
        val valueView = mock[ValueView]
        when(view.addPrintStep(0)).thenReturn(printerView)
        when(printerView.createValueStatement()).thenReturn(valueView)
        // When
        presenter.add(new Printer(Value("x")))
        // Then
        verify(printerView).createValueStatement()
        verify(valueView).setValue("x")
      }
    }
    "initialize function reference when added" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockBlockView.mocked()
        val presenter = new BlockPresenter(view)
        val functionView = mock[FunctionReferenceView]
        when(view.addFunctionStep(0)).thenReturn(functionView)
        // When
        presenter.add(new FunctionReference("TestFunction"))
        // Then
        verify(functionView).setName("TestFunction")
      }
    }
  }
}
