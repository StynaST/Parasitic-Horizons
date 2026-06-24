import classes.RecipeManager

RecipeManager.replaceIngredient(crafting, 'immersiveengineering:material:23', 'hbm:wire_fine:30')
RecipeManager.replaceIngredient(crafting, 'immersiveengineering:material:21', 'hbm:wire_fine:31')
RecipeManager.replaceIngredient(crafting, 'immersiveengineering:material:22', 'hbm:wire_fine:1300')

// removals

def exclusions = [
    'wirecoil',
    'metal_device1:13'
]

RecipeManager.removeByMod(crafting, 'immersiveengineering', exclusions)
mods.immersiveengineering.alloy_kiln.removeAll()
mods.immersiveengineering.blast_furnace.removeAll()
mods.immersiveengineering.blueprint_crafting.removeAll()
mods.immersiveengineering.bottling_machine.removeAll()
mods.immersiveengineering.coke_oven.removeAll()
mods.immersiveengineering.crusher.removeAll()
mods.immersiveengineering.excavator.removeAll()
mods.immersiveengineering.fermenter.removeAll()
mods.immersiveengineering.metal_press.removeAll()
mods.immersiveengineering.mixer.removeAll()
mods.immersiveengineering.refinery.removeAll()
mods.immersiveengineering.squeezer.removeAll()
mods.immersiveengineering.arc_furnace.removeAll()
