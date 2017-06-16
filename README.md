[![Build Status](https://travis-ci.org/robhinds/genotype.png)](https://travis-ci.org/robhinds/genotype)

# genotype
Just for fun, this project allows you to use some very simple genetic algorithm code to search for solutions. It is very basic, and just supports Int/Double/Boolean genes at the moment, and just uses simple gaussian mutation for numerics and random flips for booleans.

To use it, simply define a `case class` that represents your encoded solution - the attributes need to be of the type `Gene[A]`

For example: 
```
case class ThreeNumbers(x: IntGene, y: IntGene, z: IntGene)
```

You can then provide an instance of that solution (the instance allows additional configuration to be set, such as max/min for numerics) along with an errorRate function (function that given a solution as input returns a score that represents the rate of error for how it performs)

For example, the below configuration uses the above defined `ThreeNumbers` case class and attempts to search for a solution that such that the product of the three numbers is as close as possible to 10,000

```
EvolutionFactory(
  candidate = ThreeNumbers(IntGene(min = 0, max = 100), IntGene(min = 0, max = 100), IntGene(min = 0, max = 100)),
  populationConfig = PopulationConfig(50),
  evolutionConfig = EvolutionConfig(50),
  errorRate = (c: ThreeNumbers) => Math.abs(10000.0 - (c.x.value.get * c.y.value.get * c.z.value.get))
).run
```
