package org.task;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure{
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = Objects.requireNonNullElse(blocks, Collections.emptyList());
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .flatMap(this::unpackBlock)
                .filter(block -> block.getColor().equalsIgnoreCase(color))
                .findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .flatMap(this::unpackBlock)
                .filter(block -> block.getMaterial().equalsIgnoreCase(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) blocks.stream().flatMap(this::unpackBlock).count();
    }

    private Stream<Block> unpackBlock(Block block){
        if(block instanceof CompositeBlock){
            return ((CompositeBlock) block).getBlocks().stream()
                    .flatMap(this::unpackBlock);
        } else {
            return Stream.of(block);
        }
    }
}
