package to.tinypota.ollivanders.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import to.tinypota.ollivanders.common.recipe.OllivandersCookingRecipeJsonBuilder;
import to.tinypota.ollivanders.registry.builder.WoodBlockRegistry;
import to.tinypota.ollivanders.registry.common.OllivandersItems;
import to.tinypota.ollivanders.registry.common.OllivandersRecipeSerializers;

import java.util.List;
import java.util.function.Consumer;

public class OllivandersRecipeGenerator extends FabricRecipeProvider {
	public OllivandersRecipeGenerator(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		for (var storage : WoodBlockRegistry.WOOD_BLOCK_STORAGES) {
			var logTag = TagKey.of(RegistryKeys.ITEM, new Identifier("c", storage.getName() + "_logs"));
			var hasLog = "has_" + storage.getName() + "_log";
			var hasLogCondition = VanillaRecipeProvider.conditionsFromTag(logTag);
			var hasPlanks = "has_" + storage.getName() + "_planks";
			var hasPlanksCondition = VanillaRecipeProvider.conditionsFromItem(storage.getPlanks().asItem());
			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getPlanks(), 4).input(logTag).criterion(hasLog, hasLogCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getWood(), 3).input('l', storage.getLog()).pattern("ll").pattern("ll").criterion(hasLog, hasLogCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getStrippedWood(), 3).input('l', storage.getStrippedLog()).pattern("ll").pattern("ll").criterion(hasLog, hasLogCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getPressurePlate()).input('p', storage.getPlanks()).pattern("pp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getButton()).input(storage.getPlanks()).criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getSlab(), 6).input('p', storage.getPlanks()).pattern("ppp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getStairs(), 4).input('p', storage.getPlanks()).pattern("p  ").pattern("pp ").pattern("ppp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getDoor(), 3).input('p', storage.getPlanks()).pattern("pp").pattern("pp").pattern("pp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getTrapdoor(), 2).input('p', storage.getPlanks()).pattern("ppp").pattern("ppp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getFence(), 3).input('p', storage.getPlanks()).input('s', Items.STICK).pattern("psp").pattern("psp").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getFenceGate()).input('p', storage.getPlanks()).input('s', Items.STICK).pattern("sps").pattern("sps").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getSign(), 3).input('p', storage.getPlanks()).input('s', Items.STICK).pattern("ppp").pattern("ppp").pattern(" s ").criterion(hasPlanks, hasPlanksCondition).offerTo(exporter);
			ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, storage.getHangingSign(), 6).input('s', storage.getStrippedLog()).input('c', Items.CHAIN).pattern("c c").pattern("sss").pattern("sss").criterion(hasLog, hasLogCondition).offerTo(exporter);
		}
		
		for (var entry : OllivandersItems.WANDS.getEntries()) {
			var wand = entry.getObject();
			offerLathe(exporter, List.of(wand.getCraftBlocks()), RecipeCategory.MISC, wand, 0.15F, 150, "");
		}
	}
	
	public static void offerLathe(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
		offerMultipleLatheOptions(exporter, OllivandersRecipeSerializers.LATHE, inputs, category, output, experience, cookingTime, group, "_from_lathing");
	}
	
	public static void offerMultipleLatheOptions(Consumer<RecipeJsonProvider> exporter, RecipeSerializer<? extends AbstractCookingRecipe> serializer, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group, String method) {
		for (ItemConvertible itemConvertible : inputs) {
			OllivandersCookingRecipeJsonBuilder.createLathe(Ingredient.ofItems(itemConvertible), category, output, experience, cookingTime).group(group).criterion(RecipeProvider.hasItem(itemConvertible), RecipeProvider.conditionsFromItem(itemConvertible)).offerTo(exporter, RecipeProvider.getItemPath(output) + method + "_" + RecipeProvider.getItemPath(itemConvertible));
		}
	}
}