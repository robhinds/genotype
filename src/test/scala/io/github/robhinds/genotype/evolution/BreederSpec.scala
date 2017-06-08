package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import io.github.robhinds.genotype.evolution.Breeder.breed
import org.scalatest.{FunSpec, Matchers}

class BreederSpec extends FunSpec with Matchers {

  describe("cross breeding a random case class") {
    it("should handle breeding simple integer gene") {
      val a = IntGene(value = Some(1))
      val b = IntGene(value = Some(2))
      val c = breed(a, b)
      List(a, b) should contain (c)
    }
    it("should handle breeding simple boolean gene") {
      val a = BooleanGene(value = Some(true))
      val b = BooleanGene(value = Some(false))
      val c = breed(a, b)
      List(a, b) should contain (c)
    }
    it("should handle breeding simple double gene") {
      val a = DoubleGene(value = Some(1.2))
      val b = DoubleGene(value = Some(2.4))
      val c = breed(a, b)
      List(a, b) should contain (c)
    }
    it("should cross breed a candidate") {
      case class Candidate(a: IntGene, b: BooleanGene, c: DoubleGene, d: IntGene, e: BooleanGene, f: DoubleGene)
      val a = Candidate(IntGene(value = Some(1)), BooleanGene(value = Some(true)), DoubleGene(value = Some(2.3)), IntGene(value = Some(4)), BooleanGene(value = Some(true)), DoubleGene(value = Some(5.6)))
      val b = Candidate(IntGene(value = Some(2)), BooleanGene(value = Some(false)), DoubleGene(value = Some(1.3)), IntGene(value = Some(8)), BooleanGene(value = Some(false)), DoubleGene(value = Some(9.6)))
      val c = breed(a, b)
      c should not be a
      c should not be b
    }

  }

}
