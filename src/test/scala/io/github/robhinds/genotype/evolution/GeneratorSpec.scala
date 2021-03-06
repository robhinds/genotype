package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import io.github.robhinds.genotype.evolution.Generator.generate
import org.scalatest.{FunSpec, Matchers}
import shapeless._

class GeneratorSpec extends FunSpec with Matchers {

  describe("generating a random case class") {
    it("should generate a random intgene between 1 and -1") {
      val i = generate(IntGene(min = -1, max = 1))
      i.value.get should be <= 1
      i.value.get should be >= -1
    }
    it("should generate a random double") {
      val i = generate(DoubleGene(min = 100.0, max = 200.0))
      i.value.get should be <= 200.0
      i.value.get should be >= 100.0
    }
    it("should generate a random boolean") {
      generate(BooleanGene())
    }
    it("should generate a Hlist") {
      generate(IntGene() :: DoubleGene() :: BooleanGene() :: HNil)
    }
    it("should work with a case class to hlist") {
      case class Test(x: IntGene, y: DoubleGene, z: BooleanGene)
      val c = Generic[Test].to(Test(IntGene(), DoubleGene(), BooleanGene()))
      generate(c)
    }
    it("what happens with sealed traits") {
      sealed trait Shape
      case class Square(width: Int, height: Int) extends Shape
      case class Circle(radius: Int) extends Shape

      val c = Generic[Shape].to(Circle(1))
      assertThrows[RuntimeException] {
        generate(c)
      }
    }
    it("should generate for a simple case class") {
      case class Test(x: IntGene, y: DoubleGene, z: BooleanGene)
      generate(Test(IntGene(), DoubleGene(), BooleanGene()))
    }
  }

}
