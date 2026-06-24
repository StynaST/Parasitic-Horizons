```groovy
import classes.RecipeManager
```


## Examples

// remove everthing but material 1
RecipeManager.removeByMod(crafting, 'immersiveengineering', 'immersiveengineering:material:1')
*Use a list*

def exclusions = [
    'wirecoil',
    'metal_device1:13'
]

RecipeManager.removeByMod(crafting, 'immersiveengineering', exclusions)


### Replace a ingredient

RecipeManager.replaceIngredient(crafting, 'immersiveengineering:material:22', 'hbm:wire_fine:1300')