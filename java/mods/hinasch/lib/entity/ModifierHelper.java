package mods.hinasch.lib.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ModifierHelper {

	/**
	 *
	 * @param uuid UUID。fromStringと生成ソフトとかであらかじめ作っておく
	 * @param name 名前。なんでもいい
	 * @param amount 効果量。オペレーションでかわる
	 * @param operation オペレーションの種類。Staticsに定数を用意してるのでそれ使う
	 * @return
	 */
	public static AttributeModifier getNewAttributeModifier(UUID uuid,String name,double amount,int operation){
		return new AttributeModifier(uuid,name,amount,operation);
	}
	/**
	 * 正常でない？
	 * @param living
	 * @param attribute
	 * @param modifier
	 * @return
	 */
	public static boolean hasModifier(EntityLivingBase living,IAttribute attribute,AttributeModifier modifier){
		if(living.getEntityAttribute(attribute)!=null){
			return living.getEntityAttribute(attribute).getModifier(modifier.getID())!=null;
		}
		return false;
	}

	@Deprecated
	public static void removeModifier(ItemStack is,IAttribute attribute,AttributeModifier modifier,EntityEquipmentSlot slot){
		if(is.getAttributeModifiers(slot).containsEntry(attribute.getAttributeUnlocalizedName(), modifier)){
			is.getAttributeModifiers(slot).remove(attribute.getAttributeUnlocalizedName(), modifier);
		}
	}

	//アイテム側のattibuteの仕様がアレなので触らない
	@Deprecated
	public static void refleshModifier(ItemStack is,IAttribute attribute,AttributeModifier modifier,EntityEquipmentSlot slot){
////		if(is.getAttributeModifiers(slot).containsEntry(attribute.getAttributeUnlocalizedName(), modifier)){
////			removeAttributeModifier(is,attribute.getAttributeUnlocalizedName(),modifier,slot);
////		}
//		if(is.getTagCompound()==null){
//			Multimap<String,AttributeModifier> multimap = is.getItem().getAttributeModifiers(slot, is);
//			multimap.entries().forEach(in ->{
//				is.addAttributeModifier(in.getKey(), in.getValue(), slot);
//			});
//		}
//		is.addAttributeModifier(attribute.getAttributeUnlocalizedName(), modifier,slot);
//		HSLib.logger.trace("modifier helper1", modifier);
//		HSLib.logger.trace("modifier helper2", is.getAttributeModifiers(slot));
	}
	public static void removeModifier(EntityLivingBase living,IAttribute attribute,AttributeModifier modifier){
		if(living.getEntityAttribute(attribute)!=null){
			if(living.getEntityAttribute(attribute).hasModifier(modifier)){
				living.getEntityAttribute(attribute).removeModifier(modifier);
			}
		}
	}
	public static void refleshModifier(EntityLivingBase living,IAttribute atttribute,AttributeModifier modifier){
		if(living.getEntityAttribute(atttribute)==null){
			living.getAttributeMap().registerAttribute(atttribute);
		}

		if(living.getEntityAttribute(atttribute).getModifier(modifier.getID())!=null){
			living.getEntityAttribute(atttribute).removeModifier(modifier.getID());
		}
		living.getEntityAttribute(atttribute).applyModifier(modifier);
	}

//	public static void setAttributeModifier(ItemStack is,Multimap<Pair<String,EntityEquipmentSlot>, AttributeModifier> multimap, EntityEquipmentSlot equipmentSlot){
//
//		NBTTagList tagList = UtilNBT.newTagList();
//		multimap.entries().forEach(in -> {
//			NBTTagCompound tag = SharedMonsterAttributes.writeAttributeModifierToNBT(in.getValue());
//			tag.setString("AttributeName", in.getKey());
//
//			tagList.appendTag(nbt);
//		});
//		Multimap<String, AttributeModifier> multimap;
//
//        if (is.hasTagCompound() && is.getTagCompound().hasKey("AttributeModifiers", 9))
//        {
//            multimap = HashMultimap.<String, AttributeModifier>create();
//            NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
//
//            for (int i = 0; i < nbttaglist.tagCount(); ++i)
//            {
//                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
//                AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
//
//                if (attributemodifier != null && (!nbttagcompound.hasKey("Slot", 8) || nbttagcompound.getString("Slot").equals(equipmentSlot.getName())) && attributemodifier.getID().getLeastSignificantBits() != 0L && attributemodifier.getID().getMostSignificantBits() != 0L)
//                {
//                    multimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
//                }
//            }
//        }
//        else
//        {
//            multimap = this.getItem().getAttributeModifiers(equipmentSlot, this);
//        }
//	}
//    public static void removeAttributeModifier(ItemStack is,String attributeName, AttributeModifier modifier, EntityEquipmentSlot equipmentSlot)
//    {
//    	NBTTagCompound nbt = is.getTagCompound();
//        if (nbt == null)
//        {
//        	nbt = new NBTTagCompound();
//        }
//
//        if (!nbt.hasKey("AttributeModifiers", 9))
//        {
//        	nbt.setTag("AttributeModifiers", new NBTTagList());
//        }
//
//        NBTTagList nbttaglist = nbt.getTagList("AttributeModifiers", 10);
//
//        NBTTagCompound nbttagcompound = SharedMonsterAttributes.writeAttributeModifierToNBT(modifier);
//        nbttagcompound.setString("AttributeName", attributeName);
//
//        UtilNBT.getStream(nbttaglist).forEach(in ->{
//        	AttributeModifier modi = SharedMonsterAttributes.readAttributeModifierFromNBT(in.second());
//        	if(modifier.getID()==modi.getID()){
//        		nbttaglist.removeTag(in.first());
//        	}
//        });
//
//    }
}
