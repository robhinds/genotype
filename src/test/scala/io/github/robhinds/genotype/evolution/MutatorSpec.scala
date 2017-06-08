package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Genes.{BooleanGene, DoubleGene, IntGene}
import io.github.robhinds.genotype.evolution.Mutator.mutate
import org.scalatest.{FunSpec, Matchers}

class MutatorSpec extends FunSpec with Matchers {

  describe("mutating a random case class") {
    it("should handle mutating simple integer gene") {
      val a = IntGene(value = Some(100))
      val c = mutate(a)
      a should not be c
    }
    it("should handle breeding simple boolean gene") {
      val a = BooleanGene(value = Some(true))
      mutate(a)
    }
    it("should handle breeding simple double gene") {
      val a = DoubleGene(value = Some(1.2))
      val c = mutate(a)
      a should not be c
    }
    it("should cross breed a candidate") {
      case class Candidate(a: IntGene, b: BooleanGene, c: DoubleGene)
      val a = Candidate(IntGene(value = Some(1)), BooleanGene(value = Some(true)), DoubleGene(value = Some(2.3)))
      val c = mutate(a)
      c should not be a
    }

  }

}
