import org.task.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WallTest {
    Structure structure;

    private void initEmptyList(){
        structure = new Wall(Collections.emptyList());
    }

    private void initOnlyBlocksList(){
        List<Block> blockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("blue", "sand"),
                new BuildingBlock("blue", "clay")
        );
        structure = new Wall(blockList);
    }

    private void initOnlyCompositeBlocksList(){
        List<Block> firstBlockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("blue", "sand"),
                new BuildingBlock("blue", "clay")
        );
        List<Block> secondBlockList = List.of(
                new BuildingBlock("red", "sand"),
                new BuildingBlock("yellow", "iron"),
                new BuildingBlock("blue", "iron")
        );
        CompositeBlock firstCompositeBlock = new CompositeBuildingBlock(firstBlockList);
        CompositeBlock secondCompositeBlock = new CompositeBuildingBlock(secondBlockList);
        structure = new Wall(List.of(firstCompositeBlock,secondCompositeBlock));
    }

    private void initMixedBlocksList(){
        List<Block> firstBlockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("blue", "sand"),
                new BuildingBlock("blue", "clay")
        );
        List<Block> secondBlockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("yellow", "iron"),
                new BuildingBlock("blue", "iron")
        );
        CompositeBlock firstCompositeBlock = new CompositeBuildingBlock(firstBlockList);
        CompositeBlock secondCompositeBlock = new CompositeBuildingBlock(secondBlockList);
        List<Block> finalBlockList = List.of(
                new BuildingBlock("green", "wood"),
                firstCompositeBlock,
                new BuildingBlock("red", "wood"),
                new BuildingBlock("green", "iron"),
                secondCompositeBlock
        );
        structure = new Wall(finalBlockList);
    }

    private void initNestedCompositeBlocks(){
        List<Block> firstBlockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("blue", "sand"),
                new BuildingBlock("blue", "clay")
        );
        List<Block> secondBlockList = List.of(
                new BuildingBlock("red", "sand"),
                new BuildingBlock("yellow", "iron"),
                new BuildingBlock("blue", "iron")
        );
        CompositeBlock firstCompositeBlock = new CompositeBuildingBlock(firstBlockList);
        CompositeBlock secondCompositeBlock = new CompositeBuildingBlock(secondBlockList);
        List<Block> nestedCompositeBlocksList = List.of(
                new BuildingBlock("red", "iron"),
                secondCompositeBlock,
                new BuildingBlock("blue", "clay")
        );
        CompositeBlock nestedCompositeBlock = new CompositeBuildingBlock(nestedCompositeBlocksList);
        List<Block> finalBlockList = List.of(
                new BuildingBlock("green", "wood"),
                firstCompositeBlock,
                new BuildingBlock("red", "wood"),
                nestedCompositeBlock,
                new BuildingBlock("green", "iron")
        );
        structure = new Wall(finalBlockList);
    }

    @Test
    public void shouldReturnEmptyWhenFindingColorForEmptyList(){
        //given
        initEmptyList();

        //when
        Optional<Block> result = structure.findBlockByColor("red");

        //then
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnAnyBlueElementWhenFindingColorForNoComposites(){
        //given
        initOnlyBlocksList();
        List<Block> expectedElementsList = List.of(
                new BuildingBlock("blue", "sand"),
                new BuildingBlock("blue", "clay")
        );

        //when
        Optional<Block> result = structure.findBlockByColor("blue");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(expectedElementsList.contains(result.get()));
    }

    @Test
    public void shouldReturnAnyRedElementWhenFindingColorForOnlyComposites(){
        //given
        initOnlyCompositeBlocksList();
        List<Block> expectedElementsList = List.of(
                new BuildingBlock("red", "sand"),
                new BuildingBlock("red", "clay")
        );

        //when
        Optional<Block> result = structure.findBlockByColor("red");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(expectedElementsList.contains(result.get()));
    }

    @Test
    public void shouldReturnAnyYellowElementWhenFindingColorForMixedBlocks(){
        //given
        initMixedBlocksList();
        Block expectedBlock = new BuildingBlock("yellow", "iron");

        //when
        Optional<Block> result = structure.findBlockByColor("yellow");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedBlock, result.get());
    }

    @Test
    public void shouldReturnAnyYellowElementWhenFindingColorForNestedComposites(){
        //given
        initNestedCompositeBlocks();
        Block expectedBlock = new BuildingBlock("yellow", "iron");

        //when
        Optional<Block> result = structure.findBlockByColor("yellow");

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedBlock, result.get());
    }

    @Test
    public void shouldReturnEmptyListWhenFindingMaterials(){
        //given
        initEmptyList();

        //when
        List<Block> result = structure.findBlocksByMaterial("clay");

        //then
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnListOfClayMaterialsWhenFindingMaterialsForNoComposites(){
        //given
        initOnlyBlocksList();
        List<Block> expectedBlockList = List.of(
                new BuildingBlock("red", "clay"),
                new BuildingBlock("blue", "clay")
        );

        //when
        List<Block> result = structure.findBlocksByMaterial("clay");

        //then
        Assertions.assertEquals(expectedBlockList.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedBlockList));
    }

    @Test
    public void shouldReturnListOfSandMaterialsWhenFindingMaterialsForOnlyComposites(){
        //given
        initOnlyCompositeBlocksList();
        List<Block> expectedBlockList = List.of(
                new BuildingBlock("red", "sand"),
                new BuildingBlock("blue", "sand")
        );

        //when
        List<Block> result = structure.findBlocksByMaterial("sand");

        //then
        Assertions.assertEquals(expectedBlockList.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedBlockList));
    }

    @Test
    public void shouldReturnListOfIronMaterialsWhenFindingMaterialsForMixedBlocks(){
        //given
        initMixedBlocksList();
        List<Block> expectedBlockList = List.of(
                new BuildingBlock("yellow", "iron"),
                new BuildingBlock("blue", "iron"),
                new BuildingBlock("green", "iron")

        );

        //when
        List<Block> result = structure.findBlocksByMaterial("iron");

        //then
        Assertions.assertEquals(expectedBlockList.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedBlockList));
    }

    @Test
    public void shouldReturnListOfSandMaterialsWhenFindingMaterialsForNestedComposites(){
        //given
        initNestedCompositeBlocks();
        List<Block> expectedBlockList = List.of(
                new BuildingBlock("red", "sand"),
                new BuildingBlock("blue", "sand")
        );

        //when
        List<Block> result = structure.findBlocksByMaterial("sand");

        //then
        Assertions.assertEquals(expectedBlockList.size(), result.size());
        Assertions.assertTrue(result.containsAll(expectedBlockList));
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsForEmptyList(){
        //given
        initEmptyList();

        //when
        int result = structure.count();

        //then
        Assertions.assertEquals(0, result);
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsForNoComposites(){
        //given
        initOnlyBlocksList();

        //when
        int result = structure.count();

        //then
        Assertions.assertEquals(3, result);
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsForOnlyComposites(){
        //given
        initOnlyCompositeBlocksList();

        //when
        int result = structure.count();

        //then
        Assertions.assertEquals(6, result);
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsForMixedBlocks(){
        //given
        initMixedBlocksList();

        //when
        int result = structure.count();

        //then
        Assertions.assertEquals(9, result);
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsForNestedComposites(){
        //given
        initNestedCompositeBlocks();

        //when
        int result = structure.count();

        //then
        Assertions.assertEquals(11, result);
    }
}
