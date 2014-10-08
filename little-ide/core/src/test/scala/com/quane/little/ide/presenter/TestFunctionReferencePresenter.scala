package com.quane.little.ide.presenter

import com.quane.little.data.model.FunctionRecord
import com.quane.little.data.service.FunctionService
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view.{MockFunctionArgumentView, MockFunctionReferenceView}
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.{FunctionParameter, FunctionDefinition, FunctionReference}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, WordSpec}

/** Test cases for the [[com.quane.little.ide.presenter.FunctionReferencePresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter
  extends WordSpec with ShouldMatchers with MockitoSugar {

  "FunctionReferencePresenter" should {
    "store name changes" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        val presenter = new FunctionReferencePresenter(MockFunctionReferenceView.mocked())
        presenter.name = "sean is cool"
        assert(presenter.name == "sean is cool")
      }
    }

    /* Test View */

    "register itself with view immediately" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        verify(view).registerViewPresenter(presenter)
      }
    }
    "not set name before initialization" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        val view = MockFunctionReferenceView.mocked()
        new FunctionReferencePresenter(view)
        verify(view, never).setName(anyString)
      }
    }
    "initialize name in its view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        presenter.name = "abc"
        verify(view).setName("abc")
      }
    }
    "initialize defined parameters in the view" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(new FunctionReference("TestFunction"))
        // Then
        verify(view, times(3)).createArgument()
      }
    }
    "initialize all arguments when all are specified" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(
          new FunctionReference("TestFunction")
            .addArg("a", Value("x"))
            .addArg("b", Value(137))
            .addArg("c", Value(true))
        )
        // Then
        presenter.args(0).name should be("a")
        presenter.args(1).name should be("b")
        presenter.args(2).name should be("c")
        presenter.args(0).value.compile() should be(Value("x"))
        presenter.args(1).value.compile() should be(Value(137))
        presenter.args(2).value.compile() should be(Value(true))
      }
    }
    "initialize all arguments when first is specified (others get defaults)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(
          new FunctionReference("TestFunction")
            .addArg("a", Value("x"))
          // note: "b" is not specified
          // note: "c" is not specified
        )
        // Then
        presenter.args(0).name should be("a")
        presenter.args(1).name should be("b")
        presenter.args(2).name should be("c")
        presenter.args(0).value.compile() should be(Value("x"))
        presenter.args(1).value.compile() should be(Value("")) // initialized with default
        presenter.args(2).value.compile() should be(Value("")) // initialized with default
      }
    }
    "initialize all arguments when second is specified (others get defaults)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(
          new FunctionReference("TestFunction")
            // note: "a" is not specified
            .addArg("b", Value(137))
          // note: "c" is not specified
        )
        // Then
        presenter.args(0).name should be("a")
        presenter.args(1).name should be("b")
        presenter.args(2).name should be("c")
        presenter.args(0).value.compile() should be(Value("")) // initialized with default
        presenter.args(1).value.compile() should be(Value(137))
        presenter.args(2).value.compile() should be(Value("")) // initialized with default
      }
    }
    "initialize all arguments when third is specified (others get defaults)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(
          new FunctionReference("TestFunction")
            // note: "a" is not specified
            // note: "b" is not specified
            .addArg("c", Value(true))
        )
        // Then
        presenter.args(0).name should be("a")
        presenter.args(1).name should be("b")
        presenter.args(2).name should be("c")
        presenter.args(0).value.compile() should be(Value("")) // initialized with default
        presenter.args(1).value.compile() should be(Value("")) // initialized with default
        presenter.args(2).value.compile() should be(Value(true))
      }
    }
    "initialize some arguments when some are defined (others get defaults)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
          .addParam("c", ValueType.Boolean)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView.mocked()
        val presenter = new FunctionReferencePresenter(view)
        // When
        presenter.initialize(
          new FunctionReference("TestFunction")
            // note: "a" is not specified
            .addArg("b", Value(137))
            .addArg("c", Value(true))
        )
        // Then
        presenter.args(0).name should be("a")
        presenter.args(1).name should be("b")
        presenter.args(2).name should be("c")
        presenter.args(0).value.compile() should be(Value("")) // initialized with default
        presenter.args(1).value.compile() should be(Value(137))
        presenter.args(2).value.compile() should be(Value(true))
      }
    }

    /* Assert compiled function references.. */

    "compile with name (default)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        // Then
        val err = intercept[IllegalAccessException] {
          // When
          val function = presenter.compile()
        }
        err.getMessage should be("Function reference name not set.")
      }
    }
    "compile with name" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.name = "newName"
        // When
        val function = presenter.compile()
        // Then
        assert(function.name == "newName")
      }
    }
    "compile with 0 parameters (when initialized from data)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.initialize(new FunctionReference("TestFunction"))
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 0)
      }
    }
    "compile with 1 parameter (when initialized from data)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.initialize(new FunctionReference("TestFunction"))
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 1)
      }
    }
    "compile with 2 parameters (when initialized from data)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val funName = "TestFunction"
        val funDefinition = new FunctionDefinition(funName)
          .addParam("a", ValueType.String)
          .addParam("b", ValueType.Integer)
        val funRecord = new FunctionRecord(null, null, funDefinition)
        val funService = mock[FunctionService]
        when(funService.findDefinition("connollyst", funName)).thenReturn(funDefinition)
        when(funService.findByUser("connollyst")).thenReturn(List(funRecord))
        testModule.bind[FunctionService] toSingle funService
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.initialize(new FunctionReference("TestFunction"))
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 2)
      }
    }
    "compile with 0 parameters (uninitialized)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.name = "TestFunction"
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 0)
      }
    }
    "compile with 1 parameters (when explicitly added)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.name = "TestFunction"
        presenter.add(
          new FunctionArgumentPresenter(new MockFunctionArgumentView)
            .initialize(new FunctionParameter("x", ValueType.Anything), Value("y"))
        )
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 1)
      }
    }
    "compile with 2 parameters (when explicitly added)" in {
      MockIDEBindingModule.modifyBindings { implicit testModule =>
        // Given
        val view = MockFunctionReferenceView()
        val presenter = new FunctionReferencePresenter(view)
        presenter.name = "TestFunction"
        presenter.add(
          new FunctionArgumentPresenter(new MockFunctionArgumentView)
            .initialize(new FunctionParameter("a", ValueType.Anything), Value("x"))
        )
        presenter.add(
          new FunctionArgumentPresenter(new MockFunctionArgumentView)
            .initialize(new FunctionParameter("b", ValueType.Anything), Value("y"))
        )
        // When
        val function = presenter.compile()
        // Then
        assert(function.args.size == 2)
      }
    }
  }

}
