//package mods.hinasch.lib.entity;
//
//import java.util.List;
//import java.util.UUID;
//
//import mods.hinasch.lib.world.XYZPos;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.EnumPushReaction;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.IProjectile;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityThrowable;
//import net.minecraft.init.Blocks;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//
//public class EntityThrowableBaseOld extends EntityThrowable implements IProjectile{
//
////	private static final DataParameter<Float> DRIVE = EntityDataManager.<Float>createKey(EntityThrowableBase.class, DataSerializers.FLOAT);
////	protected final int DRIVE = 20;
//	protected float damage = 2.0F;
//	protected int knockbackModifier = 0;
//
//	//こっから追加
//    private EntityLivingBase thrower;
//    private String throwerName;
//	private int xTile;
//	private int yTile;
//	private int zTile;
//	private Block inTile;
//	private int ticksInGround;
//	private int ticksInAir;
//	private int field_184540_av;
//
//
//	public EntityThrowableBaseOld(World par1World) {
//
//		super(par1World);
//        this.xTile = -1;
//        this.yTile = -1;
//        this.zTile = -1;
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//	public EntityThrowableBaseOld(World par1World, EntityLivingBase par2EntityLiving, float par3)
//	{
//		super(par1World,par2EntityLiving);
////		this.setDrive(par3);
//
//	}
//
//    public EntityThrowableBaseOld(World p_i1777_1_, EntityLivingBase p_i1777_2_)
//    {
//    	super(p_i1777_1_,p_i1777_2_);
//    }
//    public EntityThrowableBaseOld(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
//    {
//        super(par1World,par2EntityLivingBase);
//
//        this.posY = par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() - 0.10000000149011612D;
//        double d0 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
//        double d1 = par3EntityLivingBase.getEntityBoundingBox().minY + (double)(par3EntityLivingBase.height / 3.0F) - this.posY;
//        double d2 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
//        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
//
//        if (d3 >= 1.0E-7D)
//        {
//            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
//            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
//            double d4 = d0 / d3;
//            double d5 = d2 / d3;
//            this.setLocationAndAngles(par2EntityLivingBase.posX + d4, this.posY, par2EntityLivingBase.posZ + d5, f2, f3);
////            this.yOffset = 0.0F;
//            float f4 = (float)d3 * 0.2F;
//            this.setThrowableHeading(d0, d1 + (double)f4, d2, par4, par5);
//        }
//    }
//
//    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
//    {
//        float f = MathHelper.sqrt_double(x * x + y * y + z * z);
//        x = x / (double)f;
//        y = y / (double)f;
//        z = z / (double)f;
//        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
//        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
//        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
//        x = x * (double)velocity;
//        y = y * (double)velocity;
//        z = z * (double)velocity;
//        this.motionX = x;
//        this.motionY = y;
//        this.motionZ = z;
//        float f1 = MathHelper.sqrt_double(x * x + z * z);
//        this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
//        this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
//        this.ticksInGround = 0;
//    }
//
//    @Override
//    public EntityLivingBase getThrower()
//    {
//        if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty())
//        {
//            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
//
//            if (this.thrower == null && this.worldObj instanceof WorldServer)
//            {
//                try
//                {
//                    Entity entity = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
//
//                    if (entity instanceof EntityLivingBase)
//                    {
//                        this.thrower = (EntityLivingBase)entity;
//                    }
//                }
//                catch (Throwable var2)
//                {
//                    this.thrower = null;
//                }
//            }
//        }
//
//        return this.thrower;
//    }
//
//	@Override
//	protected void onImpact(RayTraceResult var1) {
//
//
//	}
//
//	@Override
//    public void onUpdate()
//    {
//        this.lastTickPosX = this.posX;
//        this.lastTickPosY = this.posY;
//        this.lastTickPosZ = this.posZ;
//        super.onUpdate();
//
//        if (this.throwableShake > 0)
//        {
//            --this.throwableShake;
//        }
//
//        if (this.inGround)
//        {
//            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile)
//            {
//                ++this.ticksInGround;
//
//                if (this.ticksInGround == 1200)
//                {
//                    this.setDead();
//                }
//
//                return;
//            }
//
//            this.inGround = false;
//            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
//            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
//            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
//            this.ticksInGround = 0;
//            this.ticksInAir = 0;
//        }
//        else
//        {
//            ++this.ticksInAir;
//        }
//
//        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
//        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//        RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(vec3d, vec3d1);
//        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
//        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
//
//        if (raytraceresult != null)
//        {
//            vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
//        }
//
//        Entity entity = null;
//        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
//        double d0 = 0.0D;
//        boolean flag = false;
//
//        for (int i = 0; i < list.size(); ++i)
//        {
//            Entity entity1 = (Entity)list.get(i);
//
//            if (entity1.canBeCollidedWith())
//            {
//                if (entity1 == this.field_184539_c)
//                {
//                    flag = true;
//                }
//                else if (this.ticksExisted < 2 && this.field_184539_c == null)
//                {
//                    this.field_184539_c = entity1;
//                    flag = true;
//                }
//                else
//                {
//                    flag = false;
//                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
//                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
//
//                    if (raytraceresult1 != null)
//                    {
//                        double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
//
//                        if (d1 < d0 || d0 == 0.0D)
//                        {
//                            entity = entity1;
//                            d0 = d1;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (this.field_184539_c != null)
//        {
//            if (flag)
//            {
//                this.field_184540_av = 2;
//            }
//            else if (this.field_184540_av-- <= 0)
//            {
//                this.field_184539_c = null;
//            }
//        }
//
//        if (entity != null)
//        {
//            raytraceresult = new RayTraceResult(entity);
//        }
//
//        if (raytraceresult != null)
//        {
//           this.preHit(raytraceresult); //fix
//        }
//
//        this.posX += this.motionX;
//        this.posY += this.motionY;
//        this.posZ += this.motionZ;
//        float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
//        this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
//
//        for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
//        {
//            ;
//        }
//
//        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
//        {
//            this.prevRotationPitch += 360.0F;
//        }
//
//        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
//        {
//            this.prevRotationYaw -= 360.0F;
//        }
//
//        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
//        {
//            this.prevRotationYaw += 360.0F;
//        }
//
//        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
//        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
//        float f1 = 0.99F;
//        float f2 = this.getGravityVelocity();
//
//        if (this.isInWater())
//        {
//            for (int j = 0; j < 4; ++j)
//            {
//                float f3 = 0.25F;
//                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ, new int[0]);
//            }
//
//            f1 = 0.8F;
//        }
//
//        this.motionX *= (double)f1;
//        this.motionY *= (double)f1;
//        this.motionZ *= (double)f1;
//        this.motionY -= (double)f2;
//        this.setPosition(this.posX, this.posY, this.posZ);
//    }
//
////	@Override
////    protected float getVelocity()
////    {
////        return this.getDrive() * 1.5F;
////    }
//
//    public boolean preHit(RayTraceResult raytraceresult){
//        if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.worldObj.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.portal)
//        {
//            this.setPortal(raytraceresult.getBlockPos());
//            return true;
//        }
//        else
//        {
//        	if(raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && worldObj.getBlockState(raytraceresult.getBlockPos()).getMaterial().getMobilityFlag()==EnumPushReaction.DESTROY){
//
//        		return false;
//        	}
//            this.onImpact(raytraceresult);
//            return true;
//        }
//    }
//	public void setDamage(float par1){
//		this.damage = par1;
//	}
//
//	public float getDamage(){
//		return this.damage;
//	}
//
//	public void setKnockBackModifier(int par1){
//		this.knockbackModifier = par1;
//	}
//
//	public int getKnockBackModifier(){
//		return this.knockbackModifier;
//	}
//	@Override
//	protected void entityInit()
//	{
////		this.dataWatcher.register(DRIVE, (float)20.0F);
////		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
////		this.getDataWatcher().addObjectByDataType(DRIVE, 3);
////		this.getDataWatcher().updateObject(DRIVE, (Float)1.0F);
//	}
//
//
//
//
////	protected void setDrive(float par1){
////		this.dataWatcher.set(DRIVE, (float)par1);
//////		this.getDataWatcher().updateObject(DRIVE, (Float)par1);
//////		this.getDataWatcher().setObjectWatched(DRIVE);
////	}
////
////	protected float getDrive(){
////		float f = this.dataWatcher.get(DRIVE);
////		return f;
////	}
//
//	public void writeEntityToNBT(NBTTagCompound tagCompound)
//	{
//		//super.writeEntityToNBT(par1NBTTagCompound);
//		tagCompound.setFloat("damage.HP", this.getDamage());
////		par1NBTTagCompound.setFloat("drive",this.getDrive());
//		tagCompound.setInteger("knockbackModifier", this.getKnockBackModifier());
//
//
//		//こっから元のデータ
//        tagCompound.setInteger("xTile", this.xTile);
//        tagCompound.setInteger("yTile", this.yTile);
//        tagCompound.setInteger("zTile", this.zTile);
//        ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
//        tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
//        tagCompound.setByte("shake", (byte)this.throwableShake);
//        tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
//
//        if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer)
//        {
//            this.throwerName = this.thrower.getName();
//        }
//
//        tagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
//	}
//
//	public void readEntityFromNBT(NBTTagCompound tagCompund)
//	{
//
////		super.readEntityFromNBT(tagCompund);
//		this.setDamage(tagCompund.getFloat("damage.HP"));
////		this.setDrive(par1NBTTagCompound.getFloat("drive"));
//		this.setKnockBackModifier(tagCompund.getInteger("knockbackModifier"));
//
//		//こっから
//        this.xTile = tagCompund.getInteger("xTile");
//        this.yTile = tagCompund.getInteger("yTile");
//        this.zTile = tagCompund.getInteger("zTile");
//
//        if (tagCompund.hasKey("inTile", 8))
//        {
//            this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
//        }
//        else
//        {
//            this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
//        }
//
//        this.throwableShake = tagCompund.getByte("shake") & 255;
//        this.inGround = tagCompund.getByte("inGround") == 1;
//        this.thrower = null;
//        this.throwerName = tagCompund.getString("ownerName");
//
//        if (this.throwerName != null && this.throwerName.isEmpty())
//        {
//            this.throwerName = null;
//        }
//
//        this.thrower = this.getThrower();
//	}
//
//	public boolean checkThrough(RayTraceResult var1){
//		if(var1.typeOfHit==RayTraceResult.Type.BLOCK){
//			XYZPos pos = XYZPos.createFrom(this);
//			IBlockState state = this.worldObj.getBlockState(pos);
//			Block block =state.getBlock();
//			if(block.getCollisionBoundingBox(state, worldObj,pos)==null){
//				return true;
//			}
//		}
//		return false;
//	}
//}
