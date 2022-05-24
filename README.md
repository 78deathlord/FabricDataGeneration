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

## Making a Data Generator
To make a Data Generator simply make a class that implement the "IDataGenerator" interface
### Example
```java
public static class MyDataGenerator implements IDataGenerator {
    @Override
    public String getModId() {
        return "my_mod_id";
    }
    
    @Override
    public String getResourceDirectory() {
        return "src\\main\\resources";
    }
    
    @Override
    public Identifier getId() {
        return new Identifier("my_mod_id", "my_data_generator"); //id has to be unique for each data generator
    }
    
    @Override
    public List<IBuilder> getBuilders() {
        List<IBuilder> builders = new LinkedList<>();
        
        //add your Data Builders to the builder list
        
        /*EXAMPLE
         *
         * builders.add(new ShapedRecipeSerializable.builder(new Identifier("my_mod_id", "cool_recipe")
         *      .pattern("AAA")
         *      .pattern("ABA")
         *      .pattern("AAA")
         *      .defineKey('A', Items.DIAMOND)
         *      .defineKey('B', Items.NETHER_STAR)
         *      .result(Items.WHITE_WOOL)
         * ));
         *
         *  */
        
        return builders;
    }
}
```

##Registering Your Data Generator Method #1
```java
public class MyMod implements ModInitializer {
    @Override
    public void onInitialize() {
        //other stuff
        FabricDataRegistries.registerGenerator(new MyDataGenerator());
    }
}
```

##Registering Your Data Generator Method #2
```java
public class MyMod implements ModInitializer {
    @Override
    public void onInitialize() {
        //other stuff
        Registry.register(FabricDataGeneration.GENERATORS, generator.getId(), generator);
    }
}
```

## Generating Data
Data generation is done using fabric registries simply call FabricDataGeneration#registerGenerator to register your data generator (or simply register using Registry#register).

***Before generating data make sure to set the "generateData" environment variable to true otherwise your data will not be generated***
***It is also important to set the "enabledMods" environment variable, it takes the form of a string array***
<br><br><br>
***Examples:***<br>
***| generateData=true***<br>
***| generateData=false***<br>
***| enabledMods=my_first_mod_id, my_second_mod_id***<br>
***| enabledMods=[my_first_mod_id, my_second_mod_id]***
</details>