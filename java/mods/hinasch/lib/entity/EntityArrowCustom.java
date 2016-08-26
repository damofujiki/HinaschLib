package mods.hinasch.lib.entity;
//package com.hinasch.lib.base;
//
//import java.util.List;
//
//import com.google.common.base.Function;
//import com.hinasch.lib.WorldHelper;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.IProjectile;
//import net.minecraft.entity.monster.EntityEnderman;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
///**
// * ちゃんとEntityArrowを継承して作った矢クラス。
// * 矢はこれを使う。
// * canPickupまわりがちょと未完成。
// *
// *
// */
//public class EntityArrowCustom extends EntityArrow implements IProjectile{
//
//    private int inData;
//    private int knockbackStrength;
//	protected boolean inGround = false;
//    private Block field_145790_g;
//    private int field_145791_d = -1;
//    private int field_145792_e = -1;
//    private int field_145789_f = -1;
//	protected int ticksInAir;
//	protected int ticksInGround;
//	protected boolean isPenetrate = false;
//    public EntityArrow.PickupStatus canBePickedUp;
////    public int canBePickedUp;
//
//
//	public EntityArrowCustom(World p_i1753_1_) {
//		super(p_i1753_1_);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//    public EntityArrowCustom(World w, EntityLivingBase el, float f)
//    {
//        super(w,el,f);
//        this.renderDistanceWeight = 10.0D;
//        this.shootingEntity = el;
//
//        if (el instanceof EntityPlayer)
//        {
//            this.canBePickedUp = EntityArrow.PickupStatus.ALLOWED;;
//        }
//
//        this.setSize(0.5F, 0.5F);
//        this.setLocationAndAngles(el.posX, el.posY + (double)el.getEyeHeight(), el.posZ, el.rotationYaw, el.rotationPitch);
//        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
//        this.posY -= 0.10000000149011612D;
//        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
//        this.setPosition(this.posX, this.posY, this.posZ);
//        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
//        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
//        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
//        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, f * 1.5F, 1.0F);
//    }
//
//    @Override
//    public void onUpdate()
//    {
//        super.onEntityUpdate();
//
//        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
//        {
//            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
//            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
//            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
//        }
//
//        BlockPos blockpos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
//        IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
//        Block block = iblockstate.getBlock();
//        if (block.getMaterial() != Material.air)
//        {
//            block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
//            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(this.worldObj, blockpos, iblockstate);
//
//            if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3(this.posX, this.posY, this.posZ)))
//            {
//                this.inGround = true;
//            }
//        }
//
//        if (this.arrowShake > 0)
//        {
//            --this.arrowShake;
//        }
//
//        if (this.inGround)
//        {
//           int j = block.getMetaFromState(worldObj.getBlockState(blockpos));
//
//            if (block == this.field_145790_g && j == this.inData)
//            {
//                ++this.ticksInGround;
//
//                if (this.ticksInGround == 1200)
//                {
//                    this.setDead();
//                }
//            }
//            else
//            {
//                this.inGround = false;
//                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
//                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
//                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
//                this.ticksInGround = 0;
//                this.ticksInAir = 0;
//            }
//        }
//        else
//        {
//            ++this.ticksInAir;
//            Vec3 vec31 = new Vec3(this.posX, this.posY, this.posZ);
//            Vec3 vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
//            vec31 = new Vec3(this.posX, this.posY, this.posZ);
//            vec3 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//
//            if (movingobjectposition != null)
//            {
//                vec3 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
//            }
//
//            Entity entity = null;
//            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
//            double d0 = 0.0D;
//            int i;
//            float f1;
//
//            for (i = 0; i < list.size(); ++i)
//            {
//                Entity entity1 = (Entity)list.get(i);
//
//                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
//                {
//                    f1 = 0.3F;
//                    AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
//                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
//
//                    if (movingobjectposition1 != null)
//                    {
//                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
//
//                        if (d1 < d0 || d0 == 0.0D)
//                        {
//                            entity = entity1;
//                            d0 = d1;
//                        }
//                    }
//                }
//            }
//
//            if (entity != null)
//            {
//                movingobjectposition = new MovingObjectPosition(entity);
//            }
//
//            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
//            {
//                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
//
//                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
//                {
//                    movingobjectposition = null;
//                }
//            }
//
//            float f2;
//            float f4;
//
//            if (movingobjectposition != null)
//            {
//                if (movingobjectposition.entityHit != null)
//                {
//                	this.onPreEntityHit(movingobjectposition);
//                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//                    int k = MathHelper.ceiling_double_int((double)f2 * this.getDamage());
//
//                    if (this.getIsCritical())
//                    {
//                        k += this.rand.nextInt(k / 2 + 2);
//                    }
//
//                    DamageSource damagesource = new Function<EntityArrowCustom,DamageSource>(){
//
//
//
//						@Override
//						public DamageSource apply(EntityArrowCustom input) {
//							DamageSource base = null;
//		                    if (input.shootingEntity == null)
//		                    {
//		                    	base = DamageSource.causeArrowDamage(input, input);
//		                    }
//		                    else
//		                    {
//		                    	base = DamageSource.causeArrowDamage(input, input.shootingEntity);
//		                    }
//							return base;
//						}
//					}.apply(this);
//
//
//
//                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
//                    {
//                        movingobjectposition.entityHit.setFire(5);
//                    }
//
//                    if (movingobjectposition.entityHit.attackEntityFrom(this.getModdedDamageSource(damagesource,movingobjectposition.entityHit), (float)k))
//                    {
//                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
//                        {
//                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
//
//                            if (WorldHelper.isServer(worldObj))
//                            {
//                                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
//                            }
//
//                            /** 的をノックバック*/
//                            if (this.knockbackStrength > 0)
//                            {
//                                f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
//
//                                if (f4 > 0.0F)
//                                {
//                                    movingobjectposition.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4);
//                                }
//                            }
//
//                            /** エンチャ関係？*/
//                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
//                            {
//                                EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
//                                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
//                            }
//
//                            /** なんかパケット送ってる？ */
//                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
//                            {
//                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
//                            }
//                        }
//
//                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
//                        this.onPostEntityHit(movingobjectposition);
//
//                        /** エンダーマン以外なら当たると矢は消える*/
//                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
//                        {
//                        	if(!this.isPenetrate){
//                                this.setDead();
//                        	}
//
//                        }
//                    }
//                    else
//                    {
//                        this.motionX *= -0.10000000149011612D;
//                        this.motionY *= -0.10000000149011612D;
//                        this.motionZ *= -0.10000000149011612D;
//                        this.rotationYaw += 180.0F;
//                        this.prevRotationYaw += 180.0F;
//                        this.ticksInAir = 0;
//                    }
//                } /** あたったのがブロックの場合*/
//                else
//                {
//                    this.field_145791_d = movingobjectposition.getBlockPos().getX();
//                    this.field_145792_e = movingobjectposition.getBlockPos().getY();
//                    this.field_145789_f = movingobjectposition.getBlockPos().getZ();
//                    this.field_145790_g = this.worldObj.getBlockState(blockpos).getBlock();
//                    this.inData = block.getMetaFromState(worldObj.getBlockState(blockpos));
//                    this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
//                    this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
//                    this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
//                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
//                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
//                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
//                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
//                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
//                    this.inGround = true;
//                    this.arrowShake = 7;
//                    this.setIsCritical(false);
//
//                    /** 空気以外ならブロック衝突判定へ*/
//                    if (this.field_145790_g.getMaterial() != Material.air)
//                    {
//                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj,blockpos, iblockstate, this);
//                    }
//                }
//            }
//
//            if (this.getIsCritical())
//            {
//                for (i = 0; i < 4; ++i)
//                {
//                    this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double)i / 4.0D, this.posY + this.motionY * (double)i / 4.0D, this.posZ + this.motionZ * (double)i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
//                }
//            }
//
//            this.posX += this.motionX;
//            this.posY += this.motionY;
//            this.posZ += this.motionZ;
//            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
//            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
//
//            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//            {
//                ;
//            }
//
//            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
//            {
//                this.prevRotationPitch += 360.0F;
//            }
//
//            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
//            {
//                this.prevRotationYaw -= 360.0F;
//            }
//
//            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
//            {
//                this.prevRotationYaw += 360.0F;
//            }
//
//            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
//            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
//            float f3 = 0.99F;
//            f1 = 0.05F;
//
//            if (this.isInWater())
//            {
//                for (int l = 0; l < 4; ++l)
//                {
//                    f4 = 0.25F;
//                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
//                }
//
//                f3 = 0.8F;
//            }
//
//            /** 濡れると火は消す*/
//            if (this.isWet())
//            {
//                this.extinguish();
//            }
//
//            this.motionX *= (double)f3;
//            this.motionY *= (double)f3;
//            this.motionZ *= (double)f3;
//            this.motionY -= (double)f1;
//            this.setPosition(this.posX, this.posY, this.posZ);
//            this.doBlockCollisions();
//        }
//
//
//    }
//
//
//    public DamageSource getModdedDamageSource(DamageSource source,Entity entityHit){
//    	return source;
//    }
//    @Override
//    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
//    {
//        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
//        p_70186_1_ /= (double)f2;
//        p_70186_3_ /= (double)f2;
//        p_70186_5_ /= (double)f2;
//        p_70186_1_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
//        p_70186_3_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
//        p_70186_5_ += this.rand.nextGaussian() * (double)(this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double)p_70186_8_;
//        p_70186_1_ *= (double)p_70186_7_;
//        p_70186_3_ *= (double)p_70186_7_;
//        p_70186_5_ *= (double)p_70186_7_;
//        this.motionX = p_70186_1_;
//        this.motionY = p_70186_3_;
//        this.motionZ = p_70186_5_;
//        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
//        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
//        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);
//        this.ticksInGround = 0;
//    }
//
//    /**
//     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
//     * posY, posZ, yaw, pitch
//     */
//    @SideOnly(Side.CLIENT)
//    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
//    {
//        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
//        this.setRotation(p_70056_7_, p_70056_8_);
//    }
//
//    /**
//     * Sets the velocity to the args. Args: x, y, z
//     */
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
//    {
//        this.motionX = p_70016_1_;
//        this.motionY = p_70016_3_;
//        this.motionZ = p_70016_5_;
//
//        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
//        {
//            float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
//            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
//            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70016_3_, (double)f) * 180.0D / Math.PI);
//            this.prevRotationPitch = this.rotationPitch;
//            this.prevRotationYaw = this.rotationYaw;
//            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
//            this.ticksInGround = 0;
//        }
//    }
//
//    @Override
//    public void writeEntityToNBT(NBTTagCompound nbt)
//    {
//        nbt.setShort("xTile", (short)this.field_145791_d);
//        nbt.setShort("yTile", (short)this.field_145792_e);
//        nbt.setShort("zTile", (short)this.field_145789_f);
//        nbt.setShort("life", (short)this.ticksInGround);
//        nbt.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
//        nbt.setByte("inData", (byte)this.inData);
//        nbt.setByte("shake", (byte)this.arrowShake);
//        nbt.setByte("inGround", (byte)(this.inGround ? 1 : 0));
//        nbt.setByte("pickup", (byte)this.canBePickedUp);
//        nbt.setDouble("damage", this.getDamage());
//    }
//
//    /**
//     * (abstract) Protected helper met
//     * hod to read subclass entity data from NBT.
//     */
//    @Override
//    public void readEntityFromNBT(NBTTagCompound nbt)
//    {
//        this.field_145791_d = nbt.getShort("xTile");
//        this.field_145792_e = nbt.getShort("yTile");
//        this.field_145789_f = nbt.getShort("zTile");
//        this.ticksInGround = nbt.getShort("life");
//        this.field_145790_g = Block.getBlockById(nbt.getByte("inTile") & 255);
//        this.inData = nbt.getByte("inData") & 255;
//        this.arrowShake = nbt.getByte("shake") & 255;
//        this.inGround = nbt.getByte("inGround") == 1;
//
//
//        if (nbt.hasKey("damage", 99))
//        {
//        	this.setDamage( nbt.getDouble("damage"));
//        }
//
//        if (nbt.hasKey("pickup", 99))
//        {
//            this.canBePickedUp = nbt.getByte("pickup");
//        }
//        else if (nbt.hasKey("player", 99))
//        {
//            this.canBePickedUp = nbt.getBoolean("player") ? 1 : 0;
//        }
//    }
//    @Override
//    public void setKnockbackStrength(int p_70240_1_)
//    {
//        this.knockbackStrength = p_70240_1_;
//    }
//
//    public boolean isInGround(){
//    	return this.inGround;
//    }
//
//    public int getTicksInGround(){
//    	return this.ticksInGround;
//    }
//
//    public void onPreEntityHit(MovingObjectPosition mop){
//
//    }
//    public void onPostEntityHit(MovingObjectPosition mop){
//
//    }
//
//    @Override
//    public void onCollideWithPlayer(EntityPlayer entityIn)
//    {
//        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
//        {
//            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && entityIn.capabilities.isCreativeMode;
//
//            if (this.canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1)))
//            {
//                flag = false;
//            }
//
//            if (flag)
//            {
//                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
//                entityIn.onItemPickup(this, 1);
//                this.setDead();
//            }
//        }
//    }
//}
