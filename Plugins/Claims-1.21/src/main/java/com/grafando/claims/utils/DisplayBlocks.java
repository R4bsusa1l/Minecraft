package main.java.com.grafando.claims.utils;

import com.grafando.claims.Claims;
import main.java.com.grafando.claims.events.ClaimCreation;
import main.java.com.grafando.claims.events.OwnerCheck;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DisplayBlocks {
    public void displayBlocks(Block prevBlock, Block clickedBlock, ClaimCreation claimCreation, OwnerCheck ownerCheck, ArrayList<Block> blockList, ArrayList<Material> materialList, boolean status, Player player){  //Status true if ownerCheck, claim creation ?= false;;
        blockList.clear();
        materialList.clear();

        blockList = getBlocksForDisplay(prevBlock, clickedBlock, blockList);               //Dissilusion here
        Block cornerBot = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
        Block cornerTop = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
        for (Block tempBlock : blockList) {
            if (!status){
                if (Claims.currentlyClaimingPrevBlockPlayerMap.containsKey(player)){
                    if (Claims.currentlyClaimingPrevBlockPlayerMap.get(player).equals(tempBlock.getType())){
                        materialList.add(Claims.currentlyClaimingPrevBlockPlayerMap.get(player));
                    }else{
                        materialList.add(tempBlock.getType());
                    }
                }else{
                    materialList.add(tempBlock.getType());
                }
            }else{
                materialList.add(tempBlock.getType());
            }

            if (tempBlock.getX() == clickedBlock.getX() && tempBlock.getZ() == clickedBlock.getZ() ||
                    tempBlock.getX() == prevBlock.getX() && tempBlock.getZ() == prevBlock.getZ() ||
                    tempBlock.getX() == cornerBot.getX() && tempBlock.getZ() == cornerBot.getZ() ||
                    tempBlock.getX() == cornerTop.getX() && tempBlock.getZ() == cornerTop.getZ()) {
                if (tempBlock.getType().equals(Material.ACACIA_FENCE) ||
                        tempBlock.getType().equals(Material.BIRCH_FENCE) ||
                        tempBlock.getType().equals(Material.OAK_FENCE) ||
                        tempBlock.getType().equals(Material.SPRUCE_FENCE) ||
                        tempBlock.getType().equals(Material.WARPED_FENCE) ||
                        tempBlock.getType().equals(Material.DARK_OAK_FENCE) ||
                        tempBlock.getType().equals(Material.CRIMSON_FENCE) ||
                        tempBlock.getType().equals(Material.JUNGLE_FENCE) ||
                        //-.-.-.-.-.-.-.-           #Sepparation between fences & fence-gates
                        tempBlock.getType().equals(Material.BIRCH_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.ACACIA_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.CRIMSON_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.DARK_OAK_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.JUNGLE_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.OAK_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.SPRUCE_FENCE_GATE) ||
                        tempBlock.getType().equals(Material.WARPED_FENCE_GATE)){
                    tempBlock.setType(tempBlock.getType());
                }else {
                    if (clickedBlock.getBiome().equals(Biome.SNOWY_BEACH) || clickedBlock.getBiome().equals(Biome.SNOWY_SLOPES) ||
                            clickedBlock.getBiome().equals(Biome.SNOWY_TAIGA) || clickedBlock.getBiome().equals(Biome.SNOWY_PLAINS) ||
                            clickedBlock.getBiome().equals(Biome.FROZEN_OCEAN) || clickedBlock.getBiome().equals(Biome.FROZEN_PEAKS) ||
                            clickedBlock.getBiome().equals(Biome.FROZEN_RIVER) || clickedBlock.getBiome().equals(Biome.DEEP_FROZEN_OCEAN) ||
                            clickedBlock.getBiome().equals(Biome.ICE_SPIKES)) {
                        tempBlock.getLocation().getBlock().setType(Material.OBSIDIAN);
                    } else {
                        tempBlock.getLocation().getBlock().setType(Material.PACKED_ICE);
                    }
                }
            } else {
                if (tempBlock.getType().equals(Material.ACACIA_FENCE) ||
                    tempBlock.getType().equals(Material.BIRCH_FENCE) ||
                    tempBlock.getType().equals(Material.OAK_FENCE) ||
                    tempBlock.getType().equals(Material.SPRUCE_FENCE) ||
                    tempBlock.getType().equals(Material.WARPED_FENCE) ||
                    tempBlock.getType().equals(Material.DARK_OAK_FENCE) ||
                    tempBlock.getType().equals(Material.CRIMSON_FENCE) ||
                    tempBlock.getType().equals(Material.JUNGLE_FENCE) ||
                    //-.-.-.-.-.-.-.-           #Sepparation between fences & fence-gates
                    tempBlock.getType().equals(Material.BIRCH_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.ACACIA_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.CRIMSON_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.DARK_OAK_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.JUNGLE_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.OAK_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.SPRUCE_FENCE_GATE) ||
                    tempBlock.getType().equals(Material.WARPED_FENCE_GATE)){
                        tempBlock.setType(tempBlock.getType());
                }else{
                    if (clickedBlock.getBiome().equals(Biome.SNOWY_BEACH) || clickedBlock.getBiome().equals(Biome.SNOWY_SLOPES) ||
                            clickedBlock.getBiome().equals(Biome.SNOWY_TAIGA) || clickedBlock.getBiome().equals(Biome.SNOWY_PLAINS) ||
                            clickedBlock.getBiome().equals(Biome.FROZEN_OCEAN) || clickedBlock.getBiome().equals(Biome.FROZEN_PEAKS) ||
                            clickedBlock.getBiome().equals(Biome.FROZEN_RIVER) || clickedBlock.getBiome().equals(Biome.DEEP_FROZEN_OCEAN) ||
                            clickedBlock.getBiome().equals(Biome.ICE_SPIKES)) {
                        tempBlock.getLocation().getBlock().setType(Material.COAL_BLOCK);
                    } else {
                        tempBlock.getLocation().getBlock().setType(Material.SNOW_BLOCK);
                    }
                }
            }
        }
        if (status){
            ownerCheck.setBlockList(blockList);
            ownerCheck.setMaterialList(materialList);
        }else{
            claimCreation.setBlockList(blockList);
            claimCreation.setMaterialList(materialList);
        }
    }

    public ArrayList<Block> getBlocksForDisplay(Block prevBlock, Block clickedBlock, ArrayList<Block> blockList){
        Block clickedBlockNextX; Block clickedBlockNextZ; Block prevBlockNextX; Block prevBlockNextZ;
        Block cornerBlockBot; Block cornerBlockBotNextX; Block cornerBlockBotNextZ;
        Block cornerBlockTop; Block cornerBlockTopNextX; Block cornerBlockTopNextZ;
        if (prevBlock.getX() < clickedBlock.getX()){
            if (prevBlock.getZ() < clickedBlock.getZ()){
                clickedBlockNextX = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX()-1, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                clickedBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), clickedBlock.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextX = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX()+1, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), prevBlock.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBot = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX()+1, cornerBlockBot.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX(), cornerBlockBot.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTop = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX()-1, cornerBlockTop.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX(),cornerBlockTop.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                for (int i = prevBlock.getX()+4; i < clickedBlock.getX()-4; i = i+8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(i, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(i, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
                for (int i = prevBlock.getZ()+4; i < clickedBlock.getZ()-4; i = i+8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
            }else{
                clickedBlockNextX = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX()-1, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                clickedBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), clickedBlock.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextX = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX()+1, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), prevBlock.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBot = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX()+1, cornerBlockBot.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX(), cornerBlockBot.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTop = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX()-1, cornerBlockTop.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX(), cornerBlockTop.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                for (int i = prevBlock.getX()+4; i < clickedBlock.getX()-4; i = i+8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(i, prevBlock.getZ());
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(i, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
                for (int i = prevBlock.getZ()-4; i > clickedBlock.getZ()+4; i = i-8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
            }
        }else{
            if (prevBlock.getZ() < clickedBlock.getZ()) {
                clickedBlockNextX = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX()+1, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                clickedBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), clickedBlock.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextX = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX()-1, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), prevBlock.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBot = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX()+1, cornerBlockBot.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX(), cornerBlockBot.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTop = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX()-1, cornerBlockTop.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX(), cornerBlockTop.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                for (int i = prevBlock.getX()-4; i > clickedBlock.getX()+4; i = i-8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(i, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(i, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
                for (int i = prevBlock.getZ()+4; i < clickedBlock.getZ()-4; i = i+8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
            }else{
                clickedBlockNextX = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX()+1, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                clickedBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), clickedBlock.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextX = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX()-1, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                prevBlockNextZ = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), prevBlock.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBot = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX()+1, cornerBlockBot.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockBotNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockBot.getX(), cornerBlockBot.getZ()-1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTop = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextX = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX()-1, cornerBlockTop.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                cornerBlockTopNextZ = clickedBlock.getWorld().getHighestBlockAt(cornerBlockTop.getX(), cornerBlockTop.getZ()+1, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                for (int i = prevBlock.getX()-4; i > clickedBlock.getX()+4; i = i-8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(i, prevBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(i, clickedBlock.getZ(), HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
                for (int i = prevBlock.getZ()-4; i > clickedBlock.getZ()+4; i = i-8){
                    Block tempBlockOnPrev = clickedBlock.getWorld().getHighestBlockAt(prevBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnPrev);
                    Block tempBlockOnClick = clickedBlock.getWorld().getHighestBlockAt(clickedBlock.getX(), i, HeightMap.MOTION_BLOCKING_NO_LEAVES);
                    blockList.add(tempBlockOnClick);
                }
            }
        }
        blockList.add(prevBlock); blockList.add(clickedBlockNextX); blockList.add(clickedBlockNextZ);
        blockList.add(clickedBlock); blockList.add(prevBlockNextX); blockList.add(prevBlockNextZ);
        blockList.add(cornerBlockBot); blockList.add(cornerBlockBotNextX); blockList.add(cornerBlockBotNextZ); blockList.add(cornerBlockTop);
        blockList.add(cornerBlockTopNextX); blockList.add(cornerBlockTopNextZ);
        return blockList;
    }


}
