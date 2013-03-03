package com.quane.little.ide.language

import scala.swing.Panel
import scala.swing.TextField

class PointerPanel
        extends Panel {

    private val field = new TextField("", 16)

    def variableName = field.text

}