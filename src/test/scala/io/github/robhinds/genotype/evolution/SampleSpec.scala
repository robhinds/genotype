package io.github.robhinds.genotype.evolution

import io.github.robhinds.genotype.Configuration.{EvolutionConfig, PopulationConfig}
import io.github.robhinds.genotype.EvolutionFactory
import io.github.robhinds.genotype.Genes.IntGene
import org.scalatest.{FunSpec, Matchers}

class SampleSpec extends FunSpec with Matchers {

  describe("sample evolution setup") {
    it("should search for a solution") {
      case class ThreeNumbers(x: IntGene, y: IntGene, z: IntGene)
      val results = EvolutionFactory(
        candidate = ThreeNumbers(IntGene(0, 100), IntGene(0, 100), IntGene(0, 100)),
        populationConfig = PopulationConfig(50),
        evolutionConfig = EvolutionConfig(50),
        errorRate = (c: ThreeNumbers) => Math.abs(1000.0 - (c.x.value.get * c.y.value.get * c.z.value.get))
      ).run
      println(results.head)
    }
  }

}
