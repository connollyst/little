package com.quane.glass.language

import com.quane.glass.language.data.Value

/** @author Sean Connolly
  */
trait Expression[+T] {

    def evaluate: T

}