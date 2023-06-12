package net.skvr.superkeiththemod.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skvr.superkeiththemod.SuperKeithTheMod;
import net.skvr.superkeiththemod.block.ModBlocks;
import net.skvr.superkeiththemod.entity.custom.SolderingStationTile;

public class ModTileEnitity {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SuperKeithTheMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<SolderingStationTile>> SOLDERING_STATION = TILE_ENTITY_TYPES.register("soldering_station",
            () -> BlockEntityType.Builder.of(SolderingStationTile::new, ModBlocks.SOLDERING_STATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITY_TYPES.register(eventBus);
    }
}
