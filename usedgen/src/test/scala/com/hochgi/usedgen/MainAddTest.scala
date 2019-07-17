package com.hochgi.usedgen

import org.scalatest.{FunSpec, Matchers}

class MainAddTest extends FunSpec with Matchers {

  describe("Main") {
    it("addition") {
      com.hochgi.codegen.Main.add(1,1) should be(2)
    }
  }
}
