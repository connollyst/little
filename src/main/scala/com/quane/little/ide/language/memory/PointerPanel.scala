package com.quane.little.ide.language.memory
import scala.swing.TextField
import scala.swing.FlowPanel

/** A panel enabling a user to specify a pointer to a variable in memory by
  * name.
  *
  * @author Sean Connolly
  */
class PointerPanel
        extends FlowPanel {

    private val field = new TextField("", 16)

    contents += field

    def variableName = field.text

}