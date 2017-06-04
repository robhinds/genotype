package io.github.robhinds.genotype

import io.github.robhinds.genotype.Configuration.{EvolutionConfig, PopulationConfig}

object Scratch {


  //Change to case class with implicit conversion to HList as argument? will that work?
  //Maybe typed as [A: Generic] to allow only types that can be handled by shapeless?
  case class PopulationGenerator(p: PopulationConfig/*HList input.. implict conversion from case class?*/) {

    def generateCandidates(popSize: Int = p.size)(implicit intGen: Generator[Int], doubleGen: Generator[Double]) = {
      //looking at the HList config, generate several candidate HLists for the population
    }
  }

  case class Evolution(c: EvolutionConfig) {

  }
}
