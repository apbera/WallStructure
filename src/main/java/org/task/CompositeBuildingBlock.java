package org.task;

import java.util.List;

public class CompositeBuildingBlock implements CompositeBlock{

    private final List<Block> blocks;

    public CompositeBuildingBlock(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public String getMaterial() {
        return null;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }
}
