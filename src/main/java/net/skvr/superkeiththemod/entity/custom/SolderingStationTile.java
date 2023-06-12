package net.skvr.superkeiththemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.skvr.superkeiththemod.entity.ModTileEnitity;
import org.jetbrains.annotations.Nullable;

public class SolderingStationTile extends BlockEntity {

    public SolderingStationTile(BlockPos pos, BlockState state) {
        super(ModTileEnitity.SOLDERING_STATION.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        SolderingStationTile tile = (SolderingStationTile) be;

    }
}
