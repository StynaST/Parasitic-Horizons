import net.minecraft.item.crafting.IRecipe
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipes
import net.minecraft.item.crafting.ShapelessRecipes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.oredict.ShapedOreRecipe
import net.minecraftforge.oredict.ShapelessOreRecipe

class RecipeManager {

    // Parses 'modid:item:meta' or 'modid:item' into an ItemStack
    static ItemStack parseItem(String input) {
        def parts = input.split(':')
        if (parts.length == 3) {
            def item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parts[0], parts[1]))
            return new ItemStack(item, 1, parts[2] as int)
        } else if (parts.length == 2) {
            def item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parts[0], parts[1]))
            return new ItemStack(item)
        }
        return ItemStack.EMPTY
    }

    static void removeByMod(def crafting, String modId, List<String> exclusions = []) {
        for (IRecipe recipe : ForgeRegistries.RECIPES) {
            def recipeName = recipe.getRegistryName()
            if (recipeName.getNamespace() == modId) {
                def output = recipe.getRecipeOutput()
                if (output.isEmpty()) continue

                def itemName = output.getItem().getRegistryName().getPath()
                def meta = output.getMetadata()
                def itemWithMeta = itemName + ':' + meta

                if (!(itemName in exclusions) && !(itemWithMeta in exclusions)) {
                    crafting.remove(recipeName.toString())
                }
            }
        }
    }

    static void replaceIngredient(def crafting, String oldItemStr, String newItemStr) {
        replaceIngredient(crafting, parseItem(oldItemStr), parseItem(newItemStr))
    }

    static void replaceIngredient(def crafting, ItemStack oldItem, ItemStack newItem) {
        for (IRecipe recipe : ForgeRegistries.RECIPES) {
            List<Ingredient> ingredients = null
            boolean hasMatch = false

            try {
                ingredients = recipe.getIngredients()
                for (Ingredient ingredient : ingredients) {
                    for (ItemStack stack : ingredient.getMatchingStacks()) {
                        if (stack.getItem() == oldItem.getItem() && stack.getMetadata() == oldItem.getMetadata()) {
                            hasMatch = true
                            break
                        }
                    }
                    if (hasMatch) break
                }
            } catch (Throwable ignored) {
                continue
            }

            if (!hasMatch) continue

            def recipeName = recipe.getRegistryName().toString()
            def output = recipe.getRecipeOutput()

            crafting.remove(recipeName)

            boolean isShaped = recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe

            if (isShaped) {
                int width = recipe.getRecipeWidth()
                int height = recipe.getRecipeHeight()
                def grid = []

                for (int row = 0; row < height; row++) {
                    def rowItems = []
                    for (int col = 0; col < width; col++) {
                        int idx = row * width + col
                        if (idx < ingredients.size()) {
                            rowItems.add(swapStack(ingredients.get(idx), oldItem, newItem))
                        } else {
                            rowItems.add(null)
                        }
                    }
                    grid.add(rowItems)
                }
                crafting.addShaped(recipeName, output, grid)
            } else {
                def inputList = []
                for (Ingredient ing : ingredients) {
                    def swapped = swapStack(ing, oldItem, newItem)
                    if (swapped != null) inputList.add(swapped)
                }
                crafting.addShapeless(recipeName, output, inputList)
            }
        }
    }

    private static ItemStack swapStack(Ingredient ingredient, ItemStack oldItem, ItemStack newItem) {
        def stacks = ingredient.getMatchingStacks()
        if (stacks.length == 0) return null
        if (stacks[0].getItem() == oldItem.getItem() && stacks[0].getMetadata() == oldItem.getMetadata()) {
            return newItem.copy()
        }
        return stacks[0].copy()
    }
}
