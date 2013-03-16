package com.quane.little.ide.language.memory
import scala.swing.TextField
import scala.swing.FlowPanel
import javax.swing.BorderFactory
import java.awt.Color
import com.quane.little.ide.language.data.TextFieldPanel

/** A panel enabling a user to specify a pointer to a variable in memory by
  * name.
  *
  * @author Sean Connolly
  */
class PointerPanel
        extends FlowPanel {

    private val field = new TextFieldPanel

    contents += field

    def variableName = field.value
    
}