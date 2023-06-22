package org.task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure{
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .flatMap(this::unpackBlock)
                .filter(block -> block.getColor().equals(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .flatMap(this::unpackBlock)
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    private Stream<Block> unpackBlock(Block block){
        if(block instanceof CompositeBlock){
            return ((CompositeBlock) block).getBlocks().stream();
        } else {
            return Stream.of(block);
        }
    }

    @Override
    public int count() {
        return (int) blocks.stream().flatMap(this::unpackBlock).count();
    }


}
