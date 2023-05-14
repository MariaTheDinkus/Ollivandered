package to.tinypota.ollivanders;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import to.tinypota.ollivanders.registry.common.*;

public class Ollivanders implements ModInitializer {
	public static final String ID = "ollivanders";
	
	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
	
	@Override
	public void onInitialize() {
		OllivandersRegistries.init();
		OllivandersItemGroups.init();
		OllivandersItems.init();
		OllivandersBlocks.init();
		OllivandersSpells.init();
		OllivandersBlockEntityTypes.init();
		OllivandersEntityTypes.init();
		OllivandersEvents.init();
		OllivandersTrackedData.init();
		OllivandersCommands.init();
		OllivandersDimensions.init();
		OllivandersEntityAttributes.init();
		OllivandersStatusEffects.init();
	}
}
