package mods.hinasch.lib;

import java.util.Collection;
import java.util.Random;

import mods.hinasch.lib.item.ItemCustomEntityEgg.ISpawnOption;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.primitive.LogWrapper;
import mods.hinasch.lib.primitive.Triplet;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class CustomDropEvent {

	/** EntiytもしくはItemStack,Block*/
	public Triplet<Block,Entity,ItemStack> droppable;
	public String logIdentifier;
	public LogWrapper logger;

	public CustomDropEvent(){
		this.droppable = null;
	}

	public CustomDropEvent(Block t){
		this.droppable = Triplet.of(t, null, null);
	}
	public CustomDropEvent(Entity t){
		this.droppable = Triplet.of(null, t, null);
	}
	public CustomDropEvent(ItemStack t){
		this.droppable = Triplet.of(null, null, t);
	}
	public boolean canDrop(EntityLivingBase entityDrops,int looting){
		return false;
	}

	public int getDropValue(EntityLivingBase entityDrops,int looting){
		return 0;
	}

	public Triplet<Block,Entity,ItemStack> getDroppable(LivingDropsEvent e){

		if(this.droppable!=null){
			return this.droppable;
		}
		return Triplet.of(this.getBlock(e), this.getEntity(e), this.getItemStack(e));
	}

	public Entity getEntity(LivingDropsEvent e){
		return null;
	}

	public ItemStack getItemStack(LivingDropsEvent e){
		return null;
	}

	public Block getBlock(LivingDropsEvent e){
		return null;
	}
	public void drop(LivingDropsEvent e){
		EntityLivingBase entityDropped = e.getEntityLiving();
		Random rand = entityDropped.getRNG();
		World world = entityDropped.getEntityWorld();
		XYZPos positionDeath = XYZPos.createFrom(entityDropped);
		//WorldHelper wh = new WorldHelper(world);

//		T var1 = this.getDroppable(e);
		this.debug(positionDeath);

		if(this.getDroppable(e)==null)return;

		if(this.getDroppable(e).first!=null){
			this.debug("これはBlock");
			if(WorldHelper.isServer(world)){
				if(world.isAirBlock(positionDeath)){

					world.setBlockState(positionDeath,((Block) this.getDroppable(e).first).getDefaultState());
				}
			}
		}
		if(this.getDroppable(e).second!=null){
			this.debug("これはentity");
			Entity droppingEntity = this.getDroppable(e).second;
			droppingEntity.setPosition(positionDeath.dx, positionDeath.dy, positionDeath.dz);
			this.debug(droppingEntity);
			if(droppingEntity instanceof ISpawnOption){
				droppingEntity = ((ISpawnOption)droppingEntity).preSpawn(e.getEntityLiving().getEntityWorld(), droppingEntity, positionDeath);
			}
			if(WorldHelper.isServer(world)){
				world.spawnEntityInWorld(droppingEntity);

			}
		}
		if(this.getDroppable(e).third()!=null){
				this.debug("これはItemStack");
				ItemStack dropStack = (ItemStack)this.getDroppable(e).third();
				e.getDrops().add(ItemUtil.getEntityItem(dropStack, positionDeath, world));

		}
	}

	public int getSlimeSize(EntityLivingBase living){
		if(living instanceof EntitySlime){
			return ((EntitySlime) living).getSlimeSize();
		}
//		if(living instanceof EntityTreasureSlime){
//			return 2;
//		}
		return -1;
	}

	public void debug(Object... par1){
		if(this.logger!=null){
			this.logger.trace(this.logIdentifier,par1);
		}
	}

	public CustomDropEvent setLogger(LogWrapper d,String identifi){
		this.logger = d;
		this.logIdentifier = identifi;
		return this;
	}
	public static void processDrop(final LivingDropsEvent e,Collection<CustomDropEvent> drops){
		final EntityLivingBase entityToDrop = e.getEntityLiving();
		Random rand = entityToDrop.getRNG();
		World world = entityToDrop.worldObj;
		XYZPos positionDeath = XYZPos.createFrom(entityToDrop);
		//WorldHelper wh = new WorldHelper(world);
		final int prob = rand.nextInt(100);

		if(drops!=null && e.getSource().getEntity() instanceof EntityLivingBase && isKilledByPlayer((EntityLivingBase) e.getSource().getEntity())){

			drops.stream().filter(dropEvent -> dropEvent.canDrop(entityToDrop, e.getLootingLevel()))
			.filter(dropEvent ->dropEvent.getDropValue(entityToDrop, e.getLootingLevel())>prob)
			.forEach(dropEvent -> dropEvent.drop(e));
		}
	}


	public static boolean isKilledByPlayer(EntityLivingBase attacker){
		if(attacker instanceof EntityPlayer){
			return true;
		}
		if(attacker instanceof EntityTameable){
			if(((EntityTameable) attacker).isTamed()){
				return true;
			}
		}
		return false;
	}
}
