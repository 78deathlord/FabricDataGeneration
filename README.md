# FabricDataGeneration
Data generation for fabric. Fabric Data Generation aims to bring easy data generation for fabric

<details>
  <summary>Using in fabric mods</summary>

## Dependency and Repository
```groovy
repositories {
    maven {
        name "jitpack"
        url "https://jitpack.io"
    }
}

dependencies {
    modApi("com.github.wandering-soul6573:FabricDataGeneration:${FabricDataGenerationVersion}", {
        exclude group: "net.fabricmc.fabric-api"
    })
}
```

## Generating Data
Data generation is done using fabric registries simply call DataRegistries#register to register your data builder (or simply register using Registry#register).

Before running the game to generate the data, you need to set some environment variables to tell FDG (Fabric Data Generation) to generate your data.
Those environment variables are generateData (boolean, if FDG should generate data), and resourcePath (string, the path to output the generated data, typically the src/main/resources directory. should be the full path).
</details>